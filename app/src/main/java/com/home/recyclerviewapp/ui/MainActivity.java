package com.home.recyclerviewapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.home.recyclerviewapp.R;
import com.home.recyclerviewapp.publisher.Publisher;
import com.home.recyclerviewapp.ui.main.SocialNetworkFragment;

public class MainActivity extends AppCompatActivity {

    private Publisher publisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        publisher = new Publisher();
        if (savedInstanceState ==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, SocialNetworkFragment.newInstance()).commit();
        }
    }

    public Publisher getPublisher() {
        return publisher;
    }

}