package com.metacoders.dailyearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.metacoders.dailyearn.adapters.viewPagerAdapter;
import com.metacoders.dailyearn.fragments.dashboardFragment;
import com.metacoders.dailyearn.fragments.testActivity2;

public class homePageActivity extends AppCompatActivity {

    ViewPager viewPager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        final BottomNavigationView navigationBar = findViewById(R.id.bottom_navigation_) ;



        navigationBar.setOnNavigationItemSelectedListener(navigationItemSelectedListener) ;
       // getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, new dashboardFragment()).commit();


//my ViewPager
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new dashboardFragment() , "Plan Trip");
        adapter.AddFragment(new testActivity2() , "Ongoing Trip");



        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            navigationBar.setSelectedItemId(i);
            }

            @Override
            public void onPageSelected(int i) {


            }

            @Override
            public void onPageScrollStateChanged(int i) {


            }
        });








    }
private     BottomNavigationView.OnNavigationItemSelectedListener  navigationItemSelectedListener =

            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment selectedFragmnet = null ;

                    switch ( menuItem.getItemId())
                    {
                        case R.id.home :
                            viewPager.setCurrentItem(0);
                            break;

                        case R.id.activity :
                            viewPager.setCurrentItem(1);
                            break;

                    }
              //      getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, selectedFragmnet).commit();

                    return  true ;

                }
            } ;
}
