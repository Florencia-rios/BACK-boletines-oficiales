# BACK-boletines-oficiales

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

## Como levantar el programa
Dirigirse, dentro de la terminal, a la carpeta <i>base</i> y ejecutar el siguiente comando:

````
docker-compose build
````
````
docker-compose up
````