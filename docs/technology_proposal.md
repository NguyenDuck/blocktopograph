# Blocktopograph

## Project Objectives

### Core Mission

Build a **high-performance, multi-platform Minecraft world editor** that empowers users to create, modify, and enhance Minecraft worlds (Java Edition & Bedrock Edition) with professional-grade tools, while providing a **developer-friendly modding ecosystem**.

### Primary Goals

#### 🎯 Multi-Platform First

- **Primary Targets:** Windows & Android
- **Future Expansion:** Linux, iOS, macOS
- **Architecture:** Shared core logic with platform-specific UI layers

#### 🌍 Comprehensive World Editing

- **Bedrock Edition Support:** Full LevelDB world manipulation
- **Java Edition Support:** Complete Anvil/NBT/MCA format handling
- **Real-time Editing:** Live preview and modification capabilities
- **Batch Operations:** High-performance bulk editing and transformations

#### 🔌 Developer-Friendly Modding System

- **Safe Plugin Architecture:** Sandboxed execution environment (WebAssembly)
- **Multiple Languages:** Support for JavaScript/TypeScript, Rust, Python, AssemblyScript
- **Rich SDK:** Type-safe APIs, hot-reload, debugging tools
- **Cross-Platform Mods:** Write once, run on all platforms

#### ⚡ High Performance

- **Native Core:** Rust-based engine for maximum speed and memory efficiency
- **Efficient I/O:** Optimized world file reading/writing
- **GPU Acceleration:** Hardware-accelerated rendering where available
- **Minimal Resource Usage:** Suitable for mobile and desktop environments

#### 🛡️ Security & Stability

- **Sandboxed Plugins:** Isolated execution prevents malicious code
- **Memory Safety:** Rust eliminates entire classes of bugs
- **Audit Logging:** Track all world modifications
- **Data Validation:** Prevent corruption of world files

---

## Technology Stack

This document outlines the technology stack based on the objectives above. The core philosophy is **"Performance by Default, Security by Design"** using Rust's zero-cost abstractions and memory safety, WebAssembly's sandboxing, wrapped in platform-native UIs.

---

## 1. Core Application Framework

### Multi-Platform Architecture Overview

The application uses a **shared Rust core** with platform-specific UI layers:

```
┌─────────────────────────────────────────┐
│         Shared Rust Core Engine         │
│  (World Parsing, Plugin System, Logic)  │
└───────────────────┬─────────────────────┘
                    │
           ┌────────┴────────┐
           │                 │
      ┌────▼────┐       ┌────▼─────┐
      │ Desktop │       │  Mobile  │
      │ (Tauri) │       │ (Native) │
      └─────────┘       └──────────┘
```

---

### 1.1 Desktop Platform (Windows Primary)

