La consulta SQL para obtener un usuario por `id` es: 

```sql
select u.id, u.name, u.surname, u.email, u.role 
from users u
where u.id = ?
```
