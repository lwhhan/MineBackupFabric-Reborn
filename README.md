![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-green?logo=minecraft)
![Fabric](https://img.shields.io/badge/Loader-Fabric-blue?logo=minecraft)
![Version](https://img.shields.io/badge/version-3.4.1-informational)
![License](https://img.shields.io/badge/license-MIT-blue.svg)
![AI Assisted](https://img.shields.io/badge/Built%20with-AI%20assistance-8A2BE2)

---

### 🤖 AI Disclosure

**This project is AI-generated.**

The Fabric 1.20.1 port — including the source-code changes, the build configuration, and this README — was produced with the assistance of a large language model (AI). A human maintainer reviewed and adapted the output and is responsible for publishing it.

This is an **unofficial port**. It is **not affiliated with, endorsed by, or supported by** the original author, Leafuke. Please do **not** report issues caused by this port to the upstream project.

---

### ⚠️ Important: This is a Companion Mod!

This mod is a **companion component** for the **MineBackup desktop application** or **FolderRewind** and **cannot function on its own**. You must download and run the main application first, otherwise every command will fail.

For inter-process communication, the KnotLink service must also be running on your computer:
https://github.com/KnotLink-Protocol/KnotLinkService/releases

### **➡️ [Download the REQUIRED MineBackup Desktop Application Here](https://github.com/Leafuke/MineBackup/releases)**

On Windows we recommend [FolderRewind](https://apps.microsoft.com/detail/9nwsdgxdqws4) together with the [MineRewind](https://github.com/Leafuke/FolderRewind-Plugin-Minecraft/releases) plugin rather than the standalone desktop app.

---

## What is This?

This is a **Fabric 1.20.1** port of [MineBackup-Mod](https://github.com/Leafuke/MineBackup-Mod), ported from the official `Forge/Forge-1.20` **v3.4.1** source.

Upstream Fabric builds start at Minecraft **1.21**. Players on **Fabric 1.20.1** had no official build available, and this port fills that gap.

The core is unchanged: the backup/restore flow, the KnotLink 2.0 protocol, and the `api.v2` surface are **byte-for-byte identical** to the official Forge 1.20 source. Only the platform layer was adapted:

- mod entrypoint (`ModInitializer` instead of `@Mod`)
- event registration (`ServerLifecycleEvents` / `ServerTickEvents` / `ServerPlayConnectionEvents`)
- config directory lookup (`FabricLoader` instead of `FMLPaths`)
- mod metadata (`fabric.mod.json` instead of `mods.toml`)

## ✨ This Mod Provides

* **Rich In-Game Commands** — manage world saves from chat with the `/mb` command series: backup, restore, browse history, and schedule automation.
* **Interactive Backup Browser** — `/mb list backups current` pages through your current world's backups with timestamps and comments, plus a clickable **[Restore]** button per entry.
* **Restore Safety Countdown** — before a restore runs, a configurable countdown starts. `/mb confirm` skips it, `/mb stop` cancels.
* **Configurable Automation Welcome** — show automation status on join always, only when a plan is enabled, or never. Management buttons appear only for players with automation permission.
* **Flexible Automation Modes** — automatic backups, or friendly reminders that let you pick a safe moment.
* **Real-Time Backup Notifications** — the desktop app reports backup start, success, and failure directly into game chat.
* **Seamless Live Backup Support** — when the desktop app requests a live backup, the mod performs a safe full world save (equivalent to `/save-all`) so the snapshot always captures the latest progress.
* **Dedicated Server Restore** — hot restore works on dedicated servers through a bundled restart sidecar.

## 🖥️ Supported Versions

| Loader | Minecraft | Mappings | Status |
| :--- | :--- | :--- | :--- |
| **Fabric** | **1.20.1** | Mojang (official) | **This port** |

## 🚀 Installation Guide

1. **Download the main application.** Make sure `MineBackup` or `FolderRewind` runs correctly on your computer.
2. **Download the MineRewind plugin** if you use FolderRewind. Create a `Minecraft Saves` type configuration instead of `Default`. Available from [GitHub Releases](https://github.com/Leafuke/FolderRewind-Plugin-Minecraft/releases).
3. **Install and run the KnotLink service.** Required for inter-process communication. Download from [GitHub Releases](https://github.com/KnotLink-Protocol/KnotLinkService/releases).
4. **Enable KnotLink in the main application.** It is on by default in MineBackup and off by default in FolderRewind — enable it manually if you use FolderRewind.
5. **Build or download this mod.** Place the resulting `minebackupFabric-3.4.1.jar` into your `mods` folder.
6. **Run both at once.** The desktop application **must be running in the background** while you play.

### Requirements

| Component | Version |
| :--- | :--- |
| Minecraft | 1.20.1 |
| Fabric Loader | 0.15.11 or newer |
| Fabric API | required |
| Java | 17 or newer |
| MineBackup desktop / FolderRewind | running in background |
| KnotLink service | running on `127.0.0.1:6372` / `6376` |

## 📖 Command Reference

On a **dedicated server**, commands require permission level 2 (OP). In **singleplayer or LAN**, only the world owner can use them.

| Command | Parameters | Description |
| :--- | :--- | :--- |
| **/mb help** | `[command]` | Shows general help, or detailed help for one command. |
| **/mb save** | — | Performs a full world save in-game, equivalent to `/save-all`. |
| **/mb backup** | `[comment]` | Backs up the current world, with an optional comment. |
| **/mb restore** | `[file]` | Restores the current world. Omit the file name to restore the latest backup. |
| **/mb confirm** | — | Immediately submits a restore that is counting down. |
| **/mb stop** | — | Cancels a restore that is counting down. |
| **/mb target backup** | `<config_id> <folder> [comment]` | Backs up a specific, non-current folder managed by the desktop app. |
| **/mb target restore** | `<config_id> <folder> <file>` | Restores a specific, non-current folder. **Not available on dedicated servers** — use `/mb restore` there. |
| **/mb list configs** | — | Lists configured backup profiles and their IDs. |
| **/mb list folders** | `<config_id>` | Lists folders tracked under a configuration profile. |
| **/mb list backups current** | `[page]` | Interactively browses the current world's backups with clickable restore buttons. |
| **/mb list backups** | `<config_id> <folder>` | Lists backup files for a specific, non-current folder. |
| **/mb auto start** | `<minutes> [backup\|remind]` | Stores a world-bound automatic backup or reminder plan. Mode defaults to `backup`. |
| **/mb auto status** | — | Shows the current world's mode, interval, and next trigger time. |
| **/mb auto stop** | — | Stops the current world's automation plan. |

### 💡 Example

1. Type `/mb backup Before the Ender Dragon fight`
   > `[MineBackup] Backup task started...`
   > `[MineBackup] Backup successful!`
2. Type `/mb list backups current` to browse recent backups, or `/mb restore` to restore the latest one.
3. If a countdown is configured, use `/mb confirm` to skip it or `/mb stop` to cancel.

## ⚙️ Configuration

Settings live in `config/minebackup.properties` (created on first run).

| Key | Default | Description |
| :--- | :--- | :--- |
| `automation.joinStatusNotification.mode` | `ALWAYS` | Join-status message: `ALWAYS`, `ENABLED_ONLY`, or `NEVER`. Takes effect after reopening the world or restarting the server. |
| `restore.countdownSeconds` | `10` | Safety countdown before a restore runs. |
| `auto.currentWorld.intervalMinutes` | — | Interval of the current world's automation plan. |
| `lan.hostReopen.enabled` | `true` | Reopen the LAN world after a hot restore. |
| `lan.clientReconnect.enabled` | `true` | Automatically rejoin the world after a hot restore. |
| `dedicatedRestore.mode` | `SIDECAR` | Dedicated server restore mode. Set to `DISABLED` to turn it off. |
| `dedicatedRestore.restartScript` | empty | Explicit path to the server start script. |
| `updateCheck.enabled` | `true` | Check for mod updates on startup. |

## 🛠️ Dedicated Server Restore

Backups and browsing behave identically on a dedicated server. Restoring requires stopping and relaunching the Minecraft process, which MineBackup automates with a bundled **restart sidecar**:

1. On `/mb restore`, the server saves all worlds, hands the process to the sidecar, and shuts down safely.
2. The sidecar waits for the JVM to exit and world files to be released, then lets the desktop application perform the restore.
3. Once the desktop app reports success, failure, or cancellation, the sidecar launches your start script exactly once and exits.

**A start script is required** in the server working directory: one of `start.bat`, `start.cmd`, `run.bat`, `run.cmd` (Windows) or `start.sh`, `run.sh` (Linux/macOS), or set `dedicatedRestore.restartScript` explicitly. Players are disconnected during the restart and must reconnect manually.

If your panel or wrapper auto-relaunches the server the moment it exits, disable that behaviour — an immediate restart can race the restore.

## 🧩 Compatibility

**Just Enough Accidents (JEA)** depends on the `api.v2` surface exposed by this mod. With this port installed, JEA can request on-the-spot backups after accident detection.

The mod `id` remains `minebackup`, so mods and configs that reference it keep working.

## 🔧 Building from Source

Requires **JDK 17**.

```bash
./gradlew build
```

The mod jar is written to `build/libs/minebackupFabric-3.4.1.jar` (the file without a classifier). Place that one in `mods`.

If another mod links against `api.v2` at compile time, use the `-dev` jar instead.

## ❓ FAQ

**Q: A command fails with "backend offline" or "no response".**
A: The desktop application is not running, or the KnotLink service is not reachable. Start both and try again.

**Q: Backups are not triggered even though the desktop app is running.**
A: Check the mod version. The desktop application validates the mod against a minimum version during the KnotLink handshake; a version below the required one causes the handshake to be rejected, and every subsequent `pre_hot_backup` signal is dropped with `Rejected pre_hot_backup without a matching, fresh handshake`. This build reports **3.4.1**.

**Q: Can this mod be used by itself?**
A: No. It is a bridge and does nothing without the main application.

**Q: Where do I report bugs in this port?**
A: To the maintainer of this port, not to the upstream author.

## 📄 Credits, License & AI Disclosure

| | |
| :--- | :--- |
| Original mod | [Leafuke](https://github.com/Leafuke) — MineBackup-Mod, MIT |
| Fabric 1.20.1 port | lwhhan |
| AI assistance | Port, configuration, and this README were generated with AI assistance and reviewed by the maintainer |

Licensed under the [MIT License](LICENSE). The original copyright notice is retained; the port additions are covered by the second copyright line.

**Disclaimer:** This is an unofficial community port. Use at your own risk, and back up important worlds independently before relying on any automated backup tool.
