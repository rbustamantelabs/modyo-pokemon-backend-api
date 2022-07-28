/**
 * Controlador Pokemón
 * - Listado:
 * 			Limite: por defecto 18
 * - 
 */

package com.modyo.pokemon.api.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modyo.pokemon.api.entities.*;
import com.modyo.pokemon.api.services.PokemonService;


@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/v1")
public class pokemonController {
	
	@Autowired
	private PokemonService service;
	
	@GetMapping("/pokemon")
	public Pokemon getList(@RequestParam Map<String,String> requestParams) {
		
		Long offset = convertStringToLong(requestParams.get("offset"), 0l);
		Long limit  = convertStringToLong(requestParams.get("limit"), 20l);
				
		return service.getList(offset, limit);
	}
	
	@GetMapping("/pokemonfull")
	public Pokemon getListFull(@RequestParam Map<String,String> requestParams) {
		
		Long offset = convertStringToLong(requestParams.get("offset"), 0l);
		Long limit  = convertStringToLong(requestParams.get("limit"), 20l);
				
		return service.getListFull(offset, limit);
	}

	@GetMapping("/pokemon/{id}")
	public PokemonInfo pokemonById(@PathVariable Long id) {
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
