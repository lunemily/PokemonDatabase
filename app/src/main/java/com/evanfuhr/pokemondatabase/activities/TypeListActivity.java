package com.evanfuhr.pokemondatabase.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.evanfuhr.pokemondatabase.R;

import org.jetbrains.annotations.NonNls;

public class TypeListActivity extends AppCompatActivity {

    @NonNls
    public static final String TYPE_ID = "type_id";
    @NonNls
    public static final String TYPE = "Type";
    @NonNls
    public static final String MENU_ITEM_NOT_IMPLEMENTED_YET = "Menu item not implemented yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_list);

        setTitle(TYPE);
    }
}
