package com.home.recyclerviewapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.home.recyclerviewapp.R;
import com.home.recyclerviewapp.publisher.Observer;
import com.home.recyclerviewapp.repository.CardData;
import com.home.recyclerviewapp.repository.CardsSource;
import com.home.recyclerviewapp.repository.LocalRepositoryImplementation;
import com.home.recyclerviewapp.ui.MainActivity;
import com.home.recyclerviewapp.ui.editor.CardEditFragment;

import java.util.Calendar;

public class SocialNetworkFragment extends Fragment implements OnItemClickListener {

    SocialNetworkAdapter socialNetworkAdapter;
    CardsSource data;
    RecyclerView recyclerView;

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.action_add):{
                data.addCardData(new CardData("Заголовок новой карточки "+(data.size()+1),
                        "Описание новой карточки "+(data.size()+1), data.getCardData(socialNetworkAdapter
                        .getMenuPosition()).getColors(), false, Calendar.getInstance().getTime()));
                socialNetworkAdapter.notifyItemInserted(data.size()-1);
                recyclerView.smoothScrollToPosition(data.size()-1);
                //recyclerView.scrollToPosition(data.size()-1); //Почему-то не работает(!)
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
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int menuPosition = socialNetworkAdapter.getMenuPosition(); //получаем menuPosition из адаптера
        switch (item.getItemId()){
            case (R.id.action_update):{

                //Создаем Колбэк
                Observer observer = new Observer() {
                    @Override
                    public void receiveMessage(CardData cardData) {
                        ((MainActivity) requireActivity()).getPublisher().unSubscribe(this);
                        cardData.setLike(true);
                        data.updateCardData(menuPosition,cardData);
                        socialNetworkAdapter.notifyItemChanged(menuPosition);
                    }
                };
                ((MainActivity) requireActivity()).getPublisher().subscribe(observer);
                ((MainActivity) requireActivity()).getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, CardEditFragment.newInstance(data.getCardData(menuPosition))).addToBackStack("").commit();
                return true;
            }
            case (R.id.action_delete):{
                data.deleteCardData(menuPosition);
                socialNetworkAdapter.notifyItemRemoved(menuPosition);
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initRecycler(view);
        setHasOptionsMenu(true);

    }

    private void initAdapter() {
        socialNetworkAdapter = new SocialNetworkAdapter(this);
        data = new LocalRepositoryImplementation(requireContext().getResources()).init();
        socialNetworkAdapter.setData(data);
        socialNetworkAdapter.setOnItemClickListener(SocialNetworkFragment.this);

    }

    void initRecycler(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager); //задали LinearLayoutManager
        recyclerView.setHasFixedSize(true); //мы можем указать эту команду recyclerView что у него все элементы фиксированного размера и это ускорит работу inflater'a по надуванию нашего макета, и он не будет измерять каждый элемент, и это ускорит его работу.
        recyclerView.setAdapter(socialNetworkAdapter);

        //Поверхностная работа с Animator
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setChangeDuration(5000);
        animator.setRemoveDuration(5000);
        recyclerView.setItemAnimator(animator);

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