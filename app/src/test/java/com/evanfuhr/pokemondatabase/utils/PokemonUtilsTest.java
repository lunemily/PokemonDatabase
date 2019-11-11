package com.evanfuhr.pokemondatabase.utils;

import org.junit.Test;

import java.util.List;

public class PokemonUtilsTest {

    @Test
    public void getAbilityProseLinks() {
        String prose = "The [weather]{mechanic:weather} changes to [strong sunlight]{mechanic:strong-sunlight}" +
                " when this Pokémon enters battle and does not end unless cancelled by another weather condition." +
                "\n\nIf multiple Pokémon with this ability, []{ability:drizzle}, []{ability:sand-stream}," +
                " or []{ability:snow-warning} are sent out at the same time, the abilities will activate" +
                " in order of [Speed]{mechanic:speed}, respecting []{move:trick-room}.  Each ability's" +
                " weather will cancel the previous weather, and only the weather summoned by the slowest of the Pokémon will stay.";

        List<String> proseLinks = PokemonUtils.getProseLinks(prose);
        for (String link : proseLinks) {
            System.out.println(link);
        }
    }
}
