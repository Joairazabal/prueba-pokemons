package com.jemersoft.tecnica.service.impl;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemersoft.tecnica.dto.PageDTO;
import com.jemersoft.tecnica.exception.ServerExternalException;
import com.jemersoft.tecnica.service.ConsumerPokeService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class ConsumePokeServiceImpl implements ConsumerPokeService {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Recupera datos paginados desde una API externa y los mapea a un objeto
     * PageDTO.
     *
     * @param url          el endpoint de la URL desde donde se recuperan los datos.
     * @param uriVariables variables URI opcionales para incluir en la solicitud.
     * @param itemType     la clase de los elementos en el PageDTO.
     * @param <T>          el tipo de los elementos en el PageDTO.
     * @return un objeto PageDTO que contiene los datos paginados.
     * @throws ServerExternalException si ocurre un error al recuperar datos de la
     *                                 API externa.
     */
    @Override
    public <T> PageDTO<T> getExternalApiDataWithPages(String url, Map<String, String> uriVariables, Class<T> itemType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Application");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            if (uriVariables != null && uriVariables.get("limit") == null) {
                uriVariables.put("limit", "10");
            }

            String fullUrl = this.formatUriVariables(uriVariables, BASE_URL + url);
            log.info(fullUrl);

            ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class);

            PageDTO<T> page = objectMapper.readValue(response.getBody(),
                    objectMapper.getTypeFactory().constructParametricType(PageDTO.class, itemType));
            return page;
        } catch (Exception e) {
            throw new ServerExternalException("EXT-500", 500, e.getMessage());
        }
    }

    /**
     * Recupera datos desde una API externa y los mapea a un objeto del tipo
     * especificado.
     *
     * @param url          el endpoint de la URL desde donde se recuperan los datos.
     * @param uriVariables variables URI opcionales para incluir en la solicitud.
     * @param responseType la clase del objeto en el que se mapearán los datos.
     * @param <T>          el tipo del objeto de respuesta.
     * @return un objeto del tipo especificado que contiene los datos recuperados.
     * @throws ServerExternalException si ocurre un error al recuperar datos de la
     *                                 API externa.
     */
    @Override
    public <T> T getExternalApiData(String url, Map<String, String> uriVariables, Class<T> responseType) {
        try {
            String fullUrl = this.formatUriVariables(uriVariables, BASE_URL + url);

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Application");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            log.info("Formatted URL: " + fullUrl);
            ResponseEntity<T> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, responseType);
            return response.getBody();
        } catch (Exception e) {
            log.error(url, e);
            throw new ServerExternalException("EXT-500", 500, e.getMessage());
        }
    }

    /**
     * Formatea las variables URI y las agrega a la URL proporcionada.
     *
     * @param uriVariables Un mapa que contiene las variables URI para la solicitud.
     * @param url          La URL base a la que se agregarán las variables URI.
     * @return La URL completa con las variables URI formateadas y agregadas.
     */
    @Override
    public String formatUriVariables(Map<String, String> uriVariables, String url) {
        if (uriVariables != null && !uriVariables.isEmpty()) {
            url += "?" + uriVariables.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));

            return url;
        }
        return url;
    }

}
