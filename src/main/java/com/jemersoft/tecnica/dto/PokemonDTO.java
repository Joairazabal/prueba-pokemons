package com.jemersoft.tecnica.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PokemonDTO {

    private String name;
    private String url;
    private long weight;
    private List<String> types;
    private List<String> abilities;
}
