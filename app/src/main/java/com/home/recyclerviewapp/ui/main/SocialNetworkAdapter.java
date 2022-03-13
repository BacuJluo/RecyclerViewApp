package com.home.recyclerviewapp.ui.main;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.home.recyclerviewapp.R;
import com.home.recyclerviewapp.repository.NoteData;
import com.home.recyclerviewapp.repository.NotesSource;

import java.util.Random;


public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.MyViewHolder> {

    private NotesSource notesSource;

    OnItemClickListener onItemClickListener;

    Fragment fragment;

    private int menuPosition;

    public int getMenuPosition() {
        return menuPosition;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(NotesSource notesSource) {
        this.notesSource = notesSource;
        notifyDataSetChanged(); //команда адаптеру отрисовать все (абсолютно все) полученные данные
    }

    SocialNetworkAdapter() {

    }
    SocialNetworkAdapter(NotesSource notesSource) {
        this.notesSource = notesSource;
    }
    SocialNetworkAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
         return new MyViewHolder(layoutInflater.inflate(R.layout.fragment_social_network_cardview_item, parent, false)); }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindContentWithLayout(notesSource.getCardData(position));
    }

    @Override
    public int getItemCount() {
        return notesSource.size();
    }

    //класс который более нигде не будет использоваться его можно делать внутри другого класса.
    class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView textViewTitle;
        private final TextView textViewDescription;
        private final ToggleButton like;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle =  itemView.findViewById(R.id.title);
            textViewDescription =  itemView.findViewById(R.id.description);
            like =  itemView.findViewById(R.id.like);
            fragment.registerForContextMenu(itemView);


//          нужно было найти menuPosition, поэтому пришлось пользоваться itemView и делать для него кликЛистенер
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    menuPosition = getLayoutPosition();
                    v.showContextMenu(); //для отображения как контекстное Меню. И ставим значение true для отображения
                    return true;
            //по умолчанию идет True..
            //когда выбираем клик по itemView нужно заменять на false..
                }
            });

        }

        //связываем контент с макетом
        public void bindContentWithLayout(NoteData content){
            textViewTitle.setText(content.getTitle());
            textViewDescription.setText(content.getDescription()+"\n\n"+content.getDate());
            like.setChecked(content.isLike());
            //textViewTitle.setBackgroundResource(content.getColors());
            textViewTitle.setBackgroundTintMode(PorterDuff.Mode.ADD);
            textViewTitle.setBackgroundColor(fragment.getResources().getColor(content.getColors(),fragment.requireActivity().getTheme()));

        }


    }


}
