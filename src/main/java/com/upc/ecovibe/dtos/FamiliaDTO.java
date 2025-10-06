package com.upc.ecovibe.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FamiliaDTO {
    private Long id;
    private String nombre;
    private Long ownerUserId;
}
