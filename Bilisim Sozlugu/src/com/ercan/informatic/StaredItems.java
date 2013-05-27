package com.ercan.informatic;

import java.util.ArrayList;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;


public class StaredItems {
	public ArrayList<WordItem> items=new ArrayList<WordItem>();
	public StaredItems(Context context)
	{
	}
	public StaredItems()
	{
		
	}
	
	public static StaredItems getStaredItems(Context context)
	{
		SharedPreferences pref=context.getSharedPreferences("prefs", 0);
		String items=pref.getString("StaredItems", null);
		
		if(items!=null)
		{
			return  fromJson(items);
		}
		return new StaredItems();
	}
	
	public static void AddWord(Context context,WordItem w) 
	{
		SharedPreferences pref=context.getSharedPreferences("prefs", 0);
		StaredItems std=getStaredItems( context);
		if(std.items==null)
			std.items=new ArrayList<WordItem>();
		
		for(int i=0;i<std.items.size();i++)
		{
			WordItem w1 =std.items.get(i);
			
			if(w1.key.equals(w.key))
			{
				Toast.makeText(context,"Already in the favorites !", 
		                Toast.LENGTH_SHORT).show();
				return;
			}
		}

		Toast.makeText(context,"Added to favorites", 
                Toast.LENGTH_SHORT).show();
		
		std.items.add(w);
		String itemTo=new Gson().toJson(std);
	
		Editor editor=pref.edit();

		editor.putString("StaredItems", itemTo);
		editor.commit();
	}
	public static void deleteWord(Context context,WordItem w)
	{
		SharedPreferences pref=context.getSharedPreferences("prefs", 0);
		StaredItems std=getStaredItems( context);
		if(std.items==null)
			std.items=new ArrayList<WordItem>();
		
		for(int i=0;i<std.items.size();i++)
		{
			WordItem w1 =std.items.get(i);
			
			if(w1.key.equals(w.key))
			{
				std.items.remove(i);
				Toast.makeText(context,"Deleted from favorites", 
		                Toast.LENGTH_SHORT).show();
				break;
			}
		}
		String itemTo=new Gson().toJson(std);//starred items array list
		
		Editor editor=pref.edit();
		editor.putString("StaredItems", itemTo);
		editor.commit();
	}
	public static StaredItems fromJson(String ddd)
	{
		return new Gson().fromJson(ddd, StaredItems.class);
	}

}
