package com.modyo.pokemon.api.services;

import com.modyo.pokemon.api.entities.*;

public interface PokemonService {

	Pokemon getList(Long offset, Long limit);
	
	Pokemon getListFull(Long offset, Long limit);

	PokemonInfo getItemForId(Long id);
	
}