package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.*;
import com.upc.ecovibe.entities.HistorialPuntos;
import com.upc.ecovibe.repositories.HistorialPuntosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GamificacionService {

    private final HistorialPuntosRepository historialRepo;

    public GamificacionService(HistorialPuntosRepository historialRepo) {
        this.historialRepo = historialRepo;
    }


    private static final Map<String, Integer> ACCIONES = Map.of(
            "TRANSPORTE_SOSTENIBLE", 20,
            "REDUCCION_ENERGIA",     15,
            "RECICLAJE",             10,
            "DIETA_SOSTENIBLE",      12
    );

    private record LogroDef(String nombre, int umbral) {}
    private static final List<LogroDef> LOGROS = List.of(
            new LogroDef("Semilla verde", 20),
            new LogroDef("Brotes eco",    60),
            new LogroDef("Árbol joven",   120),
            new LogroDef("Bosque",        250)
    );

    private static final List<RecompensaDTO> RECOMPENSAS = List.of(
            new RecompensaDTO(1, "Cupón 10% tienda eco", "Descuento en productos sostenibles", 50),
            new RecompensaDTO(2, "Botella reutilizable", "Acero inoxidable 500ml",            120),
            new RecompensaDTO(3, "Pack bolsas tela",     "3 bolsas reusables",                 80)
    );


    @Transactional
    public EstadoGamificacionDTO otorgarPuntos(OtorgarPuntoRequest req) {
        Integer pts = ACCIONES.get(req.getCodigoAccion());
        if (pts == null) {
            throw new IllegalArgumentException("Código de acción no válido");
        }

        HistorialPuntos h = new HistorialPuntos();
        h.setUsuarioId(req.getUsuarioId());
        h.setCodigoAccion(req.getCodigoAccion());
        h.setPuntos(pts);
        h.setDetalle(req.getDetalle());
        historialRepo.save(h);

        return estado(req.getUsuarioId(), "Se otorgaron " + pts + " puntos por " + req.getCodigoAccion());
    }

    @Transactional(readOnly = true)
    public EstadoGamificacionDTO estado(Long usuarioId) {
        return estado(usuarioId, null);
    }

    @Transactional(readOnly = true)
    public EstadoGamificacionDTO estado(Long usuarioId, String mensajeExtra) {
        int total = historialRepo.saldoPuntos(usuarioId);

        String actual = "-";
        String siguiente = "-";
        Integer umbralSig = null;
        int base = 0;

        for (var l : LOGROS) {
            if (l.umbral() <= total) {
                actual = l.nombre();
                base = l.umbral();
            } else {
                siguiente = l.nombre();
                umbralSig = l.umbral();
                break;
            }
        }

        int progreso = 0;
        if (umbralSig != null) {
            int rango = umbralSig - base;
            progreso = rango > 0 ? Math.min(100, Math.max(0, (int)Math.round(((total - base) * 100.0) / rango))) : 100;
        } else {
            progreso = LOGROS.isEmpty() ? 0 : 100;
        }

        return EstadoGamificacionDTO.builder()
                .puntosTotales(total)
                .logroActual(actual)
                .siguienteLogro(siguiente)
                .umbralSiguiente(umbralSig)
                .progresoPorcentaje(progreso)
                .mensaje(mensajeExtra)
                .build();
    }

    @Transactional(readOnly = true)
    public List<RecompensaDTO> listarRecompensas() {
        return RECOMPENSAS;
    }

    @Transactional
    public EstadoGamificacionDTO canjear(CanjearRequest req) {
        var recompensa = RECOMPENSAS.stream()
                .filter(r -> r.getId().equals(req.getRecompensaId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Recompensa no existe"));

        int saldo = historialRepo.saldoPuntos(req.getUsuarioId());
        if (saldo < recompensa.getCostoPuntos()) {
            throw new IllegalArgumentException("Puntos insuficientes");
        }

        HistorialPuntos h = new HistorialPuntos();
        h.setUsuarioId(req.getUsuarioId());
        h.setCodigoAccion("CANJE_" + recompensa.getId());
        h.setPuntos(-recompensa.getCostoPuntos());
        h.setDetalle("Canje: " + recompensa.getNombre());
        historialRepo.save(h);

        return estado(req.getUsuarioId(), "Canje confirmado: " + recompensa.getNombre());
    }
}
