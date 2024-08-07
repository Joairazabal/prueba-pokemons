package com.jemersoft.tecnica.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class OtherDTO {

    @JsonAlias("dream_world")
    private DreamWorldDTO dreamWorld;

}
