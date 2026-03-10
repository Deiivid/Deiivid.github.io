# David Web (KMP)

Migración del portfolio a **Kotlin Multiplatform Web (target JS/IR)**.

## Ejecutar en local

1. Tener JDK 17+ y Gradle instalado.
2. Ejecutar:

```bash
gradle jsBrowserDevelopmentRun
```

## Build de producción

```bash
gradle jsBrowserProductionWebpack
```

Salida esperada en `build/dist/js/productionExecutable/`.

## Estructura

- `src/jsMain/kotlin/Main.kt`: renderiza toda la web desde Kotlin.
- `src/jsMain/resources/index.html`: shell HTML + Tailwind + fuentes.
- `src/jsMain/resources/site.js`: interacciones (typing, mini-juego y formulario).
- `src/jsMain/resources/assets/`: imágenes y CV usados por la web.

## Notas

- Se mantienen `index.html` y `portfolio-details.html` originales en la raíz como referencia histórica.
