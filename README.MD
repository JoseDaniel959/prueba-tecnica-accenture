
<h1 style="text-align:center;">Prueba técnica accenture</h1>

En este repositorio puede encontrar la solución de la prueba técnica **Prueba Practica Dev. Backend** el cual puede 
encontrar toda su descrición y criterios de aceptación en el archivo Prueba ````Prueba Practica Dev. Backend.pdf````

**Importante:** Todos los comandos mostrados en esta documentación son comandos de Linux, por lo que se pide amablemente al lector
que, si se encuentra usando Windows entonces por favor descargue <a href="https://learn.microsoft.com/es-es/windows/wsl/install">WSL</a>, el cual es un maquina virtual de Linux dentro de Windows y le permitirá ejecutar los comandos más fácilmente en caso de desear de ejecutar esta aplicación. Para el WSL escoja
una imagen de ubuntu.

- <a href="#instalacion">1. Instalación</a>
    - <a href="#instalacionRapida">1.1 Instalación rápida</a>
    - <a href="#instalacionDesarrollo">1.1 Instalación para desarrollo</a>
    
- <a href="#endpoints">2. Endpoints</a>
- <a href="#explicacionBackend">3. Explicación de la prueba</h2>
- <a href="#tecnologiasUtilizadas">3.1 Tecnologias utilizadas</a>
- <a id="ER">3.3 Modelo Entidad Relación y Base de datos</a>
- <a href="despliege">4. Despliegue de la aplicacion con Terraform en AWS</a>




En esta sección se muestran 2 opciones para la instalación en local. La primera es la más rápida ya que solo usa docker y 
permite probar los endpoints de la aplicación directamente, pero no es ideal usarla cuando se desee desarrollar. La segunda opción se 
presenta como alternativa en caso de desear desarrollar codigo dentro de la aplicación y realizar los test (Para no estar ejecutando mvn package y docker compose up cada vez que se hace un cambio en la aplicación de spring-webflux)

Antes de pasar a las opciones de instalación se debe de descargar el repositorio usando el siguiente commando

```shell-session
~$ git clone 
```

También se debe de descargar docker y docker compose. En el siguiente <a href="https://docs.docker.com/engine/install/ubuntu/">link</a>
puede encontrar los pasos para la instalación en linux de docker y en el siguiente <a href="https://docs.docker.com/desktop/setup/install/linux/">link</a> puede 
encontrar los pasos para la instalación de docker compose


<h3 id="instalacionRapida" >1.1 Instalación Rápida</h3>

*DISCLAIMER: Esta opción de instalación es ideal cuando solo se quiere probar los endpoints de la aplicación y no para desarrollar ni probar los test de la aplicación*

Una vez se hayan ejecutado los pasos de la documentación oficial de docker se debe de ejecutar los siguiente pasos:

**1. Asegurarse que dentro de el archivo ```application.properties``` esté la siguiente opción**
```text
spring.profiles.active=docker
```
esto permitirá a la aplicación de spring-boot saber cual es la URL de la base de datos la 
cual se encuentra en un contenedor de docker

**2. entrar a la carpeta prueba-tecnica-accenture y hacer docker compose up**

```shell-session
~$ cd prueba-tecnica-accenture/
~/prueba-tecnica-accenture$ sudo docker compose up d --build
```

El anterior comando lo que hace es crear los servicios que está definidos dentro del 
```docker-compose.yaml```. El cual son 2 servicios: la RestFul API de java y un contenedor con
postgres 16

**3. Hacer docker ps -a para ver el output de los contenedores corriendo**
```shell-session
~/prueba-tecnica-accenture$ sudo docker ps -a
CONTAINER ID   IMAGE                   COMMAND                  CREATED          STATUS                     PORTS                                         NAMES
4925343806a5   java_app_image:0.0.1    "java -jar java-app.…"   24 seconds ago   Up 23 seconds              0.0.0.0:8080->8080/tcp, [::]:8080->8080/tcp   java_app
3b10b13de453   postgres:16.15-alpine   "docker-entrypoint.s…"   24 seconds ago   Up 23 seconds              0.0.0.0:5433->5432/tcp, [::]:5433->5432/tcp   postgres_db
```
Al ejecutar docker ps -a se imprime la tabla anterior. Se debe de asegurar que en la columna status diga **up**

