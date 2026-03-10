gradle kotlinUpgradeYarnLock
# David Web (KMP)

Portfolio en **Kotlin Multiplatform + Compose Web** (target JS/IR).

## Ejecutar en local

1. Tener JDK 17+ y Gradle instalado.
2. Ejecutar:

```bash
gradle :composeApp:wasmJsBrowserDevelopmentRun
```

## Build de producción

```bash
gradle :composeApp:wasmJsBrowserProductionWebpack
```

Salida esperada en `build/dist/js/productionExecutable/`.

## Estructura

- `composeApp/src/wasmJsMain/kotlin/main.kt`: punto de entrada con `ComposeViewport`.
- `composeApp/src/commonMain/kotlin/App.kt`: UI completa en Compose UI.
- `composeApp/src/wasmJsMain/resources/index.html`: shell mínimo de arranque.
- `composeApp/src/wasmJsMain/resources/assets/`: imágenes y CV usados por la web.

## Notas

- `index.html` solo monta la app; el contenido vive en Compose.
- Si alguna máquina falla con `kotlinStoreYarnLock`, ejecutar una vez `gradle kotlinUpgradeYarnLock`.
