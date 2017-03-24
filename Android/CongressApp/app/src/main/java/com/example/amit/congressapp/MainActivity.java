package com.example.amit.congressapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView = null;
    Toolbar toolbar = null;
    int prv = R.id.nav_Leg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Legislators");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        //My Code// Load Default Leg Bar
        LegislatorFrag legFrag = new LegislatorFrag();
        //BillsFrag legFrag = new BillsFrag();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container,legFrag);
        ft.commit();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_Leg) {
            LegislatorFrag legFrag = new LegislatorFrag();
            android.support.v4.app.FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,legFrag);
            this.getSupportActionBar().setTitle("Legislators");
            ft.commit();


        } else if (id == R.id.nav_Bills) {
            BillsFrag billFrag = new BillsFrag();
            android.support.v4.app.FragmentTransaction ft =this.getSupportFragmentManager().beginTransaction() ;
            ft.replace(R.id.fragment_container,billFrag);
            this.getSupportActionBar().setTitle("Bills");
            ft.commit();

        } else if (id == R.id.nav_Comm) {
            CommFrag comFrag = new CommFrag();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,comFrag);
            getSupportActionBar().setTitle("Committees");
            ft.commit();


        } else if (id == R.id.nav_Fav) {
            FavouritesFrag ftFrag = new FavouritesFrag();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,ftFrag);
            getSupportActionBar().setTitle("Favorites");
            ft.commit();
        }else if(id == R.id.nav_am){
            //start new about me activity here and pause the previous one
            Intent intent = new Intent(this, AboutMe.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
