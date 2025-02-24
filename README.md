# Blocktopograph

Blocktopograph is an open-source world editor for Minecraft Bedrock Edition (MCBE).

![Discord Server](https://dcbadge.limes.pink/api/server/https://discord.gg/u8GCn23naN)

## Features

-   **Cross-Platform Compatibility**
    Designed to run seamlessly on multiple platforms, leveraging Rust's portability.

-   **Bevy Engine Integration**
    Utilizes the Bevy game engine for efficient rendering and system management.

-   **Advanced World Editing**
    Provides comprehensive tools to edit MCBE worlds with support for the latest features.

## Prerequisites

Before building the project, ensure you have the following installed:

-   **Rust**
    Install the latest stable version from the [official Rust website](https://www.rust-lang.org/tools/install).

-   **Platform-Specific Dependencies**
    Depending on your target platform(s), additional dependencies may be required. Refer to the platform-specific instructions below.

## Building the Project

Follow these steps to build Blocktopograph:

1. **Clone the Repository**

    Open your terminal and run:

    ```bash
    git clone https://github.com/NguyenDuck/blocktopograph.git
    cd blocktopograph
    ```

2. **Build for Desktop Platforms (Windows, macOS, Linux)**

    Ensure you have the necessary build tools for your operating system. Then, run:

    ```bash
    cargo build --release
    ```

    The compiled binary will be located in `target/release/`.

3. **Build for Mobile Platforms (iOS, Android)**

    - **Android**: Follow the [Rust on Android guide](https://rust-lang.github.io/rustup/cross-compilation.html#android) to set up your environment and build the project.

    - **iOS**: Refer to the [Rust on iOS guide](https://rust-lang.github.io/rustup/cross-compilation.html#ios) for instructions on building for iOS devices.

## Contributing

We welcome contributions from the community. To contribute:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes with clear and descriptive messages.
4. Submit a pull request for review.

Please ensure that your code adheres to the project's coding standards and includes appropriate tests.

## Stargazers over time

[![Stargazers over time](https://starchart.cc/NguyenDuck/blocktopograph.svg?variant=adaptive)](https://starchart.cc/NguyenDuck/blocktopograph)

## License

This project is licensed under two different licenses for source code and non-code assets:

-   **Source Code**: Licensed under the GNU General Public License v3.0 (GPL-3.0). You are free to modify and distribute the source code under the terms of the GPL-3.0. See the full license text at:

    [https://www.gnu.org/licenses/gpl-3.0.html](https://www.gnu.org/licenses/gpl-3.0.html)

-   **Non-Code Assets (images, sounds, models, etc.)**: Licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA 4.0). You are allowed to:

    -   **Share**: Copy and redistribute the material in any medium or format.
    -   **Adapt**: Remix, transform, and build upon the material.

    However, you **must**:

    -   Give appropriate credit to NguyenDuck.
    -   Provide a link to the license.
    -   Indicate if changes were made.
    -   **Not use the assets for commercial purposes.**
    -   Distribute any derivative works under the same license.

-   **Third-Party Assets**: Some assets used in this project may not be owned by NguyenDuck and could have their own licenses. It is your responsibility to verify and comply with the original licenses of these assets.

-   **Disclaimer**: This software and its associated assets are provided "as is" without any warranty, express or implied.

---

```
© 2025 NguyenDuck. All rights reserved.
```
