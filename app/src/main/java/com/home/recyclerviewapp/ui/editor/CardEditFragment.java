package com.home.recyclerviewapp.ui.editor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.recyclerviewapp.R;
import com.home.recyclerviewapp.repository.CardData;
import com.home.recyclerviewapp.ui.MainActivity;


public class CardEditFragment extends Fragment {

    CardData cardData;

    public static CardEditFragment newInstance(CardData cardData) {
        CardEditFragment fragment = new CardEditFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("cardData", cardData);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_card_edit, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            cardData = getArguments().getParcelable("cardData");


            //Передаем сообщение Паблишеру что мы изменили карточку
            ((MainActivity) requireActivity()).getPublisher().sendMessage(cardData);
            //Говорим фрагментМенеджеру
            ((MainActivity)requireActivity()).getSupportFragmentManager().popBackStack();
        }
    }
}