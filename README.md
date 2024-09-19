# java-filmorate
Template repository for Filmorate project

![](filmrate.png)

* #  /films/{id}
```sql
    SELECT * 
    FROM film_id
    WHERE film_id = id;
```

* # /films
```sql
    SELECT *
    FROM film;
```

* # /films//popular

```sql
select 
    f.film_id, 
    f.name
from film f 
join (
	select film_id,COUNT(user_id) as rn
	from likes l 
	group by film_id 
	order by rn desc
	limit 10
) as subfilms
on f.film_id = subfilms.film_id
order by subfilms.rn desc;
```
* # /users
```sql
    SELECT *
    FROM users;
```

* # /users/{id}

```sql
    SELECT *
    FROM users
    WHERE user_id=id;
```

* # /users/{id}/friends

```sql

    select u.*
    from filmorate.public.users u 
    where user_id in (
        select friend_id
        from filmorate.public.friendships f 
        where f.user_id = id
    );
```
* # /users/{id}/friends/common/{otherId}
```sql
    select *
    from filmorate.public.users u 
    where user_id in (select f.friend_id
                    from filmorate.public.friendships f 
                    inner join filmorate.public.friendships f2 on f.friend_id=f2.friend_id
                    where f.user_id = id and f2.user_id = otherId
    );
```