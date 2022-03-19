package com.home.recyclerviewapp.ui.editor;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.home.recyclerviewapp.R;
import com.home.recyclerviewapp.repository.NoteData;
import com.home.recyclerviewapp.ui.MainActivity;

import java.util.Calendar;


public class CardEditFragment extends Fragment {

    NoteData noteData;
    Calendar calendar;

    public static CardEditFragment newInstance(NoteData noteData) {
        CardEditFragment fragment = new CardEditFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("noteData", noteData);
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
            noteData = getArguments().getParcelable("noteData");
            ((EditText)view.findViewById(R.id.inputTitle)).setText(noteData.getTitle());
            ((EditText)view.findViewById(R.id.inputDescription)).setText(noteData.getDescription());

            //Создаем ДатаПикер
            calendar = Calendar.getInstance();
            calendar.setTime(noteData.getDate());
            ((DatePicker) view.findViewById(R.id.inputDate)).init(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH+1),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ((DatePicker) view.findViewById(R.id.inputDate)).setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                           calendar.set(Calendar.YEAR,year);
                           calendar.set(Calendar.MONTH,monthOfYear);
                           calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    }
                });
            }


            view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteData.setTitle(((EditText)view.findViewById(R.id.inputTitle)).getText().toString());
                    noteData.setDescription(((EditText)view.findViewById(R.id.inputDescription)).getText().toString());

//Старый метод использования DataPicker
//Задаем год, месяц, день
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                        DatePicker datePicker = view.findViewById(R.id.inputDate);
                        calendar.set(Calendar.YEAR,datePicker.getYear());
                        calendar.set(Calendar.MONTH,datePicker.getMonth());
                        calendar.set(Calendar.DAY_OF_MONTH,datePicker.getDayOfMonth());
                    }

                    noteData.setDate(calendar.getTime());
                    //Передаем сообщение Паблишеру что мы изменили карточку
                    ((MainActivity) requireActivity()).getPublisher().sendMessage(noteData);
                    //Закрываем фрагмент менеджер и сохраняем результат
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });

        }

    }

}
