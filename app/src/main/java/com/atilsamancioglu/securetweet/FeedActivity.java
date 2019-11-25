package com.atilsamancioglu.securetweet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    ArrayList<String> userEmailFromFB;
    ArrayList<String> userTweetFromFB;
    TweetRecyclerAdapter tweetRecycerAdapter;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_tweet) {
            Intent intentToTweet = new Intent(FeedActivity.this, TweetActivity.class);
            startActivity(intentToTweet);
        } else if (item.getItemId() == R.id.signout) {

            firebaseAuth.signOut();

            Intent intentToSignUp = new Intent(FeedActivity.this, SignUpActivity.class);
            intentToSignUp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentToSignUp);
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userEmailFromFB = new ArrayList<>();
        userTweetFromFB = new ArrayList<>();

        getDataFromFirestore();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tweetRecycerAdapter = new TweetRecyclerAdapter(userEmailFromFB,userTweetFromFB);
        recyclerView.setAdapter(tweetRecycerAdapter);


    }

    public void getDataFromFirestore() {
        CollectionReference collectionReference = firebaseFirestore.collection("Tweets");

        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(FeedActivity.this,e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if (queryDocumentSnapshots != null) {

                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {

                        Map<String,Object> data = snapshot.getData();

                        //Casting
                        String tweet = (String) data.get("tweet");
                        String userEmail = (String) data.get("useremail");

                        userTweetFromFB.add(tweet);
                        userEmailFromFB.add(userEmail);

                        tweetRecycerAdapter.notifyDataSetChanged();

                    }


                }

            }
        });


    }
}
