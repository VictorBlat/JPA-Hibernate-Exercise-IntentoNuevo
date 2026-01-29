# UniGraoVerso
Un ejercicio increíble con un nombre terrible.

El objetivo de esta práctica es transformar una aplicación de escritorio básica en un sistema robusto de gestión de datos astronómicos utilizando JPA (Jakarta Persistence API) e Hibernate.

## Persistencia en dos bases de datos

El reto principal consiste en lograr que la aplicación sea capaz de persistir datos de forma intercambiable entre dos motores de base de datos (MySQL y SQLite) **sin modificar apenas el código fuente.**
Para ello, he decidido poner dos botones de inicio. Sentíos libre de modificar el mecanismo para intercambiar entre un sistema y otro.

**La aplicación final tiene que cargar datos desde ambos sistemas, y poder actualizar, añadir y eliminar planetas y sistemas solares.**


Conviene saber que:
- MySQL es un **servidor** de base de datos. Funciona con comunicaciones cliente-servidor en el puerto 3306.
- SQLite es una versión muy portable de MySQL. La base de datos se guarda en un fichero .db, y el usuario no necesita instalar nada para poder ejecutar la aplicación localmente.

## La estructura de la plantilla

La plantilla tiene todo el *frontend* de la aplicación ya realizado. Sigue el estándar Modelo-Vista-Controlador.
- Modelo: clases `Planet` y `SolarSystem`. Las clases que definen los datos y su estructura
- `UniGraoVerseController`: aquí se cargan y se centraliza el uso de datos
- `View`: consiste tanto en los archivos `.fxml` como en las clases `XViewController.java`. En estas, se maneja la lógica del frontend y se llama al controlador cuando se le requiere.
**Deberías crear tus clases propias para manejar la persistencia**

## Objetivos desglosados
Por si necesitas ir paso a paso:
- Añade al archivo `pom.xml` las dependencias necesarias de Hibernate, el driver de MySQL y SQLite
- Añade las etiquetas necesarias de Hibernate a las clases Planet y SolarSystem. Ten en cuenta que, si quieres que los planetas en SQL tengan una referencia a su SolarSystem correspondiente (recomendado), deberás añadir una referencia de cada Planet a su SolarSystem, junto a la lógica necesaria para esto.
- Crea el archivo `persistence.xml` en el directorio `resources/META-INF`. Deberás crear 2 unidades de persistencia
- Implementa las funcionalidades de persistencia. Es tu decisión editar y crear las clases necesarias para esto.
  - Nombres como `PlanetDAO` o `SolarSystemDAO` son bastantes adecuados. Estas clases técnicamente se consideran parte del model, aunque también puedes crear un package `DAO` para estas.
  - El `UniGraoVerseController` debe llamar a estas clases. Revisa todos los métodos que tiene, muchos de ellos deberán ser modificados.
  - En `PlayViewController` deberás editar el método `setupDeleteButton`. Puedes editar más métodos si lo crees conveniente, o si quieres añadir funcionalidades de manera opcional.
  - En `MainViewController` edita dos líneas para seleccionar la unidad de persistencia adecuada.
Edita 
## Entregar
- Un .zip con este repositorio o un link a un repositorio online. Puedes hacer un fork en github si lo prefieres. ¡No borres la carpeta `.git`!
- Un archivo `README.md` (borra/edita este) en el que expliques qué clases has creado, qué retos y problemas has tenido y las funcionalidades finales conseguidas.
- La base de datos exportada en formato tanto `.sql` (para MySQL) y el archivo `.db` (o como hayas decidido llamar a la base de datos SQLite)

# README - Victor Blat Giner
# Dependencias añadidas 
He añadido las siguientes dependencias al proyecto:

- Hibernate Core: El motor de persistencia que mapea objetos Java a tablas SQL
- MySQL Connector: Driver para conectar con bases de datos MySQL 
- SQLite JDBC: Driver para trabajar con SQLite
- Hibernate Community Dialects: Incluye soporte para SQLite

# Clases creadas
DAOController.java
- Clase controladora que gestiona la conexión con la base de datos:
  - init(String unitName): Inicializa la conexión con la unidad de persistencia elegida (MySQL o SQLite)
  - getEntityManagerFactory(): Devuelve la fábrica de EntityManager para hacer operaciones con la BD
  - close(): Cierra la conexión cuando terminamos

PlanetDAO.java
- Clase que maneja todas las operaciones de base de datos para los planetas:

  - obtenerTodos(): Recupera todos los planetas de la BD
  - obtenerPorId(int id): Busca un planeta específico por su ID
  - guardar(Planet p): Inserta un nuevo planeta en la BD
  - actualizar(Planet p): Modifica un planeta existente
  - eliminar(int id): Borra un planeta de la BD

