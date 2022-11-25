package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private FloatingActionButton btAddNote;
    private NotesAdapter notesAdapter;

    private final Database database = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        notesAdapter = new NotesAdapter();
        rvNotes.setAdapter(notesAdapter);

        btAddNote.setOnClickListener(view -> {
            Intent intent = AddNoteActivity.newIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        showNotes();
    }

    private void initViews() {
        rvNotes = findViewById(R.id.rvNotes);
        btAddNote = findViewById(R.id.btAddNote);
    }

    private void showNotes() {
        notesAdapter.setNotes(database.getNotes());
    }
}