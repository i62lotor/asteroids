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
package org.rltsistemas.asteroids.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestErrorHandler {

	
	@ExceptionHandler(value = { MissingServletRequestParameterException.class, IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public HttpEntity<Error> processBadRequest(HttpServletRequest req, Exception ex) {
		log.warn("Error en los parámetros de entrada: "+req.getRequestURI());
		final Error response = getErrorResponse(req, ex, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(response,  HttpStatus.BAD_REQUEST );
	}
	
	@ExceptionHandler(value = { WebClientRequestException.class, WebClientResponseException.class })
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	@ResponseBody
	public HttpEntity<Error> processWebClientException(HttpServletRequest req, Exception ex) {
		log.warn("Error en el servicio de la nasa");
		final Error response = getErrorResponse(req, ex, HttpStatus.BAD_GATEWAY);
		
		return new ResponseEntity<>(response,  HttpStatus.BAD_GATEWAY );
	}
	
	
	private Error getErrorResponse(HttpServletRequest req, Exception ex, HttpStatus status) {
		return Error.builder().status(status.value()).error(status.name())
				.message(ex.getMessage())
				.path(req.getRequestURI().substring(req.getContextPath().length()))
				.build();
		
		
	}
}
