package com.example.babydiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity2 extends AppCompatActivity {

    NavController navCtl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        NavHost navHost = (NavHost)getSupportFragmentManager().findFragmentById(R.id.main2_navhost);
        navCtl = navHost.getNavController();

        NavigationUI.setupActionBarWithNavController(this,navCtl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.base_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (!super.onOptionsItemSelected(item)){
            switch (item.getItemId()){
                case android.R.id.home:
                    navCtl.navigateUp();
                    return true;
                default:
                    NavigationUI.onNavDestinationSelected(item,navCtl);
            }
        }else{
            return true;
        }
        return false;
    }
}