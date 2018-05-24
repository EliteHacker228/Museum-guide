package com.example.max.mainwindow.newslistpackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.max.mainwindow.R;
import com.example.max.mainwindow.UniversalWebview;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<NewsElement> mData;
    private Context context;
    private LayoutInflater mInflater;
    //private int position;

    MyRecyclerViewAdapter(Context context, List<NewsElement> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsElement news = mData.get(position);

        holder.NewsTitle.setText(news.getNewsText());
        holder.NewsDate.setText(news.getNewsDate());
        holder.NewsTime.setText(news.getNewsTime());



        if(news.getNewsPicURL().isEmpty()){
            Picasso.get().load("https://yt3.ggpht.com/a-/AJLlDp3w3Ok_TD46pLqIlFB7_TbbwUHQ4D867hKRhQ=s900-mo-c-c0xffffffff-rj-k-no").into(holder.NewsPoster);

        }else {
            Picasso.get().load(news.getNewsPicURL()).resize(80, 80).into(holder.NewsPoster);

        }

        holder.newsClicker.setRecord(news);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  { //Тут инициализируем элементы
        TextView NewsTitle, NewsTime, NewsDate;
        ImageView NewsPoster;
        CardView newsCard;
        NewsClicker newsClicker = new NewsClicker();

        ViewHolder(View itemView) {
            super(itemView);
            NewsTitle = itemView.findViewById(R.id.publis_title);
            NewsPoster=itemView.findViewById(R.id.publish_picture);
            NewsTime=itemView.findViewById(R.id.publish_time);
            NewsDate=itemView.findViewById(R.id.publish_date);
            newsCard = itemView.findViewById(R.id.publish_newscard);

            newsCard.setOnClickListener(newsClicker);

        }


    }

   String getItem(int id) {
       return mData.get(id).newsText;
   }

    class NewsClicker implements View.OnClickListener {

        NewsElement newsElement;
        int position;

        @Override
        public void onClick(View v) {

           Intent intent = new Intent(context, UniversalWebview.class);
            position=mData.indexOf(newsElement);
            intent.putExtra("Passkey", "News");
           intent.putExtra("URL", mData.get(position).getNewsLink());
           context.startActivity(intent);

        }



        public void setRecord(NewsElement newsElement){
            this.newsElement=newsElement;
        }
    }


}