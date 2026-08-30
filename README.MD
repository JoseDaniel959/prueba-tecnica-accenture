
<h1 style="text-align:center;">Prueba técnica accenture</h1>

En este repositorio puede encontrar la solución de la prueba técnica **Prueba Practica Dev. Backend** el cual puede 
encontrar toda su descrición y criterios de aceptación en el archivo Prueba ````Prueba Practica Dev. Backend.pdf````

**Importante:** Todos los comandos mostrados en esta documentación son comandos de Linux, por lo que se pide amablemente al lector
que, si se encuentra usando Windows entonces por favor descargue WSL, el cual es un maquina virtual de Linux dentro de Windows y le permitirá ejecutar los comandos más fácilmente en caso de desear de ejecutar esta aplicación. Preferiblemente para WSL escoja
una imagen de ubuntu.

- <a href="#instalacion">1. Instalación</a>
    - <a href="#instalacionRapida">1.1 Instalación rápida</a>
    - <a href="#instalacionDesarrollo">1.1 Instalación para desarrollo</a>
-

<h2 id="instalacion">1 Instalación en Local</h2>

En esta sección se muestran 2 opciones para la instalación en local. La primera es la más rápida ya que solo usa docker y 
permite probar la aplicación directamente. La segunda opción se presenta como alternativa en caso de desear desarrollar 
codigo dentro de la aplicación (Para no estar ejecutando mvn package y docker compose up cada vez que se hace un cambio
en el código)

Antes de pasar a las opciones de instalación se debe de descargar el repositorio usando el siguiente commando

```console
~$ git clone 
```

También se debe de descargar docker y docker compose. En el siguiente <a href="https://docs.docker.com/engine/install/ubuntu/">link</a>
puede encontrar los pasos para la instalación en linux de docker y en el siguiente <a href="https://docs.docker.com/desktop/setup/install/linux/">link</a> puede 
encontrar los pasos para la instalación de docker compose


<h3 id="instalacionRapida" >1.1 Instalación Rápida</h3>

*DISCLAIMER: Esta opción de instalación es ideal cuando solo se quiere probar los endpoints de la aplicación y no para desarrollar ni probar los test de la aplicación*

Una vez se hayan ejecutado los pasos de la documentación oficial de docker se debe de ejecutar los siguiente pasos:

1. Asegurarse que dentro de el archivo ```application.properties``` esté la siguiente opción

```text
spring.profiles.active=docker
```
esto permitirá a la aplicación de spring-boot saber cual es la URL de la base de datos la 
cual se encuentra en un contenedor de docker

2. entrar a la carpeta application y hacer docker compose up

```console
~$ cd application/
~/application$ sudo docker compose up d --build
```

El anterior comando lo que hace es crear los servicios que está definidos dentro del 
```docker-compose.yaml```. El cual son 2 servicios: la RestFul API de java y un contenedor con
postgres 16

3. Hacer docker ps -a para ver el output de los contenedores corriendo
```console
~/application$ sudo docker ps -a
CONTAINER ID   IMAGE                   COMMAND                  CREATED          STATUS                     PORTS                                         NAMES
4925343806a5   java_app_image:0.0.1    "java -jar java-app.…"   24 seconds ago   Up 23 seconds              0.0.0.0:8080->8080/tcp, [::]:8080->8080/tcp   java_app
3b10b13de453   postgres:16.15-alpine   "docker-entrypoint.s…"   24 seconds ago   Up 23 seconds              0.0.0.0:5433->5432/tcp, [::]:5433->5432/tcp   postgres_db
```
Al ejecutar docker ps -a se imprime la tabla anterior. Se debe de asegurar que en la columna status diga **up**

4. Una vez tenga los servicios corriendo como se detalla en el paso anterior ya puede probar los 
endpoints de la aplicación. Se sugiere usar visual studio code y descargar la extension 
<a href=https://marketplace.visualstudio.com/items?itemName=humao.rest-client>REST Client</a> ya 
que en el proyecto se encuentra un archivo llamado ```consultas.http``` el cual ya tiene 
todas las consultas http listas para probar la aplicación

<h3 id="instalacionDesarrollo">1.2 Instalación para desarrollar</h3>

*DISCLAIMER: Esta alternativa es ideal cuando se desee desarrollar código en el proyecto corriendo y ejecutar los test de la aplicación*


1. Asegurarse que dentro de el archivo ```application.properties``` esté la siguiente opción

```text
spring.profiles.active=dev
```

2. Descargar el openjdk 17

```console
~$ sudo apt-get install openjdk-17-jre
```

3. Asegurarse de estar en la versión 17 con el siguiente comando
```console
~$ java -version
openjdk version "17.0.20" 2026-07-21
OpenJDK Runtime Environment (build 17.0.20+8-1-24.04-Ubuntu)
OpenJDK 64-Bit Server VM (build 17.0.20+8-1-24.04-Ubuntu, mixed mode, sharing)
```
si el anterior comando no le muestra un output, es porque no se descargó java correctamente y lo debe
de volver a instalar

4. Descargar Maven
```console 
~$ sudo apt install maven
```

5. Verificar que Maven está instalado

```console 
~$ maven -version
Maven home: /usr/share/maven
Java version: 17.0.20, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "6.6.87.2-microsoft-standard-wsl2", arch: "amd64", family: "unix"
```
si el anterior comando no le muestra un output, es porque no se descargó maven correctamente y lo debe
de volver a instalar

6. Tener Docker y Docker Compose instalado

```console 
~$ docker -v
```

En caso de no tenerlo instalado o no esté corriendo dentro del sistema, por favor leer los pasos para instalarlo y ejecutarlo en
<a href="https://docs.docker.com/engine/install/ubuntu/">Ubuntu</a>

7. Ejecutar el docker compose dentro de la carpeta de application/

```console
~$ cd application/
~/application$ sudo docker compose -f dev.docker-compose.yaml up --build -d
```

8. Cuando el anterior comando finalice, ejecutar el siguiente comando
```console
~/application$ sudo docker exec postgres_db psql -U postgres -d test -c "
CREATE TABLE FRANQUICIA(
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
    stock bigint NOT NULL CHECK (stock > 0),
    sucursal_id bigint REFERENCES sucursal(id)
);
"
```




El anterior comando crea las tablas dentro de la base de datos test. La sentencia que crea esta base de datos está en
el archivo ```init.sql```