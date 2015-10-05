package com.shaznee.simplenotes.activities;

import com.shaznee.simplenotes.R;
import com.shaznee.simplenotes.models.Note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

public class NoteEditor extends Activity {
	
	private Note note;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_editor);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = this.getIntent();
		note = new Note();
		note.setKey(intent.getExtras().getString("key"));
		note.setText(intent.getExtras().getString("text"));
		
		EditText txtNoteEditor = (EditText) findViewById(R.id.noteText);
		txtNoteEditor.setText(note.getText());
		txtNoteEditor.setSelection(note.getText().length());
		
	}
	
	private void saveAndFinish() {
		EditText txtNoteEditor = (EditText) findViewById(R.id.noteText);
		String noteText = txtNoteEditor.getText().toString();
		
		Intent intent = new Intent();
		intent.putExtra(Note.KEY, note.getKey());
		intent.putExtra(Note.TEXT, noteText);
		setResult(RESULT_OK, intent);
		finish();	
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			saveAndFinish();			
		}
		return false;
	}
	
	@Override
	public void onBackPressed() {
		saveAndFinish();
	}

}
