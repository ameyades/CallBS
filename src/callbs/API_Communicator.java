package callbs;

import itp341.deshpande.ameya.Final.project.app.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.util.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.google.gson.annotations.SerializedName;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;




public class API_Communicator extends Activity{
	String SeriesName;
	String testoutput;
	String text2;
	String dataname;
	List<DataPoint> donedata;
	Boolean lock = false;
	GraphView graphView;
	
	class RestError {
	    @SerializedName("code")
	    public int code;
	    @SerializedName("error")
	    public String errorDetails;
	}
	
	public String loadJSONFromAsset(String filename) {
		String json = null;
		try {
											   
            InputStream is = getAssets().open(filename + ".json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
		
	}
	
	public List<DataPoint> readReqString(Cluster cl) 
	{
		List<DataPoint> datacollection = new ArrayList<DataPoint>();
	    JSONObject Results = cl.getResults();	    
	    JSONArray series;
		try {
			series = Results.getJSONArray("series");
			JSONObject clump = series.getJSONObject(0);
		    JSONArray data = clump.getJSONArray("data");
		    for(int h = 0; h < data.length(); h++)
		    {
		    	 String year = data.getJSONObject(h).getString("year");
		    	 int numyear = Integer.parseInt(year);
		    	 String rawperiod = data.getJSONObject(h).getString("period");
		    	 String value = data.getJSONObject(h).getString("value");
		    	 Double numvalue = Double.parseDouble(value);
		    	 String periodtype = rawperiod.substring(0, 1);
		         int period = Integer.parseInt(rawperiod.substring(1, rawperiod.length()));
		         datacollection.add(new DataPoint(numyear, period, periodtype, numvalue));
		    }
		    return datacollection;    
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Your request FAILED. The BLS only lets you access its data 25 times a day. Try again tomorrow?", Toast.LENGTH_LONG).show();
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datacollection;
	    
	}
	
	public List<DataPoint> readFileStream(String str) {
		List<DataPoint> datacollection = new ArrayList<DataPoint>();
		try {
				JSONObject object = new JSONObject(str);
				JSONObject Results = object.getJSONObject("Results");
				JSONArray series = Results.getJSONArray("series");
				JSONObject clump = series.getJSONObject(0);
				JSONArray data = clump.getJSONArray("data");  
			    for(int h = 0; h < data.length(); h++)
			    {
			    	 String year = data.getJSONObject(h).getString("year");
			    	 int numyear = Integer.parseInt(year);
			    	 String rawperiod = data.getJSONObject(h).getString("period");
			    	 String value = data.getJSONObject(h).getString("value");
			    	 Double numvalue = Double.parseDouble(value);
			    	 String periodtype = rawperiod.substring(0, 1);
			         int period = Integer.parseInt(rawperiod.substring(1, rawperiod.length()));
			         datacollection.add(new DataPoint(numyear, period, periodtype, numvalue));
			    }
			    return datacollection; 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datacollection;
		
	}
	
	public  List<DataPoint> readJsonStream (InputStream in) throws IOException {
		//JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		StringBuffer total = new StringBuffer();
		String line = "";
		while ((line = r.readLine()) != null) {
		    total.append(line);
		}
		testoutput = line;
		return null;
	}
	
	public List<DataPoint> readDataArray(JsonReader reader) throws IOException {
	     List<DataPoint> dataraw = new ArrayList<DataPoint>();

	     reader.beginArray();
	     while (reader.hasNext()) {
	       dataraw.add(readData(reader));
	      }
	      reader.endArray();
	      return dataraw;
    }
	
	public DataPoint readData(JsonReader reader) throws IOException {
	     int year = 0;
	     String rawperiod;
	     int period = 0;
	     String periodtype = null;
	     Double value = null;
	     reader.beginObject();
	     while (reader.hasNext()) {
	       String name = reader.nextName();
	       if (name.equals("year")) {
	         year = reader.nextInt();
	       } else if (name.equals("period")) {
	         rawperiod = reader.nextString();
	         periodtype = rawperiod.substring(0, 1);
	         period = Integer.parseInt(rawperiod.substring(1, rawperiod.length()));
	       }  else if (name.equals("value")) {
	          value = reader.nextDouble();
	       } else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     return new DataPoint(year, period, periodtype, value);
	}	
	String APIKEY;
	InputStream variablefoo;
	Handler handler;
	private static java.util.Scanner scanner;
	public static String convertStreamToString(java.io.InputStream is) {
	    scanner = new java.util.Scanner(is);
		java.util.Scanner s = scanner.useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	
	public void GraphData(List<DataPoint> some)
	{
		 String months[] =	{
		         "January" , "February" , "March" , "April", "May",
		        "June", "July", "August", "September", "October",
		        "November", "December", "January" , "February" , "March" , "April", "May",
		        "June", "July", "August", "September", "October",
		        "November", "December", "January" , "February" , "March" , "April", "May",
		        "June", "July", "August", "September", "October",
		        "November", "December"
		    };
    
		GraphViewData[] data = new GraphViewData[some.size()];
		try
		{
		if(some.get(0).getPeriodtype().equals("M"))
		{
			graphView = new LineGraphView(
				    this // context
				    , dataname +  " /Months,2012 - 2014" // heading
				);
		}
		else
		{
			graphView = new LineGraphView(
				    this // context
				    , dataname +  "/Days,2012 - 2014" // heading
				);
		}
		for(int i = 0; i < some.size(); i++)
		{
			int place = some.get(i).getPeriod();
			double value = some.get(some.size() - i - 1).getValue();
			data[i] = new GraphViewData(i, value);
		}
		GraphViewSeries series = new GraphViewSeries(data);
		graphView.addSeries(series);
		graphView.setScrollable(true);
		LinearLayout layout = (LinearLayout) findViewById(R.id.graphplace);
		layout.addView(graphView);
		}
		catch(Exception e) {
			Toast.makeText(getApplicationContext(), "Your request FAILED. The data repository probably has been taken off the internet. Try another option?", Toast.LENGTH_LONG).show();
			API_Communicator.this.finish(); 
		}
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		API_Communicator.this.finish(); 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graphview);
		List<DataPoint> retrieved;
		handler = new Handler();
		Bundle bundle;
		Intent i = getIntent();
		bundle = i.getExtras();
		APIKEY = (String) bundle.getString("FINALKEY");
		String uri = "http://api.bls.gov/publicAPI/v2/timeseries/data/";
		uri += APIKEY;
		String httpstring = "";
		try {
			dataname = (String) bundle.getString("FINALNAME");
			httpstring =  new RequestTask().execute(uri).get();
			retrieved = readFileStream(httpstring);
	     	GraphData(retrieved);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "Your request FAILED. The data repository probably has been taken off the internet. Try another option?", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "Your request FAILED. The data repository probably has been taken off the internet. Try another option?", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}	
	}

	@Override
	public View onCreatePanelView(int featureId) {
		// TODO Auto-generated method stub
		return super.onCreatePanelView(featureId);
	}
}
