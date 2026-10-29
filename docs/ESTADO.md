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

### Recorrido manual de notificaciones

Pruebas realizadas el 21 y 22 de octubre en Medium Phone API 37, sobre `codex/revision-avances` (1969025). Completan los pendientes manuales del apartado anterior.

| Caso | Resultado observado |
| --- | --- |
| Denegar el permiso desde el diálogo Android | La app muestra permiso denegado y permite volver a intentarlo. |
| Conceder el permiso y enviar | Aparece “Notificación enviada.” y el panel de Android muestra “Nuevo contenido en MMConecta” con el texto de la receta. |
| Desactivar solo Contenido nuevo, manteniendo habilitadas las notificaciones de la app | Al pulsar el botón de prueba aparece el aviso de canal desactivado; no aparece una confirmación de envío. |
| Reactivar Contenido nuevo y reintentar sin reiniciar la app | El error se reemplaza por “Notificación enviada.” y reaparece el icono de notificación en la barra de Android. |

Ruta utilizada: panel de notificaciones → ajustes → Notificaciones de las apps → MMConecta → Otras → Contenido nuevo. El canal quedó activado al terminar.

No fue necesario cambiar código durante este recorrido. Se comprobó la publicación visual y la recuperación tras el bloqueo; no se verificaron sonido, lectura hablada con TalkBack, pulsación del aviso ni dispositivos físicos. La revisión cruzada de Francisca y la tarea #6 siguen pendientes de cierre.

### Conservación del detalle durante la transición

La navegación usa destinos `AppScreen` en lugar de cadenas. El destino Detail contiene el LearningContent que se está mostrando: al volver al catálogo, AnimatedContent puede seguir dibujando ese detalle durante fadeOut, aunque selectedId ya se haya limpiado. Antes, el detalle saliente consultaba selectedContent del estado actual y podía quedar vacío de inmediato.

Se mantiene la animación de Fran y el manejo existente de Volver/Atrás. `testDebugUnitTest assembleDebug` aprobado el 22 de octubre. Las pruebas unitarias existentes no verifican fotogramas de animación. Pendiente comprobación visual: abrir una tarjeta, volver con el botón y con Atrás, y verificar que el contenido permanezca durante la salida y que los filtros se conserven.

## Integración de las ramas publicadas

Revisión del 22 de octubre. `codex/revision-avances` reúne los avances publicados de Michel y Francisca, incluida `feature/catalogo` (7f09142). El merge conserva los commits y sus autores originales.

- Se adopta Navigation Compose de Francisca para catálogo, detalle y perfil. Los destinos de detalle mantienen su propio argumento durante la salida; esto sustituye el AppScreen temporal sin volver a depender de una selección global que se borra.
- Se conserva ContentDetailScreen como única pantalla de detalle, con pasos, desplazamiento y aviso de contenido académico. Se incorpora el estado “No se encontró el contenido” de la alternativa de Francisca. Se elimina la pantalla duplicada y se conserva una sola búsqueda de contenido en HomeViewModel.
- Se mantienen perfil y validaciones, favoritos y filtros, persistencia local, cámara, notificaciones y sus correcciones. Los ViewModels permanecen fuera de los destinos para conservar el estado durante la navegación.
- Las transiciones de fundido se configuran en NavHost, incluido el regreso. Se evita abrir destinos duplicados mediante launchSingleTop.
- README actualizado con las funciones actuales y los pasos para traer la rama integrada.

Validación: compilación y 16 pruebas unitarias aprobadas; 7 pruebas Android aprobadas en Medium Phone API 37 (4 de repositorios y 3 nuevas de navegación). Las pruebas nuevas cubren botón Volver y dispatcher Atrás conservando categoría, recreación de actividad en detalle y borrador del perfil al volver/recrear sin guardarlo. Recreación de actividad no equivale a muerte del proceso.

