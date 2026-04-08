# MCP Workspace

This folder is reserved for local MCP-related tooling that supports the web project.

## Structure

- `server/`: MCP server code, adapters, or local experiments
- `scripts/`: helper scripts to run or package MCP tooling

## Rules

- keep secrets and machine-specific values out of committed files
- document any required runtime in the corresponding script or server module
- prefer small single-purpose tools over one giant local utility

## Suggested Use

Use this area for:
- local content extraction helpers
- migration assistants
- UI inspection utilities
- project-specific automation that should live near the repo
