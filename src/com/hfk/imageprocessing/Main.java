package com.hfk.imageprocessing;

import android.os.Bundle;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;
import android.content.res.Resources;
import android.content.Intent;

import com.hfk.imageprocessing.blur.*;

public class Main extends ListActivity {
	
	public static final String FILTER_GROUP = "FilterGroup";
	public static final String FILTER_GROUP_BLUR = "FilterGroupBlur";
	public static final String FILTER_GROUP_MATHMORPH = "FilterGroupMathMorph";
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// Create an array of Strings, that will be put to our ListActivity
		Resources res = getResources();
		String[] names = res.getStringArray(R.array.filters_available);
		// Create an ArrayAdapter, that will actually make the Strings above
		// appear in the ListView
		this.setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, names));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Bundle bundle = new Bundle();
		if(position == 0)
			bundle.putString(FILTER_GROUP, FILTER_GROUP_BLUR);
		if(position == 1)
			bundle.putString(FILTER_GROUP, FILTER_GROUP_MATHMORPH);

		Intent myIntent = new Intent(Main.this, ImageProcessingActivity.class);
		//Intent myIntent = new Intent(Main.this, DrawableImageProcessingActivity.class);
		myIntent.putExtras(bundle);
		startActivity(myIntent);
	}
}
