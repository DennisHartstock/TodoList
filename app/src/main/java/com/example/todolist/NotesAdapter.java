package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private ArrayList<Note> notes = new ArrayList<>();
    private OnNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public ArrayList<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false
        );
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        viewHolder.tvNote.setText(note.getText());

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

        int color = ContextCompat.getColor(viewHolder.itemView.getContext(), colorResId);
        viewHolder.tvNote.setBackgroundColor(color);

        viewHolder.itemView.setOnClickListener(view -> {
            if (onNoteClickListener != null) {
                onNoteClickListener.onClick(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNote = itemView.findViewById(R.id.tvNote);
        }
    }

    interface OnNoteClickListener {

        void onClick(Note note);
    }
}
