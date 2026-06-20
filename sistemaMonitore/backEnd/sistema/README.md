## PRIMEROS PASOS

Para correr el proyecto localmente:

1. Iniciar el servicio de MySQL
    1.0 sudo -i
    1.1 mysql -u usuario -p
    1.2 CREATE database baseDatosAuto;
    1.3 USE baseDatosAuto;
    1.4 CREATE TABLE IF NOT EXISTS ubicaciones (     vehiculo_id VARCHAR(50),lat DOUBLE,     lng DOUBLE,     timestamp VARCHAR(50) );

2. Abrir el proyecto en Visual Studio Code.
3. Ejecutar el archivo index.html utilizando la extensión Live Server.

## Estructura de carpetas 

  ### Por defecto tiene las siguientes carpetas:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

   ### Se adicionaron nuevas carpetas:

- `backEnd`:
    -src
     -db
     -middleware
     -modulos

- `FrontEnd`:
    -css
    -js
    -paginas

## Arquitectura

Se utilizó una arquitectura de tres capas bajo un modelo cliente-servidor. La capa de presentación fue desarrollada con HTML, CSS, JavaScript y Bootstrap; la lógica de negocio se implementó en Java; y la capa de datos se gestionó mediante MySQL. Esta estructura permite una mejor organización del sistema, facilita el mantenimiento y favorece la escalabilidad de la aplicación.

## Pregunta reflexion (sección 03.1 D)

Si en un sistema real existiera tanto un caché (Redis) como una base de datos persistente,
¿qué deberías garantizar al eliminar un vehículo para evitar inconsistencias entre ambos? No es necesario implementarlo. Solo explícalo con tus propias palabras.  

RTA: Uno guarda informacion temporal y el otro permanente 

## Reporte IA 

1. Uso de chatGpt: Recordar conexión base de datos con java, las conexiones que habia hecho con anterioridad fueron en php y python

2. Uso de gemini: Fallo de conexión con mysql, (antes habia verificado el puerto de escucha mediante comando sudo netstat -tlnp | grep -i mysql desde consola) no conectaba, pregunte y hacia falta el conector de mysql, depues de eso mysql no queria aceptarme la contraseña sin cumplir con un formato, asi que le cambie el formato a mysql para que me aceptara la contraseña.

3. Uso de chatGpt: Verificar endpoint que quedara adecuado correctamente al requisito y consulta de “persistir la coordenada en un map” porque desconocia que era eso

4. Uso chatGpt: Decidi mysql prompt completo para realizar el ejercicio y entre sus resultados fueron:

			Si te preguntan:
		"¿Por qué utilizaste PreparedStatement?"
			Puedes responder:
			"Porque permite enviar parámetros de forma segura a la consulta SQL, evita problemas de formato y ayuda a prevenir inyecciones SQL."
Validar datos.
✅ Conectarte a MySQL mediante JDBC.
✅ Persistir información en una base de datos.
✅ Manejar errores básicos.

5. Uso chat GPT: Ponerle el estado del vehículo

6. Uso chatGPT: Conformación del frontEnd, no los realizo completos y agregar una página para el mapa y usar Leaflet. (Sugerencia de chat) y punto completo:
        ◦ Mapa con ubicaciones
        ◦ Integra un mapa usando Leaflet.js (gratuito, sin API key).
        ◦ Pinta la última posición de cada vehículo con un marcador.
        ◦ Al hacer clic en un marcador: muestra ID y estado del vehículo en un popup.

7. Uso chatGPT: Actualización automática y polling porque desconocía como hacer el polling por lo tanto elegí esa. Sugerencia también de la IA: Eso se hace con JavaScript + setInterval().

8.Uso gemini: Conexión bakend y frontend. Sugerencia la forma más nativa y limpia de hacerlo en Java es utilizando la librería nativa com.sun.net.httpserver.HttpServer e incorporando una dependencia ligera como Gson para poder responder en formato JSON . necesitas un Servidor HTTP que traduzca esas peticiones de la red y ejecute tu controlador.

9. Uso ChatGPT: Dentro de las prompts cuando le indicaba alguna instrucción:
    9.1 Mezclaba los front y sus diversas carpetas yo le indique que me mantuviera el orden por el que empece. 
    9.2 La verificacion de espacios en blanco, la IA usaba isBalck() esa una version mejorada del isEmpty pero que no funciona en versiones inferiores a la 8 (Que es la que manejo actualmente)
    9.3 Inicialmente habia dejado una funcion null que era actualizarHora en api.js lo que genero errores, fue necesario verificar la funcion.
    9.4 Queria que incluyera javax.servlet cuando ya estaba manejando HttpServer

10. Uso ChatGPT: Indicacion para el script de simulacion (Sugerencias: FrontEnd o Node)



