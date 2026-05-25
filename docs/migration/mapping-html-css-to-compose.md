# Mapping HTML/CSS To Compose

Quick reference for this repo.

## Layout

- `display: flex; flex-direction: row` -> `Row`
- `display: flex; flex-direction: column` -> `Column`
- `gap` -> `Arrangement.spacedBy(...)`
- `justify-content` -> horizontal or vertical arrangement depending on parent
- `align-items` -> `Alignment.*`

## Sizing

- fixed width/height -> `Modifier.width/height`
- max width -> `Modifier.widthIn(max = ...)`
- min height for aligned cards -> `Modifier.heightIn(min = ...)`
- aspect ratio media -> `Modifier.aspectRatio(...)`

## Visual Styling

- `border-radius` -> `RoundedCornerShape(...)`
- `border` -> `Modifier.border(...)`
- `box-shadow` -> `Modifier.shadow(...)` or layered `Box`
- gradients/blur glows -> `Brush` or layered backgrounds

## Responsive Strategy

- CSS breakpoints -> `BoxWithConstraints`
- mobile-specific layout branch -> width checks with explicit thresholds
- avoid assuming browser DOM/CSS fallback behavior in Compose

## Interaction

- hover-only behavior should degrade safely on touch devices
- DOM modal behavior -> layered `Box` with `zIndex`, scrim, and centered content
