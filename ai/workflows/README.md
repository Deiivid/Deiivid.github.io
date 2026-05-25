# AI Workflows

This directory contains reproducible multi-agent workflow specs for the portfolio repo.

These YAML files are not ad hoc notes. They are execution contracts:

- one workflow = one clear objective
- stages run in order
- each stage has a single owner agent
- inputs and outputs are explicit
- quality gates are mandatory
- failure policy is declared up front

## Execution Model

Treat each workflow as a deterministic handoff chain.

1. Select the workflow by trigger or manual intent.
2. Load the workflow YAML.
3. Execute stages in order.
4. Pass only declared outputs to the next stage.
5. Stop on gate failure unless the workflow explicitly allows retry or escalation.

The workflow specs assume a repository context for a Kotlin Multiplatform portfolio site with Compose Web/Wasm and GitHub Pages deploys.

## Conventions

- `id`: stable machine-readable identifier.
- `description`: short summary of the workflow goal.
- `triggers`: when the workflow should be considered.
- `stages`: ordered list of handoffs.
- `stage.owner`: logical agent role, not a human name.
- `stage.input`: required artifacts or context for the stage.
- `stage.output`: artifacts produced by the stage.
- `stage.quality_gates`: checks that must pass before advancing.
- `failure_policy`: what to do when a stage fails.

## Agent Roles

Use consistent technical roles so the orchestration stays readable:

- `intake-agent` for brief triage and scope definition
- `architecture-agent` for structure, constraints, and module boundaries
- `implementation-agent` for code changes
- `ui-agent` for visual parity and layout work
- `review-agent` for correctness, drift, and consistency checks
- `qa-agent` for validation and smoke testing
- `release-agent` for packaging and deployment orchestration

## Artifact Rules

- Use repository-relative paths for files.
- Keep outputs narrow and reusable.
- Prefer markdown summaries when the result is meant for another agent.
- Prefer concrete file paths, commands, and acceptance criteria over vague prose.
- Do not invent hidden state. If a workflow depends on something external, declare it.

## Failure Policy

Default behavior across workflows:

- `halt` on missing requirements or ambiguous scope
- `retry` once for transient validation failures
- `escalate` when a decision affects architecture, release safety, or public UI behavior

If a stage cannot complete safely, it should produce a concise failure note with:

- what failed
- what input was missing
- what decision is needed next

## Workflow Files

- `feature-from-brief.yaml`: turns a product brief into scoped implementation work.
- `ui-parity-fix.yaml`: repairs Compose UI drift against the intended visual design.
- `release-to-pages.yaml`: validates and publishes the site to GitHub Pages.

## Practical Use

These specs are meant to be:

- readable by humans
- deterministic for agents
- easy to extend without changing their meaning

When adding a new workflow, keep it small, explicit, and tied to one delivery outcome.
