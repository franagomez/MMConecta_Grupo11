# MMConecta — cronograma compartido de Michel y Francisca

**Objetivo:** preparar el MVP académico del caso Master Martini y que ambas integrantes puedan ejecutarlo, explicarlo y modificarlo en su defensa individual.

**Fecha objetivo provisional: 31 de octubre de 2026.** Michel indicó «31»; se asume octubre por el contexto. Falta confirmar si corresponde a EP2, EP3 o ambas y la hora límite. Este calendario es una propuesta de reparto para acordar entre ambas, no un registro de trabajo ya realizado.

**Cómo usar esta planilla:** cada mañana revisar la fila del día; al terminar, registrar enlace al commit/PR, evidencia y bloqueos en la tabla de seguimiento. La compañera revisa el cambio antes de integrarlo. Estimación inicial: 2–3 horas por persona al día y 15 minutos de coordinación; ajustar según disponibilidad real.

## 1. Punto de partida

- La base Android existe localmente: Kotlin, Compose, Material 3, paquetes `ui/`, `viewmodel/`, `model/`, `repository/`.
- Se observó MMConecta ejecutándose en el emulador. Se probaron «Chocolatería» (una tarjeta) y «Todos» (tres tarjetas) correctamente.
- Los contenidos son ficticios y el filtro mantiene estado en memoria. Esto no acredita persistencia ni funcionalidades aún pendientes.
- A la preparación de este documento, el repositorio remoto estaba vacío. **Publicar esta planilla no significa que el código de la app ya esté compartido.** Compartir la base y verificar que Francisca pueda abrirla es la primera dependencia.
- Pendientes: navegación, detalle, perfil validado, Room, cámara, notificaciones, animaciones y pruebas completas.

## 2. Qué manda y qué falta confirmar

| Fuente | Situación | Uso |
|---|---|---|
| Caso Master Martini, 5 páginas | Leído | Contexto, accesibilidad, datos ficticios y límites del MVP |
| Pauta PDF EP3, 8 páginas | Leída | Defensa individual de 15 minutos, indicadores y ponderaciones |
| Captura EP2 | Leída | Encargo en pareja, 8 %, áreas generales requeridas |
| Rúbrica detallada EP2 | Pendiente de recibir | Confirmar mínimos, cantidad de pantallas/recursos y formato de entrega; no inventarlos |
| Guía 7 | Vista en clases según Michel, archivo pendiente de recibir aquí | Contrastar estructura enseñada; una guía no sustituye la rúbrica |
| 13 presentaciones Genially | Acceso y títulos comprobados; lectura completa pendiente | Consultar la unidad correspondiente antes de implementar |

**Alcance acordado para planificar:** cámara y notificaciones locales como dos recursos nativos. No se atribuye un mínimo de dos al PDF EP3, porque el documento disponible no lo fija explícitamente. Validarlo con EP2.

La defensa no requiere diapositivas adicionales según el PDF EP3. Priorizar app, dominio del código y cambios en vivo. La pauta indica que no se admite IA ni recursos de apoyo durante la defensa.

## 3. Cronograma diario

