# Velum Profile — Stage 2

Added the first real 3D pet renderer.

## Included
- Lightweight dependency-free OBJ parser.
- Cached/flattened OBJ meshes; no model parsing every frame.
- Jellie model + texture.
- T-Rex model + texture.
- Pet follows the local player with smooth movement.
- Pet position is camera-relative for the existing Velum Render3D pipeline.
- Pet model is centered and grounded automatically.
- Rendering uses Minecraft's existing POSITION_TEX_COLOR pipeline.
- No new external libraries.

## Mobile considerations
- Models are parsed lazily and cached.
- The Jellie mesh is only 132 triangles.
- No per-frame file IO or OBJ parsing.
- The renderer uses the existing Minecraft rendering API and should remain compatible with Fabric/Zalith.
