package com.example.newcatering;

import android.os.Bundle;

import com.example.newcatering.animation.DepthTransformation;
import com.example.newcatering.animation.RotateDownTransformer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.newcatering.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DepthTransformation depthTransformation = new DepthTransformation();
        RotateDownTransformer rotateDownTransformer=new RotateDownTransformer() {
            @Override
            protected void onTransform(View view, float position) {
                super.onTransform(view, position);
            }
        };
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setPageTransformer(true,rotateDownTransformer);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}