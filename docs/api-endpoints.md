Method | Path | Input DTO | Output DTO / Status | Descripción
POST | /api/admin/testers | CreateTesterRequest | 201 + Location header | Crear Tester
GET | /api/admin/testers | — | 200 + [TesterDto] | Listar Testers
GET | /api/admin/testers/{id} | — | 200 + TesterDto404 | Obtener Tester por ID
PUT | /api/admin/testers/{id} | UpdateTesterRequest | 204 No Content404 | Actualizar name/email
PUT | /api/admin/testers/{id}/password | UpdatePasswordRequest | 204 No Content404 | Cambiar contraseña
DELETE | /api/admin/testers/{id} | — | 204 No Content404 | Borrar Tester