Una vez tenga los servicios corriendo como se detalla en el paso anterior ya puede probar los 
endpoints de la aplicación. Se sugiere usar visual studio code y descargar la extension 
<a href=https://marketplace.visualstudio.com/items?itemName=humao.rest-client>REST Client</a> ya 
que en el proyecto se encuentra un archivo llamado ```consultas.http``` el cual ya tiene 
todas las consultas http listas para probar la aplicación

<h3 id="instalacionDesarrollo">1.2 Instalación para desarrollar</h3>

*DISCLAIMER: Esta alternativa es ideal cuando se desee desarrollar código en el proyecto corriendo y ejecutar los test de la aplicación*


**1. Asegurarse que dentro de el archivo ```application.properties``` esté la siguiente opción**
```text
spring.profiles.active=dev
```

**2. Descargar el openjdk 17**
```shell-session
~$ sudo apt-get install openjdk-17-jre
```

**3. Asegurarse de estar en la versión 17 con el siguiente comando**
```shell-session
~$ java -version
openjdk version "17.0.20" 2026-07-21
OpenJDK Runtime Environment (build 17.0.20+8-1-24.04-Ubuntu)
OpenJDK 64-Bit Server VM (build 17.0.20+8-1-24.04-Ubuntu, mixed mode, sharing)
```
si el anterior comando no le muestra un output, es porque no se descargó java correctamente y lo debe
de volver a instalar

**4. Descargar Maven**
```shell-session 
~$ sudo apt install maven
```

**5. Verificar que Maven está instalado**

```shell-session 
~$ maven -version
Maven home: /usr/share/maven
Java version: 17.0.20, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "6.6.87.2-microsoft-standard-wsl2", arch: "amd64", family: "unix"
```
si el anterior comando no le muestra un output, es porque no se descargó maven correctamente y lo debe
de volver a instalar

**6. Tener Docker y Docker Compose instalado**

```shell-session 
~$ docker -v
```

En caso de no tenerlo instalado o no esté corriendo dentro del sistema, por favor leer los pasos para instalarlo y ejecutarlo en
<a href="https://docs.docker.com/engine/install/ubuntu/">Ubuntu</a>. Es posible que se necesite tambíen descargar docker compose

**7. Instalar postgres 16**

para la instalación de <a href="https://www.postgresql.org/download/linux/ubuntu/">postres</a>16 ejecuta el siguiente comando

```shell-session 
~$ sudo apt install postgresql-16
```

**8. Creación de tablas y base de datos**

Para la creación de las tablas debe seguir los siguientes pasos

conectarse a postgres con el usuario postgres

```shell-session 
~$ sudo -u postgres psql template1
```

En la consola de postgres crear la base de datos prueba

```shell-session 
postgres=# CREATE DATABASE prueba;
```
cambiar a la base de datos prueba con el siguiente comando

```shell-session 
postgres=# \c prueba
```

Una vez se cambie a la base de datos prueba se debe de crear las tablas

```shell-session 
prueba=# CREATE TABLE FRANQUICIA(
	id bigserial CONSTRAINT primary_key_franquicia PRIMARY KEY,
	nombre varchar CONSTRAINT nombre_franquicia_not_null NOT NULL
);


CREATE TABLE SUCURSAL(
	id bigserial CONSTRAINT primary_key_sucursal PRIMARY KEY,
	nombre varchar CONSTRAINT nombre_sucursal_not_null NOT NULL,
	franquicia_id bigint REFERENCES franquicia(id)
);

CREATE TABLE PRODUCTO(
	id bigserial CONSTRAINT primary_key_producto PRIMARY KEY,
	nombre varchar CONSTRAINT nombre_producto_not_null NOT NULL,
	stock bigint NOT null CHECK (stock >= 0),
	sucursal_id bigint REFERENCES sucursal(id)
);

adicionalmente si desea insertar datos, en el archivo ```init.sql``` hay insert de datos de prueba por si desea poblar la base de edatos

Ahora debe de crear la base de pruebas test, para esto debe de seguir pasos similiares

Crear base de datos test
```shell-session 
postgres=# CREATE DATABASE test;
```

cambiar a la base de datos test
```shell-session 
postgres=# \c test
```

crear las tablas
```shell-session 
test=# CREATE TABLE FRANQUICIA(
	id bigserial CONSTRAINT primary_key_franquicia PRIMARY KEY,
	nombre varchar CONSTRAINT nombre_franquicia_not_null NOT NULL
);


