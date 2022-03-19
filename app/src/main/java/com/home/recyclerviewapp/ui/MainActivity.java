package com.home.recyclerviewapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.home.recyclerviewapp.R;
import com.home.recyclerviewapp.publisher.Publisher;
import com.home.recyclerviewapp.ui.main.SocialNetworkFragment;

public class MainActivity extends AppCompatActivity {

    private Publisher publisher;
    private Navigation navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        publisher = new Publisher();
        navigation = new Navigation(getSupportFragmentManager());
        if (savedInstanceState ==null){
            //getSupportFragmentManager().beginTransaction().replace(R.id.container, SocialNetworkFragment.newInstance()).commit();
            navigation.replaceFragment(SocialNetworkFragment.newInstance(), false);
        }
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Navigation getNavigation() {
        return navigation;
    }

}