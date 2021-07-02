package com.pocketgarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView listRecycler;
    ArrayList<Plant> plantList;
    static PlantListAdapter adapter;
    View noPlants;
    ImageButton back, addPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        readFile();
        //initialize views
        back = findViewById(R.id.backButtonList);
        //added button to add plant from list screen
        addPlant = findViewById(R.id.addPlantButton);
        noPlants = findViewById(R.id.noPlantsView);
        listRecycler = findViewById(R.id.plantListRecycler);
        listRecycler.setLayoutManager(new LinearLayoutManager(this));

        //Read the file to get the plant list
        readFile();

        back.setOnClickListener(this);
        addPlant.setOnClickListener(this);

        adapter = new PlantListAdapter(ListActivity.this, plantList, getFilesDir());
        listRecycler.setAdapter(adapter);
        if(plantList.isEmpty())
        {
            noPlants.setVisibility(View.VISIBLE);
        }
        else
        {
            noPlants.setVisibility(View.INVISIBLE);
        }

    }

   @Override
   public void onClick(View v)
   {
       if(v == back)
       {
           startActivity(new Intent(this.getApplicationContext(), MainActivity.class));
       }
       if(v == addPlant){
           Intent i = new Intent(getApplicationContext(), EditPlantInfo.class);
           i.putExtra("previous", "ListActivity");
           startActivity(i);
       }
   }
    //Read the file into the plant list
    public void readFile()
    {
        try {
            File internalStorageDir = getFilesDir();
            File plants = new File(internalStorageDir, "plants_file.csv");
            FileInputStream fis = new FileInputStream(plants);
            ObjectInputStream ois = new ObjectInputStream(fis);
            plantList = (ArrayList<Plant>) ois.readObject();
            ois.close();
        }
        catch(Exception e)
        {
            Toast.makeText(ListActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}
