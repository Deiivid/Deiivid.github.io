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

## Update Policy

Add an entry here when a migration or architecture decision would otherwise be rediscovered later.
