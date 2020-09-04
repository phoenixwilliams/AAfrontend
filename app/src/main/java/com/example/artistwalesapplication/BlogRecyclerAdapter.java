package com.example.artistwalesapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.TextViewHolder>
{
    private String[] blogs;
    private String[] dates;
    public BlogRecyclerAdapter(String[] blogs, String[] dates)
    {
        this.blogs = new String[blogs.length];
        this.dates = new String[dates.length];
        int j = blogs.length;
        for (int i=0;i<blogs.length;i++)
        {
            this.blogs[j-1] = blogs[i];
            this.dates[j-1] = dates[i];
            j = j-1;
        }
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showblog_layout,parent,false);
        BlogRecyclerAdapter.TextViewHolder textViewHolder = new BlogRecyclerAdapter.TextViewHolder(view);
        return textViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        holder.blogTextView.setText(blogs[position]);
        holder.blogTitleTextView.setText(dates[position]);
    }

    @Override
    public int getItemCount() {
        return blogs.length;
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder
    {

        TextView blogTextView;
        TextView blogTitleTextView;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            blogTextView = itemView.findViewById(R.id.blog_textview);
            blogTitleTextView = itemView.findViewById(R.id.blog_title_textview);
        }
    }
}
