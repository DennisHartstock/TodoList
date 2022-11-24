package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinearLayout llNotes;
    private FloatingActionButton btAddNote;

    private ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Note note = new Note(i, "Note " + i, random.nextInt(3));
            notes.add(note);
        }
        showNotes();

        btAddNote.setOnClickListener(view -> {
            Intent intent = AddNoteActivity.newIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    private void initViews() {
        llNotes = findViewById(R.id.llNotes);
        btAddNote = findViewById(R.id.btAddNote);
    }

    private void showNotes() {

        for (Note note : notes) {
            View view = getLayoutInflater().inflate(R.layout.note_item, llNotes, false);
            TextView tvNote = view.findViewById(R.id.tvNote);
            tvNote.setText(note.getText());

            int colorResId;
            switch (note.getPriority()) {
                case 0:
                    colorResId = android.R.color.holo_green_light;
                    break;
                case 1:
                    colorResId = android.R.color.holo_orange_light;
                    break;
                case 2:
                    colorResId = android.R.color.holo_red_light;
                    break;
                default:
                    colorResId = R.color.teal_200;
            }

            int color = ContextCompat.getColor(this, colorResId);
            tvNote.setBackgroundColor(color);

            llNotes.addView(view);
        }
    }
}