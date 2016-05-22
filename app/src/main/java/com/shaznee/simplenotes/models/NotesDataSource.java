package com.shaznee.simplenotes.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.content.SharedPreferences;

public class NotesDataSource {
	
	private static final String PREFKEY = "notes";
	private SharedPreferences notePrefs;
	
	public NotesDataSource(Context context) {
		notePrefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
	}

	public List<Note> findAll() {
		
		Map<String, ?> notesMap = notePrefs.getAll();
		SortedSet<String> keys = new TreeSet<String>(notesMap.keySet());
		List<Note> notes = new ArrayList<Note>();
		for (String key : keys) {
			Note note = new Note();
			note.setKey(key);
			note.setText((String) notesMap.get(key));
			notes.add(note);
		}		
		return notes;
		
	}
	
	public boolean update(Note note) {
		SharedPreferences.Editor editor = notePrefs.edit();
		editor.putString(note.getKey(), note.getText());
		editor.commit();
		return true;	
	}
	
	public boolean remove(Note note) {
		
		if (notePrefs.contains(note.getKey())) {
			SharedPreferences.Editor editor = notePrefs.edit();
			editor.remove(note.getKey());
			editor.commit();
		}
		return true;
	}
}
