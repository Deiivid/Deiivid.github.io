# Review Compose Structure

Use this prompt when reviewing the architecture or composition structure of the Compose Web app.

## Review Focus

Inspect:
- composable boundaries
- state ownership
- duplicated styling logic
- responsiveness
- wasm friendliness
- resource loading patterns

## What Good Looks Like

- screens are split into readable sections
- reusable card/button/tag patterns are extracted only when repetition is real
- state is hoisted and event handling is explicit
- layout decisions are understandable at a glance
- no accidental coupling between unrelated sections

## Red Flags

- one file doing too much with little separation
- repeated magic numbers without a reason
- desktop-only layout assumptions
- expensive recomposition paths
- hidden side effects inside composables
- project content and deployment concerns mixed together

## Output Style

When using this review prompt:
1. list concrete findings first
2. point to the exact composable or block
3. propose the smallest safe fix
4. call out residual risks or follow-up refactors separately
