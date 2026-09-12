# Velum Profile — Stage 1

This stage adds the unified `Profile` module under `Player`.

## Added
- `velum.client.profile.ProfileModule`
- `ProfileManager` with a render-event lifecycle hook
- `PetManager` state model (`None`, `Jellie`, `T-Rex`)
- `ModelManager` state model (`Default`, `Amogus`, `Rabbit`, `Demon`, `Freddy`)
- Settings for Player Model, Pets, Accessories, Cosmetics, pet animation and third-person visibility
- Module registration in `ModuleManager`
- English translation keys

## Mobile constraints
The stage does not add desktop-only APIs or external libraries. The render hook is dormant until the model renderer is added, so enabling the module does not parse models or allocate per-frame render data.

## Next stage
Implement the cached OBJ pet renderer and connect Jellie first, then T-Rex. The renderer should be resource-cached and only run while Profile is enabled.

## Build note
The supplied Gradle wrapper uses Gradle 8.12.1. This environment cannot download the wrapper distribution, so a full Gradle build could not be executed here. The source changes were made directly against the supplied Velum 1.21.4 source tree.
