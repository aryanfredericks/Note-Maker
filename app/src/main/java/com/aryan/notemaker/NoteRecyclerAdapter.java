package com.aryan.notemaker;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.util.ArrayList;
public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<NoteEntity> arrayList;

    public NoteRecyclerAdapter(Context context, ArrayList<NoteEntity> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.note_card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.card_title.setText(arrayList.get(position).title);
        holder.card_desc.setText(arrayList.get(position).desc);

        holder.card.setOnClickListener(v->{
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.activity_update_note);
            TextView inputTitle = dialog.findViewById(R.id.inputTitle);
            TextView inputDesc = dialog.findViewById(R.id.inputDesc);
            inputTitle.setText(arrayList.get(position).title);
            inputDesc.setText(arrayList.get(position).desc);
            ImageView createNote = dialog.findViewById(R.id.createNote);
            ImageView backBtn = dialog.findViewById(R.id.backBtn);
            backBtn.setOnClickListener(nsdb->{
                dialog.cancel();
            });
            createNote.setOnClickListener(sd->{
                if (inputTitle.getText().toString().isEmpty()||inputDesc.getText().toString().isEmpty()){
                    Toast.makeText(context,"Invalid Input",Toast.LENGTH_SHORT).show();
                }
                else{
                    NoteDatabase db = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class,"my-database").allowMainThreadQueries().build();
                    NotesDao notesDao = db.dao();
                    NoteEntity newNote = new NoteEntity(
                            Integer.parseInt(String.valueOf(arrayList.get(position).id)),
                            inputTitle.getText().toString(),
                            inputDesc.getText().toString()
                    );
                    notesDao.updateNote(newNote);
                    arrayList.set(position,newNote);
                    notifyItemChanged(position);
                }
                dialog.dismiss();
            });
            dialog.show();
        });

        holder.card.setOnLongClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Deleting Note")
                    .setMessage("Confirm Delete??")
                    .setIcon(R.drawable.delete_icon)
                    .setPositiveButton("Yes" , (dialog, which) -> {
                        NoteDatabase db = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class,"my-database").allowMainThreadQueries().build();
                        NotesDao notesDao = db.dao();
                        int id = Integer.parseInt(String.valueOf(arrayList.get(position).id));
                        notesDao.deleteNote(id);
                        arrayList.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("No",(dialog, which) -> {

                    });
            alert.show();
            Toast.makeText(context, "Removed Note", Toast.LENGTH_SHORT).show();
            return false;
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout card;
        TextView card_title,card_desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            card_title = itemView.findViewById(R.id.card_title);
            card_desc = itemView.findViewById(R.id.card_desc);
        }
    }
}
