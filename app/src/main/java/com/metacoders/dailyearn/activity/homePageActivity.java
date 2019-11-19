package com.metacoders.dailyearn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.metacoders.dailyearn.LoginActivity;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.adapters.viewPagerAdapter;
import com.metacoders.dailyearn.fragments.allHistoryFragment;
import com.metacoders.dailyearn.fragments.balanceFragment;
import com.metacoders.dailyearn.fragments.dashboardFragment;
import com.metacoders.dailyearn.fragments.profileFragment;

public class homePageActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    NavigationView navigationView ;
    ImageView hamburger ;

    ViewPager viewPager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar ;

        final BottomNavigationView navigationBar = findViewById(R.id.bottom_navigation_) ;

        drawerLayout = findViewById(R.id.drawer_layout);
         toolbar = findViewById(R.id.toolbar);
         hamburger = findViewById(R.id.profile_image);

        toolbar.setTitle("Daily Earn");
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navigation_view);
        navigationBar.setOnNavigationItemSelectedListener(navigationItemSelectedListener) ;
       // getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, new dashboardFragment()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.fundTransfer:
                       Intent i  = new Intent(getApplicationContext()  , FundTransferActivity.class);
                       startActivity(i);
                        return true;
                    case R.id.packagelistMenu:
                        Intent ii  = new Intent(getApplicationContext()  , packageListActivity.class);
                        startActivity(ii);
                        return  true ;
                    case  R.id.tree_view:
                        Intent o  =  new Intent(getApplicationContext() , refActivity.class);

                        dashboardFragment dashboardfragment = new dashboardFragment();
                        String REf = dashboardFragment.getaffId();
                        o.putExtra("ref" , REf) ;

                        startActivity(o);
                        return  true ;
                    case R.id.productlist:
                        Intent igi  = new Intent(getApplicationContext()  , productListActivity.class);
                        startActivity(igi);
                        return true;

                    case R.id.deposit:
                        Intent iggg  = new Intent(getApplicationContext()  , paymentSelectActivity.class);
                        iggg.putExtra("method" , "deposit") ;
                        startActivity(iggg);

                        return true;

                    case R.id.withdraw:
                        Intent igg  = new Intent(getApplicationContext()  , withdrawActivity.class);
                        startActivity(igg);

                        return true;

                    case R.id.menu_go_logout:

                        /// log out
                      //  FirebaseUser muser = FirebaseAuth.getInstance().getCurrentUser();
                        FirebaseAuth mauth = FirebaseAuth.getInstance();
                        mauth.signOut();
                        Intent igfif  = new Intent(getApplicationContext()  , LoginActivity.class);
                        startActivity(igfif);

                        return true;
                    case R.id.daily_activity:
                        Intent igfi  = new Intent(getApplicationContext()  , dailyActivity.class);
                        startActivity(igfi);
                    /// log out

                    return true;

                    default:
                        return true;
                }
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.vlose) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

      //  toolbar.setNavigationIcon(R.drawable.ic_side_nav);
        toolbar.setNavigationIcon(null);          // to hide Navigation icon
    //    toolbar.setDisplayHomeAsUpEnabled(false);
        hamburger.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
//my ViewPager
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new dashboardFragment() , "Plan Trip");
        adapter.AddFragment(new balanceFragment() , "Ongoing Trip");
        adapter.AddFragment(new profileFragment() , "Profile");
        adapter.AddFragment(new allHistoryFragment() , "History");



        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


            }

            @Override
            public void onPageSelected(int i) {

                navigationBar.setSelectedItemId(i);
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

                        case R.id.favorites :
                            viewPager.setCurrentItem(2);
                            break;
                        case  R.id.history :
                            viewPager.setCurrentItem(3);

                    }
              //      getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, selectedFragmnet).commit();

                    return  true ;

                }
            } ;
}
