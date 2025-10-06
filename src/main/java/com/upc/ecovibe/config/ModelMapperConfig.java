package com.upc.ecovibe.config;

import com.upc.ecovibe.dtos.ActividadesDiariasDTO;
import com.upc.ecovibe.entities.ActividadesDiarias;
import com.upc.ecovibe.entities.Familia;
import com.upc.ecovibe.entities.InstitucionEducativa;
import com.upc.ecovibe.entities.MiembroFamiliar;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper m = new ModelMapper();

        m.getConfiguration().setSkipNullEnabled(true);

        Converter<Familia, Long> familiaToId = ctx ->
                ctx.getSource() == null ? null : ctx.getSource().getId();

        Converter<MiembroFamiliar, Long> miembroToId = ctx ->
                ctx.getSource() == null ? null : ctx.getSource().getId();

        Converter<InstitucionEducativa, Long> instToId = ctx ->
                ctx.getSource() == null ? null : ctx.getSource().getId();

        m.createTypeMap(ActividadesDiarias.class, ActividadesDiariasDTO.class)
                .addMappings(mp -> {
                    mp.map(src -> src.getUsuario().getId(), ActividadesDiariasDTO::setUsuarioId);
                    mp.using(familiaToId).map(ActividadesDiarias::getFamilia, ActividadesDiariasDTO::setFamiliaId);
                    mp.using(instToId).map(ActividadesDiarias::getInstitucion, ActividadesDiariasDTO::setInstitucionId);
                });

        return m;
    }
}
