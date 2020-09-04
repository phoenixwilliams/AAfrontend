package com.example.artistwalesapplication;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkRecyclerAdapter extends RecyclerView.Adapter<WorkRecyclerAdapter.ImageViewHolder>
{
    private Bitmap[] images;
    private String[] labels;
    private String[] prices;

    public WorkRecyclerAdapter(Bitmap[] images, String[] labels, String[] prices)
    {
        this.images = images;
        this.labels = labels;
        this.prices = prices;
    }

    @NonNull
    @Override
    public WorkRecyclerAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showwork_layout,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkRecyclerAdapter.ImageViewHolder holder, int position)
    {
        holder.album.setImageBitmap(images[position]);
        holder.albumLabel.setText(labels[position]);
        holder.albumPrice.setText(prices[position]);
    }

    @Override
    public int getItemCount() {
       return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView album;
        TextView albumLabel;
        TextView albumPrice;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            album = itemView.findViewById(R.id.album);
            albumLabel = itemView.findViewById(R.id.album_labels);
            albumPrice = itemView.findViewById(R.id.album_prices);
        }
    }
}
