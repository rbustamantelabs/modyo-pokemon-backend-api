package com.modyo.pokemon.api.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PokemonConfiguracion {

	@Bean("clienteRest")
    RestTemplate registrarRestTemplate() {
		 return new RestTemplate();
    }
}