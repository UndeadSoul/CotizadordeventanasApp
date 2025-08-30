package com.example.betaaplication;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.betaaplication.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_quote, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Database
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        try {
            new Thread(() ->{
                if (db.daoData().countData()==0){
                    insertInitialValues(db);
                }else {

                }
            }).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void insertInitialValues(AppDatabase db){
        new Thread(() -> {
            //perfiles20
            db.daoData().addData(new Data("preciolinea20blanco", "10"));
            db.daoData().addData(new Data("preciolinea20bronce", "83775"));
            db.daoData().addData(new Data("preciolinea20madera", "89737"));
            db.daoData().addData(new Data("preciolinea20mate", "10"));
            db.daoData().addData(new Data("preciolinea20titaneo", "10"));

            db.daoData().addData(new Data("kgmetrorielsup20", "0.548"));
            db.daoData().addData(new Data("kgmetrorielinf20", "0.670"));
            db.daoData().addData(new Data("kgmetrozocalo20", "0.448"));
            db.daoData().addData(new Data("kgmetrocabezal20", "0.402"));
            db.daoData().addData(new Data("kgmetrobatiente20", "0.475"));
            db.daoData().addData(new Data("kgmetrotraslapo20", "0.462"));
            db.daoData().addData(new Data("kgmetrojamba20", "0.467"));

            //vidrios
            db.daoData().addData(new Data("m2plancha", "4.5"));

            db.daoData().addData(new Data("precioplanchabronce4", "10"));
            db.daoData().addData(new Data("precioplanchabronce5", "10"));
            db.daoData().addData(new Data("precioplanchainc4", "25000"));
            db.daoData().addData(new Data("precioplanchainc5", "10"));
            db.daoData().addData(new Data("precioplanchasolar4", "10"));
            db.daoData().addData(new Data("precioplanchasolar5", "10"));

            //agregados
            db.daoData().addData(new Data("mrollofelpa55", "600"));
            db.daoData().addData(new Data("preciorollofelpa55", "22848"));
            db.daoData().addData(new Data("mrolloburlete302", "100"));
            db.daoData().addData(new Data("preciorolloburlete302", "18921"));
            db.daoData().addData(new Data("mrolloburlete506", "10"));
            db.daoData().addData(new Data("preciorolloburlete506", "10"));

            db.daoData().addData(new Data("preciopackpestillo20", "10"));
            db.daoData().addData(new Data("cantporpackpestillo20", "10"));
            db.daoData().addData(new Data("preciopackcaracol20", "10"));
            db.daoData().addData(new Data("cantporpackcaracol20", "10"));
            db.daoData().addData(new Data("preciopackrod20", "10"));
            db.daoData().addData(new Data("cantporpackrod20", "10"));
            db.daoData().addData(new Data("preciopacktornillo", "10"));
            db.daoData().addData(new Data("cantporpacktornillo", "10"));
            db.daoData().addData(new Data("preciopacksilicona", "10"));
            db.daoData().addData(new Data("cantporpacksilicona", "10"));

            //servicios
            db.daoData().addData(new Data("costokmflete", "10"));
            db.daoData().addData(new Data("preciomercadom2", "75000"));

        }).start();



    }
}