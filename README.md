# parking-backend


| Método Http | Endpoint                                          | Query Params | Cuerpo JSON de la petición | Respuesta JSON de la petición                                             | Códigos HTTP de respuesta posibles       |
|-------------|---------------------------------------------------|--------------|----------------------------|---------------------------------------------------------------------------|------------------------------------------|
| GET         | /spaces                                           |              |                            | `[{"id":"...","name":"...","location":{"latitude":...,"longitude":...}}]` | 200 OK, 400 Bad Request                  |
| GET         | /spaces/{id}                                      |              |                            | `{"id":"...","site":{"name":"...","paking":[{"id":1,"floors":[...]}]}}`   | 200 OK, 404 Not Found                    |
| GET         | /spaces/{id}/parkings/{id}                        |              |                            | `{"id":1,"zone":"Zona Norte","layout":1}`                                 | 200 OK, 404 Not Found                    |
| GET         | /spaces/{id}/parkings/{id}/floors                 |              |                            | `[{"number":1,"slots":[{"id":"1","status":false,"type":"Carro"}]}]`       | 200 OK, 400 Bad Request                  |
| GET         | /spaces/{id}/parkings/{id}/floors/{id}            |              |                            | `{"number":1,"slots":[{"id":"1","status":false,"type":"Carro"}]}`         | 200 OK, 404 Not Found                    |
| PATCH       | /spaces/{id}/parkings/{id}/floors/{id}/slots/{id} |              | `{"status":true}`          | `{"number":1,"slots":[{"id":"1","status":false,"type":"Carro"}]}`         | 200 OK, 404 Not Found                    |

### Ejemplos de Respuestas JSON

#### 1. GET /spaces
```json
[
  {
    "id": "68153f1560d6227593690c79",
    "name": "Centro Mayor",
    "location": {
      "latitude": 4.60971,
      "longitude": -74.08175
    }
  },
  {
    "id": "68153f1560d6227593690c79",
    "name": "Centro Mayor",
    "location": {
      "latitude": 4.60971,
      "longitude": -74.08175
    }
  }
]
```

#### 2. GET /spaces/{id}
```json
{
  "id": "68153f1560d6227593690c79",
  "site": {
    "name": "Centro Mayor",
    "rating": "4.4",
    "covered": true,
    "location": {
      "latitude": 4.60971,
      "longitude": -74.08175
    },
    "address": {
      "street": "Calle 26",
      "number": 25,
      "city": "Bogotá",
      "state": "Cundinamarca",
      "country": "Colombia"
    },
    "paking": [
      {
        "id": 1,
        "zone": "Zona Norte",
        "layout": 1,
        "floors": [
          {
            "number": 1,
            "slots": [
              {
                "id": "1",
                "status": false,
                "type": "Carro"
              }
            ]
          }
        ]
      }
    ]
  }
}
```

#### 3. GET /spaces/{id}/parkings/{id}
```json
{
  "id": 1,
  "zone": "Zona Norte",
  "layout": 1
}
```

#### 4. GET /spaces/{id}/parkings/{id}/floors
```json
[
  {
    "number": 1,
    "slots": [
      {
        "id": "1",
        "status": false,
        "type": "Carro"
      }
    ]
  },
  {
    "number": 2,
    "slots": [
      {
        "id": "1",
        "status": false,
        "type": "moto"
      }
    ]
  }
]
```

#### 5. GET /spaces/{id}/parkings/{id}/floors/{id}
```json
{
  "number": 1,
  "slots": [
    {
      "id": "1",
      "status": false,
      "type": "Carro"
    }
  ]
}
```

#### 6. PATCH /spaces/{id}/parkings/{id}/floors/{id}/slots/{id}
```json
{
  "number": 1,
  "slots": [
    {
      "id": "1",
      "status": false,
      "type": "Carro"
    }
  ]
}
```