| Día | Michel: tarea principal | Francisca: tarea principal | Dependencia / revisión conjunta | Resultado y criterio para cerrar el día |
|---|---|---|---|---|
| Vie 16 oct | Preparar la base para compartir: revisar archivos ignorados, documentar SDK/JDK y procedimiento de ejecución; publicar código mediante un cambio revisable | Abrir/clonar la base cuando esté publicada, configurar entorno propio y ejecutar; reunir Guía 7 y rúbrica EP2 | Ambas confirman fecha, hora, entrega y disponibilidad; revisar juntas el alcance | Ambas ejecutan la misma versión; fuentes faltantes identificadas y responsables definidos |
| Sáb 17 oct | Definir modelo de perfil ficticio, campos e intereses; bocetar validaciones | Definir catálogo, detalle y mapa de pantallas; revisar accesibilidad y contenido | Acordar contratos de modelos/repositorios y nombres de rutas antes de codificar | Mapa y contratos escritos, sin datos reales; tareas ajustadas a EP2 si ya está disponible |
| Dom 18 oct | Crear ProfileScreen y ProfileViewModel con campos e intereses editables | Crear navegación inicio → catálogo → detalle y regreso; conectar tarjetas a detalles por ID | Francisca coordina el archivo de navegación para evitar ediciones simultáneas | Formulario editable y recorrido catálogo/detalle ejecutables, sin afirmar validación completa |
| Lun 19 oct | Implementar validación por campo, mensajes, iconos y bloqueo de guardado inválido | Conectar pantalla de perfil a navegación; comprobar Material 3, jerarquía y tamaños legibles | Michel revisa navegación; Francisca prueba datos inválidos | Flujo completo accesible; errores claros y sin pérdida inesperada al interactuar |
| Mar 20 oct | Organizar StateFlow del perfil: edición, error y confirmación de guardado provisional | Organizar estado del catálogo/detalle: filtro, vacío y error cuando corresponda; evitar loaders ficticios permanentes | Ambas explican evento → ViewModel → estado → UI | La UI responde coherentemente; estados demostrables y lógica fuera de los Composables |
| Mié 21 oct | Crear Room: entidad/DAO de perfil y contrato de repositorio | Crear entidad/DAO de favoritos y operación de consulta por ID de contenido | Michel coordina AppDatabase y dependencias; acordar migraciones antes de cambiar esquemas | Guardado/consulta local implementados y compilación compartida; aún verificar reinicio al día siguiente |
| Jue 22 oct | Conectar perfil a Room: guardar, recuperar y actualizar | Conectar favoritos a catálogo/detalle y mostrar lista de favoritos | Revisar juntas operaciones de base de datos y concurrencia | Cerrar y reabrir la app conserva perfil y favoritos; actualizar no duplica registros |
| Vie 23 oct | Integrar cámara para imagen de prueba del perfil: URI, almacenamiento y cancelación | Integrar notificación local de demostración: canal, texto, permiso según Android y disparador explícito | Usar solo contenido sintético/de prueba; explicar permisos necesarios según implementación | Cada recurso funciona por separado; rechazar/cancelar no cierra la app ni muestra éxito falso |
| Sáb 24 oct | Verificar recuperación/presentación de imagen y explicar flujo de cámara a Francisca | Verificar notificación visible, permiso denegado y reintento; explicar implementación a Michel | Revisión cruzada de los dos recursos y sus límites | Ambas ejecutan y explican cámara y notificaciones; evidencias individuales de cada flujo |
| Dom 25 oct | Incorporar animación útil de error/confirmación de formulario y revisar comportamiento con texto grande | Incorporar transición o animación útil en catálogo/favoritos y revisar adaptación visual | Evitar animaciones que oculten errores o dificulten lectura | UI legible con tamaños de texto mayores; animaciones justificadas y sin bloquear interacción |
| Lun 26 oct | Probar catálogo, detalle, favoritos y notificaciones hechos por Francisca | Probar perfil, validaciones, persistencia e imagen hechos por Michel | Pruebas cruzadas, orientación, reinicio, entradas vacías/incorrectas y permisos | Lista de fallos con pasos de reproducción y prioridad; corregir primero bloqueos y pérdidas de datos |
| Mar 27 oct | Corregir fallos de perfil, datos y cámara; actualizar documentación técnica | Corregir fallos de navegación, catálogo y notificaciones; revisar matriz de rúbrica | Auditoría conjunta requisito → código → demostración; consultar al docente dudas reales | Cada requisito tiene evidencia o pendiente explícito; app ejecutable desde instalación limpia |
| Mié 28 oct | Ensayar defensa individual de 15 min; modificar validación y añadir componente sin ayuda | Ensayar defensa individual de 15 min; modificar persistencia y ajustar recurso nativo sin ayuda | Intercambiar ejercicios para cubrir todas las áreas; compañera observa y toma notas | Ambas completan recorrido y cambios en vivo; registrar dificultades y volver a practicar |
| Jue 29 oct | Repetir ensayo centrado en áreas débiles y verificar entorno/dispositivo | Verificar ejecución desde clon limpio y preparar entrega según formato real de EP2 | Congelar nuevas funciones; corregir solo fallos relevantes | Versión candidata estable, instrucciones reproducibles y archivos exigidos identificados |
| Vie 30 oct | Revisar versión final, permisos, datos ficticios y evidencias de pruebas | Revisar README, historial, enlaces, archivos de entrega y acceso al repositorio | Ambas confirman checklist y versión exacta; entregar antes si el plazo oficial lo exige | Paquete revisado y listo; conservar copia local; no enviar automáticamente al campus |
| Sáb 31 oct | Verificar recepción/entrega según plazo confirmado y preparar defensa propia | Verificar recepción/entrega según plazo confirmado y preparar defensa propia | Día de margen; evitar añadir funciones nuevas; defensa solo si está programada ese día | Entrega comprobada por el equipo; registrar retroalimentación para EFT |

