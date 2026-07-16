# Migration Decisions

Track the non-obvious decisions made during the HTML to KMP migration.

## Current Decisions

### 1. `docs/` remains the published output folder

Reason:
GitHub Pages currently consumes generated static output from `docs/`.

Impact:
Extra documentation under `docs/migration/` is acceptable, but build/publish scripts should avoid wiping these notes unless intentionally regenerated.

### 2. Repo-local `.codex/` is documentation, not an auto-installed Codex skill registry

Reason:
Codex auto-loaded skills live outside the repo, but project-local guidance is still valuable here.

Impact:
These files are meant to be read as project context, versioned with the app, and kept aligned with the codebase.

### 3. Prefer incremental UI parity over large rewrites

Reason:
The project is already close to the HTML reference in several sections, so targeted fixes are safer than broad restyling.

Impact:
When a section is off, adjust sizing, spacing, composition, and extracted patterns first.

### 4. Keep repo context split between durable docs and reusable agent guidance

Reason:
The repository now uses `AGENTS.md` for top-level operating rules, `docs/project-context.md` for project-wide context, `.codex/` for repo guardrails, and `ai/skills/` for reusable markdown guidance.

Impact:
Future edits should keep those layers aligned instead of duplicating the same rules in several places. Keep `ai/` readable and reusable, but avoid adding sensitive, local-only, or machine-specific data there.

### 5. The portfolio uses a cinematic 2.5D office as its primary navigation

Reason:
The portfolio content is presented as an animated workspace: the character walks to the desk, starts typing, and reveals the main navigation around him.

Impact:
CV, projects, profile, experience, email, and social links remain accessible as regular Compose UI controls. The office always fills the browser viewport without a card wrapper. The first-person entrance is a 4.8-second, 60 fps H.264 continuous camera sequence built from one stable rear view. It advances behind the chair with restrained walking motion, lets the chair back fully occlude the lens, and emerges into the seated perspective before holding a pixel-matched last frame for the Compose handoff. The only perspective change happens during that complete physical occlusion; the sequence contains no dissolves, interpolated scene cuts, or whole-frame focus pulses. David's wireless low-profile Aurora Lily58—with two clearly separated 29-key halves, white MBK-style keycaps, thin matte plates, contact shadows, and no connecting cable—is visible in the seated view; the workstation also includes a Mac Mini and an Android development phone. The seated frame is kept as a high-resolution 3344×1882 asset so the desk materials, hands, and keyboard stay crisp on Retina displays. The six controls then appear as responsive orbital navigation inside the physical ultrawide monitor. Selecting a node retracts the other five and expands that section from its own position; all text, icons, actions, and content remain native Compose UI. The Luffy, Goku, and Naruto collectible trio is a static desk prop centered directly below the monitor; its position and scale never change during the intro or the portfolio view. The city window remains unobstructed and David is not rendered again in third person.

## Update Policy

Add an entry here when a migration or architecture decision would otherwise be rediscovered later.
