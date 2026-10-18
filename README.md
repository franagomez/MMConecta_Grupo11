# MMConecta · Grupo 11

Prototipo académico DSY1105 para el caso Master Martini. Equipo: Michel y Francisca.

## Estado

Prototipo con catálogo y detalle, perfil validado, favoritos persistentes e imagen opcional tomada con la cámara. Kotlin, Compose, Material 3 y lógica separada en ViewModels y repositorios. Esta rama incluye avances pendientes de revisión cruzada; las notificaciones y animaciones siguen pendientes.

## Abrir y ejecutar

1. Instalar Android Studio desde https://developer.android.com/studio.
3. Abrir esta carpeta como proyecto, no la carpeta superior.
4. Configurar SDK Android 35 y Build Tools 35.0.0 desde SDK Manager.
5. Usar JDK 17 o 21 para Gradle y sincronizar el proyecto.
6. Crear un emulador API 26 o superior (preferentemente API 35), o conectar un dispositivo.
7. Ejecutar `app`. Verificar que se muestran tres tarjetas y que cada filtro muestra su categoría.

También se puede compilar con `./gradlew assembleDebug` una vez instalado el SDK y configurado `local.properties` con `sdk.dir` o la variable `ANDROID_HOME`.

La base se instaló y ejecutó en el emulador API 37. Se verificaron los filtros Chocolatería y Todos. Ver docs/ESTADO.md para el alcance de la revisión.

## Organización

- `model`: estructura de datos.
- `repository`: obtención de contenidos ficticios.
- `viewmodel`: HomeViewModel y HomeUiState; lógica de filtrado y estado.
- `ui/home`: pantalla inicial; representa el estado y envía eventos al ViewModel.
- `ui/theme`: tema provisional claro/oscuro.
- `MainActivity`: punto de entrada.

## Trabajo en pareja

Propuesta para acordar entre ambas: Francisca puede comenzar catálogo/detalle y Michel perfil/validaciones. Revisar juntas navegación, persistencia y recursos nativos; ambas deben poder explicar y modificar todas las partes.

Trabajar con ramas `feature/catalogo` y `feature/perfil`, commits pequeños y revisión de la compañera antes de integrar. No compartir `local.properties`, claves ni datos personales. La asignación es propuesta, no un reparto confirmado.

## Próximas etapas

- Revisión cruzada e integración de las ramas de detalle, perfil, favoritos y cámara.
- Notificaciones locales (segundo recurso nativo, tarea #6 de Francisca).
- Animaciones útiles y pruebas del recorrido completo.
- Confirmar en la rúbrica EP2 si se exige Room: actualmente perfil y favoritos usan SharedPreferences; la imagen usa almacenamiento interno.
- Ensayo de defensa individual con cambios de código.

## Control de requisitos

Consultar `docs/CONTROL_RUBRICA.md` antes de ampliar el alcance o marcar una función como terminada.

## Plan de trabajo compartido

- [Cronograma diario](docs/CRONOGRAMA.md)
- [Tareas asignadas a Michel y Francisca](https://github.com/franagomez/MMConecta_Grupo11/issues)

## Primer inicio de Francisca

En Android Studio, seleccionar Clone Repository e ingresar https://github.com/franagomez/MMConecta_Grupo11.git. Elegir una carpeta propia. Al abrir, usar JVM 21 si Android Studio ofrece cambiarla, instalar SDK 35 cuando lo solicite y sincronizar Gradle. Ejecutar app en un emulador o dispositivo API 26 o superior. Confirmar que Chocolatería muestra una tarjeta y Todos muestra tres. No copiar local.properties desde otro computador.

## Perfil de demostración

Desde **Mi perfil** se puede completar nombre, correo, tipo de negocio e intereses con datos ficticios. La validación está en `ProfileViewModel`, separada de la pantalla Compose. El perfil se guarda localmente con SharedPreferences mediante un repositorio. Solo los cambios confirmados con Guardar perfil se recuperan al volver a abrir la app. Ver `docs/ESTADO.md` para pruebas realizadas y pendientes.

## Favoritos

Cada tarjeta permite guardar o quitar el contenido de favoritos. El filtro **Mis favoritos** se combina con la categoría seleccionada y muestra un mensaje si la lista está vacía. Los identificadores se conservan en preferencias locales mediante `LocalFavoritesRepository`; la pantalla solo presenta el estado del ViewModel.

## Cámara del perfil

Mi perfil → Tomar imagen de prueba abre la aplicación de cámara. Al confirmar, guarda una miniatura PNG en el almacenamiento interno; cancelar conserva la anterior. No se guarda una foto de resolución completa ni se sube a un servidor. La imagen se guarda independientemente del formulario. `ProfilePhotoViewModel` gestiona el estado y `LocalProfilePhotoRepository` la escritura. Revisar pruebas y limitaciones en `docs/ESTADO.md`.