**Si se atrasan:** proteger ejecución, validaciones, persistencia, recursos exigidos y dominio individual. Reducir extras de contenido y decoración, nunca omitir requisitos confirmados. No dejar la primera prueba completa para el día 30.

## 4. Contratos y límites para trabajar en paralelo

- `model/`: clases de datos compartidas; acordar cambios de campos antes de integrarlos.
- `repository/`: acceso a datos; la UI no accede directamente a DAO/Room.
- `viewmodel/`: estado, validaciones y coordinación de operaciones. Usar ViewModel por funcionalidad cuando tenga estado propio; no crear clases vacías solo por cumplir nombres.
- `ui/`: Screens, Composables, navegación y tema. Recibe estado y emite acciones.
- Michel coordina configuración Gradle/AppDatabase. Francisca coordina navegación/tema. **Coordinar no significa propiedad exclusiva:** ambas revisan y comprenden estos archivos.
- Perfil e intereses: definir campos estrictamente necesarios para la demo. RUT, dirección y teléfono aparecen como posibilidades del caso, no se declaran obligatorios sin contrastar la rúbrica.
- No se planifica backend, autenticación real ni integración web empresarial: son extras fuera del MVP inicial y no exigencias verificadas.
- Cámara: contenido de prueba sin información personal. Notificaciones: locales y voluntarias para la demostración, sin envío a terceros.

## 5. Checklist de evaluación y evidencia

Los pesos siguientes corresponden **solo a EP3**, cuyo total es 100 %; EP3 representa el 32 % de la asignatura según el material recibido.

| Indicador EP3 | Peso | Qué debe demostrar cada una | Evidencia de cierre | Responsable principal / revisora |
|---|---:|---|---|---|
| IE2.1.1 Validación | 20 % | Explicar reglas, añadir condición y corregir error en vivo | Caso válido + inválido + modificación funcional | Michel / Francisca |
| IE2.1.2 Diseño y navegación | 7 % | Justificar jerarquía, Material 3, navegación y usabilidad | Recorrido completo, retorno y adaptación | Francisca / Michel |
| IE2.2.1 Modularidad | 22 % | Incorporar clase, componente o animación sin romper estructura | Cambio en vivo compilado y explicado | Ambas |
| IE2.2.2 Estado y control | 7 % | Explicar estados y relación con acciones/UI | Filtro y formulario muestran cambios coherentes | Ambas |
| IE2.3.1 Persistencia | 18 % | Modificar guardado/consulta/actualización | Datos recuperados tras reinicio y cambio en vivo | Michel coordina; ambas implementan y revisan |
| IE2.3.2 Arquitectura y colaboración | 7 % | Justificar MVVM, repositorios e historial real de trabajo | Paquetes claros, commits y revisiones verificables | Ambas |
| IE2.4.1 Recursos y permisos | 7 % | Explicar integración y permisos de cámara/notificaciones | Uso, cancelación/denegación y recuperación | Cámara: Michel; notificaciones: Francisca; revisión cruzada |
| IE2.4.2 Resultado nativo | 12 % | Ejecutar recurso y ajustar presentación/lógica | Imagen renderizada y notificación visible; ajuste en vivo | Ambas |

## 6. Protocolo GitHub

1. Antes de empezar, actualizar `main` y crear una rama específica: `feature/perfil`, `feature/catalogo`, `feature/room-perfil`, `feature/favoritos`, `feature/camara` o `feature/notificaciones`.
2. Hacer commits pequeños explicando el cambio. No subir `local.properties`, `.idea/`, `.gradle/`, `build/`, claves, credenciales ni datos personales reales.
3. Abrir un pull request: objetivo, requisito cubierto, archivos compartidos modificados, pruebas y captura si aplica.
4. La otra integrante revisa y ejecuta el recorrido afectado. Corregir observaciones antes de integrar.
5. Evitar trabajar simultáneamente sobre configuración Gradle, AppDatabase y navegación sin coordinar.
6. Después de integrar, ambas actualizan su copia y comprueban ejecución. No atribuir a una compañera commits o revisiones que no realizó.

