package com.example.soccersocialnetwork.Set_Football_Pitches.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.soccersocialnetwork.R;
import com.example.soccersocialnetwork.Set_Football_Pitches.flagment.SetFootballPitchesInfoFragment;
import com.example.soccersocialnetwork.Set_Football_Pitches.flagment.SetListFreeTimeFragment;
import com.example.soccersocialnetwork.football_field_owner.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class SetFootballPitchesActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    String key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_pitches);
        setControl();
        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
        setIcon();
    }

    public String getKey() {
        return key;
    }

    private void setControl() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }
    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SetFootballPitchesInfoFragment(), "");
        adapter.addFragment(new SetListFreeTimeFragment(), "");
        viewPager.setAdapter(adapter);

    }
    private void setIcon()
    {
        tabLayout.getTabAt(0).setIcon(R.drawable.icon_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_gio_trong);
    }
}