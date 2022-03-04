package com.home.recyclerviewapp.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.home.recyclerviewapp.R;
import com.home.recyclerviewapp.repository.CardData;
import com.home.recyclerviewapp.repository.CardsSource;
import com.home.recyclerviewapp.repository.LocalRepositoryImplementation;

public class SocialNetworkFragment extends Fragment implements OnItemClickListener {

    SocialNetworkAdapter socialNetworkAdapter;
    CardsSource data;

    public static SocialNetworkFragment newInstance() {
        SocialNetworkFragment fragment = new SocialNetworkFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social_network, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu );
        super.onCreateOptionsMenu(menu, inflater);

    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.action_add):{
                data.addCardData(new CardData("Заголовок новой карточки "+(data.size()+1),
                        "Описание новой карточки "+(data.size()+1), R.color.yellow, false));
                socialNetworkAdapter.notifyItemInserted(data.size()-1);
                return true;
            }
            case (R.id.action_clear):{
                data.clearCardsData();
                socialNetworkAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initRecycler(view);
        setHasOptionsMenu(true);


        //Для того чтобы работал наш RecyclerView нужно сделать 3 вещи
        //1.найти его в макете
        //2.прописать его ЛаяутМэнеджер 41 строка.
        //3.связать его со своим адаптером
        // и в адаптере должны быть данные.
    }

    private void initAdapter() {
        socialNetworkAdapter = new SocialNetworkAdapter();
        data = new LocalRepositoryImplementation(requireContext().getResources()).init();
        socialNetworkAdapter.setData(data);
        socialNetworkAdapter.setOnItemClickListener(SocialNetworkFragment.this);

    }

    void initRecycler(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager); //задали LinearLayoutManager
        recyclerView.setHasFixedSize(true); //мы можем указать эту команду recyclerView что у него все элементы фиксированного размера и это ускорит работу inflater'a по надуванию нашего макета, и он не будет измерять каждый элемент, и это ускорит его работу.
        recyclerView.setAdapter(socialNetworkAdapter);
    }

    public String[] getData() {
        String[] data = getResources().getStringArray(R.array.titles);
        return data;
    }

    @Override
    public void onItemClick(int position) {
        String[] data = getData();
        Toast.makeText(requireContext(), " Нажали на" + data[position], Toast.LENGTH_SHORT).show();
    }

}