CREATE TABLE SUCURSAL(
	id bigserial CONSTRAINT primary_key_sucursal PRIMARY KEY,
	nombre varchar CONSTRAINT nombre_sucursal_not_null NOT NULL,
	franquicia_id bigint REFERENCES franquicia(id)
);

CREATE TABLE PRODUCTO(
	id bigserial CONSTRAINT primary_key_producto PRIMARY KEY,
	nombre varchar CONSTRAINT nombre_producto_not_null NOT NULL,
	stock bigint NOT null CHECK (stock >= 0),
	sucursal_id bigint REFERENCES sucursal(id)
);


```


El anterior comando crea las tablas dentro de la base de datos test. La sentencia que crea esta base de datos está en
el archivo ```init.sql```. Con los pasos anteriores, se debe de haber creado 2 endpoints para pode acceder a las 2 bases de datos craadas
localhost:5432/prueba y localhost:5432/test


<h2 id="endpoints">2. Endpoints</h2>
En la siguiente Sección se muestra los endpoints que se implementaron según los requisitos de la prueba técnica


<h3>2.1 Exponer endpoint para agregar una nueva franquicia</h3>

```text
### Craer franquicia en local
POST http://localhost:8080/franquicia
content-type: application/json

{
  "nombre": "mcdonalds"
    
}

### Craer franquicia en la nube
POST http://35.87.184.43:8080/franquicia
content-type: application/json

{
  "nombre": "mcdonalds"
    
}
```

<h3>2.2 Exponer endpoint para agregar una nueva sucursal a la franquicia</h3>

```text
### Crear Sucursal en local
POST http://localhost:8080/sucursal
content-type: application/json

{
  "nombre": "mcdonalds",
  "franquiciaId":1
    
}

### Craer sucursal en la nube
POST http://35.87.184.43/sucursal
content-type: application/json

{
  "nombre": "mcdonalds",
  "franquiciaId":1
    
}
```

<h3>2.3 Exponer endpoint para agregar un nuevo producto a la sucursal</h3>

```text
### Crear Producto nuevo
POST http://localhost:8080/producto
content-type: application/json

{
  "nombre": "hamburguesa",
  "stock": 1,
  "sucursalId":1
}

### Crear Producto nuevo
POST http://35.87.184.43:8080/producto
content-type: application/json

{
  "nombre": "hamburguesa",
  "stock": 1,
  "sucursalId":1
}
```

<h3>2.4 Exponer endpoint para eliminar un nuevo producto a una sucursal</h3>

```text
### Eliminar Producto en local
DELETE http://localhost:8080/producto/1

### Eliminar Producto en 
DELETE http://35.87.184.43:8080/producto/1
```

<h3>2.5 Exponer endpoint para modificar un Stock de un nuevo producto</h3>

```text
### Crear Producto nuevo
### Modificar Stock de un producto en local
PATCH http://localhost:8080/producto/2/stock
content-type: application/json

{
  "stock":2
}

### Modificar Stock de un producto en local
PATCH http://35.87.184.43:8080/producto/2/stock
content-type: application/json

{
  "stock":2
}
```

<h3>2.6 Exponer endpoint para agregar que permita mostrar cual es el producto que más stock tiene por sucursal para una
franquicia puntual. Debe retoma un listado de productos que indiquen a que sucursal pertenece.</h3>

```text
### Productos con mayor stock por sucursal por franquicia seleccioada en local
GET http://localhost:8080/producto/conMayorStockPorSucursalPorFranquicia/4

### Productos con mayor stock por sucursal por franquicia seleccioada en la nube
GET http://35.87.184.43:8080/producto/conMayorStockPorSucursalPorFranquicia/4
```

<h3>2.7 Plus si se expone endpoint que permita actualizar el nombre de la franquicia</h3>

```text
### editar nombre a franquicia en local