**[Tauri v2](https://v2.tauri.app/)** (Rust + Web Frontend)

| Feature                | Why Tauri?                                                                                    |
| :--------------------- | :-------------------------------------------------------------------------------------------- |
| **Security**           | Built-in security hardening. The backend is Rust (memory safe), and the frontend is isolated. |
| **Performance**        | Extremely lightweight binaries (~3-10MB). Heavy lifting is done in Rust, not JS.              |
| **Windows Support**    | Excellent WebView2 integration, native installers (MSI, NSIS), auto-updater.                  |
| **Frontend Agnostic**  | Use React/Vue/Svelte for modern UI without Electron's overhead.                               |
| **Plugin Integration** | Direct FFI to Rust core, enabling Wasm plugin hosting.                                        |

**Windows-Specific Optimizations:**

- WebView2 for native browser engine
- MSIX packaging for Microsoft Store distribution
- Windows APIs for file association (`.mcworld`, `.mca` files)

---

### 1.2 Mobile Platform (Android Primary)

#### [Tauri Mobile (Alpha)](https://github.com/tauri-apps/tauri-mobile)

Tauri v2 is developing mobile support for iOS and Android.

**Pros:**

- WebView-based UI maintains visual consistency
- Single codebase for desktop + mobile

**Cons:**

- Still in alpha, may have stability issues
- WebView performance on budget Android devices can vary

---

### Strategy (Phased Approach)

#### Phase 1: Desktop Foundation

- Build Windows app with **Tauri v2**
- Establish Rust core architecture
- Implement plugin system

#### Phase 2: Android Implementation

- **If Tauri Mobile is stable:** Migrate to Tauri Mobile for rapid cross-platform deployment
- **If Tauri Mobile is not ready:** Build Android app with **Rust + Jetpack Compose**
    - Extract Rust core to shared library
    - Create Kotlin/Compose UI layer
    - Use JNI bindings via `uniffi-rs`

**Alternative considered:** _Electron_ (No mobile support), _Qt_ (Steep learning curve, paid license for mobile), _React Native_ (Poor Rust integration).

---

### 1.3 Android-Specific Mobile Optimizations

#### Storage Access

- **Storage Access Framework (SAF):** Use Android 11+ Scoped Storage for secure world file access
- **External Storage:** Handle `/Android/data/com.mojang.minecraftpe/files/games/com.mojang/` paths
- **Permission Handling:** Request `MANAGE_EXTERNAL_STORAGE` for legacy storage access (Android 10-)

#### Performance Considerations

- **Memory Constraints:** Implement chunked loading (max 16-32 chunks in memory at once)
- **Background Processing:** Use Kotlin Coroutines + Rust async runtime for non-blocking I/O
- **Battery Optimization:** Batch write operations, minimize wake locks
- **APK Size:** Native libraries (~2-5MB), keep total APK under 50MB for better adoption

#### Mobile UX Patterns

- **Touch Gestures:** Pinch-to-zoom, two-finger pan for 2D/3D world view
- **Material You:** Dynamic theming support for Android 12+
- **Adaptive UI:** Support for tablets, foldables, and split-screen
- **Offline-First:** All editing happens locally, no internet required

#### Build Configuration

```toml
# Cargo.toml - Android targets
[lib]
crate-type = ["cdylib"]  # For JNI

[profile.release]
opt-level = "z"          # Optimize for size
lto = true               # Link-time optimization
codegen-units = 1        # Better optimization
strip = true             # Remove debug symbols
```

**Android NDK Targets:**

- `aarch64-linux-android` (ARM64 - primary, 95%+ of devices)
- `armv7-linux-androideabi` (ARM32 - legacy support)
- `x86_64-linux-android` (Emulator testing)

---

## 2. Backend & World Parsing (The "Engine")

**Language:** **Rust**  
Rust is non-negotiable for this stack due to the requirements for security, performance, memory safety, and **cross-compilation** to Android/iOS without a garbage collector.

### World Parsing Libraries

#### Bedrock Edition (LevelDB)

**`bleveldb` Ecosystem (Safe Rust Wrapper over Mojang's C++ Fork)**

- **Architecture:**
    - **`bleveldb-sys`**: Direct FFI bindings to Mojang's official C++ LevelDB fork, ensuring 100% format compatibility.
    - **`bleveldb`**: A high-level, idiomatic Rust API built on top of the `-sys` crate for memory safety and ease of use.
- **Pros:** Native compatibility with Bedrock's specific LevelDB modifications; high-performance and battle-tested.
- **Cons:** Complex cross-compilation for Android (requires NDK and a C++ toolchain).
- **Current State:** Using both crates to leverage the power of the C++ core while maintaining Rust's safety.

**Mobile Optimization:**

- Use memory-mapped I/O (`mmap`) for faster chunk access on modern Android devices
- Implement LRU cache for recently accessed chunks (~50MB cache on mobile)
- Lazy loading: Only decompress chunks when needed

#### Java Edition (Anvil/MCA + NBT)

**Primary Libraries:**

- **`fastanvil`** - High-performance region file parser
- **`fastnbt`** - Zero-copy NBT deserialization
- **`flate2`** - Zlib/GZip decompression (chunk compression)

**Mobile Optimization:**

- Region file streaming (don't load entire `.mca` into memory)
- Incremental chunk parsing (parse only requested chunk sections)
- Background decompression using `tokio` async tasks

### Cross-Platform I/O Strategy

```rust
#[cfg(target_os = "android")]
use android_logger;

#[cfg(not(target_os = "android"))]
use env_logger;

// Unified async I/O across platforms
pub trait WorldDecoder: Send + Sync {
    async fn get_chunk(&self, x: i32, z: i32) -> Result<UnifiedChunk>;
    async fn set_chunk(&mut self, x: i32, z: i32, chunk: UnifiedChunk) -> Result<()>;
}
```

---

## 3. Secure Modding System (The "Sandbox")

**WebAssembly (Wasm) via [Extism](https://extism.org/) or [Wasmer](https://wasmer.io/)**

To satisfy the strict security requirements ("bad host detector", "bad data detector"), plugins **cannot** be native DLLs/Shared Objects. They must be sandboxed.

### Architecture

1.  **Host (Rust/Tauri):** Defines a strict API (Contract) that plugins can call.
2.  **Plugin (Wasm):** Compiled from Rust, AssemblyScript, Go, or JavaScript.
3.  **Sandbox:** The Wasm runtime isolates the plugin. It cannot access files or network unless the Host explicitly allows it via a "Host Function".

### Security Implementation

- **Log Everything:** Every call crossing the Host<->Guest boundary is intercepted by the Host. We can log `read_chunk(x, z)`, `get_block_id()`, etc.
- **Access Control:** The system can enforcing granular permissions. E.g., "This plugin is only allowed to Read chunks, not Write."
- **Bad Data Detector:** Since all data passes through the Host, we can run validation logic on the modified data returned by a plugin _before_ applying it to the world.

### User-Friendly Modding (Developer Experience)

To ensure the "Moddable" goal is met without alienating modders:

- **Supported Languages:** While Wasm is the target, modders can write in high-level languages.
    - **JavaScript/TypeScript:** Powered by **Extism JS PDK** (uses QuickJS inside Wasm). This allows modders to write standard JS/TS.
    - **Rust:** The "Gold Standard" for performance. Compiles to highly optimized Wasm. Best for heavy World Generation or bulk chunk processing keys.
    - **AssemblyScript:** A great middle-ground. Familiar TS-like syntax but strict typing for near-native Wasm speed.
    - **Python:** Supported via Wasm-compatible Python runtimes.

- **DK (Development Kit):** We will provide an `@blocktopograph/sdk` npm package.
    - **Type Definitions:** Full TypeScript definitions for the editor API (`world.getBlock()`, `transaction.commit()`).
    - **CLI Tool:** A simple `blocktopograph create` CLI to scaffold new mods and handle the Wasm compilation step automatically.
    - **Hot Reloading:** Modify a `.ts` file -> Auto-compile to `.wasm` -> Editor instantly reloads the plugin without restarting.

### Advanced Modding Architecture (Client + Server)

To support the distributed nature of the app, mods are split into two logical parts:

#### 1. Server-Side Mods (The "Logic")

- **Environment:** Runs in the Rust Server (via Extism/Wasm).
- **Capabilities:** Full access to World Data, Block manipulation, World Gen.
- **Communication:** Can talk to other Server Mods via the **Event Bus**.

#### 2. Client-Side Mods (The "View")

- **Environment:** Runs in the Tauri Client (WebView / JS Sandbox).
- **Capabilities:** Custom UI overlay, 3D Rendering (Shaders), Input handling.
- **Communication:** Can talk to other Client Mods via `window.dispatchEvent`.

#### 3. The "Net-Bridge" (Cross-Network Communication)

A secure tunnel for a Client Mod to talk to its Server counterpart (and vice-versa).

- **Protocol:** `gRPC` Stream carries a generic `ModPacket { mod_id: string, data: bytes }`.
- **Flow:**
    1.  Client Mod sends `api.sendToServer({ action: "spawn_entity" })`.
    2.  Client Core wraps this into a `ModPacket` and pushes it over gRPC.
    3.  Server Core receives packet, looks up `mod_id`, and calls the Wasm function `on_network_message(data)`.
    4.  Server Mod processes logic and sends a response back via `host.sendToClient(...)`.

---

### Mod-to-Mod Communication (Local)

## 4. Security & Auditing Features

To address "bad host/data detector":

- **Network Guard:** By default, disable all network access for the Tauri app except for whitelisted update checks. Plugins get **ZERO** network access unless explicitly granted by the user per-plugin.
- **File System Jail:** The editor core only opens the specific world folder selected by the user. Plugins never see absolute paths, only relative handles provided by the Host.
- **Audit Log:** creating a log that records every file modification and plugin action.

---

## 5. Client-Server Architecture

To support both **Bundled (Desktop App)** and **Headless (Remote Server)** modes, we will use a **Unified Decoupled Architecture**.

### Components

1.  **Server (`bserver`):** A standalone Rust binary.
    - Wraps the Core Logic and Plugin System.
    - Exposes a **gRPC** API (via `tonic`).
    - **Headless Mode:** Runs on a dedicated server (Linux/Windows), binds to `0.0.0.0`.
    - **Bundled Mode:** Spawned as a "Sidecar" by the Client, binds to `127.0.0.1` (random port).

2.  **Client (`bclient`):** The Tauri Application.
    - **Frontend:** React + gRPC-Web Client.
    - **Mode Switch:**
        - _Local:_ Spawns the Server binary automatically. Connects to `localhost`.
        - _Remote:_ Connects to a user-specified IP address.

### Communication Protocol

**gRPC (with Protobuf)**

- **Why?**
    - **Performance:** Highly efficient for large binary payloads (Chunk data).
    - **Streaming:** Supports streaming Chunk data to the client for real-time visualization.
    - **Schema:** Strict API definition (`.proto`) ensures the Client and Server never get out of sync.

---

## 6. UI/UX Strategy

**Frontend:** **React** + **TailwindCSS** + **Shadcn/ui**

- **Aesthetics:** Modern, "Dark Mode" first, glassmorphism effects.
- **Visualization:** Use `Three.js` or `React-Three-Fiber` for rendering 3D slices of the worldchunks in the browser view.

---

## 7. Future Proofing Strategy (High-Performance Rendering)

To address future requirements for "High Performance Shaders" or advanced rendering beyond WebGL capabilities:

### Level 1: WebGPU (The Web Standard)

- **Approach:** Use the upcoming **WebGPU** standard directly in the Tauri Frontend (React).
- **Pros:** Native-like GPU access from JS, high performance compute shaders, standard web compatibility.
- **Cons:** Dependent on WebView support (Chrome/Edge/Safari support is growing rapidly).

### Level 2: Native Rust Rendering (The "Nuclear" Option)

If Web functionality is insufficient, we can seamlessly upgrade to **Native Rendering**:

- **Technique:** Use `raw-window-handle` to let Rust draw directly to a child window or overlay surface using `wgpu` or `vulkan`.
- **Architecture:**
    - Tauri handles the "UI Shell" (Menus, Inputs, HUD).
    - Rust spawns a native `wgpu` surface for the "3D Viewport".
- **Benefit:** Zero abstraction overhead, full control over the GPU pipeline, capability to run complex Compute Shaders for world simulation.

---

## Development Roadmap

### Phase 1: Core Foundation (Windows Desktop)

1. **Prototype:** Minimal Tauri v2 app that opens a Bedrock LevelDB world and displays 2D chunk data
2. **Parser Integration:**
    - Implement `fastanvil` + `fastnbt` for Java Edition worlds
    - Finalize `bleveldb-sys` bindings for Bedrock Edition
    - Create unified `WorldDecoder` trait
3. **Basic UI:** React-based chunk viewer with hex/grid display
4. **Plugin System (MVP):** Integrate Extism/Wasmer to run a simple Wasm "block replacer" plugin

### Phase 2: Advanced Features (Windows)

1. **Full Plugin SDK:**
    - JS/TS/Rust development kits
    - Hot-reload functionality
    - Plugin marketplace UI
2. **3D Visualization:** Three.js/WebGPU-based 3D world renderer
3. **Advanced Editing Tools:**
    - Brush tools, selection tools
    - Undo/redo system
    - Batch operations
4. **gRPC Server:** Split architecture into client-server for remote editing

### Phase 3: Android Port

**Decision Point:** Evaluate Tauri Mobile stability

- **Path A (Tauri Mobile is stable):**
    - Migrate to Tauri Mobile
    - Adjust UI for touch/mobile constraints
    - Test on Android 11+ devices
- **Path B (Tauri Mobile not ready):**
    - Extract Rust core to shared library (`libblocktopograph.so`)
    - Build Kotlin/Jetpack Compose Android UI
    - Implement JNI bindings via `uniffi-rs`
    - Handle Android storage permissions (SAF)

### Phase 4: Optimization & Polish

1. **Performance Tuning:**
    - Mobile memory optimizations (chunk caching)
    - Battery usage optimization
    - APK size reduction
2. **Platform Integration:**
    - Windows: File associations, installer (MSI/NSIS)
    - Android: Share menu integration, widget support
3. **Security Audit:** Third-party review of plugin sandbox
4. **Beta Testing:** Community testing on diverse devices

### Phase 5: Launch & Maintenance

1. **Public Release:** Windows (Microsoft Store, GitHub) + Android (Play Store)
2. **Documentation:** User guides, plugin development tutorials
3. **Community:** Plugin marketplace, Discord/forum support

---

## 8. Legal Compliance & Copyright

To ensure long-term stability and minimize legal risks associated with Minecraft's intellectual property, the following strategies are implemented:

### 8.1 Asset Usage Policy

- **Zero Redistribution:** The software will **not** include any official Minecraft assets (textures, models, sounds, icons) in its distribution or repository.
- **Client-Side Extraction:** Visual assets will be dynamically loaded from the user's local Minecraft installation. Users must provide the path to their legitimate game files, from which the editor will extract necessary textures for previewing.
- **Fallback Visuals:** If no official assets are found, the editor will use simplified flat colors or open-source (CC0/CC-BY) fallback textures.

### 8.2 Branding & Intellectual Property

- **Official Disclaimer:** Every distribution and public interface (README, About screen) must prominently display: _NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT._
- **Naming Conventions:** The name "Blocktopograph" is used as the primary identifier to avoid brand confusion. "Minecraft" is only used in secondary descriptions to indicate compatibility.

---

## Technology Summary Table

| Component          | Windows                          | Android                         | Notes                                          |
| :----------------- | :------------------------------- | :------------------------------ | :--------------------------------------------- |
| **UI Framework**   | Tauri v2 + React                 | Tauri Mobile OR Jetpack Compose | Phased decision based on Tauri Mobile maturity |
| **Core Engine**    | Rust                             | Rust (shared library)           | 100% shared codebase                           |
| **Bedrock Parser** | `bleveldb` (C++ FFI via -sys)    | `bleveldb` (C++ FFI via -sys)   | Using Mojang's fork for 100% compatibility     |
| **Java Parser**    | `fastanvil` + `fastnbt`          | `fastanvil` + `fastnbt`         | Identical implementation                       |
| **Plugin Runtime** | Extism/Wasmer (Wasm)             | Extism/Wasmer (Wasm)            | Identical plugin API                           |
| **3D Rendering**   | WebGPU OR native `wgpu`          | WebGPU (WebView) OR Vulkan      | Performance-dependent                          |
| **Communication**  | gRPC (Tonic)                     | gRPC (Tonic)                    | Client-server architecture                     |
| **Packaging**      | MSI, NSIS, MSIX                  | APK (AAB for Play Store)        | Platform-specific installers                   |
| **Distribution**   | GitHub Releases, Microsoft Store | Google Play Store, F-Droid      | Multi-channel distribution                     |

---

_Last edited: 2026-01-11_
