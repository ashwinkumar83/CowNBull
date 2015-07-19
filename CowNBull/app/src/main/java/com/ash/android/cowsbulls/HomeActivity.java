package com.ash.android.cowsbulls;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

@SuppressLint("NewApi")
public class HomeActivity extends Activity {

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }
    
    public void callAction(View view) {
    	Intent intent = null;
        switch (view.getId()) {
        case R.id.newGameBttn:
            // Button instrButton = (Button) findViewById(R.id.instrBttn); 
       	 	System.out.println("In callAction New Game");
             intent = new Intent(this, UserChoiceActivity.class);
             startActivity(intent);
         break;
        case R.id.instrBttn:
         // Button instrButton = (Button) findViewById(R.id.instrBttn); 
    	 System.out.println("In callAction Instructions");
          intent = new Intent(this, InstructionActivity.class);
          startActivity(intent);
          break;
        case R.id.shareBttn:
        	System.out.println("In callAction Share");
        	Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
        	//sendIntent.setAction(Intent.ACTION_SEND);
        	sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Awesome Word Game !! Try it out !!");
        	sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.ash.android.cowsbulls&hl=en");
        	sendIntent.setType("text/plain");
        	startActivity(Intent.createChooser(sendIntent, "Share via"));
        case R.id.exitBttn:
            // Button instrButton = (Button) findViewById(R.id.instrBttn); 
       	 System.out.println("In callAction Exit");
       	 	setResult(5);
             finish();
            
             break;
        default:
          break;
        }
      }
}
