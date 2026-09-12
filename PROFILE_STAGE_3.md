# Velum Profile — Stage 3

Added on top of Stage 2:

- Jellie/T-Rex companion movement smoothing.
- Lightweight idle bob animation when `Animate Pets` is enabled.
- No per-frame model parsing or file I/O.
- Pet rendering remains disabled when the Profile module is disabled.
- Existing Profile settings and OBJ cache are preserved.

## Android focus

The implementation uses Minecraft/Fabric rendering APIs already present in the client and does not add another rendering library. OBJ files are parsed once and cached.

## Next check

Build this revision with GitHub Actions and test `Player -> Profile -> Pets -> Jellie` in a real world on Zalith. If that renders correctly, the next stage can add a proper pet GUI preview and then custom player models/accessories.
