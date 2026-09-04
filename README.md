# MiraHomes

MiraHomes is the EssentialsX-backed player homes GUI for the Mira Paper server suite. It gives players a clean visual selector for their existing Essentials homes without creating or maintaining a second home database.

## Download

[**Download MiraHomes v0.1.1**](https://github.com/FiveSOCE/Mira-Homes/releases/download/v0.1.1/MiraHomes-0.1.1.jar)

[View All Releases](https://github.com/FiveSOCE/Mira-Homes/releases)

## Requirements / Dependencies

- Paper 1.21.11
- Java 21
- EssentialsX 2.22.0 or newer
- MiraCosmetics 0.1.1+ optional/recommended for teleport visuals

## How MiraHomes Works

EssentialsX remains the source of truth for home creation, names, ownership, teleport behaviour, delays, cooldowns and permissions. MiraHomes reads the player's live Essentials home list and renders it as a compact paginated GUI.

Clicking a home closes the GUI and dispatches Essentials' own namespaced `/home <name>` command. MiraHomes does not pretend that command acceptance means teleport success.

v0.1.1 removes the old local three-ring particle renderer. When MiraCosmetics is installed, its global Paper `PlayerTeleportEvent` listener renders the player's configured TELEPORT cosmetic only after Essentials actually performs a teleport. This also means direct `/home <name>` teleports get exactly the same visual path as GUI-selected homes.

The `/home` and `/homes` command bridge routes no-argument player access into the MiraHomes GUI while `/home <name>` remains the normal direct Essentials flow.

## Commands

| Command | Permission | What it does |
| --- | --- | --- |
| `/mhomes` | `mirahomes.use` | Opens the MiraHomes GUI. |
| `/mhome` | `mirahomes.use` | Alias for `/mhomes`. |
| `/home` | Essentials access + MiraHomes routing | Opens the GUI when used without a home name. |
| `/homes` | Essentials access + MiraHomes routing | Opens the GUI. |
| `/home <name>` | Essentials permissions | Remains EssentialsX's named-home teleport command. |

## Visual Ownership

MiraHomes owns the menu only. EssentialsX owns the teleport. MiraCosmetics owns successful teleport visuals.

No teleport particles are spawned merely because a GUI command was accepted, preventing false-positive or duplicate effects when Essentials denies, delays or later completes a teleport.

## Building

```bash
gradle clean build
```

The output JAR is created in `build/libs/`.
