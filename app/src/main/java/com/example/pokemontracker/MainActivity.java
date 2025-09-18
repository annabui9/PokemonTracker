package com.example.pokemontracker;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        findViewListen();
        rangeLevelSpinner();
        resetValues();
        twoDecimalPlaces();
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
        hpEdit.setText("0");
        attackEdit.setText("0");
        defenseEdit.setText("0");
    }

    private void checkInputs() {
        int errorCount = 0;

        String nationalNumString = nationalNumEdit.getText().toString().trim();
        if (nationalNumString.isEmpty()) {
            nationalNumText.setTextColor(getColor(R.color.red));
            emptyField().show();
            errorCount++;
        } else {
            int nationalNumInt = Integer.parseInt(nationalNumString);
            if (nationalNumInt <= 0 || nationalNumInt >= 1010) {
                nationalNumText.setTextColor(getColor(R.color.red));
                Toast.makeText(this, "National Number must be between 1 and 1010",
                        Toast.LENGTH_SHORT).show();
                errorCount++;
            } else {
                nationalNumText.setTextColor(getColor(R.color.black));
            }
        }


        String nameString = nameEdit.getText().toString().trim();
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

        String speciesString = speciesEdit.getText().toString().trim();
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
        }

        String heightString = heightEdit.getText().toString().trim();
        if (heightString.isEmpty()) {
            heightText.setTextColor(getColor(R.color.red));
            emptyField().show();
            errorCount++;
        } else {
            Double heightDouble = Double.parseDouble(heightString);
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
            Double weightDouble = Double.parseDouble(weightString);
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
            Double hpDouble = Double.parseDouble(hpString);
            if (hpDouble < 1 || hpDouble > 362) {
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
            Double attackDouble = Double.parseDouble(attackString);
            if (attackDouble < 0 || attackDouble > 526) {
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
            Double defenseDouble = Double.parseDouble(defenseString);
            if (defenseDouble < 10 || defenseDouble > 614) {
                defenseText.setTextColor(getColor(R.color.red));
                Toast.makeText(this, "Defense must be between 10 and 614",
                        Toast.LENGTH_SHORT).show();
                errorCount++;
            } else {
                defenseText.setTextColor(getColor(R.color.black));
            }
        }

        if(errorCount == 0){
            Toast.makeText(this, "Information has been stored in database!", Toast.LENGTH_SHORT).show();

        }
    }

    private Toast emptyField(){
        return Toast.makeText(this, "Please complete all fields",
                Toast.LENGTH_SHORT);
    }


}