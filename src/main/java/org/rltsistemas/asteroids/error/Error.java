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

import java.io.Serializable;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
/**
 * Representa una respuesta de error. Proporcona información sobre el problema
 * producido mientras se realizaba una operación. Es una versión simplificada de
 * JSON-API http://jsonapi.org/format/#error-objects
 */
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class Error implements Serializable{
	
	private static final long serialVersionUID = 5676546996323221670L;
	
	private int status;
	private String error;
	private String message ;
	private String path;	
	
	
}
