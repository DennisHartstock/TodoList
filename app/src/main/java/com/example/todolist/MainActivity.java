package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private FloatingActionButton btAddNote;
    private NotesAdapter notesAdapter;

    private NoteDatabase noteDatabase;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDatabase = NoteDatabase.getInstance(getApplication());
        initViews();

        notesAdapter = new NotesAdapter();

        notesAdapter.setOnNoteClickListener(note -> {

        });

        rvNotes.setAdapter(notesAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
                ) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target
                    ) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Note note = notesAdapter.getNotes().get(position);

                        Thread thread = new Thread(() -> {
                            noteDatabase.notesDao().remove(note.getId());
                            handler.post(() -> showNotes());
                        });
                        thread.start();
                    }
                });
        itemTouchHelper.attachToRecyclerView(rvNotes);

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
        Thread thread = new Thread(() -> {
            List<Note> notes = noteDatabase.notesDao().getNotes();
            handler.post(() -> notesAdapter.setNotes(notes));
        });
        thread.start();
    }
}