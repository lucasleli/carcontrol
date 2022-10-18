package br.com.lucasleli.carcontrol.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VehicleDto {
    @JsonProperty
    @NotNull
    @NotBlank
    String plate;
}
