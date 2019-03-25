package com.example.newsapi;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapi.api.ArticlesItem;
import com.squareup.picasso.Picasso;

import java.util.List;

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    Context context;
    List<ArticlesItem> data;

    public NewsAdapter(Context context, List<ArticlesItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NewsViewHolder( LayoutInflater.from( context ).inflate( R.layout.tampilan_list, viewGroup, false ) );
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, final int i) {

        newsViewHolder.itemView.startAnimation( AnimationUtils.loadAnimation( context,R.anim.scale ) );

        newsViewHolder.tvDesc.setText( data.get( i ).getTitle() );
        newsViewHolder.tvAuthor.setText( data.get( i ).getAuthor() );

        Picasso.get().load( data.get( i ).getUrlToImage() ).into( newsViewHolder.imgNews );

        newsViewHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity( new Intent( context, WebCustom.class ).putExtra( "url", data.get( i ).getUrl() ) );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor, tvDesc;
        ImageView imgNews;

        public NewsViewHolder(@NonNull View itemView) {
            super( itemView );

            tvAuthor = itemView.findViewById( R.id.tv_author );
            tvDesc = itemView.findViewById( R.id.tv_desc );
            imgNews = itemView.findViewById( R.id.img_news );
        }
    }


}
