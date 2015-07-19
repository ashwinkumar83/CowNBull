package com.ash.android.cowsbulls;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlayAreaListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final  List<String> values;
	
	public PlayAreaListAdapter(Context context,
			int listViewResourceId, List<String> values) {
		super(context, listViewResourceId, values);
		this.context = context;
		this.values = values;
	}

	
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.oneplayergamenew_activity, parent, false);
	    
	  
	    TextView textView = (TextView) rowView.findViewById(R.id.playWordTxt);
	    TextView textView1 = (TextView) rowView.findViewById(R.id.noOfBulls);
	    TextView textView2 = (TextView) rowView.findViewById(R.id.noOfCows);
	    TextView textView3 = (TextView) rowView.findViewById(R.id.noOfGuess);
	    TextView timeTakentxt = (TextView) rowView.findViewById(R.id.timeTakenTxt);
	    
	    String playData = values.get(position);
	    System.out.println("playData:: " + playData);
	    String[] playDataArray = playData.split("\\|");
	    System.out.println("playDataArray::: " + playDataArray);
	    textView3.setText(playDataArray[0]!=null?playDataArray[0]:"");
	    textView.setText(playDataArray[1]!=null?playDataArray[1]:"");
	    textView1.setText(playDataArray[2]!=null?playDataArray[2]:"");
	    textView2.setText(playDataArray[3]!=null?playDataArray[3]:"");
	    timeTakentxt.setText(playDataArray[4]!=null?playDataArray[4]:"");
	    return rowView;
	  }


}
