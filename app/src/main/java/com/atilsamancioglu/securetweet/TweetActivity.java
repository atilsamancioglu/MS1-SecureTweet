package com.atilsamancioglu.securetweet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class TweetActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    EditText tweetText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        tweetText = findViewById(R.id.tweetText);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void tweetButtonClicked(View view) {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userEmail = firebaseUser.getEmail();

        String tweet = tweetText.getText().toString();

        HashMap<String, Object> postData = new HashMap<>();
        postData.put("useremail",userEmail);
        postData.put("tweet",tweet);
        postData.put("date", FieldValue.serverTimestamp());

        firebaseFirestore.collection("Tweets").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Intent intent = new Intent(TweetActivity.this,FeedActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TweetActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });






    }
}
