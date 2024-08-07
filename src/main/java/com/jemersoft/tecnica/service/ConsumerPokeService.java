package com.jemersoft.tecnica.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jemersoft.tecnica.dto.PageDTO;

@Service
public interface ConsumerPokeService {

    public <T> PageDTO<T> getExternalApiDataWithPages(String url, Map<String, String> uriVariables, Class<T> itemType);

    public <T> T getExternalApiData(String url, Map<String, String> uriVariables,
            Class<T> responseType);

    public String formatUriVariables(Map<String, String> uriVariables, String url);

}