La primera ejecución detectó incompatibilidad de Espresso antiguo con API 37. Se actualizaron únicamente las dependencias de pruebas a Runner 1.7.0, JUnit 1.3.0 y Espresso 3.7.0; la corrección de InputManager está descrita en las [notas oficiales de AndroidX Test](https://developer.android.com/jetpack/androidx/releases/test). La segunda ejecución mostró que la prueba enviaba Atrás antes de que el destino estuviera listo; se corrigió la sincronización con runOnIdle y una comprobación del detalle antes de volver. La ejecución final pasó completa.

La integración técnica está preparada en una sola rama. Falta la revisión cruzada humana antes del merge a main según el protocolo del equipo. No se atribuye a Francisca una revisión que todavía no ha realizado ni se declara cerrada la rúbrica completa. Siguen pendientes las pruebas amplias de accesibilidad, texto grande, dispositivos físicos y la defensa individual.

## Adaptación del detalle y encabezados

Revisión del 23 de octubre: los títulos del contenido y “Para comenzar” incluyen semántica de encabezado, para permitir navegación por secciones con lectores de pantalla.

Nuevas pruebas Android:

- Detalle con descripción larga en un área de 320 × 480 dp y fontScale 2 dentro de la composición de prueba: permite desplazarse hasta el último paso y activar Volver. Comprueba también los encabezados semánticos. No cambia el tamaño de letra del sistema.
- Contenido inexistente: muestra el mensaje correspondiente y permite volver.
- Giro real de la actividad a horizontal desde el detalle: conserva el destino, permite acceder a los pasos y volver al catálogo. Restaura requestedOrientation al finalizar.

Estas comprobaciones no sustituyen la inspección visual de todas las pantallas, la lectura hablada con TalkBack ni las pruebas en un teléfono físico.

Resultado: compilación y 16 pruebas unitarias aprobadas; ejecución final de 10 pruebas Android aprobada en API 37. Se corrigió el desplazamiento de la prueba para buscar nodos todavía no compuestos en la lista; no fue un fallo de navegación de la app.

## Apertura desde notificaciones — 27 de octubre

La notificación local incorpora un PendingIntent inmutable dirigido a MainActivity. Al tocarla puede abrir MMConecta y se configura su eliminación automática. Si la actividad ya está abierta, se reutiliza; no se incorpora navegación a una receta concreta.

Validación: compilación y 16 pruebas unitarias aprobadas; 11 pruebas Android aprobadas en API 37. La nueva prueba publica un aviso y verifica título, texto, acción asociada al paquete de la app y marca de eliminación automática. Concede el permiso de notificaciones durante la prueba y limpia el aviso al finalizar. La primera ejecución consultó la publicación demasiado pronto; se añadió una espera acotada de cinco segundos y la ejecución completa pasó.

Pendiente: tocar el aviso desde la bandeja, con la app en segundo plano y cerrada, para verificar visualmente la apertura. La prueba automatizada comprueba la acción configurada, pero no realiza ese toque. Se mantiene pendiente la revisión cruzada antes de integrar a main.

## Formulario y acciones del teclado — 29 de octubre

Nombre ofrece la acción Siguiente y transfiere el foco a Correo. Correo mantiene su tipo de teclado de email y ofrece Listo, que cierra la entrada sin guardar. Guardar perfil cierra el teclado y llama al flujo de validación y persistencia existente. Ambos campos se componen juntos para que el destino del foco esté disponible en la lista. Se conserva imePadding y el desplazamiento del formulario.

La prueba nueva comprueba el cambio de foco, conservación del borrador, ausencia de guardado automático y acceso a Guardar. No comprueba el dibujo ni el idioma de las teclas del proveedor de teclado.

Validación: compilación y 16 pruebas unitarias aprobadas. En la ejecución Android, las 11 pruebas previas pasaron; la nueva falló por buscar Guardar antes de componer ese elemento de LazyColumn. Se corrigió únicamente el desplazamiento de la prueba mediante performScrollToNode y la repetición individual pasó en API 37. No se repitió la suite completa después de ese ajuste de prueba.

En el emulador local se verificó show_ime_with_hard_keyboard=0 y se cambió a 1 para permitir teclado virtual junto al teclado del Mac. La nueva prueba pasó con esa configuración. Es una preferencia del emulador, no un cambio de configuración que se distribuya con la app. Pendiente inspección visual manual del teclado y pruebas en teléfono físico.
