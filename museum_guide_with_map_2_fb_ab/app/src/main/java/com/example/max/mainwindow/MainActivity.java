package com.example.max.mainwindow;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.max.mainwindow.museumpackage.ListFragment;
import com.example.max.mainwindow.museumpackage.Museum;
import com.example.max.mainwindow.newslistpackage.NewsParserFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {


    NewsParserFragment newsFragment = new NewsParserFragment();
    ListFragment listFragment = new ListFragment();
    MapFragment mapFragment = new MapFragment();
    static ArrayList<Museum> museums = new ArrayList<>();
    String lastOpen;
    String savedState;
    SharedPreferences sharedPreferences;
    TreeMap<String, Museum> musemsTree = new TreeMap<>();


    void saveData() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(savedState, lastOpen);
        editor.commit();
    }

    void loadData() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        this.lastOpen = sharedPreferences.getString(savedState, "news");

    }

    static public ArrayList<Museum> getMuseums() {
        return museums;
    }

    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAlarm();
        if(museums.isEmpty()) {
            initDB();
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Новости на Znak.com");
        loadData();


        if (savedInstanceState == null) {



            fragmentManager.beginTransaction().replace(R.id.placeholder, newsFragment).commit();
            setTitle("Новости на Znak.com");
            lastOpen = "news";


        } else {
            switch (lastOpen) {
                case "news":
                    fragmentManager.beginTransaction().replace(R.id.placeholder, newsFragment).commit();
                    setTitle("Новости на Znak.com");
                    break;

                case "list":
                    fragmentManager.beginTransaction().replace(R.id.placeholder, listFragment).commit();
                    setTitle("Музеи Екатеринбурга");
                    break;

                case "map":
                    setTitle("Карта музеев в Екатеринбурге");
                    fragmentManager.beginTransaction().replace(R.id.placeholder, mapFragment).commit();
                    break;
            }
        }


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_news:


                    fragmentManager.beginTransaction().replace(R.id.placeholder, newsFragment).commit();
                    lastOpen = "news";
                    saveData();
                    setTitle("Новости на Znak.com");
                    return true;
                case R.id.navigation_museums:
                    lastOpen = "list";
                    saveData();
                    fragmentManager.beginTransaction().replace(R.id.placeholder, listFragment).commit();
                    setTitle("Музеи Екатеринбурга");
                    return true;

                case R.id.navigation_map:
                    lastOpen = "map";
                    saveData();
                    setTitle("Карта музеев в Екатеринбурге");
                    fragmentManager.beginTransaction().replace(R.id.placeholder, mapFragment).commit();
                    return true;
            }
            return false;
        }
    };

    private void startAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;


        myIntent = new Intent(MainActivity.this, AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);


        manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 345600000, pendingIntent);
    }

    public void initDB() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fireapp-12b506.firebaseio.com/museums");

        ref.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Museum m = dataSnapshot.getValue(Museum.class);
                museums.add(m);
                musemsTree.put(m.getUi(), m);


            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Museum changedMuseum = dataSnapshot.getValue(Museum.class);
                String key = changedMuseum.getUi();
                Museum sourceMuseum = musemsTree.get(key);
                musemsTree.put(key, changedMuseum);
                changedMuseum.setUi(key);
                int index = museums.indexOf(sourceMuseum);
                museums.set(index, changedMuseum);


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Museum removedMuseum = dataSnapshot.getValue(Museum.class);
                museums.remove(removedMuseum);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }


        });
    }
}
