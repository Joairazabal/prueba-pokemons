package com.jemersoft.tecnica.dto;

import java.util.List;

import lombok.Data;

@Data
public class MoveDTO {
    private SpecieDTO move;
    private List<VersionGroupDetailDTO> versionGroupDetails;
}
