/*******************************************************************************
 * Copyright 2021 Rafael López Torres
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.rltsistemas.asteroids.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;


@Configuration
public class AppConfiguration {
	
	@Value("${nasa.neo.base.url}")
	private String neoBaseUrl;
	
	@Value("${proxy.url:no_proxy}")
	private String proxyUrl;
	
	@Value("${proxy.port}")
	private int proxyPort;
	
	@Bean
	public WebClient.Builder webClientBuilder() {
		 Builder webClientBuilder = WebClient.builder()
		        .baseUrl(neoBaseUrl)
		        .defaultHeader(HttpHeaders.CONTENT_TYPE,
		        		MediaType.APPLICATION_JSON_VALUE)
		     ;
		 if(!"no_proxy".equals(proxyUrl)) {
			 webClientBuilder.clientConnector(proxyConector());
		 }
		 
		 return webClientBuilder;
	}
	
	
	private ReactorClientHttpConnector proxyConector() {
		@SuppressWarnings("deprecation")
		HttpClient httpClient = HttpClient.create()
	            .tcpConfiguration(tcpClient -> tcpClient
	                .proxy(proxy -> proxy
	                    .type(ProxyProvider.Proxy.HTTP)
	                    .host(proxyUrl)
	                    .port(proxyPort)));

		return new ReactorClientHttpConnector(httpClient);
	}
}
