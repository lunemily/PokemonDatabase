package com.evanfuhr.pokemondatabase.activities.display;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.display.PokemonShowdownRawFragment;

public class PokemonShowdownActivity extends AppCompatActivity {

    public static final String placeHolderRawTeams = "=== [gen7] Ditto Team ===\n" +
            "\n" +
            "Ditto @ Destiny Knot  \n" +
            "Ability: Limber  \n" +
            "Shiny: Yes  \n" +
            "EVs: 252 SpA / 4 SpD / 252 Spe  \n" +
            "Timid Nature  \n" +
            "IVs: 0 Atk  \n" +
            "- Transform  \n" +
            "\n" +
            "Ditto2 @ Destiny Knot  \n" +
            "Ability: Imposter  \n" +
            "Shiny: No  \n" +
            "EVs: 252 Atk / 4 SpD / 252 Spe  \n" +
            "Adamant Nature  \n" +
            "IVs: 0 Atk  \n" +
            "- Transform  \n" +
            "\n" +
            "\n" +
            "=== [gen7] Poygon Team ===\n" +
            "\n" +
            "Porygon @ Normalium Z  \n" +
            "Ability: Download  \n" +
            "EVs: 248 HP / 8 SpA / 252 SpD  \n" +
            "Calm Nature  \n" +
            "IVs: 0 Atk  \n" +
            "- Conversion  \n" +
            "- Shadow Ball  \n" +
            "- Tri Attack  \n" +
            "- Protect  \n" +
            "\n" +
            "\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_showdown);

        setTitle("Showdown Helper");

        setDefaultFragment(new PokemonShowdownRawFragment());

    }

    private void showRawTeams() {

    }

    // This method is used to set the default fragment that will be shown.
    private void setDefaultFragment(Fragment defaultFragment)
    {
        this.replaceFragment(defaultFragment);
    }

    public void replaceFragment(Fragment destFragment) {
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.dynamic_fragment_frame_layout, destFragment);

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }


}
