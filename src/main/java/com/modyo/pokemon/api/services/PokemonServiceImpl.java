package com.modyo.pokemon.api.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.modyo.pokemon.api.entities.Pokemon;
import com.modyo.pokemon.api.entities.PokemonInfo;
import com.modyo.pokemon.api.entities.SmallPokemon;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
public class PokemonServiceImpl implements PokemonService {

	private static final String BASEURL_POKEMON = "https://pokeapi.co/api/v2";
	
	@Autowired
	private RestTemplate clienteRest;

	@Autowired
    private Environment env;

	
	@Override
	public Pokemon getList(Long offset, Long limit)
	{
		String url = BASEURL_POKEMON + "/pokemon?offset="+offset+"&limit="+limit;

		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
	    
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
	    
	    ResponseEntity<Pokemon> response = clienteRest.exchange(
	    							url, 
	    							HttpMethod.GET,
	    							entity, 
	    							new ParameterizedTypeReference<Pokemon>() {}
	    						);
	    
	    
	    Pokemon resultado = response.getBody();

	    String urlBackend = env.getProperty("modyo.backend.pokemon.url");

	    String _next = resultado.getNext();
	    if (_next != null) {
	    	_next = _next.replace(BASEURL_POKEMON, urlBackend);
	    	resultado.setNext(_next);
	    }
	    
	    if (resultado.getPrevious() != null) {
	    	String _previous = (String)resultado.getPrevious();
	    		   
	    	_previous = _previous.replace(BASEURL_POKEMON, urlBackend);
	    	
	    	resultado.setPrevious(_previous);
	    }
	    
	  
	    return resultado;
	}
	
	@Override
	public Pokemon getListFull(Long offset, Long limit)
	{   
	    Pokemon resultado = this.getList(offset, limit);
	    
	    List<SmallPokemon> lista = resultado.getResults();
	    
	    for (int i = 0; i < lista.size(); i++) {
	    	SmallPokemon item = lista.get(i);
	    	
	    	item.setPokemonInfo(this.getItemForId(item.getID()));
	    	
	    	lista.set(i, item);
	    	
	        System.out.println(i);
	    }
	  
	    return resultado;
	}
	
	@Override
	public PokemonInfo getItemForId(Long id) {
		
		String url = BASEURL_POKEMON + "/pokemon/" + id;

		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
	    
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
	    
	    ResponseEntity<PokemonInfo> response = clienteRest.exchange(
	    							url, 
	    							HttpMethod.GET,
	    							entity, 
	    							new ParameterizedTypeReference<PokemonInfo>() {}
	    						);
	    
	    
	    return response.getBody();
		
	}

}
