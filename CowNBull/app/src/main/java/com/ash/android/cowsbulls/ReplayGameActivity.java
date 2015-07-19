package com.ash.android.cowsbulls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ReplayGameActivity extends Activity  {
	Button replaybttn;
	Button homebttn;
	Context context;
 
 @Override
 public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	  // super(context);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.restart_activity);
       
       final Window window = this.getWindow();
       
       Intent intent = getIntent();
   	   String completedTime = intent.getStringExtra("completedTime");
       
      // window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
       window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
       window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       
       
       
       //dialog.show();
      /* homebttn = (Button) findViewById(R.id.homebttn);
       homebttn.setOnClickListener(this);
       
       replaybttn = (Button) findViewById(R.id.restartbttn);
       replaybttn.setOnClickListener(this);*/
       
     /* TextView completedTimeTxt = (TextView)findViewById(R.id.completedTimeTxt); 
       completedTimeTxt.setText("Time Taken: " + completedTime );*/
       
     /*  TextView creditTxt = (TextView)findViewById(R.id.creditsTxt); 
       creditTxt.setSelected(true);
       creditTxt.setText("Josh Studios");*/
       
   }
    
  /* @Override
    public void onClick(View view) {
    	Intent intent = null;
        switch (view.getId()) {
        case R.id.restartbttn:
            // Button instrButton = (Button) findViewById(R.id.instrBttn); 
       	 	System.out.println("In userChoice Replay");
             intent = new Intent(context,UserChoiceActivity.class);
             context.startActivity(intent);
         break;
        case R.id.homebttn:
         // Button instrButton = (Button) findViewById(R.id.instrBttn); 
    	 System.out.println("In userChoice Home");
          intent = new Intent(context, HomeActivity.class);
          context.startActivity(intent);
          break;
        }
    }*/
        
     public void userChoice(View view) {
        	Intent intent = null;
            switch (view.getId()) {
            case R.id.restartbttn:
                // Button instrButton = (Button) findViewById(R.id.instrBttn);
            	
           	 	System.out.println("In userChoice Replay");
                 intent = new Intent(this,UserChoiceActivity.class);
                 startActivity(intent);
             break;
            case R.id.homebttn:
             // Button instrButton = (Button) findViewById(R.id.instrBttn); 
        	 System.out.println("In userChoice Home");
        	  setResult(4);
        	  finish();
              intent = new Intent(this, HomeActivity.class);
              startActivity(intent);
              break;
        }    
  }
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	 System.out.println("replay requestCode:: " + requestCode);
     	System.out.println("replay resultCode:: :: " + resultCode);
         if(requestCode== 5){
             setResult(4);
             finish();
         }
        // super.onActivityResult(requestCode, resultCode, data);
     }
}
