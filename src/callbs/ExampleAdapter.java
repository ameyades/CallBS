package callbs;

import itp341.deshpande.ameya.Final.project.app.R;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExampleAdapter extends CursorAdapter{
	 private List<String> items;

	    private TextView text;

	    public ExampleAdapter(Context context, Cursor cursor, List<String> items) {

	        super(context, cursor, false);

	        this.items = items;

	    }

	    public void bindView(View view, Context context, Cursor cursor) {

	        text.setText(items.get(cursor.getPosition()));

	    }

	    public View newView(Context context, Cursor cursor, ViewGroup parent) {

	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	        View view = inflater.inflate(R.layout.item, parent, false);

	        text = (TextView) view.findViewById(R.id.search);

	        return view;

	    }
	
	
}
