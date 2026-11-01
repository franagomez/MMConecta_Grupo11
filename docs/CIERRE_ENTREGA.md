# Revisión para la entrega de MMConecta

Fecha de revisión: 1 de noviembre de 2026. Esta lista no sustituye la rúbrica oficial ni acredita una defensa realizada.

## Versión a revisar

Rama: `codex/revision-avances`. PR #12 reúne los cambios de ambas conservando los autores. La revisión de GitHub todavía no muestra una aprobación; no se ha fusionado a main.

Para actualizar una copia con el trabajo local guardado:

```sh
git fetch origin
git switch codex/revision-avances
git pull --ff-only origin codex/revision-avances
```

Entorno usado: JDK 21 para Gradle, SDK 35 para compilar y emulador API 37 para pruebas. La app admite desde API 26. `local.properties` es propio de cada computador y no se comparte.

## Recorrido que ambas deben completar

1. Abrir catálogo, cambiar categoría, marcar favorito y abrir detalle. Volver conservando filtro.
2. Abrir Mi perfil, escribir un nombre/correo incorrecto y guardar para ver errores. Corregir, elegir negocio/intereses y guardar. Cerrar y reabrir: comprobar datos.
3. En Nombre pulsar Siguiente: debe enfocar Correo. Listo termina la edición sin guardar. Comprobar que Guardar siga accesible con el teclado.
4. Tomar una imagen de prueba; cancelar una segunda captura y comprobar que la primera se conserva.
5. Autorizar notificaciones y enviar aviso. Denegar o bloquear y comprobar el mensaje correspondiente. Tocar el aviso con la app en segundo plano y cerrada.
6. Revisar catálogo con anchos de móvil y tablet, orientación horizontal y texto grande. Verificar lectura, controles y contraste con el dispositivo real que se usará.
7. Francisca registra versión, resultado y observaciones en el PR; Michel corrige y ambas revisan antes de fusionar.

## Correspondencia con lo estudiado

- MVVM, Compose, Material 3, StateFlow, navegación y validaciones: implementados.
- Persistencia: preferencias para perfil/favoritos y archivo interno para la imagen. EP3 admite Room u otro mecanismo; falta contrastar la rúbrica detallada EP2 de DSY1105.
- Guía 9: catálogo adaptado mediante Window Size Classes. Perfil y detalle limitan ancho de lectura.
- Guía 10: NavHost/NavController y barras superiores; no se replica el menú inferior ni el sistema de eventos del ejercicio.
- Guía 11: formulario y errores mediante ViewModel; no se replica una pantalla de resumen ni contraseña porque el perfil no implementa autenticación.
- Guía 7: usa Trello y repositorio privado en su ejercicio. Aquí se utilizan Issues y repositorio público; confirmar instrucción de entrega con el docente.
- Sin Gemini, IA ni backend en la aplicación. Las dependencias de pruebas no añaden servicios a la app.

## Pendientes que no se pueden acreditar automáticamente

- Rúbrica EP2 original: el PDF de EP2 localizado anteriormente corresponde a FullStack, no Móviles.
- Revisión cruzada y ejecución en el computador de cada integrante.
- Comprobación manual de teclado, toque de notificación, lector de pantalla y teléfono físico.
- Defensa: cada estudiante explica y modifica validación, estado, persistencia y recursos nativos en 15 minutos, según EP3.
- Confirmar fecha/hora, acceso del profesor, formato de entrega y recepción en el campus. El día 31 mencionado antes era provisional; no se presenta como plazo verificado.

El APK debug permite pruebas; no es una publicación en Play Store ni reemplaza el código solicitado por el profesor.
