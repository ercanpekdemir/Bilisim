package com.ercan.informatic;



import android.app.Activity;
import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.ads.*;
/**
 * Displays a word and its definition.
 */
public class WordActivity extends Activity {
	Button favor, back;
	String key,def;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.word);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        favor=(Button)findViewById(R.id.favor);
        favor.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
					StaredItems.AddWord(getApplicationContext(), new WordItem(key,def));
					
			
			}
		});

        if(getIntent().getExtras()!=null)
        {
	        key=getIntent().getExtras().getString("key");
	        def=getIntent().getExtras().getString("def");
        }
        if(key!=null)
        {    
            TextView word = (TextView) findViewById(R.id.word);
            TextView definition = (TextView) findViewById(R.id.definition);
        		word.setText(key);
        		definition.setText(def);
        	
        }
        else
        {
	        Uri uri = getIntent().getData();
	        @SuppressWarnings("deprecation")
			Cursor cursor = managedQuery(uri, null, null, null, null);
	
	        if (cursor == null) {
	            finish();
	        } else {
	            cursor.moveToFirst();
	
	            TextView word = (TextView) findViewById(R.id.word);
	            TextView definition = (TextView) findViewById(R.id.definition);
	
	            int wIndex = cursor.getColumnIndexOrThrow(DictionaryDatabase.KEY_WORD);
	            int dIndex = cursor.getColumnIndexOrThrow(DictionaryDatabase.KEY_DEFINITION);
	
	            key=cursor.getString(wIndex);
	            def=cursor.getString(dIndex);
	            word.setText(key);
	            definition.setText(def);
	        }
        }
    }


}
