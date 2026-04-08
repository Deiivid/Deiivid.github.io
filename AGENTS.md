# AGENTS.md

## Repo Context

This repository contains a Kotlin Multiplatform portfolio built with Compose Web/Wasm.

Core app path:
- `composeApp/`

Project-local AI context:
- `.codex/config.toml`
- `.codex/prompts/`
- `.codex/skills/`
- `docs/migration/`
- `tools/mcp/`

## Working Agreement

When working in this repo:

1. Read `.codex/config.toml` first for project paths and guardrails.
2. Use the matching file in `.codex/prompts/` when the task is about:
   - HTML to KMP migration
   - Compose structure review
   - extracting repeated UI patterns
3. Use the matching note in `.codex/skills/` when reviewing parity, migration choices, or Compose Web quality.
4. Preserve the current visual identity unless the user explicitly asks for redesign.
5. Keep `docs/migration/` updated when a non-obvious migration decision is made.

## Important Notes

- `docs/` is currently used for published static output, so changes inside `docs/migration/` should not interfere with deployment.
- The files in `.codex/` are repo-local guidance, not globally installed Codex skills.
- `tools/mcp/` is reserved for local tooling and experimentation tied to this project.
