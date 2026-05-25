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

- Header navigation
- Hero section with profile and CTA links
- Skills section
- Timeline / experience section
- Projects section
- Interactive game / modal section
- Contact section
- Footer

## Source Map

- `composeApp/src/commonMain/kotlin/App.kt`
  Main UI composition.
- `composeApp/src/wasmJsMain/kotlin/main.kt`
  Browser entry point.
- `composeApp/src/wasmJsMain/resources/`
  App shell and published assets.
- `docs/migration/`
  Migration decisions and HTML-to-Compose mapping notes.
- `ai/`
  Human-readable orchestration and reusable repo guidance.
- `.codex/`
  Guardrails, prompts, and focused skills for agent work.

## Rules

- Preserve the current public visual identity.
- Prefer small, incremental changes.
- Keep business logic out of composables.
- Extract repeated UI only when the reuse is real.
- Keep mobile behavior intentional.
- Keep deployment mechanics separate from UI concerns.

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
