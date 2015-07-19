package com.ash.android.cowsbulls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ash.android.cowsbulls.engine.CowAndBullEngine;
import com.ash.android.cowsbulls.utility.CommonUtils;

@SuppressLint("NewApi")
public class UserChoiceActivity extends Activity {

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userchoice_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }
    
    public void playAction(final View view) {
    	Intent intent = null;
    	RadioGroup userchoiceGrp = (RadioGroup)findViewById(R.id.radioGroup1);
    	 // get selected radio button from radioGroup
		int selectedId = userchoiceGrp.getCheckedRadioButtonId();
		//RadioButton userchoice = (RadioButton) findViewById(selectedId);
		 switch (selectedId) {
	        case R.id.player1:
	            // Button instrButton = (Button) findViewById(R.id.instrBttn); 
	       	 	System.out.println("In playAction Player 1");
	             intent = new Intent(this, OnePlayerGameNewActivity.class);
	             startActivity(intent);
	         break;
	        case R.id.player2:
	        	createAlertDialog();
    		default:
    				break;
      		}
     }
    
    public void createAlertDialog(){
    	   // Button instrButton = (Button) findViewById(R.id.instrBttn); 
    	System.out.println("In playAction Player 2");
			
			//final FrameLayout frameView = new FrameLayout(view.getContext());
		//	alertDialogBuilder.setView(frameView);
		//	LayoutInflater inflater = alertDialog.getLayoutInflater();
			LayoutInflater inflater = LayoutInflater.from(this);
			View dialoglayout = inflater.inflate(R.layout.twoplayergame_activity, null);
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.Transparent_Dialog);
			
			alertDialogBuilder.setView(dialoglayout);
			
			// set title
			alertDialogBuilder.setTitle("Player 1");
			// create alert dialog
			
			final EditText player1Word = (EditText)dialoglayout.findViewById(R.id.playWordText);
			
			//alertDialogBuilder.setView(alertDialogBuilder.getLayoutInflater().inflate(R.layout.twoplayergame_activity, null));
			//setView(findViewById(R.layout.twoplayergame_activity));
			// set dialog message
			alertDialogBuilder
				//.setMessage("Click yes to exit!")
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
					//	EditText myEditText = (EditText)findViewById(R.id.playWordText);
						boolean errorFlag = false;
				    	if(player1Word.getText()==null || player1Word.getText().toString().isEmpty()){
				    		showError("Word should not be blank!!");
				    		errorFlag = true;
				    		dialog.dismiss();
				    	}
				    	
				    	String playWord = player1Word.getText().toString();
				    	
				    	
				    	if(!CommonUtils.isAlpha(playWord)){
				    		showError("Play Word should not be a number!!");
				     		errorFlag = true;
				    		dialog.dismiss();
				    	}
				    	
				    	
						//String playWord = "blue";
				    	if(playWord.length() > 4){
				    		showError("Play Word should be 4 letters!!");
				    		errorFlag = true;
				    		dialog.dismiss();
				    	}
				    	
						try {
							CowAndBullEngine cb = new CowAndBullEngine(getApplicationContext(),playWord);
							if(cb.checkDuplicateChars(playWord)){
								showError("Invalid.Word contains duplicate letters!!");
								errorFlag = true;
								dialog.dismiss();
							}
							if(!cb.validatePlayWord(playWord)){
								showError(playWord + " :: Invalid Word. Type Again!!");
								errorFlag = true;
								dialog.dismiss();
							}
						} catch (Exception e) {
							errorFlag = true;
							dialog.dismiss();
						}
						
						// if this button is clicked, close
						// current activity
						if(!errorFlag){
						UserChoiceActivity.this.finish();
						
						Intent intent = new Intent(getApplicationContext(), OnePlayerGameNewActivity.class);
						intent.putExtra("playWord", player1Word.getText().toString());
			            startActivity(intent);
						}
						
					}
				  })
				.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.dismiss();
					}
				});
			  /*  .setOnDismissListener(new DialogInterface.OnDismissListener() { 
			    public void onDismiss( DialogInterface dialog) { 
			       finish();
			    }
			});*/
				
			
			final AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
			alertDialog.show();
			
    }
    
   
    
    public void showError(String errorMsg){
    	Toast toast = Toast.makeText(getApplicationContext(),errorMsg, 20);
		toast.show();
		//createAlertDialog();
    }
}
