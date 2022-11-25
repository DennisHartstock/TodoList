package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private FloatingActionButton btAddNote;

    private final Database database = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

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
        llNotes.removeAllViews();

        for (Note note : database.getNotes()) {
            View view = getLayoutInflater().inflate(R.layout.note_item, llNotes, false);
            TextView tvNote = view.findViewById(R.id.tvNote);
            tvNote.setText(note.getText());

            view.setOnClickListener(view1 -> {
                database.remove(note.getId());
                showNotes();
            });

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