---
name: repo-context
description: Use when you need a compact reusable summary of this portfolio repo's stack, feature map, rules, and non-sensitive operating constraints.
---

# Repo Context

Use this skill when working anywhere in this repository and you need the minimum context to stay aligned.

## Read First

- `AGENTS.md`
- `docs/project-context.md`
- `.codex/config.toml`

## Repo Shape

- `composeApp/`: app implementation
- `docs/`: published output plus durable notes
- `ai/`: orchestration docs and reusable guidance
- `.codex/`: repo guardrails and focused skills
- `tools/mcp/`: local tooling and repo knowledge support

## Working Rules

- Preserve the public visual identity.
- Prefer incremental changes over rewrites.
- Keep business logic out of composables.
- Extract repeated UI only when reuse is real.
- Keep deployment mechanics separate from UI logic.

## Git Rules

- Do not rewrite history unless explicitly requested.
- Do not force-push unless history reset is part of the task.
- Keep commits small and descriptive.

## Sensitive Data

Do not add or commit:

- `.env` files
- secrets, tokens, passwords, certificates, keystores
- personal IDE caches or local machine config
- generated artifacts that belong only on a developer machine

If something looks sensitive, confirm before including it.
