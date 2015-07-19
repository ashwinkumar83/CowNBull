package com.ash.android.cowsbulls;

import java.util.ArrayList;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ash.android.cowsbulls.engine.CowAndBullEngine;
import com.ash.android.cowsbulls.utility.AssetsPropertyReader;
import com.ash.android.cowsbulls.utility.CommonUtils;
import com.ash.android.cowsbulls.vo.CowBullResultVo;


public class OnePlayerGameNewActivity extends Activity {
	private EditText myEditText = null;
	private ListView myListView = null;
	private ArrayList<String> playList  =null;
	private PlayAreaListAdapter aa=null;
	private AssetsPropertyReader assetsPropertyReader;
	private Context context;
	private Properties p;
	private CowAndBullEngine cb = null;
	private boolean playStatus = true;
	private Button guessBttn;
	private long startTimePoint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.oneplayergame_activity);
    	
    	playList  = new ArrayList<String>();
    	
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
    
    	aa = new PlayAreaListAdapter(this,android.R.layout.simple_list_item_1,playList);
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
    	
    	startTimePoint = System.currentTimeMillis();
     }
    
    @SuppressLint("NewApi")
	public void guessAction(View view) {
      	try{
    	myEditText.setError(null);//removes error
    	
    	Button guessBttn = (Button)findViewById(R.id.guessbttn);
   	 
    	if(playStatus){	
    		
    	String guessWord = myEditText.getText().toString();
    	
    	if(myEditText.getText()==null || guessWord.isEmpty()){
    		myEditText.setError("Word should not be blank!!");
    		return;
    	}
    	
    	if(!CommonUtils.isAlpha(guessWord)){
    		myEditText.setError("Play Word should not be a number!!");
    		return;
    	}
    	
    	if(guessWord.length() > 4 || guessWord.length() < 4 ){
    		myEditText.setError("Word should be of 4 letters!!");
    		return;
    	}
    	
    	
		if(cb.checkDuplicateChars(guessWord)){
			myEditText.setError("Invalid. 	Word contains duplicate letters!!");
			return;
		}
		
		if(!cb.validatePlayWord(guessWord)){
			myEditText.setError(guessWord+"::Invalid Word.Type again!!");
			return;
		}
		
    	CowBullResultVo result  = compareGuessWord(guessWord);
    	StringBuilder sb = new StringBuilder();
    	sb.append(playList.size()+1);
    	sb.append("|");
    	sb.append(myEditText.getText().toString());
    	sb.append("|");
    	sb.append(result.getResult());
    	sb.append("|");
    	sb.append(currentTimeString());
    	playList.add(0, sb.toString());
    	aa.notifyDataSetChanged();
    	
    	
    	Intent intent = null;
    	if(result.getStatus()){
    		Toast.makeText(this, "You Win!!", Toast.LENGTH_LONG).show();
    		playList.add(0, "You win!!|Congrats!!| | | |");
    		guessBttn.setClickable(false);
        	//aa.notifyDataSetChanged();
        	restartActivity();
        	 
    	}else{
    		if(playList.size()>10){
    			playList.add(0, "You Lose!!|PlayWord:|"+ cb.getPlayWord()+"| | |" );
    			Toast.makeText(this, "You Lose.. Try Again!!", Toast.LENGTH_LONG).show();
    			guessBttn.setClickable(false);
    			restartActivity();
    		/*	intent = new Intent(this, ReplayGameActivity.class);
    	        startActivity(intent);*/
    		}else{
    	
				if(playList.size()<10){
		    		Toast.makeText(this,11 - playList.size() + " more chances to go !!", Toast.LENGTH_LONG).show();
		    	}else{
		    		Toast.makeText(this,"Alert : Last chance !!", Toast.LENGTH_LONG).show();
		    	}
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
    
    private void restartActivity(){
    	
    	Intent	intent = new Intent(this, ReplayGameActivity.class);
    	intent.putExtra("completedTime",currentTimeString());
    	startActivityForResult(intent, 4);
    	//finish();
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
    
    private String currentTimeString() {
    	  long interval;
    	//  if( !paused )
    	    interval = System.currentTimeMillis() - startTimePoint;
    	 /* else
    	    interval = currentTime;*/

    	  int tens = (int) interval;
    	  int seconds = (int) interval / 1000;
    	  int minutes = seconds / 60;
    	  int hours = minutes / 60;
    	  tens = tens % 10;
    	  seconds = seconds % 60;

    	  return String.format("%d:%02d:%02d", hours, minutes, seconds);
    	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	System.out.println("requestCode:: " + requestCode);
    	System.out.println("resultCode:: " + resultCode);
        if(resultCode==4){
           // setResult(5);
            finish();
        }
      //  super.onActivityResult(requestCode, resultCode, data);
    }
}
