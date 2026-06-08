# AGENTS.md

This file defines repository-wide instructions for agents working anywhere inside this project tree.

## Project context

- This is a Kotlin Multiplatform, multi-module codebase with shared business logic reused by Android, iOS, and Web clients.
- Modules are intentionally separated by responsibility:
  - `helpers`: cross-platform utilities and reusable primitives
  - `core`: shared app-level infrastructure and common services
  - `feature_*`: feature modules containing reusable product logic
  - `feature_*_compose`: reusable shared Compose Multiplatform UI modules
  - `iosUmbrellaModule`: exported iOS framework aggregation layer
  - `android`: Android host app
  - `ios`: iOS host app in Swift/SwiftUI
  - `web-compose`: Web host app
- Favor preserving module boundaries over quick cross-module shortcuts.

## Working agreements

- Make focused, minimal changes that fit the current architecture.
- Fix root causes instead of layering temporary patches where practical.
- Do not move platform-specific code into shared modules unless it is truly reusable across targets.
- Do not introduce new dependencies, modules, or architectural patterns unless the task clearly needs them.
- Keep public APIs stable unless the requested change explicitly requires API updates.

## Architecture expectations

- Shared Kotlin code belongs in KMP modules first; host apps should stay thin and mostly wire UI, DI, and platform integrations.
- `helpers` should remain generic and reusable; avoid app-specific feature logic there.
- `core` should hold shared infrastructure, parsing, networking abstractions, and app-wide common services.
- `feature_*` modules should encapsulate a single feature end-to-end:
  - `data` for repositories, dao, network, mappers, and models
  - `presentation` for state, actions, events, and view model logic
- `iosUmbrellaModule` should aggregate/export shared KMP modules for Swift consumption, not become a second feature layer.
- Platform overrides should stay in the relevant platform source set or host app.

## Editing guidance

- Before changing files, check whether the change belongs in `commonMain`, a platform source set, or a host app.
- Match existing naming and package conventions such as `com.justadeveloper96.pokedex_kmp...`.
- Keep DI registration close to the owning module.
- Prefer extending existing abstractions over duplicating parallel implementations.
- Preserve existing MIT license headers in source files when editing files that already contain them.
- Do not add inline comments unless the task specifically benefits from them.

## Validation guidance

- Prefer targeted validation first:
  - module compilation for touched modules
  - relevant tests for touched shared code
  - platform-specific verification only where impacted
- Useful commands may include:
  - `./gradlew ktlintCheck`
  - `./gradlew test`
  - `./gradlew :android:assembleDebug`
  - `./gradlew :web-compose:browserDevelopmentRun`
- If a command is expensive or outside the user’s request, mention it before running when interactive approval matters.

## Coordination with code standards

- Follow the detailed coding conventions in `CODESTANDARDS.md`.
- If this file and `CODESTANDARDS.md` ever seem to conflict, prefer the more specific instruction for the file or module being changed.
