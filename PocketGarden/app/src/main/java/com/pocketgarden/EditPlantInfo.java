package com.pocketgarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EditPlantInfo extends AppCompatActivity implements View.OnClickListener {

    EditText name, scientificName, minTemp, maxTemp, wateringDays;
    ImageButton back, save, delete, indoor, outdoor;
    ArrayList<Plant> plantList;
    Plant p;
    int i;
    String previous;
    boolean in, out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plant_info);

        name = findViewById(R.id.nameTextField);
        scientificName = findViewById(R.id.scientificNameTextField);
        minTemp = findViewById(R.id.minTextField);
        maxTemp = findViewById(R.id.maxTextField);
        wateringDays = findViewById(R.id.waterDaysTextField);

        outdoor = findViewById(R.id.outdoorButton);
        indoor = findViewById(R.id.indoorButton);
        delete = findViewById(R.id.deleteButton);
        back = findViewById(R.id.backButton);
        save = findViewById(R.id.saveButton);

        //Disabled to maintain the image swap theme
        //outdoor.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        //indoor.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));

        readFile();
        i = -1;
        Bundle b = getIntent().getExtras();
        if(b.get("index") != null) {
            i = b.getInt("index");
            p = plantList.get(i);
            name.setText(p.plantName);
            if(p.exactPlantName.equals("Scientific Name"))
            {
                scientificName.setHint("Scientific Name");
            }
            else {
                scientificName.setText(p.exactPlantName);
            }
            minTemp.setText(Double.toString(p.tempMin));
            maxTemp.setText(Double.toString(p.tempMax));
            wateringDays.setText(Integer.toString(p.wateringDays));
            if(p.isInside())
            {
                //indoor.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
                //Swaps image to outline version when disabled
                //Swaps image to filled version when enabled
                indoor.setImageResource(R.drawable.ic_house_24px);
                outdoor.setImageResource(R.drawable.ic_wb_sunny_outlined_24px);
            }
            else if (p.isOutside())
            {
                //outdoor.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
                //Swaps image to outline version when disabled
                //Swaps image to filled version when enabled
                outdoor.setImageResource(R.drawable.ic_wb_sunny_24px);
                indoor.setImageResource(R.drawable.ic_house_outline_24px_);
            }

        }
        if(b.get("previous") != null)
        {
            previous = b.getString("previous");
        }

        back.setOnClickListener(this);
        save.setOnClickListener(this);
        outdoor.setOnClickListener(this);
        indoor.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v == indoor)
        {
            in = true;
            out = false;
            //outdoor.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
            //indoor.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
            //Swaps image to outline version when disabled
            //Swaps image to filled version when enabled
            outdoor.setImageResource(R.drawable.ic_wb_sunny_outlined_24px);
            indoor.setImageResource(R.drawable.ic_house_24px);
        }
        if(v == outdoor)
        {
            in = false;
            out = true;
            //indoor.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
            //outdoor.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
            //Swaps image to outline version when disabled
            //Swaps image to filled version when enabled
            outdoor.setImageResource(R.drawable.ic_wb_sunny_24px);
            indoor.setImageResource(R.drawable.ic_house_outline_24px_);
        }
        if(v == delete)
        {
            if(i != -1)
            {
                plantList.remove(i);
                writeFile();
            }
            goToNextActivity();
        }
        if(v == back)
        {
            finish();
        }
        if(v == save) {
            //create a new plant to add to the list
            String n = name.getText().toString().trim();
            String sn = scientificName.getText().toString().trim();
            int wd = 0;
            double min = 0.0, max = 0.0;

            //this okay variable is so that all of the errors display at once
            //and the plant is not created
            //if any of these don't meet the requirements, don't make the plant
            boolean okay = true;
            if(wateringDays.getText().toString().isEmpty()) {
                wateringDays.setError("Required");
                okay = false;
                            }
            if(wateringDays.getText().toString().equals("0")) {
                wateringDays.setError("Cannot be 0");
                okay = false;
            }
            if(minTemp.getText().toString().isEmpty()) {
                minTemp.setError("Required");
                okay = false;
            }
            if(maxTemp.getText().toString().isEmpty()) {
                maxTemp.setError("Required");
                okay = false;
                            }
            if(TextUtils.isEmpty(n)) {
                name.setError("Required");
                okay = false;
                            }
            if(in == false && out == false)
            {
                Toast.makeText(this.getApplicationContext(), "Must select either indoor or outdoor!", Toast.LENGTH_SHORT).show();
                okay = false;
            }

            if(!okay)
            {
                return;
            }
            else {
                max = Double.parseDouble(maxTemp.getText().toString());
                min = Double.parseDouble(minTemp.getText().toString());
                wd = Integer.parseInt(wateringDays.getText().toString());
                if(TextUtils.isEmpty(sn)) {
                    sn = "Scientific Name";
                }

                //replace the old plant if it existed
                if (i != -1) {
                    plantList.set(i, new Plant(n, sn, wd, min, max, in, out));
                    writeFile();
                }
                //add plant to the list and write to file if it did not exist
                else
                {
                    Plant newPlant = new Plant(n, sn, wd, min, max, in, out);
                    plantList.add(newPlant);
                    writeFile();
                }
            }
        }
    }

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
            Toast.makeText(EditPlantInfo.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void writeFile()
    {
        File internalStorageDir = getFilesDir();
        File plants = new File(internalStorageDir, "plants_file.csv");
        if(plants.exists())
        {
            //removes the previous file so it does not keep the old plant list in the file
            plants.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(plants);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(plantList);
            oos.close();
            goToNextActivity();
        } catch (Exception e) {
            Toast.makeText(EditPlantInfo.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void goToNextActivity()
    {
        if(previous.equals("ListActivity"))
        {
            startActivity(new Intent(this.getApplicationContext(), ListActivity.class));
            finish();
        }
        else if(previous.equals("MainActivity"))
        {
            startActivity(new Intent(this.getApplicationContext(), MainActivity.class));
            finish();
        }
        else
        {
            finish();
        }
    }
}
