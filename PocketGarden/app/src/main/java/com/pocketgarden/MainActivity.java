package com.pocketgarden;

import android.app.ActionBar;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainActivity extends AppCompatActivity {

    //Moved the button original calling here
    //so that way they can be accessed throughout
    ImageButton sunday_button;
    ImageButton monday_button;
    ImageButton tuesday_button;
    ImageButton wednesday_button;
    ImageButton thursday_button;
    ImageButton friday_button;
    ImageButton saturday_button;

    ArrayList<Plant> userPlantList;
    FabSpeedDial dial;

    File internalStorageDir;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Testing some calendar things for notifications
        calendar = Calendar.getInstance();
        //Toast.makeText(MainActivity.this, "Day of the Week is " + calendar.get(Calendar.DAY_OF_YEAR), Toast.LENGTH_SHORT).show();

        //Initializes buttons
        //-KyleMMoore
        buttonInit();
    }
    /*
    buttonInit()
    for initializing ImageButtons
    and for creating listeners
    for onButtonPopupWindow()
     */
    private void buttonInit(){
        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        sunday_button = (ImageButton) findViewById(R.id.sunday_button);

        monday_button = (ImageButton) findViewById(R.id.monday_button);

        tuesday_button = (ImageButton) findViewById(R.id.tuesday_button);

        wednesday_button = (ImageButton) findViewById(R.id.wednesday_button);

        thursday_button = (ImageButton) findViewById(R.id.thursday_button);

        friday_button = (ImageButton) findViewById(R.id.friday_button);

        saturday_button = (ImageButton) findViewById(R.id.saturday_button);

        userPlantList = new ArrayList<>();

        dial = (FabSpeedDial) findViewById(R.id.speed_dial);

        dial.setMenuListener(new SimpleMenuListenerAdapter(){
            @Override
            public boolean onMenuItemSelected(MenuItem item) {
                //Toast.makeText(MainActivity.this, "Item clicked = " + item.toString(), Toast.LENGTH_SHORT).show();

                //Opens the plant activity with the given plant list to write the new plant into
                if(item.toString().equals("Add Plant")) {
                    Intent i = new Intent(getApplicationContext(), EditPlantInfo.class);
                    i.putExtra("list", userPlantList);
                    i.putExtra("previous", "MainActivity");
                    startActivity(i);
                }
                if(item.toString().equals("My Plants")) {
                    Intent i = new Intent(getApplicationContext(), ListActivity.class);
                    i.putExtra("list", userPlantList);
                    startActivity(i);
                }
                if(item.toString().equals("Weather")){
                    Intent i = new Intent(getApplicationContext(), WeatherActivity.class);
                    startActivity(i);
                }
                return true;
            }
        });

        sunday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(shake);
                onButtonPopupWindow(view);
            }
        });
        monday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(shake);
                onButtonPopupWindow(view);
            }
        });
        tuesday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(shake);
                onButtonPopupWindow(view);
            }
        });
        wednesday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(shake);
                onButtonPopupWindow(view);
            }
        });
        thursday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(shake);
                onButtonPopupWindow(view);
            }
        });
        friday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(shake);
                onButtonPopupWindow(view);
            }
        });
        saturday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(shake);
                onButtonPopupWindow(view);
            }
        });

    }

    public void onButtonPopupWindow(View view){
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.popupwindow, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //allows taps off of the window to dismiss it
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0,0);
    }

    //Write the file into the plant list
    public void save()
    {
        internalStorageDir = getFilesDir();
        File plants = new File(internalStorageDir, "plants_file.csv");
        if(plants.exists())
        {
            plants.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(plants);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userPlantList);
            oos.close();
        }
        catch(Exception e) {
            Toast.makeText(MainActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    //Read the file into the plant list
    public void readFile()
    {
        try {
            internalStorageDir = getFilesDir();
            File plants = new File(internalStorageDir, "plants_file.csv");
            FileInputStream fis = new FileInputStream(plants);
            ObjectInputStream ois = new ObjectInputStream(fis);
            userPlantList = (ArrayList<Plant>) ois.readObject();
            ois.close();
        }
        catch(Exception e)
        {
            Toast.makeText(MainActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

}
