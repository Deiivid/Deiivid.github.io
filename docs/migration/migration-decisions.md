# Migration Decisions

Track the non-obvious decisions made during the HTML to KMP migration.

## Current Decisions

### 1. GitHub Actions publishes the generated Wasm application

Reason:
`.github/workflows/deploy.yml` builds `wasmJsBrowserDistribution` on every relevant push to `main` and deploys `composeApp/build/dist/wasmJs/productionExecutable` through GitHub Pages Actions.

Impact:
`docs/` remains available for project documentation and legacy static assets, but it is not the deployment source. Publishing does not require manually copying the generated application into that folder.

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

### 5. The portfolio opens directly as a concise professional dashboard

Reason:
The first viewport must explain who David is, what he builds, and where to see proof of his work without waiting for an intro or learning a custom navigation model.

Impact:
The page opens immediately with the Android, Kotlin, KMP, and applied-AI positioning, two clear actions, David's portrait, and three factual project cards. The existing dark cyan-and-amber identity remains, but the former full-screen video and office navigation are no longer part of the runtime path. Large desktop viewports reproduce the approved presentation composition; smaller or shorter viewports keep the same content in a compact scrolling layout.

## Update Policy

Add an entry here when a migration or architecture decision would otherwise be rediscovered later.
