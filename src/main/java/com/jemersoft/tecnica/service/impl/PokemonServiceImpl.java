package com.jemersoft.tecnica.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jemersoft.tecnica.dto.DetailPokemonReceivedDTO;
import com.jemersoft.tecnica.dto.FlavorTextEntryDTO;
import com.jemersoft.tecnica.dto.PageDTO;
import com.jemersoft.tecnica.dto.PokemonDTO;
import com.jemersoft.tecnica.dto.PokemonDetailDTO;
import com.jemersoft.tecnica.dto.SpecieDetailDTO;
import com.jemersoft.tecnica.exception.ServerExternalException;
import com.jemersoft.tecnica.service.ConsumerPokeService;
import com.jemersoft.tecnica.service.PokemonService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class PokemonServiceImpl implements PokemonService {

    private final ConsumerPokeService consumerPokeService;

    /**
     * Recupera una lista paginada de Pokémon desde una API externa y obtiene
     * detalles adicionales para cada Pokémon.
     *
     * @return un objeto PageDTO que contiene información detallada sobre cada
     *         Pokémon.
     */
    public PageDTO<PokemonDTO> getPokemons() {
        PageDTO<PokemonDTO> response = this.consumerPokeService.getExternalApiDataWithPages("pokemon/", null,
                PokemonDTO.class);
        List<CompletableFuture<PokemonDTO>> futuresPokemons = new ArrayList<>();

        if (response != null && response.getResults() != null) {
            ExecutorService executor = Executors.newFixedThreadPool(10);
            for (PokemonDTO summary : response.getResults()) {
                CompletableFuture<PokemonDTO> future = CompletableFuture.supplyAsync(() -> {
                    try {
                        PokemonDetailDTO details = getPokemonDetails(summary.getName());
                        log.info("Detalles del Pokémon: " + details); // Verifica los detalles aquí
                        return PokemonDTO.builder()
                                .name(summary.getName())
                                .url(details.getUrl())
                                .abilities(details.getAbilities())
                                .weight(details.getWeight())
                                .types(details.getTypes())
                                .build();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        return PokemonDTO.builder()
                                .name(summary.getName())
                                .url(summary.getUrl())
                                .abilities(new ArrayList<>())
                                .weight(0L)
                                .types(new ArrayList<>())
                                .build();
                    }
                }, executor);
                futuresPokemons.add(future);
            }
        }

        List<PokemonDTO> pokemons = futuresPokemons.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        response.setResults(pokemons);
        return response;
    }

    /**
     * Recupera información detallada sobre un Pokémon específico por nombre.
     *
     * @param url la URL desde donde se recuperan los detalles del Pokémon.
     * @return un objeto PokemonDetailDTO que contiene información detallada sobre
     *         el
     *         Pokémon.
     */
    @Override
    public PokemonDetailDTO getPokemonDetails(String name) {
        PokemonDetailDTO pokemonDetailDTO = new PokemonDetailDTO();
        try {
            DetailPokemonReceivedDTO details = this.consumerPokeService.getExternalApiData("pokemon/" + name, null,
                    DetailPokemonReceivedDTO.class);

            SpecieDetailDTO specieDetails = this.consumerPokeService.getExternalApiData("pokemon-species/" + name, null,
                    SpecieDetailDTO.class);

            pokemonDetailDTO.setAbilities(details.getAbilities().stream()
                    .map(details1 -> details1.getAbility().getName()).collect(Collectors.toList()));

            String description = Arrays.stream(specieDetails.getFlavorTextEntries())
                    .filter(entry -> "es".equals(entry.getLanguage().getName()))
                    .map(FlavorTextEntryDTO::getFlavorText)
                    .collect(Collectors.joining(", "));

            pokemonDetailDTO.setDescription(description);
            pokemonDetailDTO.setMoves(details.getMoves().stream()
                    .map(details1 -> details1.getMove().getName()).collect(Collectors.toList()));
            pokemonDetailDTO.setTypes(details.getTypes().stream().map(details1 -> details1.getType().getName())
                    .collect(Collectors.toList()));
            pokemonDetailDTO.setName(details.getName());
            pokemonDetailDTO.setUrl(details.getSprites().getOther().getDreamWorld().getFront_default());
            pokemonDetailDTO.setWeight(details.getWeight());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServerExternalException("EXT-500", 500, e.getMessage());
        }
        return pokemonDetailDTO;
    }
}
