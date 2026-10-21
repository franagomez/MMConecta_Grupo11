# Estado de verificación

- Base Android creada con Compose, Material 3, ViewModel y repositorio de contenidos ficticios.
- Android Studio y SDK configurados en el equipo de Michel. Gradle usa Java 21.
- La captura del equipo mostró instalación exitosa y MMConecta ejecutándose en el emulador API 37.
- Prueba manual realizada en el emulador: Chocolatería muestra una tarjeta y Todos restaura las tres.
- Pendiente: comprobar ejecución en el equipo de Francisca y completar funciones de la evaluación.
- Cámara implementada en codex/camara-perfil; notificaciones y animaciones pendientes. Las verificaciones de cada avance se detallan abajo.
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

## Verificación manual de favoritos — 17 octubre 2026

Versión probada: rama codex/favoritos, commit 4fd0d6d, emulador Medium Phone API 37.

- Guardar Chocolatería cambia a Quitar de favoritos sin abrir el detalle.
- Mis favoritos muestra únicamente el contenido guardado.
- Combinar con Pastelería muestra lista vacía; volver a Chocolatería recupera la tarjeta.
- Abrir detalle y Volver conserva categoría y filtro Mis favoritos.
- Detener con Stop de Android Studio, ejecutar con Run y activar Mis favoritos recupera el contenido guardado.
- Quitar el último favorito muestra lista vacía; desactivar el filtro restaura el catálogo.
- No se observaron fallos en este recorrido. Queda pendiente revisión cruzada de Francisca y accesibilidad/orientación en otros tamaños. Tarea #3: en revisión, no cerrada.

## Cámara del perfil — 17 octubre 2026

Rama `codex/camara-perfil`, basada en `codex/favoritos`. Pendiente de revisión de Francisca; no integrada a main.

- Captura delegada mediante TakePicturePreview: devuelve una miniatura Bitmap. No requiere URI de salida ni FileProvider en este flujo; no es una captura de resolución completa.
- La app no solicita permiso CAMERA porque abre una aplicación de cámara externa. Si no puede abrirla, muestra un error. Se deben comprobar otros dispositivos y políticas de cámara.
- PNG privado en filesDir, escritura con AtomicFile en IO. Cancelar no escribe; un fallo conserva el estado anterior y muestra error.
- Pruebas automáticas: compilación correcta, 14 pruebas unitarias y 3 de repositorios en emulador API 37 aprobadas. La prueba de imagen usa bitmaps sintéticos en carpeta temporal y comprueba lectura y reemplazo.
- Prueba manual API 37: abrir cámara, capturar escena sintética del emulador y confirmar muestra la miniatura y mensaje de guardado.
- Abrir de nuevo y cancelar con Atrás conserva la imagen y muestra mensaje de cancelación.
- Stop y Run de Android Studio, luego Mi perfil: la imagen se recupera del almacenamiento.
- Pendiente: revisión cruzada, dispositivo físico, orientación, accesibilidad y simulación de fallo real de escritura/cámara no disponible. El manejo de error existe, pero no se declara verificado manualmente en esas condiciones.
- Cámara es el primer recurso nativo; notificaciones siguen pendientes (#6). No se declara cumplida la rúbrica completa.

## Mensajes y avisos del perfil

Rama `codex/mensajes-perfil`, basada en `codex/camara-perfil`. Avance parcial de #7.

- Transición breve de entrada/salida en mensajes de guardado, validación y cámara. El texto permanece visible hasta que cambia el estado.
- Región semántica Polite para que los lectores de pantalla puedan anunciar cambios sin interrumpir. No equivale a una prueba con TalkBack.
- Prioridad del aviso: operación en curso, error de almacenamiento, confirmación y errores del formulario.
- `testDebugUnitTest assembleDebug`: correcto. No se añadieron pruebas que repliquen la animación.
- Pendientes: revisión visual en emulador, TalkBack, teclado virtual, texto grande, orientación y revisión de Francisca. La tarea #7 continúa abierta.

## Revisión conjunta de avances

- Rama `codex/revision-avances`: combina `codex/mensajes-perfil` y el avance de Fran `8a0b727` de `codex/camara-perfil`, conservando la historia de ambas. Sin conflictos de Git.
- `testDebugUnitTest assembleDebug` aprobado el 20 de octubre. Esto verifica compilación y pruebas unitarias existentes; no verifica todavía la entrega real de notificaciones.
- La rama `feature/catalogo` (7f09142) sigue separada. Su navegación requiere comparación antes de integrarla con el detalle que ya existe.
- Hallazgo para el siguiente paso: el notificador comprueba POST_NOTIFICATIONS, pero no comprueba si el canal está bloqueado. Puede mostrar “Notificación enviada” aunque Android no la muestre. Pendiente corrección y prueba de canal desactivado.
- Pendiente recorrido en emulador con permiso concedido/denegado y revisión cruzada. No integrado a main.

### Corrección del aviso de notificaciones

El notificador consulta si las notificaciones de la app están habilitadas y si el canal Contenido nuevo tiene importancia NONE antes de publicar. En esos casos el ViewModel muestra un mensaje para revisar Ajustes, sin confirmar envío. Se conserva el manejo independiente del permiso POST_NOTIFICATIONS.

Compilación y pruebas unitarias aprobadas. Dos pruebas nuevas comprueban el error por bloqueo con posterior reintento y la falta de permiso usando un notificador simulado. Falta comprobar el bloqueo real desde Ajustes en el emulador; estas pruebas no sustituyen ese recorrido.

### Comprobación Android del canal de notificaciones

`connectedDebugAndroidTest` aprobado el 21 de octubre: 4 pruebas en Medium Phone API 37. La nueva prueba comprueba la creación y nombre del canal real y, si el permiso o canal están bloqueados en ese momento, comprueba que el notificador rechaza el envío. No modifica permisos ni publica avisos. Al depender de la configuración actual, no demuestra por sí sola ambos escenarios de bloqueo.

La publicación visible, la denegación desde el diálogo y el bloqueo manual del canal siguen pendientes. Las otras tres pruebas verifican los repositorios de perfil, favoritos e imagen.
