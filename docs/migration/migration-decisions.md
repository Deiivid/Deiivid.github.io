# Migration Decisions

Track the non-obvious decisions made during the HTML to KMP migration.

## Current Decisions

### 1. `docs/` remains the published output folder

Reason:
GitHub Pages currently consumes generated static output from `docs/`.

Impact:
Extra documentation under `docs/migration/` is acceptable, but build/publish scripts should avoid wiping these notes unless intentionally regenerated.

### 2. Repo-local `.codex/` is documentation, not an auto-installed Codex skill registry

Reason:
Codex auto-loaded skills live outside the repo, but project-local guidance is still valuable here.

Impact:
These files are meant to be read as project context, versioned with the app, and kept aligned with the codebase.

### 3. Prefer incremental UI parity over large rewrites

Reason:
The project is already close to the HTML reference in several sections, so targeted fixes are safer than broad restyling.

Impact:
When a section is off, adjust sizing, spacing, composition, and extracted patterns first.

### 4. Keep repo context split between durable docs and reusable agent guidance

Reason:
The repository now uses `AGENTS.md` for top-level operating rules, `docs/project-context.md` for project-wide context, `.codex/` for repo guardrails, and `ai/skills/` for reusable markdown guidance.

Impact:
Future edits should keep those layers aligned instead of duplicating the same rules in several places. Keep `ai/` readable and reusable, but avoid adding sensitive, local-only, or machine-specific data there.

## Update Policy

Add an entry here when a migration or architecture decision would otherwise be rediscovered later.
