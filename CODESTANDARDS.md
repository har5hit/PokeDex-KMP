# CODESTANDARDS.md

This document captures the coding standards for this repository’s Kotlin Multiplatform, Android, iOS, and Web code.

## Core principles

- Optimize for shared, testable business logic in KMP modules.
- Keep host apps thin and platform-native in presentation and integration points.
- Prefer clarity and consistency over clever abstractions.
- Keep each module cohesive and avoid leaking feature details into lower-level modules.

## Module placement rules

- Put code in `commonMain` only when it is genuinely reusable across targets.
- Put target-specific implementations in `androidMain`, `iosMain`, or `jsMain`.
- Keep SwiftUI and iOS app lifecycle code in `ios/`.
- Keep Android UI, manifest, and Android-only integrations in `android/`.
- Keep web host UI and runtime setup in `web-compose/`.
- Add new feature logic to a `feature_*` module instead of expanding host apps with shared business behavior.
- Put shared Compose UI in dedicated Compose-focused shared modules rather than inside host apps.

## Kotlin standards

- Follow ktlint-compatible formatting and existing repository style.
- Use explicit, descriptive names for classes, functions, properties, and files.
- Prefer immutable `val` over `var` unless mutation is required.
- Keep functions small and intention-revealing.
- Avoid one-letter names except for trivial local lambdas where already idiomatic.
- Prefer composition and small abstractions over inheritance-heavy designs.
- Keep constructors and DI wiring straightforward.
- Use interfaces where they clarify ownership, testability, or platform substitution.

## KMP design standards

- Model shared behavior behind common contracts where platform implementations differ.
- Use `expect`/`actual` only when a normal abstraction cannot express the platform variation cleanly.
- Avoid referencing Android, iOS, or JS frameworks directly from shared code.
- Keep source set dependencies directional:
  - `helpers` lowest-level reusable foundation
  - `core` depends on `helpers`
  - `feature_*` depends on `core`
  - host apps depend on feature modules
- Do not create circular dependencies between modules.

## Presentation and state

- Follow the repo’s MVVM approach in shared modules.
- View models should expose state and one-off events clearly, and accept user actions explicitly.
- Keep UI state models simple and serializable where practical.
- Put presentation logic in view models, not in host-platform views.
- Keep host views responsible for rendering, binding, and platform UI behavior.

## Data layer

- Repositories should own orchestration between network, persistence, and mappers.
- Keep DAO, network DTO, domain, and UI models distinct when responsibilities differ.
- Prefer dedicated mapper functions/classes over ad hoc conversion scattered through the codebase.
- Keep database and network concerns out of presentation code.
- Handle pagination, caching, and error translation in the data/domain layer rather than in views.

## Dependency injection

- Use Koin consistently in shared Kotlin modules.
- Keep DI definitions inside the owning module, such as `HelperModule`, `CoreModule`, or feature modules.
- Prefer constructor injection over service-locator style access in shared code.
- Keep platform bootstrap code thin and delegate object creation into shared DI where possible.

## Concurrency and flows

- Use coroutines and flows consistently with existing patterns.
- Keep dispatcher usage explicit for work that changes thread/context expectations.
- Avoid blocking calls in shared coroutine code.
- Ensure view model state/event updates happen through the established flow-based APIs.

## Testing

- Prefer TDD-friendly, deterministic shared code.
- Add or update targeted tests when changing business logic, parsing, mapping, repository behavior, or view model behavior.
- Keep tests close to the module and platform they validate.
- Use existing testing libraries and patterns already present in the repo, including Kotest and Mockk.
- Do not add broad integration coverage when a focused unit test is enough.

## Android standards

- Keep Android-specific logic inside the Android module or `androidMain` source sets.
- Prefer Compose for new Android UI work unless the surrounding file/pattern is clearly view-based and the task should stay consistent with it.
- Reuse existing Android dependency declarations from `buildSrc` rather than hardcoding versions inline.
- Respect existing Gradle and packaging configuration when adding Android dependencies.

## iOS standards

- Keep iOS code idiomatic Swift/SwiftUI.
- Use the Kotlin umbrella framework as a bridge to shared logic, not as a place to reimplement shared behavior in Swift.
- Keep Swift wrappers lightweight and focused on adapting flows/state into SwiftUI-friendly structures.
- Favor small SwiftUI views and explicit data flow.

## Web standards

- Keep Compose Multiplatform web-specific setup within `web-compose`.
- Avoid introducing web-only behavior into shared modules unless guarded behind a shared abstraction with platform implementation.
- Preserve existing webpack and distribution conventions.

## Gradle and dependency management

- Keep dependency versions centralized in `buildSrc/src/main/kotlin/`.
- Reuse the existing plugin and module conventions before adding new build logic.
- Keep module `build.gradle.kts` files aligned with neighboring modules.
- Prefer the smallest dependency surface necessary for the task.

## Change hygiene

- Make the smallest architectural change that solves the problem cleanly.
- Avoid unrelated refactors while touching a file.
- Preserve file organization and naming conventions already used by the module.
- Update documentation when behavior, setup, or developer workflow materially changes.
