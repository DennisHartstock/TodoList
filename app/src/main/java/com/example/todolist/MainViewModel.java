package com.example.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final NoteDatabase noteDatabase;
    private int count = 0;
    private final MutableLiveData<Integer> countLD = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getInstance(application);
    }

    public LiveData<List<Note>> getNotes() {
        return noteDatabase.notesDao().getNotes();
    }

    public void showCount() {
        count++;
        countLD.setValue(count);
    }

    public LiveData<Integer> getCount() {
        return countLD;
    }

    public void remove(Note note) {
        new Thread(() -> noteDatabase.notesDao().remove(note.getId())).start();
    }
}
