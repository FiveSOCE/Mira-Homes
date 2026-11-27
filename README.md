# MiraHomes

## Version-specific downloads

| Paper target | Java | Final JAR | Download |
| --- | --- | --- | --- |
| **1.18.2** | 17 | `MiraHomes_1.18.2.jar` | [Legacy release](https://github.com/FiveSOCE/Mira-Homes/releases/tag/dev-1.18.2) |
| **1.21.11–26.2** | 21 baseline; modern server may require Java 25 | `MiraHomes_1.21.11-26.2.jar` | [Modern release](https://github.com/FiveSOCE/Mira-Homes/releases/tag/compat-modern-1.21.11-26.2) |

The named release assets become available after CI succeeds. Full gameplay testing and intermediate Paper 1.19–1.21.10 compatibility are not yet certified.

[Legacy source](https://github.com/FiveSOCE/Mira-Homes/tree/compat/1.18.2) · [Modern source](https://github.com/FiveSOCE/Mira-Homes) · [All releases](https://github.com/FiveSOCE/Mira-Homes/releases)

MiraHomes provides a paginated home-selection GUI for existing EssentialsX home locations. It delegates all underlying teleport logic, warmup, permissions, cooldown and storage to EssentialsX.

## Dependencies

- **Required:** Appropriate Paper and Java runtime and a compatible **EssentialsX** installation. The hard plugin dependency is `Essentials`.
- **Optional:** MiraCosmetics for teleport effects when a teleport actually succeeds.
- **Not required:** MiraCore or Vault.
- Back up EssentialsX home data before version changes and remove duplicate MiraHomes JARs.

## Commands and permissions

| Command | Permission | Function |
| --- | --- | --- |
| `/mhomes`, `/mhome` | `mirahomes.use` (everyone) | Open home GUI |
| `/home`, `/homes` | EssentialsX permissions | Open GUI when invoked without a home name |
| `/home <name>` | EssentialsX permissions | Direct EssentialsX home teleport |

MiraHomes declares `mirahomes.use`, default everyone. Named-home permissions are controlled by EssentialsX.

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
