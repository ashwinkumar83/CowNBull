package com.ash.android.cowsbulls;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class TwoPlayerGameActivity extends Activity {
	Button okbttn;
	
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.twoplayergame_activity);
	   
	   okbttn=(Button)findViewById(R.id.okbttn);
   }
    
 /*  @Override
    public void onClick(View view) {
    	Intent intent = null;
        switch (view.getId()) {
        case R.id.okbttn:
            // Button instrButton = (Button) findViewById(R.id.instrBttn); 
       	 	System.out.println("In userChoice Replay");
             intent = new Intent(context,OnePlayerGameActivity.class);
             context.startActivity(intent);
         break;
       
  }
   }*/
}
