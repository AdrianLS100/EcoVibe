package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.CalculadoraInstitucionDTO;
import com.upc.ecovibe.dtos.CalculadoraPersonalDTO;
import com.upc.ecovibe.dtos.CalculadoraFamiliarDTO;

public interface ICalculadoraService {

    CalculadoraPersonalDTO estimar(CalculadoraPersonalDTO request);
    Long guardarComoRegistroDiario(Long usuarioId, CalculadoraPersonalDTO request);

    // Familiar
    CalculadoraFamiliarDTO estimar(CalculadoraFamiliarDTO request);
    Long guardarComoRegistroDiario(Long usuarioId, CalculadoraFamiliarDTO request);

    CalculadoraInstitucionDTO estimar(CalculadoraInstitucionDTO request);
}
