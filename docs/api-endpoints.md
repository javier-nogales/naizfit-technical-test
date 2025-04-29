# API end points

## Existing end ponts

| Method | Path                               | Input DTO                    | Output / Status             | Description               |
|--------|------------------------------------|------------------------------|-----------------------------|---------------------------|
| POST   | `/api/admin/testers`               | `CreateTesterRequest`        | **201 Created**<br>Location header | Create a new Tester       |
| GET    | `/api/admin/testers`               | –                            | **200 OK**<br>`[TesterDto]`  | List all Testers          |
| GET    | `/api/admin/testers/{id}`          | –                            | **200 OK**<br>`TesterDto`<br>**404 Not Found** | Get Tester by ID          |
| PUT    | `/api/admin/testers/{id}`          | `UpdateTesterRequest`        | **204 No Content**<br>**404 Not Found** | Update name/email         |
| PUT    | `/api/admin/testers/{id}/password` | `UpdatePasswordRequest`      | **204 No Content**<br>**404 Not Found** | Change Tester password    |
| DELETE | `/api/admin/testers/{id}`          | –                            | **204 No Content**<br>**404 Not Found** | Delete a Tester           |


## Integration test request for curl
**Note:** Use the `id` returned in the first response

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
    "name":"Javier Nogales [updated]",
    "email":"javier.nog76@gmail.com"
  }'
```

Change Tester password
```shell
curl -i -X PUT http://localhost:8080/naizfit-tester-api/api/admin/testers/:id/password \
  -H "Content-Type: application/json" \
  -d '{ "newPassword":"secret456" }'

```

Delete Tester
```shell
curl -i -X DELETE http://localhost:8080/naizfit-tester-api/api/admin/testers/:id

```
