package com.shaznee.simplenotes.activities;

import java.util.List;

import com.shaznee.simplenotes.R;
import com.shaznee.simplenotes.models.Note;
import com.shaznee.simplenotes.models.NotesDataSource;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainScreen extends ListActivity{
	
	private static final int EDITOR_ACTIVITY_REQUEST = 1001;
	private static final int MENU_DELETE_ID = 1002;
	private int currentNoteId;
	private NotesDataSource dataSource;
	List<Note> notes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		registerForContextMenu(getListView());
		dataSource = new NotesDataSource(this);	
		refreshDisplay();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (item.getItemId() == R.id.action_create) {
			createNote();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Note note = notes.get(position);
		Intent intent = new Intent(this, NoteEditor.class);
		intent.putExtra(Note.KEY, note.getKey());
		intent.putExtra(Note.TEXT, note.getText());
		startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
		
	}
	
	private void refreshDisplay() {
		notes = dataSource.findAll();
		ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this, 
				R.layout.list_layout_layout, notes);
		setListAdapter(adapter);
	}
		
	private void createNote() {
		Note note = Note.getNew();
		Intent intent = new Intent(this, NoteEditor.class);
		intent.putExtra(Note.KEY, note.getKey());
		intent.putExtra(Note.TEXT, note.getText());
		startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode ==EDITOR_ACTIVITY_REQUEST && resultCode == RESULT_OK) {
			Note note = new Note();
			note.setKey(data.getStringExtra("key"));
			note.setText(data.getStringExtra("text"));
			dataSource.update(note);
			refreshDisplay();
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		currentNoteId = (int) info.id;
		menu.add(0, MENU_DELETE_ID, 0, R.string.delete);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == MENU_DELETE_ID) {
			Note note = notes.get(currentNoteId);
			dataSource.remove(note);
			refreshDisplay();
		}
		return super.onContextItemSelected(item);
	}
}