SolarSystemDAO.java
- Igual que PlanetDAO pero para sistemas solares:
  - btenerTodos(): Lista todos los sistemas solares
  - obtenerPorId(int id): Busca un sistema solar por ID
  - guardar(SolarSystem ss): Guarda un nuevo sistema solar
  - actualizar(SolarSystem ss): Actualiza un sistema existente
  - eliminar(int id): Elimina un sistema solar

# Modificaciones en clases existentes
Planet.java y SolarSystem.java
- He añadido las anotaciones de JPA para que Hibernate sepa cómo mapear estas clases a tablas:
  - @Entity: Marca la clase como entidad persistente
  - @Table: Indica el nombre de la tabla en la BD
  - @Id: Define la clave primaria
  - @GeneratedValue: Hace que el ID se genere automáticamente
  - @Column: Mapea cada atributo a una columna
  - @ManyToOne y @OneToMany: Definen la relación entre planetas y sistemas solares

También he añadido en Planet una referencia a su SolarSystem para poder navegar la relación en ambas direcciones.
UniGraoVerseController.java

- He modificado todos los métodos CRUD para que llamen a los DAOs:

  - loadSolarSystems(): Ahora carga desde la BD en vez de memoria
  - addSolarSystem(): Guarda en BD además de añadir a la lista en memoria
  - addPlanet(): Persiste el nuevo planeta en la BD
  - updatePlanet(): Actualiza tanto en memoria como en BD
  - removePlanet() y removeSolarSystem(): Eliminan de la BD y de memoria

PlayViewController.java
- He Actualizado el método updatePlanetInController() para que use el ID correcto al actualizar (antes se usaba el ID del sistema solar en vez del ID del planeta).

MainViewController.java

- He añadido las llamadas a DAOController.init() para inicializar la unidad de persistencia correcta según el botón que pulse el usuario (MySQL o SQLite).

# Archivo persistence.xml
- He creado el archivo src/main/resources/META-INF/persistence.xml que configura las dos unidades de persistencia:

  - mysql-persistence-unit: Configuración para MySQL con la URL, usuario, contraseña y dialecto
  - sqlite-persistence-unit: Configuración para SQLite con la ruta del archivo .db y su dialecto

# Problemas encontrados y soluciones
Problema 1: Tablas innecesarias (SEQ, HTE)
- Error: Hibernate creaba tablas auxiliares como planets_SEQ, solar_systems_SEQ, HTE_planets y HTE_solar_systems.
  - Causa: Usaba GenerationType.AUTO que hace que Hibernate elija automáticamente la estrategia, creando tablas de secuencias.
- Solución intentada: Cambié a GenerationType.IDENTITY que usa el autoincremento nativo de la BD... pero apareció otro problema.
Problema 2: SQLite no soporta IDENTITY
- Error: SQLFeatureNotSupportedException: not implemented by SQLite JDBC driver
  - Causa: El driver de SQLite no implementa el método getGeneratedKeys() que necesita la estrategia IDENTITY.
- Solución final: Cambié la versión de la dependencia del sqlite debido a que lo tenia en una versión antigua que no soportaba dicho método
Problema 3: Error al actualizar planetas
- Error: Los planetas no se actualizaban correctamente.
  - Causa: En PlayViewController.updatePlanetInController() estaba pasando el ID del sistema solar en vez del ID del planeta al método del controlador.
- Solución: Cambié para que obtenga el ID correcto de la primera columna de la fila (row.get(0)).

# Funcionalidades finales conseguidas
- Persistencia dual: Funciona con MySQL y SQLite intercambiablemente
- Cargar datos: La aplicación carga automáticamente desde la BD al iniciar
- Añadir: Se pueden crear nuevos planetas y sistemas solares
- Editar: Los planetas se pueden modificar haciendo doble clic en sus celdas
- Eliminar: Botón para borrar planetas y sistemas solares
- Datos iniciales: Si la BD está vacía, se cargan datos hardcoded automáticamente
- Relaciones: Los planetas mantienen su relación con su sistema solar

# Estructura final de la base de datos

Tabla: solar_systems
- id (PK, autogenerado)
- name
- star_name
- star_distance
- radius

Tabla: planets
- id (PK, autogenerado)
- name
- number_of_moons
- mass
- radius
- gravity
- last_albedo_measurement
- has_rings
- solar_system_id (FK → solar_systems)


Autor: Victor Blat

Curso: 2º DAM

Fecha: 29 de Enero 2026