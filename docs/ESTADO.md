# Estado de verificación

- Base Android creada con Compose, Material 3, ViewModel y repositorio de contenidos ficticios.
- Android Studio y SDK configurados en el equipo de Michel. Gradle usa Java 21.
- La captura del equipo mostró instalación exitosa y MMConecta ejecutándose en el emulador API 37.
- Prueba manual realizada en el emulador: Chocolatería muestra una tarjeta y Todos restaura las tres.
- Pendiente: comprobar ejecución en el equipo de Francisca y completar funciones de la evaluación.
- Cámara, notificaciones y animaciones todavía no implementados. La persistencia del perfil se detalla abajo.
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

## Persistencia del perfil

- LocalProfileRepository lee y escribe preferencias privadas de la app fuera del hilo principal.
- ProfileViewModel carga el perfil al crearse y confirma el guardado después de escribir. Mientras carga o guarda, desactiva la edición para evitar carreras.
- Los fallos de carga y escritura muestran mensajes; un guardado fallido permite reintentar.
- Ocho pruebas unitarias y compilación correctas antes de la prueba Android. Incluyen recuperación mediante repositorio simulado y fallo/reintento de escritura.
- Ajuste al plan inicial: para este perfil pequeño se usan SharedPreferences, no Room. Room para favoritos sigue pendiente; no se declara cumplido el requisito completo de persistencia de la evaluación.
- El recorrido de la sección anterior corresponde a la versión inicial en memoria: ahora la confirmación indica guardado en el dispositivo y los datos guardados deben conservarse al reiniciar.
- Pendiente: recorrido visual completo, cierre del proceso y reapertura desde la interfaz, revisión de Francisca.

- `connectedDebugAndroidTest`: una prueba aprobada en Medium Phone API 37. Guarda, recupera desde otra instancia del repositorio y actualiza todos los datos usando preferencias de prueba separadas; no modifica el perfil del usuario. Esta prueba no simula muerte del proceso.

## Verificación manual del perfil en emulador API 37

- Abrir Mi perfil desde el catálogo y regresar con Atrás: correcto.
- Guardar vacío: aparecen errores de nombre, correo, negocio e intereses.
- Completar Ana Demo, ana@example.com, negocio Pastelería e interés Panadería: los errores desaparecen y Guardar muestra confirmación local.
- Detener el proceso con Stop de Android Studio, volver a ejecutar con Run y abrir Mi perfil: recupera los cuatro valores guardados.
- Esta prueba cierra el pendiente de guardado y reapertura en este emulador. Falta revisar teclado virtual, orientación, tamaños de letra y el equipo de Francisca; no equivale a una revisión completa de accesibilidad.

## Favoritos (rama codex/favoritos)

- Rama basada en codex/perfil-usuario; revisar e integrar primero el perfil.
- Guardar/quitar desde tarjetas y filtro Mis favoritos combinado con categorías.
- Mensaje de lista vacía, indicador de carga y errores recuperables. Los botones esperan a que termine la lectura/escritura; no se cambia la selección si falla el guardado.
- Almacena solo identificadores mediante SharedPreferences. Ajuste al plan inicial: Room no se incorpora en este paso porque únicamente se necesita un conjunto de identificadores; no se afirma que el proyecto use Room.
- Pendiente: recorrido visual de favoritos y reinicio del proceso con favoritos guardados, revisión de Francisca.

### Prueba manual de favoritos

1. Guardar una tarjeta desde Todos y activar Mis favoritos: debe quedar solo esa tarjeta.
2. Elegir otra categoría: debe aparecer el mensaje de lista vacía. Volver a Todos recupera la tarjeta.
3. Abrir el detalle y volver: deben mantenerse los filtros.
4. Detener y reabrir la app; activar Mis favoritos: debe recuperarse la selección guardada.
5. Quitar el último favorito: aparece la lista vacía. Desactivar Mis favoritos recupera el catálogo.

- Verificación automática: `testDebugUnitTest assembleDebug connectedDebugAndroidTest` correcto. 12 pruebas unitarias (4 de favoritos y 8 de perfil) y 2 pruebas Android de repositorios aprobadas en API 37. La prueba de favoritos verifica guardar, recuperar desde otra instancia, quitar uno y vaciar la selección; usa preferencias de prueba separadas.
