package com.example.pokemontracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText nationalNumEdit;
    EditText nameEdit;
    EditText speciesEdit;
    EditText heightEdit;
    EditText weightEdit;
    EditText hpEdit;
    EditText attackEdit;
    EditText defenseEdit;
    RadioGroup genderRadioGroup;
    Spinner levelSpinner;
    Button saveButtonEdit;
    Button resetButtonEdit;
    TextView nationalNumText;
    TextView nameText;
    TextView speciesText;
    TextView genderText;
    TextView heightText;
    TextView weightText;
    TextView levelText;
    TextView hpText;
    TextView attackText;
    TextView defenseText;
    TextView warningText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainConstraint), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewListen();
        rangeLevelSpinner();
        resetValues();
        twoDecimalPlaces();
        viewButton();
    }

    private void resetTextColors() {
        nationalNumText.setTextColor(getColor(android.R.color.black));
        nameText.setTextColor(getColor(android.R.color.black));
        speciesText.setTextColor(getColor(android.R.color.black));
        genderText.setTextColor(getColor(android.R.color.black));
        heightText.setTextColor(getColor(android.R.color.black));
        weightText.setTextColor(getColor(android.R.color.black));
        hpText.setTextColor(getColor(android.R.color.black));
        attackText.setTextColor(getColor(android.R.color.black));
        defenseText.setTextColor(getColor(android.R.color.black));
    }

    private void findViewListen (){
        nationalNumEdit = findViewById(R.id.nationalNumET);
        nameEdit = findViewById(R.id.nameET);
        speciesEdit= findViewById(R.id.speciesET);
        heightEdit = findViewById(R.id.heightET);
        weightEdit = findViewById(R.id.weightET);
        hpEdit = findViewById(R.id.hpET);
        attackEdit = findViewById(R.id.attackET);
        defenseEdit = findViewById(R.id.defenseET);
        genderRadioGroup = findViewById(R.id.genderGroup);
        levelSpinner = findViewById(R.id.levelSpinnerID);
        saveButtonEdit = findViewById(R.id.saveButton);
        resetButtonEdit = findViewById(R.id.resetButton);
        nationalNumText = findViewById(R.id.nationalNumTV);
        nameText = findViewById(R.id.nameTV);
        speciesText = findViewById(R.id.speciesTV);
        genderText = findViewById(R.id.genderTV);
        heightText = findViewById(R.id.heightTV);
        weightText = findViewById(R.id.weightTV);
        levelText = findViewById(R.id.levelTV);
        hpText = findViewById(R.id.hpTV);
        attackText = findViewById(R.id.attackTV);
        defenseText = findViewById(R.id.defenseTV);
        warningText = findViewById(R.id.warningTV);


        saveButtonEdit.setOnClickListener(saveButtonListener);
        resetButtonEdit.setOnClickListener(resetButtonListener);
    }

    View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkInputs();
        }
    };

    View.OnClickListener resetButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resetValues();
        }
    };

    private void twoDecimalPlaces(){

        String ogHeight = heightEdit.getText().toString();
        if(!ogHeight.isEmpty()){
            double heightValue = Double.parseDouble(ogHeight);
            String cutHeight = String.format(Locale.getDefault(),"%.02f",heightValue);
            heightEdit.setText(cutHeight);
        }

        String ogWeight = weightEdit.getText().toString();
        if(!ogWeight.isEmpty()){
            double weightValue = Double.parseDouble(ogWeight);
            String cutWeight= String.format(Locale.getDefault(),"%.02f",weightValue);
            weightEdit.setText(cutWeight);
        }

    }

    private void rangeLevelSpinner(){ //sets up spinner
        List<String> levels = new ArrayList<>();
        for(int i = 1; i <= 50; i++){
            levels.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, levels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapter);
    }


    private void resetValues(){
        //put all default values
        nationalNumEdit.setText("896");
        nameEdit.setText("Glastrier");
        speciesEdit.setText("Wild Horse Pokemon");
        genderRadioGroup.clearCheck();
        heightEdit.setText("2.2");
        weightEdit.setText("800.0");
        levelSpinner.setSelection(0);
        hpEdit.setText("0");
        attackEdit.setText("0");
        defenseEdit.setText("0");
        warningText.setVisibility(View.GONE);
        resetTextColors();
    }

    private void checkInputs() {
        int errorCount = 0;

        int nationalNumInt = 0;
        String nameString = "";
        String speciesString = "";
        String genderString = "";
        double heightDouble = 0;
        double weightDouble = 0;
        int hpInt = 0;
        int attackInt = 0;
        int defenseInt =0;

        int levelInt = 0;
        try {
            String levelString = levelSpinner.getSelectedItem().toString();
            levelInt = Integer.parseInt(levelString);
        } catch (Exception e) {
            levelInt = 1;
        }


        String nationalNumString = nationalNumEdit.getText().toString().trim();
        if (nationalNumString.isEmpty()) {
            nationalNumText.setTextColor(getColor(R.color.red));
            emptyField().show();
            errorCount++;
        } else {
            nationalNumInt = Integer.parseInt(nationalNumString);
            if (nationalNumInt <= 0 || nationalNumInt >= 1010) {
                nationalNumText.setTextColor(getColor(R.color.red));
                Toast.makeText(this, "National Number must be between 1 and 1010",
                        Toast.LENGTH_SHORT).show();
                errorCount++;
            } else {
                nationalNumText.setTextColor(getColor(R.color.black));
            }
        }


        nameString = nameEdit.getText().toString().trim();
        if (nameString.isEmpty()) {
            nameText.setTextColor(getColor(R.color.red));
            emptyField().show();
            errorCount++;
        } else {
            if (nameString.length() < 3 || nameString.length() > 12) {
                nameText.setTextColor(getColor(R.color.red));
                Toast.makeText(this, "Name must be between 3-12 letters",
                        Toast.LENGTH_SHORT).show();
                errorCount++;
            } else if (!nameString.matches("[a-zA-Z\\.\\s]+")) {
                nameText.setTextColor(getColor(R.color.red));
                Toast.makeText(this, "Name can only contain letters, dots, and spaces",
                        Toast.LENGTH_SHORT).show();
                errorCount++;
            } else {
                nameText.setTextColor(getColor(R.color.black));
            }
        }

        speciesString = speciesEdit.getText().toString().trim();
        if (speciesString.isEmpty()) {
            speciesText.setTextColor(getColor(R.color.red));
            emptyField().show();
            errorCount++;
        } else {
            if (!speciesString.matches("[a-zA-Z\\s]+")) {
                speciesText.setTextColor(getColor(R.color.red));
                Toast.makeText(this, "Species name must only contain letters and spaces",
                        Toast.LENGTH_SHORT).show();
                errorCount++;
            } else {
                speciesText.setTextColor(getColor(R.color.black));
            }
        }

        int chosenGenderId = genderRadioGroup.getCheckedRadioButtonId();
        if (chosenGenderId == -1) {
            genderText.setTextColor(getColor(R.color.red));
            emptyField().show();
            errorCount++;
        } else if (chosenGenderId == R.id.unknown) {
            genderText.setTextColor(getColor(R.color.red));
            Toast.makeText(this, "Gender must be Male or Female", Toast.LENGTH_SHORT).show();
            errorCount++;
        } else {
            genderText.setTextColor(getColor(R.color.black));

            if (chosenGenderId == R.id.male){
                genderString = "Male";
            }else if(chosenGenderId == R.id.female){
                genderString = "Female";
            }
        }

        String heightString = heightEdit.getText().toString().trim();
        if (heightString.isEmpty()) {
            heightText.setTextColor(getColor(R.color.red));
            emptyField().show();
            errorCount++;
        } else {
            heightDouble = Double.parseDouble(heightString);
            if (heightDouble < 0.2 || heightDouble > 169.99) {
                heightText.setTextColor(getColor(R.color.red));
                Toast.makeText(this, "Height must be between 0.2 and 169.99",
                        Toast.LENGTH_SHORT).show();
                errorCount++;
            } else {
                heightText.setTextColor(getColor(R.color.black));
            }
        }

        String weightString = weightEdit.getText().toString().trim();
        if (weightString.isEmpty()) {
            weightText.setTextColor(getColor(R.color.red));
            emptyField().show();
            errorCount++;
        } else {
            weightDouble = Double.parseDouble(weightString);
            if (weightDouble < 0.1 || weightDouble > 992.7) {
                weightText.setTextColor(getColor(R.color.red));
                Toast.makeText(this, "Weight must be between 0.1 and 992.7",
                        Toast.LENGTH_SHORT).show();
                errorCount++;
            } else {
                weightText.setTextColor(getColor(R.color.black));
            }
        }

        String hpString = hpEdit.getText().toString().trim();
        if (hpString.isEmpty()) {
            hpText.setTextColor(getColor(R.color.red));
            emptyField().show();
            errorCount++;
        } else {
            hpInt = Integer.parseInt(hpString);
            if (hpInt < 1 || hpInt > 362) {
                hpText.setTextColor(getColor(R.color.red));
                Toast.makeText(this, "HP must be between 1-362",
                        Toast.LENGTH_SHORT).show();
                errorCount++;
            } else {
                hpText.setTextColor(getColor(R.color.black));
            }
        }

        String attackString = attackEdit.getText().toString().trim();
        if (attackString.isEmpty()) {
            attackText.setTextColor(getColor(R.color.red));
            emptyField().show();
            errorCount++;
        } else {
            attackInt = Integer.parseInt(attackString);
            if (attackInt < 0 || attackInt > 526) {
                attackText.setTextColor(getColor(R.color.red));
                Toast.makeText(this, "Attack must be between 0-526",
                        Toast.LENGTH_SHORT).show();
                errorCount++;
            } else {
                attackText.setTextColor(getColor(R.color.black));
            }
        }

        String defenseString = defenseEdit.getText().toString().trim();
        if (defenseString.isEmpty()) {
            defenseText.setTextColor(getColor(R.color.red));
            emptyField().show();
            errorCount++;
        } else {
            defenseInt = Integer.parseInt(defenseString);
            if (defenseInt < 10 || defenseInt > 614) {
                defenseText.setTextColor(getColor(R.color.red));
                Toast.makeText(this, "Defense must be between 10 and 614",
                        Toast.LENGTH_SHORT).show();
                errorCount++;
            } else {
                defenseText.setTextColor(getColor(R.color.black));
            }
        }

        if(errorCount == 0){
            warningText.setVisibility(View.GONE);
            if(isDuplicate(nationalNumInt, nameString, speciesString, genderString, heightDouble,
                    weightDouble, levelInt, hpInt, attackInt, defenseInt)){
                warningText.setVisibility(View.VISIBLE);
            }else{
                updatePoke(nationalNumInt, nameString, speciesString, genderString, heightDouble,
                        weightDouble, levelInt, hpInt, attackInt, defenseInt);
            }

        }

    }

    private Toast emptyField(){
        return Toast.makeText(this, "Please complete all fields",
                Toast.LENGTH_SHORT);
    }

    private boolean isDuplicate(int nationalNum, String name, String species, String gender,
                                double height,double weight, int level, int hp, int attack, int defense){
        String selection =
                PokeContentProvider.COL_NATNUM + "=? AND " +
                        PokeContentProvider.COL_NAME + "=? AND " +
                        PokeContentProvider.COL_SPECIES + "=? AND " +
                        PokeContentProvider.COL_GENDER + "=? AND " +
                        PokeContentProvider.COL_HEIGHT + "=? AND " +
                        PokeContentProvider.COL_WEIGHT + "=? AND " +
                        PokeContentProvider.COL_LEVEL + "=? AND " +
                        PokeContentProvider.COL_HP + "=? AND " +
                        PokeContentProvider.COL_ATTACK + "=? AND " +
                        PokeContentProvider.COL_DEFENSE + "=?";

        String[] selectionArgs = {
                String.valueOf(nationalNum),
                name,
                species,
                gender,
                String.valueOf(height),
                String.valueOf(weight),
                String.valueOf(level),
                String.valueOf(hp),
                String.valueOf(attack),
                String.valueOf(defense)
        };

        Cursor c = getContentResolver().query(PokeContentProvider.CONTENT_URI, null,
                selection, selectionArgs, null);

        boolean isDuplicate = (c != null && c.getCount() >0);
        if(c != null){
            c.close();
        }

        return isDuplicate;
    }

    private void updatePoke(int nationalNum, String name, String species, String gender,
                          double height,double weight, int level, int hp, int attack, int defense){
        ContentValues values = new ContentValues();
        values.put(PokeContentProvider.COL_NATNUM, nationalNum);
        values.put(PokeContentProvider.COL_NAME, name);
        values.put(PokeContentProvider.COL_SPECIES, species);
        values.put(PokeContentProvider.COL_GENDER, gender);
        values.put(PokeContentProvider.COL_HEIGHT, height);
        values.put(PokeContentProvider.COL_WEIGHT, weight);
        values.put(PokeContentProvider.COL_LEVEL, level);
        values.put(PokeContentProvider.COL_HP, hp);
        values.put(PokeContentProvider.COL_ATTACK, attack);
        values.put(PokeContentProvider.COL_DEFENSE, defense);

        Uri newUri = getContentResolver().insert(PokeContentProvider.CONTENT_URI, values);

        if(newUri != null){
            Toast.makeText(this, "Information has been stored in database!", Toast.LENGTH_SHORT).show();
            resetValues();
        }else {
            Toast.makeText(this, "Could not save information", Toast.LENGTH_SHORT).show();
        }

    }

    private void viewButton(){
        Button viewListButton = findViewById(R.id.viewButton);
        viewListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PokeListView.class);
                startActivity(intent);
            }
        });



    }





}