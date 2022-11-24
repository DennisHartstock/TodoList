package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private Database database = Database.getInstance();

    private EditText etNote;
    private RadioButton rbLow;
    private RadioButton rbMedium;
    private Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
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
            int id = database.getNotes().size();
            String text = etNote.getText().toString().trim();
            int priority = getPriority();

            Note note = new Note(id, text, priority);
            database.add(note);

            finish();
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