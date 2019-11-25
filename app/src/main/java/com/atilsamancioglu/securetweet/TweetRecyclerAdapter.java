package com.atilsamancioglu.securetweet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TweetRecyclerAdapter extends RecyclerView.Adapter<TweetRecyclerAdapter.PostHolder> {
    private ArrayList<String> userEmailList;
    private ArrayList<String> userTweetList;

    public TweetRecyclerAdapter(ArrayList<String> userEmailList, ArrayList<String> userTweetList) {
        this.userEmailList = userEmailList;
        this.userTweetList = userTweetList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.userEmailText.setText(userEmailList.get(position));
        holder.userTweetText.setText(userTweetList.get(position));
    }

    @Override
    public int getItemCount() {
        return userTweetList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {

        TextView userTweetText;
        TextView userEmailText;


        public PostHolder(@NonNull View itemView) {
            super(itemView);

            userTweetText = itemView.findViewById(R.id.userTweetText);
            userEmailText = itemView.findViewById(R.id.userEmailText);

        }
    }
}
