package com.ash.android.cowsbulls;

import java.util.ArrayList;
import java.util.Properties;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ash.android.cowsbulls.engine.CowAndBullEngine;
import com.ash.android.cowsbulls.utility.AssetsPropertyReader;
import com.ash.android.cowsbulls.vo.CowBullResultVo;


public class OnePlayerGameActivity extends Activity {
	private EditText myEditText = null;
	private ListView myListView = null;
	private ArrayList<String> playList  = new ArrayList<String>();
	private ArrayAdapter<String> aa=null;
	private AssetsPropertyReader assetsPropertyReader;
	private Context context;
	private Properties p;
	private CowAndBullEngine cb = null;
	private boolean playStatus = true;
	private Button guessBttn = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.oneplayergame_activity);
    	
    	myListView = (ListView)findViewById(R.id.myListView);
    	
    	myEditText = (EditText)findViewById(R.id.myEditText);

		guessBttn = (Button)findViewById(R.id.guessbttn);
		guessBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				guessAction(view);
			}
		});

		// Create the array list of to do items
    	//final ArrayList<String> todoItems = new ArrayList<String>();
    	// Create the array adapter to bind the array to the listview
    
    	aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,	playList);
    	// Bind the array adapter to the listview.
    	myListView.setAdapter(aa);
    	
    	Intent intent = getIntent();
    	String player2Word = intent.getStringExtra("playWord");
    	
    	if(player2Word!=null){
    		 setTitle("Player 2");
    		 cb =new CowAndBullEngine(this,player2Word);
    	}else{
    		cb = new CowAndBullEngine(this);
    		setTitle("Player 1");
    	}
     }
    
    public void guessAction(View view)  {
    try {
		if (playStatus) {
			String guessWord = myEditText.getText().toString();
			if (myEditText.getText() == null || guessWord.isEmpty()) {
				myEditText.setError("Word should not be blank!!");
				return;
			}

			if (guessWord.length() > 4) {
				myEditText.setError("Word should be of 4 letters!!");
				return;
			}


			if (cb.checkDuplicateChars(guessWord)) {
				myEditText.setError("Invalid. 	Word contains duplicate letters!!");
				return;
			}

			if (!cb.validatePlayWord(guessWord)) {
				myEditText.setError(guessWord + "::Invalid Word.Type again!!");
				return;
			}

			CowBullResultVo result = compareGuessWord(guessWord);
			StringBuilder sb = new StringBuilder();
			sb.append(playList.size());
			sb.append("|");
			sb.append(myEditText.getText().toString());
			sb.append("|");
			sb.append(result.getResult());
			playList.add(0, sb.toString());
			aa.notifyDataSetChanged();

			if (playList.size() < 10) {
				Toast.makeText(this, 10 - playList.size() + " more chances to go !!", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "Alert: Last chance to go !!", Toast.LENGTH_LONG).show();
			}

			Intent intent = null;
			if (result.getStatus()) {
				playList.add(0, "You win!! Congratulations!!");
				aa.notifyDataSetChanged();
				Toast.makeText(this, "You Win!!", Toast.LENGTH_LONG).show();
				playStatus = false;
				restartActivity();

			} else {
				if (playList.size() > 10) {
					playList.add(0, "You Lose!! Guess word :: " + cb.getPlayWord());
					Toast.makeText(this, "You Lose.. Try Again!!", Toast.LENGTH_LONG).show();
					playStatus = false;
					restartActivity();
    		/*	intent = new Intent(this, ReplayGameActivity.class);
    	        startActivity(intent);*/
				}

				myEditText.setText("");
			}

			// Display a transient dialog box that displays the
			// error message string resource.
			//Toast.makeText(this, R.string.app_error, Toast.LENGTH_LONG).show();
		}
	}catch(Exception e)	{
		e.printStackTrace();
		Toast.makeText(this, "Error occured! This version is not supported", Toast.LENGTH_LONG).show();
	}
    }
    
    public void userChoice(View view) {
    	Intent intent = null;
        switch (view.getId()) {
        case R.id.restartbttn:
            // Button instrButton = (Button) findViewById(R.id.instrBttn); 
       	 	System.out.println("In userChoice Replay");
             intent = new Intent(this, OnePlayerGameActivity.class);
             startActivity(intent);
         break;
        case R.id.homebttn:
         // Button instrButton = (Button) findViewById(R.id.instrBttn); 
    	 System.out.println("In userChoice Home");
          intent = new Intent(this, HomeActivity.class);
          startActivity(intent);
          break;
    }
  }
    
    private void restartActivity(){
    	Intent	intent = new Intent(this, ReplayGameActivity.class);
        startActivity(intent);
    	
    	/*final Dialog dialog = new ReplayGameActivity(this);
        dialog.show();*/

    }
	 // Called before subsequent visible lifetimes
	 // for an activity process.
	 @Override
	 public void onRestart(){
	 super.onRestart();
	 // Load changes knowing that the activity has already
	 // been visible within this process.
	 }
    private CowBullResultVo compareGuessWord(String guessword) throws Exception{
    	return cb.comparePlayerWords(guessword);
    }
}
