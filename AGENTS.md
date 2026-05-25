# AGENTS.md

## Mission

Work in this repository like a long-lived Kotlin portfolio product.

## Tech Stack

- Kotlin
- Compose Multiplatform Web / Wasm
- GitHub Pages
- Repo-local AI guidance under `.codex/`, `ai/`, and `tools/mcp/`

## Feature Map

- `composeApp/src/commonMain/kotlin/App.kt`
  Main UI composition and section layout.
- `composeApp/src/wasmJsMain/kotlin/main.kt`
  Web entry point.
- `composeApp/src/wasmJsMain/resources/`
  Published web assets and HTML shell.
- `docs/migration/`
  Migration decisions and HTML-to-Compose mapping notes.
- `docs/project-context.md`
  High-level repo context for agents and future maintainers.
- `ai/`
  Human-readable orchestration context and reusable repo guidance.
- `.codex/`
  Repo guardrails, prompts, and focused skills.
- `tools/mcp/`
  Local tooling and structured knowledge support.

## Scope

- Preserve the current public visual identity.
- Prefer incremental fixes over broad rewrites.
- Keep business logic out of composables.
- Extract repeated UI only when reuse is real.
- Keep deployment mechanics separate from UI concerns.

## Rules

- Read `.codex/config.toml` before meaningful UI or architecture work.
- Use the nearest repo guidance before adding new patterns.
- Document decisions that could be rediscovered later.
- Keep mobile behavior intentional, not as an afterthought.

## Git

- Do not rewrite history unless explicitly requested.
- Avoid force-pushes unless the user has asked for a history reset.
- Use short, descriptive commits for meaningful milestones.
- Do not create branches or remotes unless the task requires it.

## Data / Secrets

Do not add or commit:

- `.env` files
- API keys, tokens, passwords, certificates, keystores
- machine-local IDE caches or personal config
- generated artifacts that belong only on a local machine

If a file looks sensitive, stop and confirm before adding it.

## Validation

Use the most specific existing validation command available for the change.

## Documentation

When a decision matters beyond a single edit, update:

- `docs/migration/migration-decisions.md`

When a reusable pattern becomes clear, update:

- `docs/migration/html-source-patterns.md`
- `docs/migration/mapping-html-css-to-compose.md`

## Quality Bar

The result should be coherent, readable, and safe to extend later.
