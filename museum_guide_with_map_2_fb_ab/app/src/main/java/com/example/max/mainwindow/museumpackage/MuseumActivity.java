package com.example.max.mainwindow.museumpackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.max.mainwindow.R;
import com.example.max.mainwindow.UniversalWebview;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MuseumActivity extends FragmentActivity implements OnMapReadyCallback {
    TextView name, website, trivia, adress, phone;
    ConstraintLayout museumContainer;
    String UI;
    int splitter;
    RatingBar mainRatingBar;
    LinearLayout museumInformContainer;
    ImageView poster;
    String WebAdress;
    DatabaseReference ref;
    float rvl;
    double v,v1;
    private GoogleMap mMap;
    float ratingValue, allRatingValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);

        Intent intent = getIntent();
        rvl= intent.getIntExtra("allRating", 0);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        name = findViewById(R.id.name);
        website=findViewById(R.id.website);
        trivia=findViewById(R.id.trivia);
        adress=findViewById(R.id.adress);
        phone=findViewById(R.id.phone);
        poster = findViewById(R.id.poster);
        mainRatingBar = findViewById(R.id.museum_ratingBar);
        museumContainer=findViewById(R.id.museum_container);
        museumInformContainer=findViewById(R.id.museum_inf_container);
        ratingValue=intent.getFloatExtra("rating", 0.0f);
        allRatingValue=intent.getFloatExtra("allRating", 0.0f);

        UI=intent.getStringExtra("ui");
        splitter=intent.getIntExtra("splitter", 0);
        v=intent.getDoubleExtra("v",0.0);
        v1=intent.getDoubleExtra("v1",0.0);
        mainRatingBar.setRating(ratingValue);



        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fireapp-12b506.firebaseio.com/museums/"+UI);
        mainRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                splitter+=1;
                allRatingValue+=rating;

                Toast.makeText(MuseumActivity.this, "Спасибо за отзыв! Ваша оценка учтена.", Toast.LENGTH_SHORT).show();

                mainRatingBar.setRating(rating);
                ref.child("splitter").setValue(splitter);
                ref.child("rating").setValue(allRatingValue);

            }
        });


        name.setText(intent.getStringExtra("name"));
        trivia.setText(intent.getStringExtra("trivia"));
        adress.setText("Улица "+intent.getStringExtra("adress"));
        WebAdress = intent.getStringExtra("website");

        phone.setText("Телефон: "+intent.getStringExtra("phone"));

        if(intent.getStringExtra("website").equals("")){
            museumInformContainer.removeView(website);
        }else{
            website.setText("Перейти на сайт");
        }

        if(phone.getText().equals("")){
            museumInformContainer.removeView(phone);
        }else if(adress.getText().equals("неизвестна")){
            museumInformContainer.removeView(adress);
        }

        //Picasso.get().load(intent.getStringExtra("URL")).resize(640, 480).into(poster);
        if(intent.getStringExtra("URL").isEmpty()){
            Picasso.get().load("https://yt3.ggpht.com/a-/AJLlDp3w3Ok_TD46pLqIlFB7_TbbwUHQ4D867hKRhQ=s900-mo-c-c0xffffffff-rj-k-no").resize(640, 480).into(poster);
            //Log.d("NewsPosterLost", "потрачено "+news.getNewsPicURL());
        }else {
            Picasso.get().load(intent.getStringExtra("URL")).resize(640, 480).into(poster);
            //Log.d("NewsPosterLostGet", news.getNewsPicURL());

        }

        class WebsiteClicker implements View.OnClickListener{

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UniversalWebview.class);
                String message="http://"+WebAdress;

                intent.putExtra("Passkey", "Museum");

                intent.putExtra("URL", message);
                startActivity(intent);

            }
        }
        website.setOnClickListener(new WebsiteClicker());

        setTitle("Музей");


    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng ekaterinburg= new LatLng(v, v1);
        mMap.addMarker(new MarkerOptions().position(ekaterinburg).title(""));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ekaterinburg));
        mMap.setMinZoomPreference(9.0f);

    }


}