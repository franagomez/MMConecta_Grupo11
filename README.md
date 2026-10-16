# MMConecta · Grupo 11

Prototipo académico DSY1105 para el caso Master Martini. Equipo: Michel y Francisca.

## Estado

Base inicial con Kotlin, Jetpack Compose, Material 3 y separación Model / Repository / ViewModel / Screen. Inicio con tres contenidos ficticios y filtro de categoría. La selección vive en memoria; aún no hay persistencia, formularios, navegación a detalles ni recursos nativos.

## Abrir y ejecutar

1. Instalar Android Studio desde https://deve
2. loper.android.com/studio.
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

- Navegación y pantalla de detalle.
- Perfil ficticio con validaciones y estados de error.
- Favoritos y perfil persistidos con Room.
- Dos recursos nativos previstos: cámara (contenido sintético/de prueba) y notificaciones locales (recordatorio de contenido). Ambos deben manejar los permisos que correspondan, cancelación o denegación y demostrarse individualmente. Aún no implementados.
- Animaciones útiles y pruebas del recorrido completo.
- Ensayo de defensa individual con cambios de código.

## Control de requisitos

Consultar `docs/CONTROL_RUBRICA.md` antes de ampliar el alcance o marcar una función como terminada.

## Plan de trabajo compartido

- [Cronograma diario](docs/CRONOGRAMA.md)
- [Cronograma editable en Word](docs/Cronograma_MMConecta_Michel_y_Francisca.docx)

## Primer inicio de Francisca

En Android Studio, seleccionar Clone Repository e ingresar https://github.com/franagomez/MMConecta_Grupo11.git. Elegir una carpeta propia. Al abrir, usar JVM 21 si Android Studio ofrece cambiarla, instalar SDK 35 cuando lo solicite y sincronizar Gradle. Ejecutar app en un emulador o dispositivo API 26 o superior. Confirmar que Chocolatería muestra una tarjeta y Todos muestra tres. No copiar local.properties desde otro computador.
