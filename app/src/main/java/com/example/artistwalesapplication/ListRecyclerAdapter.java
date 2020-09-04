package com.example.artistwalesapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.NumberViewHolder>
{
    private String[] contactMethod;
    private String[] contactDetails;
    private ContactActivity contactActivity;

    public ListRecyclerAdapter(String[] contactMethod, String[] contactDetails, ContactActivity contactActivity)
    {
        this.contactDetails = contactDetails;
        this.contactMethod = contactMethod;
        this.contactActivity = contactActivity;
    }


    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.number_list_item,parent,false);
        NumberViewHolder numberViewHolder = new NumberViewHolder(view);

        return numberViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NumberViewHolder holder, final int position)
    {
        String text = contactMethod[position]+":\n"+contactDetails[position];
        //Log.v("contactURL",isLink + ":"+ contactDetails[position]);
        final boolean isURL = holder.isURL(contactDetails[position]);
        boolean isEmail = holder.isEmailValid(contactDetails[position]);
        Log.v("urlCheck",Boolean.toString(isURL));
        Log.v("emailCheck",Boolean.toString(isEmail));
        SpannableString spannableString = new SpannableString(text);
        if(isURL){
            Log.v("urlCheck","if called");
            spannableString.setSpan(new ForegroundColorSpan(Color.rgb(77,255,244)), contactMethod[position].length()+2,
                    text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            //text = contactMethod[position]+":"+spannableString;
            spannableString.setSpan(new UnderlineSpan(),contactMethod[position].length()+2,
                    text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            holder.listItemNumberView.setText(spannableString);

        }
        if(isEmail)
        {
            spannableString.setSpan(new ForegroundColorSpan(Color.rgb(255,224,24)),contactMethod[position].length()+2,
                    text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new UnderlineSpan(),contactMethod[position].length()+2,
                    text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            holder.listItemNumberView.setText(spannableString);
        }
        else
        {
            //Log.v("contactURL","if false");
            holder.listItemNumberView.setText(spannableString);
        }


        holder.listItemNumberView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isURL)
                {
                    contactActivity.openWebPage(contactDetails[position]);
                }
                //Log.v("textClicked",contactDetails[position]+":"+Boolean.toString(holder.isURL(contactDetails[position])));
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactDetails.length;
    }




    class NumberViewHolder extends RecyclerView.ViewHolder
    {
        private TextView listItemNumberView;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
        }

        public boolean isURL(String inputUrl)
        {
            String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
            Pattern p = Pattern.compile(URL_REGEX);
            Matcher m = p.matcher(inputUrl);//replace with string to compare
            if(m.find()) {
                return true;
            }else return false;

        }

        public boolean isEmailValid(String inputEmail)
        {
            CharSequence charSequence = (CharSequence) inputEmail;
            return Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
        }
    }
}
