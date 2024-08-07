### Resumen sobre el desarrollo

En la implementación de la API REST, optimicé la recuperación de datos de pokemones utilizando concurrencia para mejorar el rendimiento y evitar las limitaciones de hacer solicitudes secuenciales dentro de un bucle. En lugar de realizar peticiones en serie, que podrían causar tiempos de respuesta largos, implemente CompletableFuture junto con un ExecutorService para manejar múltiples solicitudes de manera asíncrona.

### Levantar el Proyecto en Local

docker-compose up --build

Antes de ejecutar el comando, es necesario que tengas instalado Docker Desktop y que este abierto. Esto hara un build de
los contenedores definidos en el docker-compose y los levantará para poder compilar el servidor.

#### Acceder a la Aplicación:

Una vez que los contenedores estén corriendo, la aplicación estará disponible en http://localhost:8080.

### Codigo de Errores

Esta parte detalla los errores y excepciones personalizados utilizados en la aplicación. Cada excepción está asociada
con un código de error único para facilitar la identificación y el manejo de errores en la misma.

#### Formato de Código de Error

**SIGLA-CODIGO DE ERROR HTTP**

Por ejemplo, EXT-500 indica un error al consumir una api externa con un código HTTP 500.

```json
EXT-500: Error al realizar una petición al servidor externo

EXT -> Hace referencia a que el error proviene de un servidor externo en este caso pokeapi, despues de las siglas EXT- puede ser cualquier codigo de error http.

```

### Documentación de la API

Esta aplicación utiliza Swagger para generar automáticamente la documentación de la API. Swagger proporciona una
interfaz interactiva para explorar y probar los endpoints de la API.
Acceso a la Documentación

Una vez que la aplicación está en funcionamiento, puedes acceder a la documentación de Swagger en la siguiente URL:

```bash
http://localhost:8080/swagger-ui.html
```

## Deploy

Desplegué el servicio utilizando una instancia de EC2 en AWS, asegurando que la aplicación esté disponible y escalable en la nube.
