package callbs;

import itp341.deshpande.ameya.Final.project.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultsActivity extends Activity {
	public  MergeSort Mergesort;
	TextView text;
	TextView text2;
	Button bestbutton;
    class String_Distance
	{
    	private String mystring;
    	private int distance;
		public String_Distance(String mystr, int dist)
		{
			setMystring(mystr);
			setDistance(dist);
		}
		public int getDistance() {
			return distance;
		}
		public void setDistance(int distance) {
			this.distance = distance;
		}
		public String getMystring() {
			return mystring;
		}
		public void setMystring(String mystring) {
			this.mystring = mystring;
		}
		
	}
	Hashtable<String, String> localhash;
	ArrayList<String> locallib;
	ArrayList<String> localkeys;
	ArrayList<String> actualchoices;
	ArrayList<String> actualnames;
	ArrayList<String> localist;
	ArrayList<String_Distance> localDists;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.selector_list);
	        handleIntent(getIntent());
	    }

	    @Override
	    protected void onNewIntent(Intent intent) {
	    	super.onNewIntent(intent);  
	    	setIntent(intent);
	        handleIntent(intent);
	    }

	    private void handleIntent(Intent intent) {
	    	localDists = new ArrayList<String_Distance>();
	        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	        	DamerauLevenshteinAlgorithm algo = new DamerauLevenshteinAlgorithm(1, 1, 1, 1);
	        	String query = intent.getStringExtra(SearchManager.QUERY);
	        	localhash = new Hashtable<String, String>();
	            actualchoices = getIntent().getStringArrayListExtra("keylist");
	            actualnames = getIntent().getStringArrayListExtra("textlist");
	            ListView choicesList = (ListView)findViewById(R.id.selection_list);
	            locallib = intent.getStringArrayListExtra("textlist");
	            localkeys = intent.getStringArrayListExtra("keylist");
	            localist = new ArrayList<String> ();
	            for(int kj = 0; kj < locallib.size(); kj++)
	            {
	              localhash.put(locallib.get(kj), localkeys.get(kj));
	              localist.add(locallib.get(kj));
	            }
	            for(int j = 0; j < actualnames.size(); j++)
	            {	
	            	int dist =  algo.execute(actualnames.get(j), query);
	            	String_Distance d = new String_Distance(locallib.get(j), dist);
	            	localDists.add(d);
	            }
	          	Hashtable<Integer, String> hackhash = new Hashtable<Integer, String>();
	          	for(int v = 0; v < localDists.size(); v++)
	          	{
	          		hackhash.put(localDists.get(v).getDistance(), localDists.get(v).getMystring());
	          	} 
	          	Integer mindistance = 100000000;
	          	for(int xx = 0; xx < localDists.size(); xx++)
	          	{
	          		if(mindistance > localDists.get(xx).getDistance())
	          		{
	          			mindistance = localDists.get(xx).getDistance();
	          		}
	          	}
	          	bestbutton = (Button) findViewById(R.id.best_result);
	         	bestbutton.setText("Best fit:" + hackhash.get(mindistance));	
	        //    Toast.makeText(getApplicationContext(), "Best fit is " + hackhash.get(mindistance), Toast.LENGTH_SHORT).show();         	
	            ArrayAdapter<String> arrayAdpt= new ArrayAdapter<String>(this, 
	            		android.R.layout.simple_list_item_2, android.R.id.text1, actualchoices){
							@Override
							public View getView(int position, View convertView,
									ViewGroup parent) {
								// TODO Auto-generated method stub
								    View view = super.getView(position, convertView, parent);
								    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
								    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
								    text1.setText(actualnames.get(position));
								    text2.setText(actualchoices.get(position));
								    return view;
							}
	            		
	            };
	            choicesList.setAdapter(arrayAdpt);
	            bestbutton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {			
						int loc = 0;
						String[] parts = ((String) bestbutton.getText()).split(":");
						for(int c = 0; c < actualnames.size(); c++)
						{
							if((actualnames.get(c)).equals(parts[1]))
							{
								loc = c;
							}
						}
						// TODO Auto-generated method stub
						 	Bundle bundle=new Bundle();
		    	         	bundle.putString("FINALNAME", actualnames.get(loc));
		    	         	bundle.putString("FINALKEY", actualchoices.get(loc));
		    	         //	Toast.makeText(getApplicationContext(), "FINALKEY " + actualnames.get(loc) + " FINALNAME" + actualchoices.get(loc), Toast.LENGTH_LONG).show();
		    	         	Intent j = new Intent(getApplicationContext(), API_Communicator.class);
		    	         	j.putExtras(bundle);
		    	            startActivity(j);
		    	            SearchResultsActivity.this.finish();
					}
	          	});
	            
	            choicesList.setOnItemClickListener(new OnItemClickListener(){
	             
	            	@Override
	                public void onItemClick(AdapterView<?> parent, View v, int position, long id)
	                {
	                    Bundle bundle=new Bundle();
	             //       Toast.makeText(getApplicationContext(), "FINALKEY " + actualnames.get(position) + " FINALNAME" + actualchoices.get(position), Toast.LENGTH_LONG).show();
	    	         bundle.putString("FINALNAME", actualnames.get(position));
	    	         	bundle.putString("FINALKEY", actualchoices.get(position));
	    	         	Intent j = new Intent(getApplicationContext(), API_Communicator.class);
	    	         	j.putExtras(bundle);
	    	            startActivity(j);
	    	            SearchResultsActivity.this.finish();
	            		
	                }

				
	            });
	        }
	    }	
}
