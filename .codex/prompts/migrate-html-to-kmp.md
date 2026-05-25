# Migrate HTML To KMP

Use this prompt when the task is to port or replicate an existing HTML/CSS section into Compose Web.

## Goal

Recreate the visual result of the HTML reference in `composeApp` while keeping the implementation idiomatic for Kotlin Multiplatform and Compose UI.

## Workflow

1. Identify the target HTML structure, spacing, sizing, colors, assets, and interactive states.
2. Split the UI into composables that follow the current structure in `App.kt`.
3. Replace CSS concepts with Compose equivalents:
   - flex row/column -> `Row` / `Column`
   - grid -> `FlowRow`, responsive branching, or explicit rows
   - gap -> `Arrangement.spacedBy`
   - border radius -> `RoundedCornerShape`
   - shadow/glow -> `shadow`, `drawBehind`, or layered `Box`
4. Reuse existing color constants, dimensions, and resource loading patterns whenever possible.
5. Preserve mobile behavior and desktop behavior explicitly.
6. Verify that the migrated block still matches the HTML intent, not just a rough approximation.

## Guardrails

- Do not dump all HTML into one giant composable.
- Do not move business logic into rendering code.
- Do not introduce new abstractions unless the section is clearly reused.
- When animation exists in the HTML, prefer a Compose-native approximation that is stable on wasm.

## Deliverable

The migrated section should:
- look close to the source HTML
- use the existing project palette and typography rules
- remain maintainable inside the current `composeApp` structure
