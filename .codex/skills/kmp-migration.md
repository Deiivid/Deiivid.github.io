# KMP Migration

Repo-local skill note for migrating static web ideas into this Kotlin Multiplatform project.

## Use This When

- porting HTML/CSS/JS into `composeApp`
- deciding whether a behavior belongs in shared UI code
- translating DOM-oriented behavior into Compose and coroutine-friendly code

## Migration Heuristics

- migrate the visual contract first, then refine structure
- use resource files for stable assets, not hardcoded web URLs
- keep wasm runtime constraints in mind for animation and canvas-heavy features
- prefer small local state models over ad hoc mutable variables
- keep deploy output concerns out of UI code

## Repository Rules

- `composeApp/src/commonMain/kotlin/App.kt` is currently the main integration surface
- if a section becomes large, split it intentionally rather than growing the file indefinitely
- update `docs/migration/migration-decisions.md` when a migration trade-off matters for future work
