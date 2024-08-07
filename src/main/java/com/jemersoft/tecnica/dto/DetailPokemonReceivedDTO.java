package com.jemersoft.tecnica.dto;

import java.util.List;

import lombok.Data;

@Data
public class DetailPokemonReceivedDTO {

    private List<AbilityDTO> abilities;
    private long height;
    private long id;
    private List<MoveDTO> moves;
    private String name;
    private SpecieDTO species;
    private SpritesDTO sprites;
    private List<StatDTO> stats;
    private List<TypeDTO> types;
    private long weight;
}
