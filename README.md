# BACK-boletines-oficiales

## Como levantar el programa
Dirigirse, dentro de la terminal, a la carpeta <i>base</i> y ejecutar el siguiente comando:

````
docker compose up --build -d
````

## Como armar request para el endpoint
Importar el siguiente curl en postman o ejecutarlo en la terminal:

````
curl --location 'http://localhost:8083/api/boletines-oficiales' \
--header 'Content-Type: application/json' \
--data '{
"fechaBoletin": <fecha_actual> (formato: dd/mm/yyyy),
"boletinesOficiales": [
<string_base64_sociedad_1>,
<string_base64_sociedad_2>,
...
]
}'
````

Los <string_base64_sociedad_1> deben contener una única sociedad, en base 64.
Como dato, para agilizar el proceso de pasaje a base 64 de cada una, ejecutar el siguiente prompt en chatGPT (adjuntando el .txt con cada sociedad, recordar separar por uno o más saltos de líneas cada una de las sociedades):

````
Hola! por favor, codificame cada sociedad (estan separadas por una o más lineas vacías entre cada una) en base 64, y además, generame una lista de strings con cada una, ya que necesito usarlo para un json, en el siguiente formato:

curl --location 'http://localhost:8083/api/boletines-oficiales' \
--header 'Content-Type: application/json' \
--data '{
"fechaBoletin": <fecha_actual> (formato: dd/mm/yyyy),
"boletinesOficiales": [
<string_base64_sociedad_1>,
<string_base64_sociedad_2>,
...
]
}'
````
Ahí te va a generar un archivo json para descargar, que ya tiene el formato del request, es decir:
````
{
"fechaBoletin": <fecha_actual> (formato: dd/mm/yyyy),
"boletinesOficiales": [
<string_base64_sociedad_1>,
<string_base64_sociedad_2>,
...
]
}'
````

Entonces, lo único que te queda es copiarlo en postman y ejecutarlo.

## Luego de ejecutado el programa:

Ingresar a la siguiente url

````
http://localhost:5000/api/download/<nombre_archivo>_<fecha_ejecucion>
````

Hay dos nombres de archivos disponibles:

- ALTAS_<yyyymmdd>
- MODIFICACIONES_<yyyymmdd>

Los mismos se van a descargar automáticamente. Y eso ya lo podrías analizar.