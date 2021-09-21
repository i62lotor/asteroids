# asteroids
Ejemplo de servicio web Restfull con Spring boot que proporciona el listado de asteroides potencialmente peligrosos para un planeta.

## Objetivo
Exponer un endpoint que reciba un planeta como parámetro y que devuelva un listado en formato json con el top 3 de asteroides más grandes con potencial riesgo de impacto en dicho planeta en los próximos 7 días. En caso de que no haya 3 o más planetas bajo estas condiciones, devolver los que sea que apliquen.

### EndPoint: /asteroids/
Parámetros:
- planet (obligatorio): nombre del planeta
- max (opcional): nº máximo de asteroides a mostrar (valor por defecto 3).

Ejemplo: /asteroids?planet=Earth

### Acceso a la documentación de la API
Aunque solo hay un endpoint es conveniente tenerlo mínimamente documentado:
[http://server:port/swagger-ui.html](http://server:port/swagger-ui.html)

## Tecnología
* Java 11
* MAVEN
* Spring Boot

## API de la NASA
La información para mostrar los asteroides se recupera del servicio Rest de la NASA: Asteroids - NeoWs
[https://api.nasa.gov/](https://api.nasa.gov/)
Este servicio acepta peticiones del tipo:
```GET https://api.nasa.gov/neo/rest/v1/feed?start_date=START_DATE&end_date=END_DATE&api_key=API_KEY```

El servicio devuelve un JSON compuesto por información de los objetos cercanos a la Tierra (near earth object), y los cuerpos celestes sobre los que orbita (orbiting_body), clave que es tenida en cuenta para determinar el parámetro "planet" indicado en la petición a nuestro endpoint.

## TODO
- Revisar los TODOs del código.
- Mejorar cobertura de test.
- Terminar de modelar el objeto de la respuesta del servicio de la Nasa, para mapear el json de una forma mas directa y sencilla.

## Normas de contribución
Todos los comentario son bienvenidos, aunque este es un proyecto de prueba, sin mayor trascendencia.