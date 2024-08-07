package com.jemersoft.tecnica.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PokemonDetailDTO extends PokemonDTO {
    List<String> moves;
    String description;
}
