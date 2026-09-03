# MiraHomes

MiraHomes is the EssentialsX-backed player homes GUI for the Mira Paper server suite. It gives players a clean visual selector for their existing Essentials homes without creating or maintaining a second home database.

## Download

[**Download MiraHomes v0.1.0**](https://github.com/FiveSOCE/Mira-Homes/releases/download/v0.1.0/MiraHomes-0.1.0.jar)

## Requirements / Dependencies

- Paper 1.21.11
- Java 21
- EssentialsX 2.22.0 or newer

## How MiraHomes Works

EssentialsX remains the source of truth for home creation, names, ownership, teleport behaviour and permissions. MiraHomes reads the player's live Essentials home list and renders it as a compact GUI. Each home appears as a White Bed named after the Essentials home, unused slots use the Mira grey-glass presentation, and the inventory expands/paginates as the number of homes grows.

Clicking a home closes the GUI and dispatches Essentials' own `/home <name>` command, so Essentials teleport restrictions, delays and permissions remain intact. After selection, MiraHomes plays a configurable three-ring particle effect matching MiraWarps, with ring radius, density, height and duration controlled through `config.yml`.

The `/home` and `/homes` command bridge routes no-argument player access into the MiraHomes GUI while `/home <name>` remains the normal direct Essentials teleport flow.

## Commands

| Command | Permission | What it does |
| --- | --- | --- |
| `/mhomes` | `mirahomes.use` | Opens the MiraHomes GUI. |
| `/mhome` | `mirahomes.use` | Alias for `/mhomes`. |
| `/home` | Essentials access + MiraHomes routing | Opens the MiraHomes GUI when used without a home name. |
| `/homes` | Essentials access + MiraHomes routing | Opens the MiraHomes GUI. |
| `/home <name>` | Essentials permissions | Remains EssentialsX's direct named-home teleport command. |

## Permissions

| Permission | Default | What it does |
| --- | --- | --- |
| `mirahomes.use` | Everyone | Allows opening the MiraHomes GUI through the MiraHomes command surface. |

EssentialsX permissions continue to control creation and teleport access to the underlying homes.
