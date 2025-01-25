
**API de Notas**

**Descripción**

Esta es una API para la gestión de notas, etiquetas y usuarios. Proporciona funcionalidades para manejar notas, archivar o desarchivar notas, gestionar etiquetas, y autenticar usuarios.


**Cómo probar los endpoints**


**1. Configuración del Proyecto**

1.  **Clonar el repositorio:**

    Primero, clona el repositorio del proyecto en tu máquina local:
    ```
    git clone https://github.com/GutberBG/BackendPruebaUnion.git
    cd nombre-del-repositorio

2.  **Base de datos:**

    -   Crea una base de datos en PostgreSQL llamada `db_notes`.

    -   Asegúrate de tener los datos correctos en el archivo `.env` para la conexión a la base de datos, como el URL, usuario, contraseña y puerto.


    **Ejemplo de `.env`:**
    ```
    SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/db_notes
    SPRING_DATASOURCE_USERNAME=tu_usuario
    SPRING_DATASOURCE_PASSWORD=tu_contraseña

3.  **Instalar las dependencias:**

    Si es necesario, instala las dependencias del proyecto ejecutando el siguiente comando (asumiendo que el proyecto está basado en Maven):

    `mvn clean install`

4.  **Ejecutar el Proyecto:**

    Para ejecutar el proyecto en tu máquina local, usa el siguiente comando:

    `mvn spring-boot:run`

    Esto iniciará la API en `http://localhost:8080`.

5.  **Crear el Usuario:** Para probar los endpoints que requieren autenticación, primero debes crear un usuario mediante el endpoint de registro:

- Realiza una solicitud **POST** a /api/auth/register con el nombre de usuario, contraseña y correo electrónico.

6.  **Autenticación:** Después de registrar un usuario, realiza una solicitud **POST** a /api/auth/login con el nombre de usuario y contraseña para obtener un **token JWT**. Este token será necesario para autenticar tus solicitudes.

**2. Probar los Endpoints con Swagger**

1. Dirígete a[ http://localhost:8080/swagger-ui/index.html.](http://localhost:8080/swagger-ui/index.html)

1. Haz clic en el botón **"Authorize"** en la esquina superior derecha.

1. Introduce el token JWT en el formato:

```
Bearer <tu_token_jwt_aqui>
  ```

4. Haz clic en **Authorize** para autenticarte.

5. Ahora puedes probar los endpoints que requieren autenticación.


**Notas Importantes**



-  **Autenticación**: Para acceder a cualquier endpoint protegido, debes crear primero un usuario y obtener el token JWT.

-  **Base de Datos**: Asegúrate de configurar correctamente tu base de datos PostgreSQL en el archivo .env.

-  **Swagger**: Usa Swagger para probar los endpoints de manera fácil y rápida después de autorizarte con el token JWT.

**Funcionalidades**
## **1. Gestión de Usuarios**

-   **Registro de nuevos usuarios**:  
    Permite registrar un nuevo usuario en el sistema.  
    **Endpoint**: `POST /api/auth/register`

-   **Autenticación de usuario**:  
    Permite a un usuario iniciar sesión y obtener un token JWT para autenticarse.  
    **Endpoint**: `POST /api/auth/login`

-   **Obtener información del usuario autenticado**:  
    Obtiene la información del usuario basado en su ID.  
    **Endpoint**: `GET /api/auth/user/{id}`


----------

## **2. Gestión de Notas**

-   **Crear una nueva nota para el usuario**:  
    Permite al usuario crear una nueva nota con un título y contenido.  
    **Endpoint**: `POST /api/notes`

-   **Ver detalles de una nota específica**:  
    Permite ver los detalles de una nota específica usando su ID.  
    **Endpoint**: `GET /api/notes/{id}`

-   **Editar una nota existente**:  
    Permite al usuario actualizar el contenido o título de una nota específica.  
    **Endpoint**: `PUT /api/notes/{id}`

-   **Eliminar una nota**:  
    Permite eliminar una nota específica usando su ID.  
    **Endpoint**: `DELETE /api/notes/{id}`

-   **Archivar una nota**:  
    Permite archivar una nota específica.  
    **Endpoint**: `POST /api/notes/{id}/archive`

-   **Desarchivar una nota**:  
    Permite desarchivar una nota específica.  
    **Endpoint**: `POST /api/notes/{id}/unarchive`

-   **Listar todas las notas de un usuario específico**:  
    Devuelve todas las notas de un usuario específico por su ID.  
    **Endpoint**: `GET /api/notes/user/{userId}`

-   **Listar todas las notas archivadas de un usuario específico**:  
    Devuelve todas las notas archivadas de un usuario específico.  
    **Endpoint**: `GET /api/notes/user/{userId}/archived`

-   **Búsqueda avanzada de notas**:  
    Permite realizar una búsqueda avanzada de notas por título, contenido o etiquetas.  
    **Endpoint**: `GET /api/notes/search`

-   **Obtener el estado de filtros y búsquedas**:  
    Permite recuperar el estado de los filtros y las búsquedas guardadas del usuario.  
    **Endpoint**: `GET /api/notes/search/state`


----------

## **3. Gestión de Etiquetas**

-   **Obtener todas las etiquetas de un usuario**:  
    Obtiene todas las etiquetas asociadas a un usuario específico.  
    **Endpoint**: `GET /api/tags/user/{userId}`

-   **Crear una nueva etiqueta para un usuario**:  
    Permite crear una nueva etiqueta asociada a un usuario específico.  
    **Endpoint**: `POST /api/tags/user/{userId}`

-   **Listar todas las etiquetas disponibles**:  
    Devuelve todas las etiquetas disponibles en el sistema.  
    **Endpoint**: `GET /api/tags`

-   **Eliminar una etiqueta**:  
    Permite eliminar una etiqueta específica.  
    **Endpoint**: `DELETE /api/tags/{id}`


----------

## **4. Funciones Avanzadas (Extra)**

-   **Archivar una nota**:  
    Permite mover una nota a la sección de archivadas para mantenerla disponible pero fuera de la vista principal.  
    **Endpoint**: `POST /api/notes/{id}/archive`

-   **Desarchivar una nota**:  
    Permite restaurar una nota archivada a su estado normal.  
    **Endpoint**: `POST /api/notes/{id}/unarchive`

-   **Búsqueda avanzada por título, etiquetas o contenido**:  
    Permite realizar búsquedas avanzadas de notas utilizando parámetros como el título, las etiquetas o el contenido.  
    **Endpoint**: `GET /api/notes/search`

-   **Guardar el estado de filtros y búsquedas**:  
    Permite almacenar el estado actual de los filtros y las búsquedas del usuario.  
    **Endpoint**: `GET /api/notes/search/state`

-   **Recuperar el estado de filtros y búsquedas guardado**:  
    Permite recuperar los filtros y las búsquedas guardadas anteriormente por el usuario.  
    **Endpoint**: `GET /api/notes/search/state`