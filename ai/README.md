# AI Orchestration

This directory is the repo-level index for AI-assisted technical work.

It exists to make orchestration explicit: who does the work, how handoffs happen, and which local references define the rules.

## What Lives Here

- `agents/`
  Role contracts for technical workers. These describe scope, inputs, outputs, guardrails, and handoff targets.
- `workflows/`
  Ordered multi-agent flows for repeatable tasks such as UI parity fixes, feature delivery, and release publishing.

## Adjacent Context

- `.codex/`
  Repo guardrails, prompts, and skills used to keep AI work consistent with the codebase.
- `tools/mcp/`
  Local MCP documentation and tooling workspace that supports structured repo knowledge.

## Design Intent

The point of this layer is not automation for its own sake.
It is to keep AI work:

- scoped
- reviewable
- technically credible
- safe to hand off between workers

## How to Read It

1. Start with the relevant agent contract in `agents/`.
2. Follow the matching workflow in `workflows/` when the task needs multiple stages.
3. Use `.codex/` and `tools/mcp/` as the repository context behind the work.
4. Keep changes narrow enough that another worker can continue without re-deriving intent.