### Cuándo una tarea está terminada

- [ ] Cumple el criterio y la fuente correspondiente.
- [ ] Compila y se ejecuta en el entorno acordado.
- [ ] Se probó el caso correcto y al menos un error/cancelación relevante.
- [ ] Se revisó por la compañera.
- [ ] Hay enlace a commit/PR y evidencia real.
- [ ] Ambas pueden explicar el cambio y ubicar su código.

## 7. Seguimiento diario editable

Estados: **Pendiente → En curso → En revisión → Hecho**. Usar **Bloqueado** con motivo, responsable de resolver y siguiente paso. No marcar Hecho por haber escrito código.

| Fecha | Estado Michel | Estado Francisca | Commit/PR y evidencia | Bloqueo / siguiente paso |
|---|---|---|---|---|
| 16 oct | Pendiente | Pendiente | — | Compartir base y confirmar fuentes/plazos |
| 17 oct | Pendiente | Pendiente | — | — |
| 18 oct | Pendiente | Pendiente | — | — |
| 19 oct | Pendiente | Pendiente | — | — |
| 20 oct | Pendiente | Pendiente | — | — |
| 21 oct | Pendiente | Pendiente | — | — |
| 22 oct | Pendiente | Pendiente | — | — |
| 23 oct | Pendiente | Pendiente | — | — |
| 24 oct | Pendiente | Pendiente | — | — |
| 25 oct | Pendiente | Pendiente | — | — |
| 26 oct | Pendiente | Pendiente | — | — |
| 27 oct | Pendiente | Pendiente | — | — |
| 28 oct | Pendiente | Pendiente | — | — |
| 29 oct | Pendiente | Pendiente | — | — |
| 30 oct | Pendiente | Pendiente | — | — |
| 31 oct | Pendiente | Pendiente | — | Confirmar entrega/defensa |

## 8. Ensayo de defensa de 15 minutos

Distribución propuesta para practicar; respetar las instrucciones del docente durante la evaluación real.

| Tiempo | Contenido |
|---|---|
| 0–1 min | Abrir/ejecutar app y explicar entorno |
| 1–3 min | Arquitectura, responsabilidades y colaboración |
| 3–5 min | Diseño, navegación y formularios con errores |
| 5–7 min | StateFlow, lógica y animaciones |
| 7–9 min | Persistencia y recuperación de datos |
| 9–11 min | Cámara, notificaciones y permisos |
| 11–15 min | Modificación solicitada y explicación; ajustar tiempos según docente |

Practicar cambios variados: nueva validación, componente nuevo, modificación de consulta/actualización en Room y ajuste de imagen/notificación. No memorizar solo un guion ni dividir el conocimiento según la autoría.

## 9. Material del curso

| Tema | Material |
|---|---|
| Fundamentos Kotlin | https://view.genially.com/6886a7aa667980b7804d7f0a |
| POO y errores | https://view.genially.com/6886a7a349ee7ab0aed34154 |
| Corrutinas | https://view.genially.com/6886d3197923351a7564b1d5 |
| Android Studio | https://view.genially.com/6886a79e49ee7ab0aed33b41 |
| Arquitectura y colaboración | https://view.genially.com/687fc01adc659d971ee91bde |
| Componentes visuales | https://view.genially.com/687fd137b14a7dbe8ae83427 |
| Diseño adaptable | https://view.genially.com/687fd13b07156720c171295e |
| Navegación | https://view.genially.com/68815ed96681577bd8abddd4 |
| Formularios I | https://view.genially.com/68815ecb6681577bd8abd54d |
| Formularios II | https://view.genially.com/68815ef7b7c9b2b9245081cc |
| Estado y animaciones | https://view.genially.com/688234ee4e58d26a68356f94 |
| Recursos nativos | https://view.genially.com/688234f1e54d3089a7fe7e8c |
| Persistencia | https://view.genially.com/688234f4999f4af448ac3df2 |

Documentos a consultar por su nombre: `Caso_Curricularizacion_DSY1105_Master_Martini.pdf`, `Evaluación Parcial 3_Estudiante_Presentación_DSY1105.pdf`, Guía 7 y rúbrica EP2. Los PDF no se adjuntan a este repositorio con este cambio; ambas deben acceder a ellos por el campus/material compartido autorizado.
