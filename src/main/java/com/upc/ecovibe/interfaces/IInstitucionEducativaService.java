package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.security.dtos.InstitucionRegisterRequest;
import com.upc.ecovibe.security.dtos.InstitucionRegisterResponse;

public interface IInstitucionEducativaService {
    InstitucionRegisterResponse registrar(InstitucionRegisterRequest req);
}
