package com.home.recyclerviewapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.home.recyclerviewapp.R;

public class MainActivity extends AppCompatActivity {
    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState ==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, SocialNetworkFragment.newInstance()).commit();
        }


    }


    
}