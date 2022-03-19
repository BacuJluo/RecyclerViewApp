package com.home.recyclerviewapp.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.home.recyclerviewapp.R;
import com.home.recyclerviewapp.publisher.Observer;
import com.home.recyclerviewapp.repository.ColorIndexConverter;
import com.home.recyclerviewapp.repository.FireStoreImplementation;
import com.home.recyclerviewapp.repository.FireStoreResponse;
import com.home.recyclerviewapp.repository.LocalSharedPreferenceRepositoryImplementation;
import com.home.recyclerviewapp.repository.NoteData;
import com.home.recyclerviewapp.repository.NotesSource;
import com.home.recyclerviewapp.repository.LocalRepositoryImplementation;
import com.home.recyclerviewapp.ui.MainActivity;
import com.home.recyclerviewapp.ui.editor.CardEditFragment;

import java.util.Calendar;
import java.util.Random;

public class SocialNetworkFragment extends Fragment implements OnItemClickListener, FireStoreResponse {

    SocialNetworkAdapter socialNetworkAdapter;
    NotesSource data;
    RecyclerView recyclerView;
    private Observer observer;

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

                    observer = new Observer() {
                        @Override
                        public void receiveMessage(NoteData noteData) {
                            ((MainActivity) requireActivity()).getPublisher().unSubscribe(this);
                            data.addNoteData(noteData);
                            socialNetworkAdapter.notifyItemChanged(data.size()-1);
                            recyclerView.scrollToPosition(data.size() - 1);
                        }
                    };
                    ((MainActivity) requireActivity()).getPublisher().subscribe(observer);
                    ((MainActivity) requireActivity()).getNavigation().addFragment(CardEditFragment.newInstance(new NoteData("","",ColorIndexConverter.getColorByIndex(ColorIndexConverter.randomColorIndex()),false,Calendar.getInstance().getTime())),true);
                return true;
            }
            case (R.id.action_clear):{
                data.clearNotesData();
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
                    public void receiveMessage(NoteData noteData) {
                        ((MainActivity) requireActivity()).getPublisher().unSubscribe(this);
                        //noteData.setLike(true);
                        data.updateNoteData(menuPosition,noteData);
                        socialNetworkAdapter.notifyItemChanged(menuPosition);
                    }
                };
                ((MainActivity) requireActivity()).getPublisher().subscribe(observer);
                ((MainActivity) requireActivity()).getNavigation().addFragment(CardEditFragment.newInstance(data.getCardData(menuPosition)), true);
                return true;
            }
            case (R.id.action_delete):{
                data.deleteNoteData(menuPosition);
                socialNetworkAdapter.notifyItemRemoved(menuPosition);
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initAdapter();
        setupSource();
        initRecycler(view);
        setHasOptionsMenu(true);
        initRadioGroup(view);

        //Log.d("mylogs","getCurrentSource = "+getCurrentSource());

    }

    void setupSource (){
        switch (getCurrentSource()){
            case (SOURCE_ARRAY):{
                //и указываем отображение на кнопке
                data = new LocalRepositoryImplementation(requireContext().getResources()).init();
                initAdapter();
                break;
            }
            case (SOURCE_SP):{
                data = new LocalSharedPreferenceRepositoryImplementation(requireContext()
                        .getSharedPreferences(LocalSharedPreferenceRepositoryImplementation.KEY_SP_2, Context.MODE_PRIVATE))
                        .init();
                initAdapter();
                break;
            }
            case (SOURCE_GF):{
                data = new FireStoreImplementation().init(new FireStoreResponse() {
                    @Override
                    public void initialized(NotesSource notesSource) {
                        initAdapter();
                    }
                });
                initAdapter();
                break;
            }
        }

    }

    private void initRadioGroup(View view) {
        view.findViewById(R.id.sourceArrays).setOnClickListener(listener);
        view.findViewById(R.id.sourceSP).setOnClickListener(listener);
        view.findViewById(R.id.sourceGF).setOnClickListener(listener);

        //проверяем по какой из кнопок кликнули
        switch (getCurrentSource()){
            case (SOURCE_ARRAY):{
                //и указываем отображение на кнопке
                ((RadioButton)view.findViewById(R.id.sourceArrays)).setChecked(true);
                break;
            }
            case (SOURCE_SP):{
                ((RadioButton)view.findViewById(R.id.sourceSP)).setChecked(true);
                break;
            }
            case (SOURCE_GF):{
                ((RadioButton)view.findViewById(R.id.sourceGF)).setChecked(true);
                break;
            }
        }
    }

    static final int SOURCE_ARRAY = 1;
    static final int SOURCE_SP = 2;
    static final int SOURCE_GF = 3;

    static String KEY_SP_S1 = "key_one"; //ключ
    static String KEY_SP_S1_CELL_C1 = "s1_cell1";

    View.OnClickListener listener = new View.OnClickListener() {
        //устанавливаем кликлистнер на радиокнопки
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.sourceArrays:{
                    setCurrentSource(SOURCE_ARRAY);
                    break;
                }
                case R.id.sourceSP:
                    setCurrentSource(SOURCE_SP);
                    break;

                case R.id.sourceGF:{
                    setCurrentSource(SOURCE_GF);
                    break;
                }
            }
            setupSource();
        }
    };

    void setCurrentSource(int currentSource){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(KEY_SP_S1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SP_S1_CELL_C1, currentSource);
        editor.apply();
    }
    int getCurrentSource(){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(KEY_SP_S1, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_SP_S1_CELL_C1,SOURCE_ARRAY);
    }

    private void initAdapter() {
        if(socialNetworkAdapter == null) {
            socialNetworkAdapter = new SocialNetworkAdapter(this);
        }
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
        animator.setChangeDuration(2000);
        animator.setRemoveDuration(2000);
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


    @Override
    public void initialized(NotesSource notesSource) {
        initAdapter();
    }
}