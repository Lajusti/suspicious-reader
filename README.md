# suspicious-reader

Proyecto para detectar lecturas sospechosas de Alejandro Lajusticia Delgado

## Setup

Este proyecto requiere:
  **gradle**
  **java 1.8**
  **lombok** https://projectlombok.org/setup/intellij https://projectlombok.org/setup/eclipse

Una vez que el proyecto compile, al bajar todas las dependencias de gradle y tener configurado correctamente lombok, se puede levantar el proyecto directamente desde el IDE y ocupara el puerto **8080**.

He generado el bootjar resultante en el directorio: **/bootjar** se puede ejecutar mediante **$java -jar ./bootjar/suspicious-reader-1.0.jar 

## Diseño

Para la solución me he basado en un diseño en arquitectura hexagonal

Dentro de cada dominio el código está separado en 3 partes:
* **infrastructure**:
  * IO en este caso únicamente desde 2 controllers, pero podría ser un message broker o cualquier entrada de datos. 
  * Entidades que reciben las entradas, tanto para los XML como para los CSV.
  * Implementaciones de los converter para cada tipo de entrada.
  * Notificaciones que se envían al generarse el evento de aplicación "SuspiciousReading" en este caso simplemente se pintan por consola.
* **application** - Casos de uso de la aplicación y eventos de aplicación
* **domain** - Entidades de dominio, servicios de dominio y interfaces que implementara la capa de infrastructure

Al utilizar un arquitectura hexagonal los test unitarios no son sobre únicamente una clase sino que se realizan desde la capa de aplicación hacia abajo, es decir que se testea la capa de aplicación y la de dominio como si fueran una única unidad mientras que la capa de infraestructura se mockea.

Por otro lado los test de integración ejecuta un test con todo el contexto de spring cargado y se lanza una petición directamente a los endpoints a testear.

El comando **gradle :check** lanza los test unitarios y al finalizar los test funcionales, con lo que al realizar la tarea **:build** hay que pasar los 2 tipos de tests.

Esta solución levanta 2 endpoints para enviar tanto los CSV como los XML:
* **[POST] localhost:8080/csv** - enviar a la aplicación un csv con las lecturas
* **[POST] localhost:8080/xml** - enviar a la aplicación un xml con las lecturas
