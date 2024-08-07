package com.jemersoft.tecnica.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class FlavorTextEntryDTO {
    @JsonAlias("flavor_text")
    private String flavorText;
    private ContentFlavorTextDTO language;
    private ContentFlavorTextDTO version;

}
