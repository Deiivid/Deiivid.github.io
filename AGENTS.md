# AGENTS.md

## Mission

Maintain this Kotlin/Compose Web portfolio as a small, production-ready product.

## Read First

- `.codex/config.toml` for stack and working constraints.
- `docs/project-context.md` for the current product and source map.
- `docs/migration/migration-decisions.md` only when a UI, architecture, or deployment decision matters.
- `ai/README.md` only for multi-agent orchestration work.

## Source Map

- `composeApp/src/commonMain/kotlin/App.kt`: app root and responsive layout.
- `composeApp/src/commonMain/kotlin/DesktopPortfolio.kt`: approved large-desktop presentation.
- `composeApp/src/commonMain/composeResources/drawable/`: runtime Compose images only.
- `composeApp/src/wasmJsMain/kotlin/main.kt`: browser entry point.
- `composeApp/src/wasmJsMain/resources/`: HTML shell and directly published files such as the CV.
- `.github/workflows/deploy.yml`: GitHub Pages build and deployment.

## Working Rules

- Preserve the approved dark cyan-and-amber visual identity.
- Prefer focused fixes over broad rewrites and keep mobile behavior intentional.
- Keep business logic out of composables and extract UI only when reuse is real.
- Do not add dependencies, redesign architecture, or change deployment behavior without a task that requires it.
- Remove a resource only after a repository-wide reference check; do not keep generated bundles in `docs/`.
- Record durable decisions in `docs/migration/migration-decisions.md`, not in this file.

## Validation

For production-facing changes run:

```bash
gradle :composeApp:wasmJsBrowserDistribution --no-daemon --console=plain
```

Pushing a relevant change to `main` deploys through GitHub Actions.

## Git and Safety

- Preserve unrelated user changes and never commit `.idea/` or machine-local files.
- Do not rewrite history or force-push unless explicitly requested.
- Never add secrets, credentials, certificates, keystores, or `.env` files.
