package com.home.recyclerviewapp.ui;


import static android.view.View.inflate;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.home.recyclerviewapp.R;
import com.home.recyclerviewapp.repository.CardData;
import com.home.recyclerviewapp.repository.CardSource;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.MyViewHolder> {

    private CardSource cardSource;

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(CardSource cardSource) {
        this.cardSource = cardSource;
        notifyDataSetChanged(); //команда адаптеру отрисовать все (абсолютно все) полученные данные
    }

    SocialNetworkAdapter(CardSource cardSource) {
        this.cardSource = cardSource;
    }

    SocialNetworkAdapter() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
         return new MyViewHolder(layoutInflater.inflate(R.layout.fragment_social_network_cardview_item, parent, false)); }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindContentWithLayout(cardSource.getCardData(position));
    }

    @Override
    public int getItemCount() {
        return cardSource.size();
    }

    //класс который более нигде не будет использоваться его можно делать внутри другого класса.
    class MyViewHolder extends RecyclerView.ViewHolder{

        private EditText textViewTitle;
        private EditText textViewDescription;
        private ToggleButton like;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle =  itemView.findViewById(R.id.title);
            textViewDescription =  itemView.findViewById(R.id.description);
            like =  itemView.findViewById(R.id.like);

        }
        //связываем контент с макетом
        public void bindContentWithLayout(CardData content){
            textViewTitle.setText(content.getTitle());
            textViewDescription.setText(content.getDescription());
            like.setChecked(content.isLike());
            textViewDescription.setBackgroundResource(content.getColors());

        }


    }


}
