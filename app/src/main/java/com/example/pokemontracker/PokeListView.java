package com.example.pokemontracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PokeListView extends AppCompatActivity {

    ListView listView;
    SimpleCursorAdapter adapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_poke_list_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.pokeListView);

        cursor = getContentResolver().query(PokeContentProvider.CONTENT_URI,
                null, null, null, null);
        String[] from = {
                PokeContentProvider.COL_NATNUM,
                PokeContentProvider.COL_NAME,
                PokeContentProvider.COL_SPECIES,
                PokeContentProvider.COL_GENDER,
                PokeContentProvider.COL_WEIGHT,
                PokeContentProvider.COL_HEIGHT,
                PokeContentProvider.COL_LEVEL,
                PokeContentProvider.COL_HP,
                PokeContentProvider.COL_ATTACK,
                PokeContentProvider.COL_DEFENSE
        };

        int[] to = {
                R.id.valueNatNum,
                R.id.valueName,
                R.id.valueSpecies,
                R.id.valueGender,
                R.id.valueWeight,
                R.id.valueHeight,
                R.id.valueLevel,
                R.id.valueHP,
                R.id.valueAttack,
                R.id.valueDefense
        };

        adapter = new SimpleCursorAdapter(this, R.layout.row_poke, cursor, from, to, 0);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longDelete(id);
                return true;
            }
        });

        backButton();

    }

    private void longDelete(long id){
        new AlertDialog.Builder(this).setTitle("Delete Pokemon")
                .setMessage("Are you sure you want to delete this Pokemon?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int rowsDeleted = getContentResolver().delete(
                                PokeContentProvider.CONTENT_URI, "_ID = ?",
                                new String[] {String.valueOf(id)}
                        );
                        if(rowsDeleted > 0){
                            Toast.makeText(PokeListView.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                            refreshList();
                        }else{
                            Toast.makeText(PokeListView.this, "Deletion failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    private void refreshList(){
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }

        cursor = getContentResolver().query(PokeContentProvider.CONTENT_URI, null,
                null, null, null);
        if (cursor != null){
            adapter.changeCursor(cursor);
            adapter.notifyDataSetChanged();
        }


    }

    private void backButton(){
        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PokeListView.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }


       // adapter = new SimpleCursorAdapter(this, )
}


