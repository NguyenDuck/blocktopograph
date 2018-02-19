# Blocktopograph

By @protolambda.

**NOTE**: This is a *very* old legacy project! Code quality is awful, but I'm not going to rewrite the whole app for "fun".
 Feel free to fork it and improve it yourself,
  it is licensed under AGPL v3; reverse engineering the MCPE format is time consuming, share your updates.

## [Download on Google-Play](https://play.google.com/store/apps/details?id=com.mithrilmania.blocktopograph)

### Showcase website

Screenshots, download links, roadmap etc. can all be found on [blocktopograph.mithrilmania.com](http://blocktopograph.mithrilmania.com).


## Get-started

Steps to get started quickly:

### Get-started: pre-installation

This project requires you to download some android SDKs, tools, libraries and drivers.

- SDKs + Tools: please check the sdk version before cloning a repo, then install sdk+tools for this version with SDK-manager.
- Libraries: You need to install the google-services and google-repository libraries with SDK-manager.
- Drivers: download the appropriate drivers for your phone to use `adb`.
    The SDK-manager provides windows drivers for the Nexus phones.
- Some libraries are downloaded by gradle itself. You do not have to worry about these.
- Sub-modules are managed with git. (See installation)


### Get-started: installation

NOTE: You may want to fork one of the dependencies (or this project) if you want to contribute.

1. `mkdir block-project` or something like that. This will be the main-container
1. `cd` into the new folder
1. `git clone` (your fork of) this repository
1. `git clone` (your fork of) [android-leveldb](https://github.com/mithrilmania/android-leveldb)
1. `cd android-leveldb` and `git submodule update` to get the
    [leveldb-mcpe](https://github.com/mithrilmania/leveldb-mcpe) git-submodule, it is required for building this project.
1. `cd ..` (back to the main container)
1. `git clone` (your fork of) [TileView](https://github.com/mithrilmania/TileView)
1. Add `local.properties` files to these projects,
    with `sdk.dir` for your sdk home,
    and with `ndk.dir` specified for `android-leveldb`.
1. Open the cloned blocktopograph repo with your IDE (android-studio and intellij-idea are tested).
    The blocktopograph repo should be the `root-module`;
     `app`, `library`(android-leveldb) and `tileview`(TileView) will be recognized as sub-modules.
1. Build the project with gradle
1. Make the project, to get android-leveldb native libs.
1. Switch build-variants of the projects you want to debug and rebuild with gradle (or leave them as is)
1. Good to go! Try running a debug build (`app` submodule)! Start with small-changes to see if you encounter any problems.


### Release-Workflow

The official Google-Play version is managed by @mithrilmania. The Release-signing keys are not available.
You can build it as debug build or sign it with your own keys.

This Google-Play version will be updated after any significant and well-written feature additions and fixes.

Questions? You can reach [@protolambda on Twitter](https://twitter.com/protolambda)!

Issues and requests are welcome too, but please use the issue-tracker for this to keep things organized.


### Wiki

Reasonable wiki-suggestions are welcome; comments should be sufficient for most parts of this project.


## LICENSE

License: **AGPL v.3**

Direct consequences: all public distributed changes in the source-code
 are required to be disclosed, including their source-code.

*Full license can be found in the [**LICENSE**](LICENSE) file in the root folder of this repository.*

NOTE: Please retain the attribution to @protolambda, the original author
 and maintainer of the official app, and later significant contributors (See [CONTRIBUTORS.md](CONTRIBUTORS.md))
 out of respect for their work towards this software.

LICENSE-head:

    Blocktopograph -- Blocktopograph is a fan-made app for MCPE, it includes a top-down world viewer and a NBT editor.
    Copyright (C) 2016 @protolambda

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.



## Contributing

Always welcome! Fork the project, change what you want, and send back a pull request.
Good (and properly written) features will be merged into the official app by @mithrilmania.
