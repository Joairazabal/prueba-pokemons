package com.jemersoft.tecnica.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class SpecieDetailDTO {

    @JsonAlias("flavor_text_entries")
    private FlavorTextEntryDTO[] flavorTextEntries;

}
