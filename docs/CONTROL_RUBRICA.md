# Control de cumplimiento

## Fuentes y límites

- Caso Master Martini: leído completo. MVP académico accesible y adaptable, datos ficticios/sintéticos/anonimizados, materiales autorizados, permisos probados sin capturar datos reales.
- PDF EP3: leído completo; defensa individual, arquitectura, diseño, formularios, estado, animaciones, persistencia, recursos nativos y cambios en vivo.
- Captura EP2: requisitos generales conocidos. Rúbrica detallada todavía no recibida.
- Guía 7: todavía no recibida. El usuario comunica que requiere ui/, viewmodel/, model/ y repository/ opcional. La base se ajustó a esa organización.
- Mínimo dos recursos nativos: comunicado por el usuario a partir de revisión de su compañera. Se adopta como alcance del proyecto (cámara + notificaciones); atribución exacta a la rúbrica pendiente de verificación. El PDF EP3 disponible no fija expresamente un número mínimo.

## Estado verificable

| Área | Estado | Evidencia pendiente |
|---|---|---|
| Arquitectura | Código inicial separado en ui, viewmodel, model, repository | Base ejecutada en equipo de Michel; explicación y ejecución por ambas pendientes |
| Diseño Material 3 | Pantalla inicial escrita | Ejecución, accesibilidad y adaptación |
| Navegación | Catálogo y detalle probados; perfil conectado; transición entre pantallas con animación (fundido) | Perfil abierto y retorno probados en equipo de Francisca (API 35), sin romper estado de favoritos; revisar equipo de Michel |
| Formularios | Perfil validado; pruebas unitarias correctas | Campos vacíos y guardado válido probados en emulador; guardado de nombre/correo aún no probado por Francisca (requiere ingresar texto); modificación en vivo pendiente |
| Estado | StateFlow y filtro probados en emulador | Resto de interacciones pendiente |
| Persistencia | Perfil implementado con repositorio local de preferencias | Perfil recuperado después de Stop/Run en API 37; favoritos probados visualmente y recuperados tras Stop/Run en API 37 y en API 35 (equipo de Francisca); guardado de nombre/correo del perfil aún no verificado por Francisca |
| Cámara | Implementado y probado (equipo de Francisca, API 35) | Foto tomada y confirmada: imagen guardada y mostrada en el perfil. Cancelación con Volver: se conserva la imagen anterior sin romper la app. Denegación del permiso de cámara aún no probada |
| Notificaciones locales | Implementado y probado (equipo de Francisca, API 35) | Canal de notificaciones creado (requerido desde Android 8); permiso POST_NOTIFICATIONS solicitado en tiempo de ejecución (requerido desde Android 13); notificación enviada y confirmada ("Notificación enviada.") tras conceder el permiso. Denegación del permiso aún no probada |
| Animaciones | Implementado y probado (equipo de Francisca, API 35) | AnimatedContent con fundido (fade) entre catálogo, detalle y perfil, probado en navegación Inicio → Perfil → Volver sin romper la app ni el estado de favoritos. Justificación de utilidad: facilita entender el cambio de pantalla a usuarios con bajo manejo digital (caso Master Martini). Observación visual detallada del efecto (grabación) aún pendiente |
| Colaboración | Reglas documentadas | Cronograma publicado; revisar base compartida y documentar aportes reales de ambas |
| Defensa | Pendiente | Ambas explican y modifican todo el proyecto sin apoyo durante la evaluación |

## Regla de trabajo

Antes de desarrollar: identificar fuente, requisito, función y evidencia esperada. Después: comprobar comportamiento y registrar resultado. Código escrito no equivale a funcionalidad probada. No declarar cumplimiento total ni inventar exigencias faltantes. Contrastar las nuevas fuentes al recibirlas y ajustar esta matriz.
