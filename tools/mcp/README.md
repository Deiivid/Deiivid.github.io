# MCP Workspace

This folder is the local control plane for repository-specific MCP documentation and lightweight stubs.

The goal is not to host a production MCP deployment. The goal is to make the repo's documentation, KMP Web migration notes, and UX/UI principles easy to discover, validate, and extend without inventing a heavier service layer than the project needs.

## Architecture

The workspace is organized around three concerns:

- `catalog.md`: human-readable inventory of the MCPs this repo wants to expose or simulate
- `server/`: configuration stubs for each MCP, kept small and explicit
- `scripts/`: deterministic helper scripts for validation and local maintenance

This layout keeps the documentation layer separate from implementation details while still making the intended MCP surface area obvious.

## MCP Roles

The current catalog is centered on documentation and repo knowledge:

- `mcp-kmp-docs`: captures KMP/Web migration guidance, architecture notes, and durable decisions
- `mcp-compose-web-patterns`: captures Compose Web structure, patterns, and reusable UI conventions
- `mcp-ux-ui-principles`: captures visual, interaction, and accessibility principles that should remain stable
- `mcp-repo-knowledge`: captures repository map, ownership boundaries, and local working conventions

These MCPs are intentionally documentation-first. They should describe what the project knows, not duplicate build logic or runtime code.

## Operating Principles

- Keep source material explicit and local to the repository.
- Prefer plain text config stubs over custom tooling unless a real need appears.
- Keep each MCP single-purpose so updates stay predictable.
- Treat the docs as a contract for future workers.
- Avoid secrets, tokens, machine-specific paths, and generated artifacts.

## Validation And Maintenance

Scripts under `scripts/` should:

- be shell-based when possible
- avoid external dependencies
- validate structure, not just file presence
- fail clearly when the MCP layout drifts

Server configs under `server/` should:

- use simple YAML or TOML
- expose source, ingestion strategy, cadence, and risk fields
- stay readable enough for a human to audit without tooling

## Suggested Use

Use this area for:

- local documentation indexes
- migration assistants
- UI inspection notes
- repo-scoped validation helpers

If a future MCP needs runtime behavior, keep the executable part separate from these documentation stubs so the repository stays easy to reason about.
