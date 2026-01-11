# Blocktopograph

_This project is under active development._

Blocktopograph is a **high-performance, multi-platform Minecraft world editor** designed to empower users with professional-grade tools for both Java and Bedrock Editions. Built with a "Performance by Default, Security by Design" philosophy, it features a shared Rust engine, a sandboxed modding system, and a modern UI.

<a href="https://discord.gg/u8GCn23naN" title="Discord Server">
    <img src="https://dcbadge.limes.pink/api/server/https://discord.gg/u8GCn23naN" />
</a>

## Core Mission

To provide a robust, developer-friendly ecosystem for Minecraft world manipulation, supporting Windows and Android with a unified core logic and platform-native experiences.

## Key Features

- **Universal Support:** Seamlessly edit both Bedrock Edition (LevelDB) and Java Edition (Anvil/NBT) worlds.
- **High Performance:** Native Rust-based engine for ultra-fast I/O and memory efficiency.
- **Secure Modding:** A sandboxed modding system powered by WebAssembly (Wasm), allowing mods in JavaScript, TypeScript, Rust, and more.
- **Data Integrity:** Built-in "bad data" detectors and audit logging to prevent world corruption.
- **Client-Server Architecture:** Support for both local editing and remote headless server management via gRPC.

## Technology Stack

| Component          | Technology                                      |
| :----------------- | :---------------------------------------------- |
| **Core Engine**    | **Rust** (Shared logic, World Parsing)          |
| **UI Framework**   | **Tauri v2** + **React** (Desktop & Mobile)     |
| **Bedrock Parser** | `bleveldb` (C++ FFI via Mojang's official fork) |
| **Java Parser**    | `fastanvil` + `fastnbt` (Pure Rust)             |
| **Plugin Runtime** | **Wasm** via **Extism/Wasmer**                  |
| **Communication**  | **gRPC** (Tonic)                                |
| **Styling**        | TailwindCSS + Shadcn/ui                         |

## Architecture Overview

The project follows a decoupled architecture where the **Shared Rust Core** handles the heavy lifting, and platform-specific layers (Tauri for Desktop/Mobile) manage the user interface.

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

## Getting Started

_(Detailed installation and setup instructions will be added as the project matures.)_

### Prerequisites

- [Minecraft](https://www.minecraft.net/en-us/about-minecraft)
- [Rust toolchain](https://rustup.rs/)
- [Node.js](https://nodejs.org/) (for frontend development)
- [Tauri CLI](https://v2.tauri.app/start/prerequisites/)

## Documentation

For more detailed technical details, see the [Technology Proposal](docs/technology_proposal.md).

---

## Stargazers over time

[![Stargazers over time](https://starchart.cc/NguyenDuck/blocktopograph.svg?variant=adaptive)](https://starchart.cc/NguyenDuck/blocktopograph)

## Legal Compliance

Blocktopograph is **NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

We are committed to respecting Mojang's intellectual property. This software does not redistribute Minecraft assets; it requires a legitimate local installation to function. For more details on our compliance strategy, see the [Legal Compliance section](docs/technology_proposal.md#8-legal-compliance--copyright) in our Technology Proposal.

## License

Licensed under either of

- Apache License, Version 2.0
  ([LICENSE-APACHE](LICENSE-APACHE) or http://www.apache.org/licenses/LICENSE-2.0)
- MIT license
  ([LICENSE-MIT](LICENSE-MIT) or http://opensource.org/licenses/MIT)

at your option.
