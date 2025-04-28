## Integration test request for curl

Create Tester
```shell
curl -i -X POST http://localhost:8080/naizfit-tester-api/api/admin/testers \
  -H "Content-Type: application/json" \
  -d '{
    "name":"Javier Nogales",
    "email":"javier.nog76@gmail.com",
    "rawPassword":"secret123",
    "birthdate":"1990-05-15",
    "sex":"MALE"
  }'
```


Get Tester by id
```shell
curl -i http://localhost:8080/naizfit-tester-api/api/admin/testers/:id
```

Get all Testers
```shell
curl -i http://localhost:8080/naizfit-tester-api/api/admin/testers
```

Update Tester
```shell
curl -i -X PUT http://localhost:8080/naizfit-tester-api/api/admin/testers/:id \
  -H "Content-Type: application/json" \
  -d '{
    "name":"Juan X. Pérez",
    "email":"juanx@ejemplo.com"
  }'
```

Change Tester password
```shell
curl -i -X PUT http://localhost:8080/naizfit-tester-api/api/admin/testers/:id/password \
  -H "Content-Type: application/json" \
  -d '{ "newPassword":"otraClave789" }'

```

Delete Tester
```shell
curl -i -X DELETE http://localhost:8080/naizfit-tester-api/api/admin/testers/:id

```
