<div align="center">
  <img src="docs/assets/img/deiividlogo.webp" alt="David Navarro logo" width="88" />
  <h1>David Web</h1>
  <p><strong>Personal portfolio built with Kotlin Multiplatform and Compose Web.</strong></p>
  <p>
    <a href="https://deiivid.github.io/">Live site</a>
    ·
    <a href="#tech-stack">Tech stack</a>
    ·
    <a href="#workflow">Workflow</a>
    ·
    <a href="#local-development">Run locally</a>
  </p>
</div>

<p align="center">
  <img src="docs/assets/img/permissionProtectImage.png" alt="Project preview" width="920" />
</p>

## Overview

This repository contains my portfolio web, implemented as a **Kotlin-first frontend** with **Compose Multiplatform for Web/Wasm**. The goal is not just to publish a static site, but to keep the UI, interactions, structure, and migration decisions inside a maintainable KMP codebase.

The project mixes:
- a production-facing portfolio
- a migration playground from HTML/CSS ideas into Compose UI
- a repo-local AI workspace with prompts, notes, and agent guidance

## Tech Stack

- Kotlin
- Compose Multiplatform Web
- Wasm target
- Coroutines
- GitHub Pages
- Repo-local AI context under `.codex/` and `tools/mcp/`

## Highlights

- Kotlin-first web portfolio instead of a traditional HTML-only stack
- Responsive sections for hero, skills, timeline, projects, game, and contact
- GitHub Pages deployment flow for the published site
- Project-local prompts and agent notes to keep migration work consistent
- Clean separation between app code, migration docs, and local tooling

## Workflow

This repo is set up to support both product work and structured iteration:

1. **Design parity**
   - Compare the current Compose Web UI against the original HTML/CSS intent.
   - Keep visual decisions documented in `docs/migration/`.

2. **Implementation**
   - Build and refine UI in `composeApp/`.
   - Use `App.kt` as the main integration point and split sections only when it improves clarity.

3. **AI-assisted migration**
   - Use `.codex/prompts/` for repeatable migration and review workflows.
   - Use `.codex/skills/` as repo-local expert notes for KMP, Compose Web, and HTML parity work.

4. **Publish**
   - Generate the site output and publish to GitHub Pages.

## Project Structure

```text
Deiivid.github.io/
├─ .codex/
│  ├─ config.toml
│  ├─ prompts/
│  └─ skills/
├─ tools/
│  └─ mcp/
├─ docs/
│  └─ migration/
├─ composeApp/
└─ AGENTS.md
```

## AI Workspace

The repository also contains a project-scoped AI layer.

- `.codex/config.toml`
  Central project map and guardrails.
- `.codex/prompts/`
  Reusable prompts for HTML-to-KMP migration, Compose review, and UI extraction.
- `.codex/skills/`
  Repo-local knowledge notes for parity analysis, migration strategy, and Compose Web review.
- `tools/mcp/`
  Reserved area for local MCP tooling, scripts, and experiments.
- `AGENTS.md`
  Working rules for agents operating inside this codebase.

This means the repo is not just source code. It also stores the reasoning scaffolding around the code.

## Local Development

Requirements:

- JDK 17 or newer
- Gradle installed globally, or a local wrapper if you add one later

Run the dev server:

```bash
gradle :composeApp:wasmJsBrowserDevelopmentRun
```

## Production Build

Build the production bundle:

```bash
gradle :composeApp:wasmJsBrowserDistribution
```

Published assets are typically consumed from the generated web output and then deployed to GitHub Pages.

## Key Paths

- `composeApp/src/wasmJsMain/kotlin/main.kt`
  Web entry point.
- `composeApp/src/commonMain/kotlin/App.kt`
  Main Compose UI implementation.
- `composeApp/src/wasmJsMain/resources/index.html`
  Shell document for the app.
- `docs/migration/`
  Migration notes, mapping decisions, and visual references.
- `tools/mcp/`
  Local tooling workspace.

## Why This Repo Is Different

Most portfolio repositories stop at "here is the final website."

This one also captures:
- how the UI was migrated
- how architecture decisions are tracked
- how agent workflows are guided
- how the codebase can scale without losing the original design intent

## Next Evolution

Planned directions for the repo:

- split large UI sections into more focused composables where it pays off
- add stronger local MCP tooling for analysis and migration tasks
- tighten deployment automation
- keep improving mobile behavior and design parity
