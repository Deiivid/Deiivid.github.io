#!/usr/bin/env bash
set -euo pipefail

root_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
server_dir="$root_dir/server"

required_files=(
  "$root_dir/README.md"
  "$root_dir/catalog.md"
  "$server_dir/mcp-kmp-docs.yaml"
  "$server_dir/mcp-compose-web-patterns.yaml"
  "$server_dir/mcp-ux-ui-principles.yaml"
  "$server_dir/mcp-repo-knowledge.yaml"
)

required_keys=(
  "name:"
  "source:"
  "ingestion_strategy:"
  "update_cadence:"
  "risks:"
)

fail() {
  echo "validate-mcp-configs: $1" >&2
  exit 1
}

for file in "${required_files[@]}"; do
  [[ -f "$file" ]] || fail "missing required file: ${file#$root_dir/}"
done

for file in "$server_dir"/*.yaml; do
  [[ -e "$file" ]] || fail "no YAML configs found in ${server_dir#$root_dir/}"
  for key in "${required_keys[@]}"; do
    grep -q "^${key}" "$file" || fail "missing key '${key%:}' in ${file#$root_dir/}"
  done
done

echo "validate-mcp-configs: ok"
