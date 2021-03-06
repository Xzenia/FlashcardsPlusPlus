package com.treskie.conrad.flashcardsplus.Add;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.treskie.conrad.flashcardsplus.Browser.CardBrowser;
import com.treskie.conrad.flashcardsplus.Controller.FlashCardDatabaseController;
import com.treskie.conrad.flashcardsplus.R;
import com.treskie.conrad.flashcardsplus.Viewer.CardViewer;

public class AddCard extends AppCompatActivity {
    EditText mFirstPart;
    EditText mSecondPart;
    FlashCardDatabaseController dc;
    int getDeckId;
    private static final String TAG = "AddCard";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFirstPart = (EditText) findViewById(R.id.firstPart);
        mSecondPart = (EditText) findViewById(R.id.secondPart);
        dc = new FlashCardDatabaseController(this);
        Bundle getInfo = getIntent().getExtras();
        getDeckId = getInfo.getInt("deckId");
    }

    public void saveToDatabase(View v){
        Intent goToDeckViewer = new Intent(this, CardBrowser.class);
        int cardId = 111111 + (int) (Math.random() * 999999);
        /*
            Grabs data from the two text fields
            Look into /res/layout/activity_add_card.xml for said text fields
        */
        String firstPart = mFirstPart.getText().toString();
        String secondPart = mSecondPart.getText().toString();
        dc.setCardId(cardId);
        dc.setDeckIdentifier(getDeckId);
        dc.setFirstPart(firstPart);
        dc.setSecondPart(secondPart);
        boolean confirm = dc.addData();
        if (confirm){
            toastMessage("Card Successfully Added!");
            goToDeckViewer.putExtra("deckId",getDeckId);
            startActivity(goToDeckViewer);
            finish();
        } else {
            toastMessage("Something went wrong!");
            Log.e(TAG,"AddCard: Card was not successfully added! Probably something wrong with the FlashCardDatabaseController!");
        }
        startActivity(goToDeckViewer);
    }

    public boolean onSupportNavigateUp(){
        Intent goToCardBrowser = new Intent(this, CardBrowser.class);
        goToCardBrowser.putExtra("deckId",getDeckId);
        startActivity(goToCardBrowser);
        finish();
        return true;
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }




    

}
