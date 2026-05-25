# MCP Scripts

This directory holds shell-first helpers for validating and maintaining `tools/mcp/`.

Current script:

- `validate-mcp-configs.sh`: checks that the expected MCP docs and YAML stubs exist and contain the required top-level keys

Usage:

```bash
bash tools/mcp/scripts/validate-mcp-configs.sh
```

Rules:

- keep scripts deterministic and dependency-free
- prefer clear failure messages over silent fixes
- keep the script surface small unless the repo needs more automation
