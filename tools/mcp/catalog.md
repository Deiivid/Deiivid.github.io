# MCP Catalog

This catalog describes the repository's documentation-oriented MCP surface. It is intentionally practical: each entry names the source material, how it should be ingested, how often it should be refreshed, and what can go wrong.

## `mcp-kmp-docs`

- Source: `docs/migration/`, `.codex/prompts/`, `.codex/skills/kmp-migration.md`
- Ingestion strategy: file-based ingestion with a light index over migration notes, decision logs, and task prompts
- Update cadence: on change for docs; weekly review for migration decisions that affect architecture
- Risks: stale migration guidance, duplicated decisions across notes, and accidental drift between docs and implementation

## `mcp-compose-web-patterns`

- Source: `composeApp/`, `.codex/skills/compose-web-review.md`, `.codex/prompts/review-compose-structure.md`
- Ingestion strategy: structure-focused ingestion from composable boundaries, screen patterns, and reusable UI snippets
- Update cadence: on change when UI structure changes; monthly review for pattern consolidation
- Risks: overfitting to a single screen, extracting patterns too early, or carrying obsolete Compose assumptions forward

## `mcp-ux-ui-principles`

- Source: repository design notes, UI guidance in `.codex/`, and durable visual decisions captured in docs
- Ingestion strategy: curated ingestion of principles, constraints, and "do not" rules rather than full source dumps
- Update cadence: only when product direction or visual language changes; otherwise quarterly review
- Risks: principle drift, contradictory guidance across documents, and turning preferences into pseudo-rules

## `mcp-repo-knowledge`

- Source: `AGENTS.md`, `.codex/config.toml`, `tools/mcp/README.md`, and repository-level documentation
- Ingestion strategy: metadata-first ingestion that maps ownership, folder roles, and local working conventions
- Update cadence: on repository-structure changes; monthly audit for stale links and folder descriptions
- Risks: outdated repository map, missing ownership boundaries, and workers relying on tribal knowledge instead of docs

## Operating Notes

- Keep this catalog focused on knowledge sources, not runtime infrastructure.
- When a source becomes noisy, narrow ingestion before adding more automation.
- When a rule matters for future workers, document it here before encoding it anywhere else.
