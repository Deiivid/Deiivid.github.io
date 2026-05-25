# Extract UI Patterns

Use this prompt when the codebase has repeated UI blocks and we want to standardize them without overengineering.

## Objective

Extract only the patterns that already exist in at least two or three places and are likely to stay consistent.

## Candidate Patterns In This Repo

- social/action buttons
- skills cards
- project cards
- timeline chips
- modal/game overlays
- section wrappers and spacing

## Extraction Rules

- extract the minimum API that keeps the caller readable
- prefer data classes only when the content is clearly structured
- do not create a generic component if the visual language still differs meaningfully
- preserve existing naming and visual hierarchy
- keep styling near usage unless reuse is real

## Success Criteria

- less duplication
- easier UI iteration
- no loss of readability
- no speculative design-system layer
