package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.CalculadoraInstitucionDTO;
import com.upc.ecovibe.dtos.CalculadoraPersonalDTO;
import com.upc.ecovibe.dtos.CalculadoraFamiliarDTO;

public interface ICalculadoraService {

    CalculadoraPersonalDTO calcularp(CalculadoraPersonalDTO request);
    // Familiar
    CalculadoraFamiliarDTO calcularf(CalculadoraFamiliarDTO request);

    CalculadoraInstitucionDTO calculari(CalculadoraInstitucionDTO request);
}
