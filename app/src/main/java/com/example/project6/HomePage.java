package com.example.project6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTab = tab.getPosition();
                switchFragments(selectedTab);
                System.out.println(selectedTab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        SharedPreferences fragPref = getSharedPreferences("FRAG", Context.MODE_PRIVATE);
        TabLayout.Tab tab = tabLayout.getTabAt(fragPref.getInt("current", 0));
        tab.select();
    }

    private void switchFragments(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch(position) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.fragmentContainerView, homeFragment);
                break;
            case 1:
                ExploreFragment exploreFragment = new ExploreFragment();
                fragmentTransaction.replace(R.id.fragmentContainerView, exploreFragment);
                break;
            case 2:
                FavoritesFragment favoritesFragment = new FavoritesFragment();
                fragmentTransaction.replace(R.id.fragmentContainerView, favoritesFragment);
                break;
            case 3:
                HistoryFragment historyFragment = new HistoryFragment();
                fragmentTransaction.replace(R.id.fragmentContainerView, historyFragment);
                break;
            case 4:
                SettingsFragment settingsFragment = new SettingsFragment();
                fragmentTransaction.replace(R.id.fragmentContainerView, settingsFragment);
                break;
            default:
                Toast.makeText(this, "How did you even end up here", Toast.LENGTH_SHORT).show();
                break;
        }
        fragmentTransaction.commit();
    }
}