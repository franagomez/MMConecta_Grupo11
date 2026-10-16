# Estado de verificación

- Base Android creada con Compose, Material 3, ViewModel y repositorio de contenidos ficticios.
- Android Studio y SDK configurados en el equipo de Michel. Gradle usa Java 21.
- La captura del equipo mostró instalación exitosa y MMConecta ejecutándose en el emulador API 37.
- Prueba manual realizada en el emulador: Chocolatería muestra una tarjeta y Todos restaura las tres.
- Pendiente: comprobar ejecución en el equipo de Francisca y completar funciones de la evaluación.
- Persistencia, cámara, notificaciones y animaciones todavía no implementados.
- No se declara una revisión completa de accesibilidad ni compatibilidad con todos los dispositivos.

## Detalle de contenido

- assembleDebug completado correctamente.
- Instalación y ejecución verificadas en emulador API 37.
- Chocolatería: abrir tarjeta muestra su detalle; Volver conserva el filtro.
- Pastelería: abrir tarjeta muestra un detalle distinto; Atrás de Android conserva el filtro.
- Contenido de demostración identificado como no oficial de Master Martini.
- Pendiente de revisión cruzada de Francisca antes de integrar la rama.

## Perfil (rama codex/perfil-usuario)

- Acceso desde Mi perfil en el catálogo y regreso con Volver o Atrás.
- Nombre, correo, tipo de negocio e intereses con errores por campo.
- ProfileViewModel gestiona el formulario y valida antes de guardar; la pantalla presenta el estado.
- Guardado solo en memoria. No sustituye el requisito de almacenamiento local.
- `testDebugUnitTest` y `assembleDebug`: correctos. Cinco pruebas cubren campos vacíos, correos incorrectos, límites del nombre, opciones, normalización y cambios después de guardar.
- Pendiente: prueba visual del formulario en emulador y revisión cruzada de Francisca.
- Esta rama parte de codex/detalle-contenido; integrar primero el detalle y luego el perfil.

### Recorrido para ambas

1. Abrir Mi perfil y pulsar Guardar perfil sin datos: deben aparecer los cuatro errores.
2. Probar Ana Demo, correo incorrecto, Pastelería y Panadería como interés: el correo debe impedir guardar.
3. Corregir a ana@example.com: debe aparecer la confirmación de guardado durante la sesión.
4. Cambiar un campo: la confirmación debe desaparecer. Volver al catálogo y reabrir el perfil conserva el borrador en memoria.
5. Explicar por qué cerrar el proceso pierde los datos: falta incorporar la persistencia.
