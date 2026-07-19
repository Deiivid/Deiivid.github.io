# Project Context

## Purpose

This repository is the source of truth for the David Navarro portfolio built with Kotlin Multiplatform and Compose Web/Wasm.

The project is meant to stay maintainable as a real product, not a one-off static site.

## Tech Stack

- Kotlin
- Compose Multiplatform Web / Wasm
- GitHub Pages deployment
- Repo-local orchestration under `.codex/`, `ai/`, and `tools/mcp/`

## Feature Map

- Professional hero with profile, CV and contact actions
- Animated Android, KMP and applied-AI identity system
- Three linked project cards
- Experience and about sections
- Dedicated large-desktop composition plus responsive compact layout

## Source Map

- `composeApp/src/commonMain/kotlin/App.kt`
  App root and responsive composition.
- `composeApp/src/commonMain/kotlin/DesktopPortfolio.kt`
  Approved large-desktop composition and motion.
- `composeApp/src/commonMain/composeResources/drawable/`
  Images referenced through Compose Resources.
- `composeApp/src/wasmJsMain/kotlin/main.kt`
  Browser entry point.
- `composeApp/src/wasmJsMain/resources/`
  App shell and directly published files, including the CV.
- `.github/workflows/deploy.yml`
  Production build and GitHub Pages deployment.
- `docs/migration/`
  Migration decisions and HTML-to-Compose mapping notes.
- `ai/`
  Human-readable orchestration and reusable repo guidance.
- `.codex/`
  Guardrails, prompts, and focused skills for agent work.

## Rules

- Preserve the approved dark cyan-and-amber visual identity.
- Prefer small, incremental changes.
- Keep business logic out of composables.
- Extract repeated UI only when the reuse is real.
- Keep mobile behavior intentional.
- Keep deployment mechanics separate from UI concerns.
- Keep only referenced runtime resources; generated web bundles belong in `composeApp/build/`, not `docs/`.

## Git Rules

- Do not rewrite history unless explicitly requested.
- Do not force-push unless the task is specifically about history reset.
- Use descriptive commits for meaningful milestones.

## Data That Should Not Be Added

- `.env` files
- secrets, tokens, passwords, certificates, and keystores
- machine-local IDE caches or personal config
- generated artifacts that are only useful on one local machine

If a file looks sensitive or local-only, stop before committing it.
