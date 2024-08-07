package com.jemersoft.tecnica.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.jemersoft.tecnica.dto.DetailPokemonReceivedDTO;
import com.jemersoft.tecnica.dto.PageDTO;
import com.jemersoft.tecnica.dto.PokemonDTO;
import com.jemersoft.tecnica.dto.PokemonDetailDTO;

@Service
public interface PokemonService {

    public PageDTO<PokemonDTO> getPokemons() throws InterruptedException, ExecutionException;

    public PokemonDetailDTO getPokemonDetails(String name);

}
