# VideoMiner — Proyecto de Integración AISS

Proyecto de integración desarrollado para la asignatura de **AISS**, organizado en una arquitectura de microservicios que automatiza la extracción, transformación y almacenamiento de datos de plataformas de vídeo.

---

## Estructura del Proyecto

El repositorio está compuesto por tres módulos o servicios independientes de **Spring Boot** y un directorio de pruebas:

* **`videominer`**: El servicio central de la aplicación. Gestiona la base de datos embebida **H2** y expone la **API REST** principal con todo el modelo de datos.
* **`peertubeminer`**: Servicio de extracción de datos (Miner). Se conecta a la API externa de **PeerTube**, recopila la información de canales/vídeos y la envía de forma flexible al servicio central.
* **`dailymotionminer`**: Servicio de extracción de datos (Miner). Diseñado de forma análoga para consumir, transformar y volcar la información obtenida desde la plataforma **Dailymotion**.
* **`postman/`**: Carpeta contenedora del archivo `VideoMiner.postman_collection.json`, que incluye todas las peticiones listas para verificar el correcto funcionamiento del ecosistema.

---

## Características Principales

* **API REST Completa**: Endpoints CRUD totalmente operativos para la gestión integral de `Canales`, `Vídeos`, `Comentarios` (Comments) y `Subtítulos` (Captions).
* **Robustez y Control de Errores**: Sistema centralizado de gestión de excepciones (`GlobalExceptionHandler`) y validación de datos implementados en el núcleo de `videominer`.
* **Documentación Interactiva**: Controladores principales completamente anotados e integrados con **Swagger / OpenAPI** para una autogeneración limpia de la documentación.
* ** atería de Pruebas**: Tests de integración y unitarios básicos nativos de Spring Boot configurados a lo largo de los tres módulos.
* **Entorno de Pruebas Postman**: Colección exhaustiva con flujos de pruebas (`GET`, `POST`, `PUT`, `DELETE`) para simular la minería de datos y verificar la persistencia en base de datos.

---

## Cómo Empezar

### 1. Requisitos Previos
* Java 17 o superior
* Maven 3.x
* Postman (para la ejecución de pruebas)

### 2. Ejecución de los Servicios
Para levantar la arquitectura completa, inicia cada módulo de Spring Boot en su puerto correspondiente:
1. **VideoMiner** (Puerto `8080`)
2. **PeerTubeMiner** (Puerto `8081`)
3. **DailyMotionMiner** (Puerto `8082`)

### 3. Pruebas
Importa la colección ubicada en `postman/VideoMiner.postman_collection.json` en tu cliente de Postman y lanza las peticiones organizadas para validar el ecosistema de microservicios.
