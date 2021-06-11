package com.example.projectfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectfinal.DetailActivity;
import com.example.projectfinal.R;
import com.example.projectfinal.modelMovie.ResultMovie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private Context context;
    private List<ResultMovie> list;

    public MovieAdapter(Context context, List<ResultMovie> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.layout_item_card, parent, false);
        MovieAdapter.MyViewHolder viewHolder = new MovieAdapter.MyViewHolder(view);

        viewHolder.rl_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(parent.getContext(), DetailActivity.class);
                List<Integer> l = list.get(viewHolder.getAdapterPosition()).getGenreIds();
                String aa = "";
                for (int k : l) {
                    aa += (aa.isEmpty() ? "" : " ") + k;
                }
                String [] imports = {
                        list.get(viewHolder.getAdapterPosition()).getTitle(),
                        list.get(viewHolder.getAdapterPosition()).getOverview(),
                        list.get(viewHolder.getAdapterPosition()).getPosterPath(),
                        list.get(viewHolder.getAdapterPosition()).getReleaseDate(),
                        list.get(viewHolder.getAdapterPosition()).getVoteAverage() + "",
                        list.get(viewHolder.getAdapterPosition()).getBackdropPath(),
                        list.get(viewHolder.getAdapterPosition()).getId() + "",
                        aa,
                };
                a.putExtra("data", imports);
                parent.getContext().startActivity(a);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder holder, int position) {
        holder.tv_titleMovie.setText(list.get(position).getTitle());
//        holder.tv_yearMovie.setText(list.get(position).getReleaseDate());
        if (list.get(position).getPosterPath() != null) {
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185" + list.get(position).getPosterPath())
                    .into(holder.iv_imageMovie);

        } else {
            holder.iv_imageMovie.setImageResource(R.drawable.poster_noimage);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_imageMovie;
        TextView tv_titleMovie;
//        TextView tv_yearMovie;
        RelativeLayout rl_card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_imageMovie = itemView.findViewById(R.id.iv_imageMovie);
            tv_titleMovie = itemView.findViewById(R.id.tv_titleMovie);
//            tv_yearMovie = itemView.findViewById(R.id.tv_yearsMovie);
            rl_card = itemView.findViewById(R.id.rl_card);
        }
    }
}
