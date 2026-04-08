# Compose Web Review

Repo-local skill note for reviewing this Compose Web app with a production mindset.

## Use This When

- reviewing layout regressions
- checking mobile compatibility
- validating visual consistency
- identifying composable extraction opportunities
- checking wasm-specific performance risks

## Review Checklist

- Does the section match the intended visual reference?
- Does it behave well on desktop and mobile widths?
- Are card heights and spacing intentionally aligned?
- Are images clipped, sized, and framed correctly?
- Are overlays and modals layered correctly?
- Is the implementation understandable without reverse engineering it?

## Preferred Output

- concrete findings
- smallest safe change
- note what was verified and what still needs runtime checking
