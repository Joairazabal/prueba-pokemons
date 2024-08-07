package com.jemersoft.tecnica.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageDTO<T> {

    private Integer count;

    private String next;

    private String previous;

    private List<T> results;

}
