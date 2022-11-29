package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddNoteActivity extends AppCompatActivity {

    private AddNoteViewModel addNoteViewModel;

    private EditText etNote;
    private RadioButton rbLow;
    private RadioButton rbMedium;
    private Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        addNoteViewModel.getShouldCloseScreen().observe(this, shouldClose -> {

            if (shouldClose) {
                finish();
            }
        });
        initViews();

        btSave.setOnClickListener(view -> {
            saveNote();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void saveNote() {
        if (etNote == null) {
            Toast.makeText(this, "Enter new note", Toast.LENGTH_SHORT).show();
        } else {
            String text = etNote.getText().toString().trim();
            int priority = getPriority();

            Note note = new Note(text, priority);
            addNoteViewModel.saveNote(note);
        }
    }

    private int getPriority() {
        int priority;

        if (rbLow.isChecked()) {
            priority = 0;
        } else if (rbMedium.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }

    private void initViews() {
        etNote = findViewById(R.id.etNote);
        rbLow = findViewById(R.id.rbLow);
        rbMedium = findViewById(R.id.rbMedium);
        btSave = findViewById(R.id.btSave);
    }
}