# HTML Analysis

Repo-local skill note for comparing an external HTML/CSS reference with the Compose Web implementation in this project.

## Use This When

- a section in the KMP web does not match the original HTML
- spacing, icons, shadows, or alignment differ from the reference
- the user shares screenshots and wants pixel-level correction

## Process

1. Inspect the HTML source or screenshot and identify:
   - layout direction
   - responsive breakpoints
   - card/image sizes
   - spacing rhythm
   - icon treatment
   - interaction states
2. Map each visual decision to the closest Compose primitive already used in the repo.
3. Prefer targeted changes over broad redesign.
4. If the HTML uses CSS-only tricks that are awkward in wasm, document the compromise explicitly.

## Project Notes

- The visual source of truth is the original portfolio HTML, not a generic Compose interpretation.
- `docs/migration/` should hold any reusable mapping decisions discovered during analysis.
