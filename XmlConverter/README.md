# Задание

Реализовать класс преобразующий xml. На вход поступает xml, в виде строки, содержащая список фильмов, актеров каждого фильма и их роли. Необходимо получить на выходе xml, в виде строки, содержащую список актеров, фильмы в которых они участвовали и роли.

На вход:
```xml
<films>
   <film>
       <title>Фильм 1</title>
       <description>Описание 1</description>
       <actors>
           <actor name="Актер 1" age="30" role="Роль 1"/>
           <actor name="Актер 2" age="23" role="Роль 2"/>
           <actor name="Актер 3" age="40" role="Роль 3"/>
       </actors>
   </film>
   <film>
       <title>Фильм 2</title>
       <description>Описание 2</description>
       <actors>
           <actor name="Актер 4" age="70" role="Роль 4"/>
           <actor name="Актер 2" age="23" role="Роль 5"/>
           <actor name="Актер 3" age="40" role="Роль 6"/>
       </actors>
   </film>
</films>
```

Необходимо получить:
```xml
<actors>
   <actor>
       <name>Актер 1</name>
       <age>30</age>
       <films>
           <film title="Фильм 1" role="Роль 1"/>
       </films>
   </actor>
   <actor>
       <name>Актер 2</name>
       <age>23</age>
       <films>
           <film title="Фильм 1" role="Роль 2"/>
           <film title="Фильм 2" role="Роль 5"/>
       </films>
   </actor>
   <actor>
       <name>Актер 3</name>
       <age>40</age>
       <films>
           <film title="Фильм 1" role="Роль 3"/>
           <film title="Фильм 2" role="Роль 6"/>
       </films>
   </actor>
   <actor>
       <name>Актер 4</name>
       <age>70</age>
       <films>
           <film title="Фильм 2" role="Роль 4"/>
       </films>
   </actor>
</actors>
```