PATCH http://localhost:8080/franquicia/4/editar-nombre
content-type: application/json

{
  "nombre": "Franquicia Amazonas"
}

### editar nombre a franquicia en la nube
PATCH http://localhost:8080/franquicia/4/editar-nombre
content-type: application/json

{
  "nombre": "Franquicia Amazonas"
}
```

<h3>2.8 Plus si se expone endpoint que permita actualizar el nombre de la sucursal</h3>

```text
### editar nombre a sucursal en local
PATCH http://localhost:8080/sucursal/4/editar-nombre
content-type: application/json

{
  "nombre": "Monitor LG"
}

### editar nombre a sucursal en la nube
PATCH http://localhost:8080/sucursal/4/editar-nombre
content-type: application/json

{
  "nombre": "Sucursal Manrique"
}
```

<h3>2.9 Plus si se expone endpoint que permita actualizar el nombre del producto</h3>

```text
### editar nombre a producto en local
PATCH http://localhost:8080/producto/4/editar-nombre
content-type: application/json

{
  "nombre": "Monitor LG"
}

### editar nombre a producto en la nube
PATCH http://localhost:8080/producto/4/editar-nombre
content-type: application/json

{
  "nombre": "Monitor LG"
}
```
<h2 id="explicacionBackend">3. Explicación de la prueba</h2>

<h3 id="tecnologiasUtilizadas">3.1 Tecnologias utilizadas</h3>



| Tecnologia |Version| Uso |
| ---- |----| --- |
|**Java**       |17.02|Lenguaje con el que se desarolla la RestFul API|
|**Postgres**   |16|Systema de base de datos donde se guarda los datos de la Restful API |
|**Docker**     |29.1.4|Systema de base de datos donde se guarda los datos de la Restful API |
|**Terraform**  |16|Herramientra de Infraestructur as Code, el cual se utiliza para desplegar la BD y la RestFulAPI | 
|**AWS**  |---|Cloud service donde terraform despliega los servicios| 

<h3>3.2 Dependencias usadas para el Proyecto de spring</h3>

| Dependencia | Uso |
| ---- | --- |
|**spring-boot-starter-webflux** | Dependencia para programación reactiva y creación de controladores|
|**spring-boot-starter-data-r2dbc**  | Dependencia obligatorio para conectar bases de datos con aplicaciones reactivas en spring  |
|**spring-boot-starter-validation**  | Dependencia para realizar validaciones de los datos que se |
|**r2dbc-postgresql** | Driver para conectar spring-web-flux con postgres | 
|**lombok** | Dependencia para tener menos código usando notaciones de java| 


<h3 id="ER">3.3 Modelo Entidad Relación y Base de datos </h3>

En esta sección se puede ver el diagrama Entidad Relación realizado para la prueba Técnica. Para el diagrama del modelo entidad relación se usa la notación de Richard Barker. Para la base de datos se usa Postgres 16. El script que se usa para la creación de las tablas se llama ``init.sql``

La prueba dice:

*Se requiere construir un API para manejar una lista de franquicias, Una franquicia se compone por un nombre y una lista de
sucursales y, a su vez, una sucursal está compuesta por un nombre y un listado de productos ofertados en la sucursal. Un
producto se compone de un nombre y una cantidad de Stock.*

Del anterior enunciado se concluye que:

- Franquicia es una tabla y tiene un atributo de nombre
- Sucursal es una tabla y tiene un atributo de nombre
- Producto es una tabla y tiene como atributs nombre y stock

- Franquicia está relacionada uno a muchos con Sucursales, una franquicia
puede tener de 0 a N Sucursales 

- Una Sucursal debe estár asociado a una y solo una franquia (relacion 1 a 1)

- Sucursal está relacionada uno a muchos con productos, una sucursal
puede tener de 0 a N Prodcutos 

- Un producto debe estar asociado a una y solo una sucursal
(relacion 1 a 1)

Modelando lo anterior se obtiene:

![modelo-er](./imagenesReadMe/Modelo%20E-R.webp)


<h2 id="despliege">4. Despliegue de la aplicacion con Terraform en AWS</h2>

Para el despliegue de la aplicación se usa Terraform y se usa AWS. La arquitectura de la aplicación es la siguiente

