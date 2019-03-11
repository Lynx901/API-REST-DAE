# API REST para la Gestión de Eventos
Las operaciones permitidas son:

`GET /eventos`: Devuelve la lista de todos los eventos disponibles

`GET /eventos/{id}`: Devuelve la información del evento con el id especificado

`GET /eventos/{id}/asistentes`: Devuelve la lista de los asistentes al evento

`GET /eventos/{id}/asistentes/{username}`: Devuelve la información de un asistente en concreto

`GET /eventos/{id}/inscritos`: Devuelve la lista de los usuarios en la lista de espera al evento

`GET /eventos/{id}/inscritos/{username}`: Devuelve la información de un usuario en la lista de espera concreto

`GET /eventos/{id}/organizador`: Devuelve la información del organizador del evento

`POST /eventos/{id}`: Crea un evento

`PUT /eventos/{id}`: Actualiza la información de un evento

`PUT /eventos({id}/asistentes/{username}`: Inscribe a un usuario a un evento

`DELETE /eventos({id}/asistentes/{username}`: Desinscribe a un usuario de un evento

`DELETE /eventos({id}/inscritos/{username}`: Desinscribe a un usuario de la lista de espera de un evento



`GET /usuario/{username}`: Devuelve la información de un usuario

`GET /usuario/{username}/eventos`: Devuelve la información de los eventos a los que se ha inscrito el usuario

`GET /usuario/{username}/listaEspera`: Devuelve la información de los eventos en los que el usuario está en la lista de espera

`GET /usuario/{username}/organizados`: Devuelve la información de los eventos que ha organizado el usuario

`POST /usuario/{username}`: Crea un usuario

```
/                            -
    /eventos                 - GET
        /{id}                - GET POST PUT
            /asistentes      - GET
               /{username}   -          PUT DELETE
            /inscritos       - GET          
               /{username}   -              DELETE
            /organizador     - GET
    /usuario                 -
        /{username}          - GET POST
            /eventos         - GET
            /listaEspera     - GET
            /organizados     - GET
```
