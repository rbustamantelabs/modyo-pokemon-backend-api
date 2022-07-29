package com.modyo.pokemon.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.modyo.pokemon.api.entities.*;
import com.modyo.pokemon.api.services.PokemonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/v1")
public class pokemonController {
	
	@Autowired
	private PokemonService service;
	
	@Operation(summary = "Lista Resultado Pokémon sin información del Pokémon")
	@GetMapping("/pokemon")
	@ResponseBody
	public Pokemon getPokemon(
			@Parameter(description = "Posición Inicial de paginado") @RequestParam String offset, 
			@Parameter(description = "Cantidad a mostrar") @RequestParam String limit) {
		
		Long _offset = convertStringToLong(offset, 0l);
		Long _limit  = convertStringToLong(limit, 12l);
				
		return service.getList(_offset, _limit);
	}


	@Operation(summary = "Lista Resultado Pokémon sin información completa de cada Pokémon")
	@GetMapping("/pokemonfull")
	@ResponseBody
	public Pokemon getPokemonFull(
			@Parameter(description = "Posición Inicial de paginado") @RequestParam String offset, 
			@Parameter(description = "Cantidad a mostrar (máx 20)") @RequestParam String limit) {
		
		Long _offset = convertStringToLong(offset, 0l);
		Long _limit  = convertStringToLong(limit, 8l);
		if (_limit > 20l) { _limit = 20l; };
		
		System.out.println(_limit);
		System.out.println(_offset);
		
		return service.getListFull(_offset, _limit);
	}

	@Operation(summary = "Trae información completa de un Pokémon")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Encontró Pokémon")
	})
	@GetMapping("/pokemon/{id}")
	public PokemonInfo getPokemonById(@Parameter(description = "Identiifador del Pokémon") @PathVariable Long id) {
		return service.getItemForId(id);
	}

	private Long convertStringToLong(String str, Long valor) {
		try {
		    return Long.valueOf(str);
		} catch (NumberFormatException e) {
		    return valor;
		}
	}
}
