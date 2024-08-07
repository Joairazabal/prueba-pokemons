package com.jemersoft.tecnica.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jemersoft.tecnica.dto.DetailPokemonReceivedDTO;
import com.jemersoft.tecnica.dto.PageDTO;
import com.jemersoft.tecnica.dto.PokemonDTO;
import com.jemersoft.tecnica.dto.PokemonDetailDTO;
import com.jemersoft.tecnica.service.PokemonService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping
    @Operation(summary = "Get all pokemons", description = "Get all pokemons")
    public PageDTO<PokemonDTO> getPokemons() throws ExecutionException, InterruptedException {
        return pokemonService.getPokemons();
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get pokemon details by name", description = "Get pokemon details by name")
    public PokemonDetailDTO getPokemonDetails(@PathVariable String name) {
        return this.pokemonService.getPokemonDetails(name);
    }
}
