package com.jemersoft.tecnica.dto;

import lombok.Data;

@Data
public class AbilityDTO {
    private SpecieDTO ability;
    private boolean isHidden;
    private long slot;
}
