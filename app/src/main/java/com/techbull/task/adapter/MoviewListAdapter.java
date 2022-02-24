package com.techbull.task.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.techbull.task.R;
import com.techbull.task.models.Movie;

import java.util.ArrayList;

public class MoviewListAdapter extends RecyclerView.Adapter<MoviewListAdapter.ViewHolder>{

    ArrayList<Movie> arrayList1;
    Context context;
    private LayoutInflater inflater;

    public MoviewListAdapter(ArrayList<Movie> arrayList, Context context) {
        this.arrayList1 = arrayList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_movie, parent, false);
        return new MoviewListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Movie moviename=arrayList1.get(position);
        viewHolder.text_title.setText(moviename.getName());
        viewHolder.text_year.setText(moviename.getYear());

        Picasso.get()
                .load(moviename.getImage())

                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return arrayList1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView text_title, text_year;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            text_title=itemView.findViewById(R.id.text_moviewname);
            text_year=itemView.findViewById(R.id.text_year);

            image= (ImageView) itemView.findViewById(R.id.img);

        }
    }
}
