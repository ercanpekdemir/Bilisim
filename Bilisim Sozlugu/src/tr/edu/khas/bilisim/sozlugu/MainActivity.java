package tr.edu.khas.bilisim.sozlugu;

import java.util.ArrayList;



import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.google.ads.*;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity implements OnClickListener {
	Button starButton;
	EditText et;
	private AdView adView;
	private TextView mTextView;
	private ListView mListView;
	MyCustomAdapter mAdapter;

	boolean buttonState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeVariables();

//		 adView = new AdView(this, AdSize.BANNER, "a151a0dab1dbc74");
//		 LinearLayout layout =(LinearLayout) findViewById(R.id.main);
//		 layout.addView(adView);
//		 adView.loadAd(new AdRequest());

		starButton.setOnClickListener(this);

		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				String query = s.toString();
				showResults(query);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() == 0)
					mTextView.setText("Use the search field to look up a word");

			}
		});
		et.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == event.KEYCODE_ENTER) {
					Uri uri = Uri.parse("http://www.bilisimsozlugu.net/"
							+ et.getText());
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				}
				return false;
			}
		});

		// et.setOnClickListener(this);
		mTextView.setOnClickListener(this);

	}

//	 @Override
//	 public void onDestroy() {
//	 if (adView != null) {
//	 adView.destroy();
//	 }
//	 super.onDestroy();
//	 }

	private void initializeVariables() {
		// TODO Auto-generated method stub
		starButton = (Button) findViewById(R.id.bStar);
		starButton.setBackgroundResource(R.drawable.star2);
		et = (EditText) findViewById(R.id.editText1);
		mTextView = (TextView) findViewById(R.id.text);
		mListView = (ListView) findViewById(R.id.list);

		// et.setSelection(3);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cool_menu, menu);

		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		super.onMenuItemSelected(featureId, item);

		switch (item.getItemId()) {
		case R.id.aboutUs:
			Intent i = new Intent("tr.edu.khas.bilisimsozlugu.ABOUTUS");
			startActivity(i);
			break;
		case R.id.help:
			Intent ii = new Intent("tr.edu.khas.bilisimsozlugu.HELP");
			startActivity(ii);
			break;
		case R.id.exit:
			finish();
			break;

		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bStar:
			et.clearFocus();
			mListView.requestFocus();
			if (!buttonState) {
				MyCustomAdapter c = new MyCustomAdapter();
				c.notifyDataSetChanged();
				mListView.setAdapter(c);
				mListView.invalidate();
				buttonState = true;
				starButton.setBackgroundResource(R.drawable.star1);
				showFavorites();
				mTextView.setText("Your starred items");

				Toast.makeText(getBaseContext(), "favorite mode",
						Toast.LENGTH_SHORT).show();
			} else {

				MyCustomAdapter c = new MyCustomAdapter();
				c.notifyDataSetChanged();
				mListView.setAdapter(c);
				mListView.invalidate();
				buttonState = false;
				starButton.setBackgroundResource(R.drawable.star2);
				showResults(et.getText().toString());
				mTextView.setText("Use the search box to look up a word");
				Toast.makeText(getBaseContext(), "search mode",
						Toast.LENGTH_SHORT).show();

			}
			break;

		default:
			break;
		}

	}

	private void showResults(String query) {
		Log.d("ss", query + " text");
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(DictionaryProvider.CONTENT_URI, null,
				null, new String[] { query }, null);// String[]{query}
													// et.getText;

		if (cursor == null) {

			// There are no results
			mTextView.setText("Press enter to searh on the internet");
			// //////////////////////////////////////////////////////////
			String[] from = new String[] { DictionaryDatabase.KEY_WORD,
					DictionaryDatabase.KEY_DEFINITION };
			int to[] = new int[] { R.id.word, R.id.definition };
			@SuppressWarnings("deprecation")
			SimpleCursorAdapter words = new SimpleCursorAdapter(this,
					R.layout.result, cursor, from, to);
			mListView.setAdapter(words);
			// mTextView.notify();
			// ////////////////////////////////////////////////////
		} else {
			// Display the number of results

			int count = cursor.getCount();
			String countString = getResources().getQuantityString(
					R.plurals.search_results, count,
					new Object[] { count, query });
			mTextView.setText(countString);

			// Specify the columns we want to display in the result
			String[] from = new String[] { DictionaryDatabase.KEY_WORD,
					DictionaryDatabase.KEY_DEFINITION };

			// Specify the corresponding layout elements where we want the
			// columns to go
			int[] to = new int[] { R.id.word, R.id.definition };

			// Create a simple cursor adapter for the definitions and apply them
			// to the ListView

			SimpleCursorAdapter words = new SimpleCursorAdapter(this,
					R.layout.result, cursor, from, to);
			mListView.setAdapter(words);

			// Define the on-click listener for the list items
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// Build the Intent used to open WordActivity with a
					// specific word Uri
					Intent wordIntent = new Intent(getApplicationContext(),
							WordActivity.class);
					Uri data = Uri.withAppendedPath(
							DictionaryProvider.CONTENT_URI, String.valueOf(id));
					wordIntent.setData(data);
					startActivity(wordIntent);
				}
			});
			mListView.invalidate();
		}
	}

	@Override
	public boolean onKeyShortcut(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		return super.onKeyShortcut(keyCode, event);
	}

	private void showFavorites() {
		mAdapter = new MyCustomAdapter();
		mAdapter.mData = StaredItems.getStaredItems(getApplicationContext()).items;
		mListView.setAdapter(mAdapter);
	}

	private class MyCustomAdapter extends BaseAdapter {

		public ArrayList<WordItem> mData = new ArrayList<WordItem>();
		private LayoutInflater mInflater;

		public MyCustomAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void addItem(final String item) {

			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public WordItem getItem(int position) {
			return (WordItem) mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.fav_result, null);
			}
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent wordIntent = new Intent(getApplicationContext(),
							WordActivity.class);
					wordIntent.putExtra("key", mData.get(position).key);
					wordIntent.putExtra("def", mData.get(position).def);
					startActivity(wordIntent);
				}
			});

			Button delete = (Button) convertView.findViewById(R.id.delete);
			delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StaredItems.deleteWord(getApplicationContext(),
							mData.get(position));
					showFavorites();
					Toast.makeText(getBaseContext(), "Favorilerden silindi.",
							Toast.LENGTH_SHORT).show();
				}
			});

			TextView w = (TextView) convertView.findViewById(R.id.word);
			TextView d = (TextView) convertView.findViewById(R.id.definition);
			w.setText(mData.get(position).key);
			d.setText(mData.get(position).def);

			return convertView;
		}

	}

}
