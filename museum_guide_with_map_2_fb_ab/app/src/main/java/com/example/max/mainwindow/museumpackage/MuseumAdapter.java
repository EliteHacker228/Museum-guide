package com.example.max.mainwindow.museumpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.max.mainwindow.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MuseumAdapter extends RecyclerView.Adapter<MuseumAdapter.MuseumsViewHolder>{
    private ArrayList<Museum> museums;
    private Context context;

    public MuseumAdapter(ArrayList<Museum> museums){
        this.museums=museums;
    }

    @Override
    public MuseumsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new MuseumsViewHolder(view);
    }


    @Override
    public void onBindViewHolder( final MuseumsViewHolder holder, int position) {
        final Museum museum = museums.get(position);


        if(museum.getSplitter()==0){
            holder.ratingBar.setRating(0.0f);
        }else {
            holder.ratingBar.setRating(museum.getRating()/museum.getSplitter());
        }
        holder.Name.setText(museum.getMname());
        holder.Adress.setText("  Улица "+museum.getAdress());


        if(museum.getURL().isEmpty()){
            Picasso.get().load("https://yt3.ggpht.com/a-/AJLlDp3w3Ok_TD46pLqIlFB7_TbbwUHQ4D867hKRhQ=s900-mo-c-c0xffffffff-rj-k-no").into(holder.imgPoster);
        }else {
            Picasso.get().load(museum.getURL()).resize(640, 480).into(holder.imgPoster);
        }

        holder.cardViewClickListener.setRecord(museum);

    }

    public class MuseumsViewHolder extends RecyclerView.ViewHolder {

        RatingBar ratingBar;
        TextView Name, Adress;
        ImageView imgPoster;
        CardView cv;


        CardViewClickListener cardViewClickListener = new CardViewClickListener();


        public MuseumsViewHolder(View itemView) {
            super(itemView);

            ratingBar = itemView.findViewById(R.id.museumList_ratingBar);
            imgPoster = itemView.findViewById(R.id.museum_poster);
            Name = itemView.findViewById(R.id.museum_name);
            Adress = itemView.findViewById(R.id.museum_adress);
            cv = itemView.findViewById(R.id.museum_list_item_cv);
            context=itemView.getContext();

            cv.setOnClickListener(cardViewClickListener);

        }
    }

    class CardViewClickListener implements View.OnClickListener{

        Museum museum;

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, MuseumActivity.class);
            intent.putExtra("name", museum.getMname()); //name
            intent.putExtra("URL", museum.getURL());
            intent.putExtra("trivia", museum.getTrivia()); //trivia
            intent.putExtra("website", museum.getWebsite()); //website
            intent.putExtra("phone", museum.getPhone()); //phone
            intent.putExtra("adress", museum.getAdress());
            intent.putExtra("v", museum.getV());
            intent.putExtra("v1", museum.getV1());
            intent.putExtra("ui", museum.getUi());
            intent.putExtra("splitter", museum.getSplitter());
            intent.putExtra("allRating", museum.getRating());



            if(museum.getSplitter()==0){
                intent.putExtra("rating", 0);
            }else {
                intent.putExtra("rating", museum.getRating()/museum.getSplitter());
            }





            ((Activity)context).startActivity(intent);


        }

        void setRecord(Museum museum){
            this.museum=museum;
        }
    }




    @Override
    public int getItemCount() {
        return museums.size();
    }


}
