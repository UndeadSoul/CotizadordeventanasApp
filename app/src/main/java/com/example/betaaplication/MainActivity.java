package com.example.betaaplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.betaaplication.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_quote, R.id.nav_settings, R.id.nav_projects, R.id.nav_clients)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        
        setupDrawerNavigation(navigationView);

        // Database operations
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        cleanupIncompleteProjects(db);
        initializeDatabase(db);
    }

    private void setupDrawerNavigation(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(item -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setPopUpTo(R.id.nav_home, false)
                    .build();

            try {
                navController.navigate(item.getItemId(), null, navOptions);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        });
    }

    private void cleanupIncompleteProjects(AppDatabase db) {
        new Thread(() -> {
            List<Project> incompleteProjects = db.projectDao().getIncompleteProjects();
            if (incompleteProjects != null && !incompleteProjects.isEmpty()) {
                List<Long> idsToDelete = incompleteProjects.stream().map(Project::getId).collect(Collectors.toList());
                db.projectDao().deleteProjectsByIds(idsToDelete);
            }
        }).start();
    }

    private void initializeDatabase(AppDatabase db) {
        new Thread(() -> {
            if (db.daoData().countData() == 0) {
                insertInitialValues(db);
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void insertInitialValues(AppDatabase db) {
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
    }
}
