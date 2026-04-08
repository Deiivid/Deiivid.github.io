# AGENTS.md

## Mission

Work in this repository as if it were a long-lived Kotlin product, not a throwaway portfolio clone.

Primary goals:
- preserve the public visual identity of the site
- keep the codebase maintainable as the portfolio grows
- improve parity with the original HTML intent without falling into random redesigns
- document non-obvious migration decisions so they are not rediscovered later

## Repository Map

- `composeApp/`
  Main application source for Compose Multiplatform Web/Wasm.
- `.codex/config.toml`
  Project-level context map and guardrails.
- `.codex/prompts/`
  Task-oriented prompts for migration, review, and extraction work.
- `.codex/skills/`
  Repo-local expert notes for HTML analysis, KMP migration, and Compose Web review.
- `docs/migration/`
  Durable project notes about migration patterns and architectural decisions.
- `tools/mcp/`
  Local MCP-related tooling workspace.

## Default Operating Mode

Before making a meaningful UI or architecture change:

1. Read `.codex/config.toml`.
2. Identify whether the request is mainly:
   - HTML parity work
   - Compose structure review
   - repeated UI extraction
   - migration decision work
3. Load the matching prompt or note from `.codex/`.
4. Make the smallest safe change that moves the result closer to the intended design or structure.

## Prompt Routing

Use these repo-local prompts when the task matches:

- `.codex/prompts/migrate-html-to-kmp.md`
  When porting or correcting a section based on HTML/CSS or screenshots.
- `.codex/prompts/review-compose-structure.md`
  When reviewing app structure, composable boundaries, or duplicated UI logic.
- `.codex/prompts/extract-ui-patterns.md`
  When repeated UI blocks are good candidates for extraction.

## Skill Routing

Use these repo-local notes when the task matches:

- `.codex/skills/html-analysis.md`
  For visual diff and HTML-vs-Compose reasoning.
- `.codex/skills/kmp-migration.md`
  For Kotlin Multiplatform migration trade-offs and implementation choices.
- `.codex/skills/compose-web-review.md`
  For Compose Web quality review, mobile behavior, and wasm constraints.

## UI Rules

- Preserve the current design language unless the user explicitly asks for a redesign.
- Prioritize visual parity over speculative refactors.
- Keep card spacing, media framing, overlays, and responsive behavior intentional.
- Avoid one-off fixes that create more inconsistency elsewhere.
- Treat mobile support as a first-class requirement, not a late polish pass.

## Architecture Rules

- Keep business logic out of composables.
- Avoid over-abstracting small UI sections.
- Extract components only when repetition is real and the resulting API stays readable.
- Prefer incremental refactors over broad rewrites.
- Keep deployment mechanics separate from UI concerns.

## Documentation Rules

When a decision matters beyond a single edit, update:
- `docs/migration/migration-decisions.md`

When a reusable HTML-to-Compose pattern becomes clear, update:
- `docs/migration/html-source-patterns.md`
- `docs/migration/mapping-html-css-to-compose.md`

## MCP Workspace

`tools/mcp/` is reserved for local tooling that supports this project.

Use it for:
- migration helpers
- inspection scripts
- content extraction tools
- future project-scoped MCP server experiments

Do not mix secrets, deployment credentials, or machine-specific values into committed files.

## Quality Bar

The expected result is not just "it works."

The expected result is:
- coherent with the rest of the site
- understandable for the next iteration
- aligned with the project migration strategy
- safe to build on top of later
