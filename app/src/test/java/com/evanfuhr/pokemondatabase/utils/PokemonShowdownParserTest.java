package com.evanfuhr.pokemondatabase.utils;

import com.evanfuhr.pokemondatabase.models.Team;
import com.evanfuhr.pokemondatabase.models.TeamPokemon;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PokemonShowdownParserTest {

    @Test
    public void splitTeams() {
        String rawTeams = "=== [gen7] Electric Hail ===\n" +
                "\n" +
                "Tapu Koko @ Electrium Z  \n" +
                "Ability: Electric Surge  \n" +
                "Shiny: Yes  \n" +
                "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                "Timid Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Thunderbolt  \n" +
                "- Dazzling Gleam  \n" +
                "- Volt Switch  \n" +
                "- Protect  \n" +
                "\n" +
                "Ikki (Raichu-Alola) (F) @ Safety Goggles  \n" +
                "Ability: Surge Surfer  \n" +
                "EVs: 4 Atk / 252 SpA / 252 Spe  \n" +
                "Hasty Nature  \n" +
                "- Fake Out  \n" +
                "- Thunderbolt  \n" +
                "- Psychic  \n" +
                "- Encore  \n" +
                "\n" +
                "Azula (Charizard-Mega-X) (F) @ Charizardite X  \n" +
                "Ability: Tough Claws  \n" +
                "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                "Jolly Nature  \n" +
                "- Flare Blitz  \n" +
                "- Dragon Claw  \n" +
                "- Thunder Punch  \n" +
                "- Shadow Claw  \n" +
                "\n" +
                "Katara (Ninetales-Alola) (F) @ Focus Sash  \n" +
                "Ability: Snow Warning  \n" +
                "Shiny: Yes  \n" +
                "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                "Timid Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Aurora Veil  \n" +
                "- Freeze-Dry  \n" +
                "- Moonblast  \n" +
                "- Protect  \n" +
                "\n" +
                "Amon (Milotic) (M) @ Adrenaline Orb  \n" +
                "Ability: Competitive  \n" +
                "Shiny: Yes  \n" +
                "EVs: 252 HP / 252 Def / 4 SpA  \n" +
                "Bold Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Hydro Pump  \n" +
                "- Hypnosis  \n" +
                "- Coil  \n" +
                "- Recover  \n" +
                "\n" +
                "Korra (Sandslash-Alola) (F) @ Life Orb  \n" +
                "Ability: Slush Rush  \n" +
                "EVs: 4 HP / 252 Atk / 252 Spe  \n" +
                "Adamant Nature  \n" +
                "- Icicle Crash  \n" +
                "- Iron Head  \n" +
                "- Earthquake  \n" +
                "- Protect  \n" +
                "\n" +
                "\n" +
                "=== [gen7] Psychic Rain ===\n" +
                "\n" +
                "Tapu Lele @ Psychium Z  \n" +
                "Ability: Psychic Surge  \n" +
                "EVs: 248 HP / 8 SpA / 252 SpD  \n" +
                "Calm Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Psyshock  \n" +
                "- Dazzling Gleam  \n" +
                "- Protect  \n" +
                "- Taunt  \n" +
                "\n" +
                "Iroh (Drampa) (M) @ Focus Sash  \n" +
                "Ability: Berserk  \n" +
                "EVs: 248 HP / 252 SpA / 8 SpD  \n" +
                "Modest Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Hurricane  \n" +
                "- Roost  \n" +
                "- Dragon Pulse  \n" +
                "- Extrasensory  \n" +
                "\n" +
                "Kingdra @ Life Orb  \n" +
                "Ability: Swift Swim  \n" +
                "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                "Modest Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Signal Beam  \n" +
                "- Ice Beam  \n" +
                "- Muddy Water  \n" +
                "- Protect  \n" +
                "\n" +
                "Politoed @ Eject Button  \n" +
                "Ability: Drizzle  \n" +
                "EVs: 248 HP / 252 Def / 8 SpA  \n" +
                "Bold Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Scald  \n" +
                "- Encore  \n" +
                "- Ice Beam  \n" +
                "- Protect  \n" +
                "\n" +
                "Blastoise-Mega @ Blastoisinite  \n" +
                "Ability: Mega Launcher  \n" +
                "EVs: 252 HP / 4 Atk / 252 SpA  \n" +
                "Quiet Nature  \n" +
                "- Fake Out  \n" +
                "- Dark Pulse  \n" +
                "- Aura Sphere  \n" +
                "- Water Pulse  \n" +
                "\n" +
                "Raichu @ Assault Vest  \n" +
                "Ability: Lightning Rod  \n" +
                "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                "Jolly Nature  \n" +
                "- Wild Charge  \n" +
                "- Nuzzle  \n" +
                "- Knock Off  \n" +
                "- Volt Switch  \n" +
                "\n" +
                "\n" +
                "=== [gen7] Doubles ===\n" +
                "\n" +
                "Charizard-Mega-X @ Charizardite X  \n" +
                "Ability: Tough Claws  \n" +
                "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                "Adamant Nature  \n" +
                "- Flare Blitz  \n" +
                "- Dragon Claw  \n" +
                "- Shadow Claw  \n" +
                "- Thunder Punch  \n" +
                "\n" +
                "Clefairy @ Eviolite  \n" +
                "Ability: Friend Guard  \n" +
                "EVs: 248 HP / 8 Def / 252 SpD  \n" +
                "Calm Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Protect  \n" +
                "- Follow Me  \n" +
                "- Heal Pulse  \n" +
                "- Helping Hand  \n" +
                "\n" +
                "Flygon (F) @ Dragonium Z  \n" +
                "Ability: Levitate  \n" +
                "Shiny: Yes  \n" +
                "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                "Jolly Nature  \n" +
                "- Earthquake  \n" +
                "- Superpower  \n" +
                "- Outrage  \n" +
                "- Protect  \n" +
                "\n" +
                "Ninetales-Alola @ Focus Sash  \n" +
                "Ability: Snow Warning  \n" +
                "Level: 50  \n" +
                "EVs: 4 HP / 252 SpA / 252 Spe  \n" +
                "Timid Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Aurora Veil  \n" +
                "- Moonblast  \n" +
                "- Freeze-Dry  \n" +
                "- Protect  \n" +
                "\n" +
                "Tyranitar @ Expert Belt  \n" +
                "Ability: Sand Stream  \n" +
                "Level: 50  \n" +
                "EVs: 252 HP / 252 Atk / 4 SpD  \n" +
                "Adamant Nature  \n" +
                "- Rock Slide  \n" +
                "- Crunch  \n" +
                "- Iron Head  \n" +
                "- Protect  \n" +
                "\n" +
                "Gardevoir @ Life Orb  \n" +
                "Ability: Telepathy  \n" +
                "Level: 50  \n" +
                "EVs: 252 HP / 252 SpA / 4 SpD  \n" +
                "Timid Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Psychic  \n" +
                "- Dazzling Gleam  \n" +
                "- Energy Ball  \n" +
                "- Taunt  \n" +
                "\n" +
                "\n" +
                "=== [gen7] Singles ===\n" +
                "\n" +
                "Espeon @ Life Orb  \n" +
                "Ability: Magic Bounce  \n" +
                "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                "Timid Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Dazzling Gleam  \n" +
                "- Psychic  \n" +
                "- Shadow Ball  \n" +
                "- Signal Beam  \n" +
                "\n" +
                "Charizard-Mega-X @ Charizardite X  \n" +
                "Ability: Tough Claws  \n" +
                "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                "Adamant Nature  \n" +
                "- Dragon Dance  \n" +
                "- Flare Blitz  \n" +
                "- Dragon Claw  \n" +
                "- Roost  \n" +
                "\n" +
                "Lycanroc-Dusk @ Focus Sash  \n" +
                "Ability: Tough Claws  \n" +
                "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                "Jolly Nature  \n" +
                "- Stealth Rock  \n" +
                "- Accelerock  \n" +
                "- Roar  \n" +
                "- Fire Fang  \n" +
                "\n" +
                "Umbreon @ Leftovers  \n" +
                "Ability: Synchronize  \n" +
                "Shiny: Yes  \n" +
                "EVs: 252 HP / 4 Atk / 252 SpD  \n" +
                "Sassy Nature  \n" +
                "- Mean Look  \n" +
                "- Payback  \n" +
                "- Toxic  \n" +
                "- Moonlight  \n" +
                "\n" +
                "Flygon @ Dragonium Z  \n" +
                "Ability: Levitate  \n" +
                "Shiny: Yes  \n" +
                "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                "Jolly Nature  \n" +
                "- U-turn  \n" +
                "- Earthquake  \n" +
                "- Outrage  \n" +
                "- Superpower  \n" +
                "\n" +
                "Milotic @ Sitrus Berry  \n" +
                "Ability: Marvel Scale  \n" +
                "EVs: 252 HP / 252 Def / 4 SpA  \n" +
                "Bold Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Aqua Ring  \n" +
                "- Scald  \n" +
                "- Ice Beam  \n" +
                "- Recover  \n" +
                "\n" +
                "\n" +
                "=== [gen7] Dick Move ===\n" +
                "\n" +
                "Milotic @ Focus Sash  \n" +
                "Ability: Marvel Scale  \n" +
                "EVs: 252 HP / 128 SpA / 128 Spe  \n" +
                "Modest Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Mirror Coat  \n" +
                "- Recover  \n" +
                "- Rest  \n" +
                "- Sleep Talk  \n" +
                "\n" +
                "Serperior @ Life Orb  \n" +
                "Ability: Contrary  \n" +
                "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                "Timid Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Leaf Storm  \n" +
                "- Giga Drain  \n" +
                "- Dragon Pulse  \n" +
                "- Protect  \n" +
                "\n" +
                "Rotom @ Choice Scarf  \n" +
                "Ability: Levitate  \n" +
                "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                "Timid Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Trick  \n" +
                "- Volt Switch  \n" +
                "- Shadow Ball  \n" +
                "- Will-O-Wisp  \n" +
                "\n" +
                "Snorlax @ Iapapa Berry  \n" +
                "Ability: Gluttony  \n" +
                "EVs: 252 HP / 252 Atk / 4 SpD  \n" +
                "Adamant Nature  \n" +
                "- Return  \n" +
                "- Recycle  \n" +
                "- Gunk Shot  \n" +
                "- Belly Drum  \n" +
                "\n" +
                "Aunt Wu (Castform) @ Assault Vest  \n" +
                "Ability: Forecast  \n" +
                "EVs: 248 HP / 252 SpA / 8 SpD  \n" +
                "Modest Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Solar Beam  \n" +
                "- Thunder  \n" +
                "- Weather Ball  \n" +
                "- Hurricane  \n" +
                "\n" +
                "Drampa @ Sitrus Berry  \n" +
                "Ability: Cloud Nine  \n" +
                "EVs: 248 HP / 8 SpA / 252 SpD  \n" +
                "Calm Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Dragon Pulse  \n" +
                "- Rest  \n" +
                "- Nature Power  \n" +
                "- Roost  \n" +
                "\n" +
                "\n";
        assertEquals(5, PokemonShowdownParser.splitTeams(rawTeams).length);
    }

    @Test
    public void parseTeams() {
        String[] splitTeams = {
                "[gen7] Electric Hail ===\n" +
                        "\n" +
                        "Tapu Koko @ Electrium Z  \n" +
                        "Ability: Electric Surge  \n" +
                        "Shiny: Yes  \n" +
                        "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                        "Timid Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Thunderbolt  \n" +
                        "- Dazzling Gleam  \n" +
                        "- Volt Switch  \n" +
                        "- Protect  \n" +
                        "\n" +
                        "Ikki (Raichu-Alola) (F) @ Safety Goggles  \n" +
                        "Ability: Surge Surfer  \n" +
                        "EVs: 4 Atk / 252 SpA / 252 Spe  \n" +
                        "Hasty Nature  \n" +
                        "- Fake Out  \n" +
                        "- Thunderbolt  \n" +
                        "- Psychic  \n" +
                        "- Encore  \n" +
                        "\n" +
                        "Azula (Charizard-Mega-X) (F) @ Charizardite X  \n" +
                        "Ability: Tough Claws  \n" +
                        "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                        "Jolly Nature  \n" +
                        "- Flare Blitz  \n" +
                        "- Dragon Claw  \n" +
                        "- Thunder Punch  \n" +
                        "- Shadow Claw  \n" +
                        "\n" +
                        "Katara (Ninetales-Alola) (F) @ Focus Sash  \n" +
                        "Ability: Snow Warning  \n" +
                        "Shiny: Yes  \n" +
                        "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                        "Timid Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Aurora Veil  \n" +
                        "- Freeze-Dry  \n" +
                        "- Moonblast  \n" +
                        "- Protect  \n" +
                        "\n" +
                        "Amon (Milotic) (M) @ Adrenaline Orb  \n" +
                        "Ability: Competitive  \n" +
                        "Shiny: Yes  \n" +
                        "EVs: 252 HP / 252 Def / 4 SpA  \n" +
                        "Bold Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Hydro Pump  \n" +
                        "- Hypnosis  \n" +
                        "- Coil  \n" +
                        "- Recover  \n" +
                        "\n" +
                        "Korra (Sandslash-Alola) (F) @ Life Orb  \n" +
                        "Ability: Slush Rush  \n" +
                        "EVs: 4 HP / 252 Atk / 252 Spe  \n" +
                        "Adamant Nature  \n" +
                        "- Icicle Crash  \n" +
                        "- Iron Head  \n" +
                        "- Earthquake  \n" +
                        "- Protect  \n",
                "[gen7] Psychic Rain ===\n" +
                        "\n" +
                        "Tapu Lele @ Psychium Z  \n" +
                        "Ability: Psychic Surge  \n" +
                        "EVs: 248 HP / 8 SpA / 252 SpD  \n" +
                        "Calm Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Psyshock  \n" +
                        "- Dazzling Gleam  \n" +
                        "- Protect  \n" +
                        "- Taunt  \n" +
                        "\n" +
                        "Iroh (Drampa) (M) @ Focus Sash  \n" +
                        "Ability: Berserk  \n" +
                        "EVs: 248 HP / 252 SpA / 8 SpD  \n" +
                        "Modest Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Hurricane  \n" +
                        "- Roost  \n" +
                        "- Dragon Pulse  \n" +
                        "- Extrasensory  \n" +
                        "\n" +
                        "Kingdra @ Life Orb  \n" +
                        "Ability: Swift Swim  \n" +
                        "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                        "Modest Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Signal Beam  \n" +
                        "- Ice Beam  \n" +
                        "- Muddy Water  \n" +
                        "- Protect  \n" +
                        "\n" +
                        "Politoed @ Eject Button  \n" +
                        "Ability: Drizzle  \n" +
                        "EVs: 248 HP / 252 Def / 8 SpA  \n" +
                        "Bold Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Scald  \n" +
                        "- Encore  \n" +
                        "- Ice Beam  \n" +
                        "- Protect  \n" +
                        "\n" +
                        "Blastoise-Mega @ Blastoisinite  \n" +
                        "Ability: Mega Launcher  \n" +
                        "EVs: 252 HP / 4 Atk / 252 SpA  \n" +
                        "Quiet Nature  \n" +
                        "- Fake Out  \n" +
                        "- Dark Pulse  \n" +
                        "- Aura Sphere  \n" +
                        "- Water Pulse  \n" +
                        "\n" +
                        "Raichu @ Assault Vest  \n" +
                        "Ability: Lightning Rod  \n" +
                        "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                        "Jolly Nature  \n" +
                        "- Wild Charge  \n" +
                        "- Nuzzle  \n" +
                        "- Knock Off  \n" +
                        "- Volt Switch  \n",
                "[gen7] Doubles ===\n" +
                        "\n" +
                        "Charizard-Mega-X @ Charizardite X  \n" +
                        "Ability: Tough Claws  \n" +
                        "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                        "Adamant Nature  \n" +
                        "- Flare Blitz  \n" +
                        "- Dragon Claw  \n" +
                        "- Shadow Claw  \n" +
                        "- Thunder Punch  \n" +
                        "\n" +
                        "Clefairy @ Eviolite  \n" +
                        "Ability: Friend Guard  \n" +
                        "EVs: 248 HP / 8 Def / 252 SpD  \n" +
                        "Calm Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Protect  \n" +
                        "- Follow Me  \n" +
                        "- Heal Pulse  \n" +
                        "- Helping Hand  \n" +
                        "\n" +
                        "Flygon (F) @ Dragonium Z  \n" +
                        "Ability: Levitate  \n" +
                        "Shiny: Yes  \n" +
                        "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                        "Jolly Nature  \n" +
                        "- Earthquake  \n" +
                        "- Superpower  \n" +
                        "- Outrage  \n" +
                        "- Protect  \n" +
                        "\n" +
                        "Ninetales-Alola @ Focus Sash  \n" +
                        "Ability: Snow Warning  \n" +
                        "Level: 50  \n" +
                        "EVs: 4 HP / 252 SpA / 252 Spe  \n" +
                        "Timid Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Aurora Veil  \n" +
                        "- Moonblast  \n" +
                        "- Freeze-Dry  \n" +
                        "- Protect  \n" +
                        "\n" +
                        "Tyranitar @ Expert Belt  \n" +
                        "Ability: Sand Stream  \n" +
                        "Level: 50  \n" +
                        "EVs: 252 HP / 252 Atk / 4 SpD  \n" +
                        "Adamant Nature  \n" +
                        "- Rock Slide  \n" +
                        "- Crunch  \n" +
                        "- Iron Head  \n" +
                        "- Protect  \n" +
                        "\n" +
                        "Gardevoir @ Life Orb  \n" +
                        "Ability: Telepathy  \n" +
                        "Level: 50  \n" +
                        "EVs: 252 HP / 252 SpA / 4 SpD  \n" +
                        "Timid Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Psychic  \n" +
                        "- Dazzling Gleam  \n" +
                        "- Energy Ball  \n" +
                        "- Taunt  \n"
        };
        List<Team> parsedTeams = PokemonShowdownParser.parseTeams(splitTeams);
        assertEquals(3, parsedTeams.size());
        assertEquals("[gen7] Electric Hail", parsedTeams.get(0).getName());
    }

    @Test
    public void splitPokemons() {
        String rawTeam = "Tapu Koko @ Electrium Z  \n" +
                "Ability: Electric Surge  \n" +
                "Shiny: Yes  \n" +
                "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                "Timid Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Thunderbolt  \n" +
                "- Dazzling Gleam  \n" +
                "- Volt Switch  \n" +
                "- Protect  \n" +
                "\n" +
                "Ikki (Raichu-Alola) (F) @ Safety Goggles  \n" +
                "Ability: Surge Surfer  \n" +
                "EVs: 4 Atk / 252 SpA / 252 Spe  \n" +
                "Hasty Nature  \n" +
                "- Fake Out  \n" +
                "- Thunderbolt  \n" +
                "- Psychic  \n" +
                "- Encore  \n" +
                "\n" +
                "Azula (Charizard-Mega-X) (F) @ Charizardite X  \n" +
                "Ability: Tough Claws  \n" +
                "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                "Jolly Nature  \n" +
                "- Flare Blitz  \n" +
                "- Dragon Claw  \n" +
                "- Thunder Punch  \n" +
                "- Shadow Claw  \n" +
                "\n" +
                "Katara (Ninetales-Alola) (F) @ Focus Sash  \n" +
                "Ability: Snow Warning  \n" +
                "Shiny: Yes  \n" +
                "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                "Timid Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Aurora Veil  \n" +
                "- Freeze-Dry  \n" +
                "- Moonblast  \n" +
                "- Protect  \n" +
                "\n" +
                "Amon (Milotic) (M) @ Adrenaline Orb  \n" +
                "Ability: Competitive  \n" +
                "Shiny: Yes  \n" +
                "EVs: 252 HP / 252 Def / 4 SpA  \n" +
                "Bold Nature  \n" +
                "IVs: 0 Atk  \n" +
                "- Hydro Pump  \n" +
                "- Hypnosis  \n" +
                "- Coil  \n" +
                "- Recover  \n" +
                "\n" +
                "Korra (Sandslash-Alola) (F) @ Life Orb  \n" +
                "Ability: Slush Rush  \n" +
                "EVs: 4 HP / 252 Atk / 252 Spe  \n" +
                "Adamant Nature  \n" +
                "- Icicle Crash  \n" +
                "- Iron Head  \n" +
                "- Earthquake  \n" +
                "- Protect  \n";
        String[] team = PokemonShowdownParser.splitPokemons(rawTeam);
        assertEquals(6, PokemonShowdownParser.splitPokemons(rawTeam).length);
    }

    @Test
    public void parsePokemons() {
        String[] splitPokemons = {
                "Tapu Koko @ Electrium Z  \n" +
                        "Ability: Electric Surge  \n" +
                        "Shiny: Yes  \n" +
                        "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                        "Timid Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Thunderbolt  \n" +
                        "- Dazzling Gleam  \n" +
                        "- Volt Switch  \n" +
                        "- Protect  ",
                "Ikki (Raichu-Alola) (F) @ Safety Goggles  \n" +
                        "Ability: Surge Surfer  \n" +
                        "Shiny: Yes  \n" +
                        "EVs: 4 Atk / 252 SpA / 252 Spe  \n" +
                        "Hasty Nature  \n" +
                        "- Fake Out  \n" +
                        "- Thunderbolt  \n" +
                        "- Psychic  \n" +
                        "- Encore  ",
                "Azula (Charizard-Mega-X) (F) @ Charizardite X  \n" +
                        "Ability: Tough Claws  \n" +
                        "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
                        "Jolly Nature  \n" +
                        "- Flare Blitz  \n" +
                        "- Dragon Claw  \n" +
                        "- Thunder Punch  \n" +
                        "- Shadow Claw  ",
                "Katara (Ninetales-Alola) (F) @ Focus Sash  \n" +
                        "Ability: Snow Warning  \n" +
                        "Shiny: Yes  \n" +
                        "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
                        "Timid Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Aurora Veil  \n" +
                        "- Freeze-Dry  \n" +
                        "- Moonblast  \n" +
                        "- Protect  ",
                "Amon (Milotic) (M) @ Adrenaline Orb  \n" +
                        "Ability: Competitive  \n" +
                        "Shiny: Yes  \n" +
                        "EVs: 252 HP / 252 Def / 4 SpA  \n" +
                        "Bold Nature  \n" +
                        "IVs: 0 Atk  \n" +
                        "- Hydro Pump  \n" +
                        "- Hypnosis  \n" +
                        "- Coil  \n" +
                        "- Recover  ",
                "Korra (Sandslash-Alola) (F) @ Life Orb  \n" +
                        "Ability: Slush Rush  \n" +
                        "EVs: 4 HP / 252 Atk / 252 Spe  \n" +
                        "Adamant Nature  \n" +
                        "- Icicle Crash  \n" +
                        "- Iron Head  \n" +
                        "- Earthquake  \n" +
                        "- Protect  \n"
        };
        List<TeamPokemon> parsedPokemons = PokemonShowdownParser.parsePokemons(splitPokemons);
        assertEquals(6, parsedPokemons.size());
        assertEquals("Tapu Koko", parsedPokemons.get(0).getName());
    }
}
