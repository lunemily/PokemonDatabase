package com.evanfuhr.pokemondatabase.utils;

import com.evanfuhr.pokemondatabase.models.Ability;
import com.evanfuhr.pokemondatabase.models.EffortValue;
import com.evanfuhr.pokemondatabase.models.Forme;
import com.evanfuhr.pokemondatabase.models.Gender;
import com.evanfuhr.pokemondatabase.models.IndividualValues;
import com.evanfuhr.pokemondatabase.models.Item;
import com.evanfuhr.pokemondatabase.models.Move;
import com.evanfuhr.pokemondatabase.models.Nature;
import com.evanfuhr.pokemondatabase.models.Pokemon;
import com.evanfuhr.pokemondatabase.models.Team;
import com.evanfuhr.pokemondatabase.models.TeamPokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonShowdownParser {

    private String mRawData;
    private List<Team> mTeams = new ArrayList<>();

    public PokemonShowdownParser() {

    }

    public PokemonShowdownParser(String rawData) {
        this.mRawData = rawData;
    }

    public void parse() {
        String[] rawTeams = splitTeams(mRawData);
        List<Team> parsedTeams = parseTeams(rawTeams);
        for (Team team : parsedTeams) {
            String[] rawPokemons = splitPokemons(team.getRawTeam());
            List<TeamPokemon> teamPokemons = parsePokemons(rawPokemons);
        }
    }

    static String[] splitTeams(String rawData) {
        return rawData.substring(4).split("\n\n=== ");
    }

    static List<Team> parseTeams(String[] rawTeams) {
        List<Team> teams = new ArrayList<>();
        for (String rawTeam : rawTeams) {
            Team team = new Team();
            String[] namedTeam = rawTeam.split(" ===\n\n");
            team.setName(namedTeam[0]);
            team.setRawTeam(namedTeam[1]);
            teams.add(team);
        }
        return teams;
    }

    static String[] splitPokemons(String rawTeam) {
        return rawTeam.split("\n\n");
    }

    static List<TeamPokemon> parsePokemons(String[] rawPokemons) {
        List<TeamPokemon> pokemons = new ArrayList<>();
        for (String rawPokemon : rawPokemons) {
            TeamPokemon pokemon = parsePokemon(rawPokemon);
            pokemons.add(pokemon);
        }
        return pokemons;
    }

    static TeamPokemon parsePokemon(String rawPokemon) {
        TeamPokemon pokemon = new TeamPokemon();

        String[] lines = rawPokemon.split("\n");

        // Iterate over lines. TODO: Move to its own method
        for (String line : lines) {
            if (line.contains("Ability:")) {
                pokemon.setAbility(new Ability(line.replace("Ability:", "").trim()));
            } else if (line.contains("Shiny:")) {
                pokemon.setShiny(true);
            } else if (line.indexOf("-") == 0) {
                // All moves start with "-"
                List<Move> moves = pokemon.getMoves();
                moves.add(new Move(line.replace("-", "").trim()));
                pokemon.setMoves(moves);
            } else if (line.contains("EVs:")) {
                pokemon.setEVs(EffortValue.parseEVs(line.replace("EVs:", "").trim()));
            } else if (line.contains("IVs:")) {
                pokemon.setIVs(IndividualValues.parseIVs(line.replace("IVs:", "").trim()));
            } else if (line.indexOf("Nature") > 4) {
                // Avoids Nature Power move
                pokemon.setNature(new Nature(line.replace("Nature", "").trim()));
            } else if (lines[0].equals(line)) {
                // This is the nickname/name/gender/item line

                // Parsing item
                pokemon.setItem(new Item(line.split("@")[1].trim()));
                line = line.split("@")[0].trim();

                // Parsing gender
                if (line.contains("(F)")) {
                    pokemon.setGender(Gender.FEMALE);
                    line = line.replace("(F)", "").trim();
                } else if (line.contains("(M)")) {
                    pokemon.setGender(Gender.MALE);
                    line = line.replace("(M)", "").trim();
                }

                // Parsing nickname
                if (line.contains("(")) {
                    pokemon.setNickname(line.substring(0, line.indexOf("(")).trim());
                    line = line.substring(line.indexOf("(") + 1, line.indexOf(")")).trim();
                } else {
                    pokemon.setNickname(line);
                }

                // Parsing forme
                if (line.contains("-")) {
                    // Parse forme
                    pokemon.setForme(Forme.parseShowdownForme(line));
                    pokemon.setName(line.replace("-" + Forme.getShowndownForme(pokemon.getForme()), ""));
                } else {
                    pokemon.setName(line);
                }
            }
        }

        return pokemon;
    }

    public List<Team> getTeams() {
        return mTeams;
    }
}
