package com.home.recyclerviewapp.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.home.recyclerviewapp.R;

public class Navigation {
    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    public void replaceFragment(Fragment fragment, boolean useBackStack) {
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        if (useBackStack) {
            fragmentTransaction.addToBackStack("");
        }
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }
    public void addFragment(Fragment fragment, boolean useBackStack) {
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment);
        if (useBackStack) {
            fragmentTransaction.addToBackStack("");
        }
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }
}
