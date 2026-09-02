# Mira-Homes

MiraHomes is the EssentialsX-backed player homes GUI for the Mira plugin suite.

## Download

**Current release: v0.1.0**

[Download MiraHomes-0.1.0.jar](https://github.com/FiveSOCE/Mira-Homes/releases/download/v0.1.0/MiraHomes-0.1.0.jar)

[View all releases](https://github.com/FiveSOCE/Mira-Homes/releases)

## Requirements

- Paper 1.21.11
- Java 21
- EssentialsX 2.22.0+

## Player flow

- `/home` opens the MiraHomes GUI
- `/homes` opens the MiraHomes GUI
- `/mhomes` and `/mhome` also open the GUI
- `/home <name>` remains an Essentials direct-home command

EssentialsX remains the source of truth. MiraHomes reads the current player's live Essentials home list and never maintains a second home database.

## GUI

- Each home is shown as a White Bed named after the Essentials home
- Dead space and borders use blank grey stained glass panes with enchantment glint
- The GUI starts compact and expands as required
- Large home lists paginate safely
- Clicking a home dispatches Essentials' own `/home <name>` command, preserving Essentials teleport rules and permissions

## Particles

After selecting a home, MiraHomes plays the same configurable three-ring effect used by MiraWarps:

- Blue ring at head height
- Red ring around the body
- Green ring above the feet

Particle radius, density, size, heights and duration are configurable in `config.yml`.

Build output: `build/libs/MiraHomes-0.1.0.jar`
