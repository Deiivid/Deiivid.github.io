# AI Agents

This directory contains technical agent profiles for orchestration inside this repo.

## Purpose

The agents here are not implementation code. They are role contracts that help AI workers:

- keep work scoped to the Kotlin Multiplatform / Compose Web codebase
- preserve the existing public visual identity of the portfolio
- coordinate handoffs between architecture, UI, QA, and release work
- avoid duplicated or conflicting edits when multiple workers run in parallel

## Convention

- One file per agent.
- YAML is preferred for machine readability and stable parsing.
- Keep the contract short, explicit, and repo-specific.
- Use the same top-level keys in every agent file:
  - `id`
  - `mission`
  - `scope`
  - `inputs`
  - `outputs`
  - `done_criteria`
  - `guardrails`
  - `handoff_to`
- Keep outputs concrete. Describe what the next worker can consume, not vague status prose.
- Keep scope aligned with this repo:
  - `composeApp/` for app implementation
  - `docs/migration/` for migration decisions and patterns
  - Compose Web / Wasm constraints
  - GitHub Pages delivery concerns

## How to Use

1. Pick the agent that matches the work.
2. Read its contract before starting.
3. Keep changes inside the agent’s scope.
4. Produce the outputs listed in the contract.
5. Hand off to the suggested next agent when the work is ready.

## Suggested Flow

- Architecture changes: `kmp-architect` -> `compose-ui-engineer` -> `qa-performance` -> `release-manager`
- UI parity or polish: `ux-ui-auditor` -> `compose-ui-engineer` -> `qa-performance`
- Release or delivery work: `release-manager` after the implementation and verification pass

## Parallel Work Rule

If another worker is already touching a section, treat that area as owned until the change is merged or explicitly handed off.
Prefer narrow edits and explicit handoffs over broad refactors.
