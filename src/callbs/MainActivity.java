package callbs;

import itp341.deshpande.ameya.Final.project.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.CursorAdapter;

public class MainActivity extends Activity {
	String[] textarray;
	String[] keyarray;
	Hashtable<String, String> hashTable;
	private ArrayList<String> items;
	private ArrayList<String> keys;
	private final int REQ_CODE_SPEECH_INPUT = 100;
	private Menu menu;
	ImageButton btnSpeak;
	TextView metext;
	SearchView searchView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;
        textarray = getResources().getStringArray(R.array.text_exhaustive);
		keyarray = getResources().getStringArray(R.array.keys_exhaustive);
		metext = (TextView) findViewById(R.id.textid1);
		hashTable = new Hashtable<String, String>();
		for(int i = 0; i < textarray.length; i++) 
		{
			hashTable.put(textarray[i], keyarray[i]);
		}
		items = new ArrayList<String> (Arrays.asList(textarray));
		keys = new ArrayList<String> (Arrays.asList(keyarray));
		btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        Resources resources = context.getResources();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, resources.getStringArray(R.array.states));
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.locationspinner);
        textView.setAdapter(adapter); 
        btnSpeak.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				promptSpeechInput();
			}
		});       
    }

	@Override
	public boolean onSearchRequested() {
		// TODO Auto-generated method stub
		return super.onSearchRequested();
	}

	@Override
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		 if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			 	Bundle bundle = new Bundle();
			 	bundle.putSerializable("hashtable", hashTable);
			 	intent.putExtras(bundle);
		        intent.putStringArrayListExtra("keylist", keys);
		        intent.putStringArrayListExtra("textlist", (ArrayList<String>) items);
		    }
		super.startActivity(intent);	
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.example, menu);
		// Associate searchable configuration with the SearchView
	    SearchManager searchManager =
	           (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    searchView =
	            (SearchView) menu.findItem(R.id.search).getActionView();
	    searchView.setSearchableInfo(
	            searchManager.getSearchableInfo(getComponentName()));
	    searchView.setSubmitButtonEnabled (true);
	    return true;
	    
	}
		 
	private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
        }
    }


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
	    case REQ_CODE_SPEECH_INPUT: {
	        if (resultCode == RESULT_OK && null != data) {

	            ArrayList<String> result = data
	                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	            metext.setText(result.get(0));
	            searchView.setQuery(result.get(0), false);
	        }
	        break;
	    }

	    }
	}
}    
