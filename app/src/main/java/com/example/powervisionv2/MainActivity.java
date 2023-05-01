package com.example.powervisionv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import android.content.Intent;
import android.widget.TextView;

import com.example.powervisionv2.datos.Datos;
import com.google.firebase.auth.FirebaseAuth;
import com.example.powervisionv2.Login;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity{


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout fragmentContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolBar();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        Datos dat = new Datos();
        Header header = new Header();
        TextView nombres = headerView.findViewById(R.id.textnombres);
        TextView correo1 = headerView.findViewById(R.id.textcorreo);
        String correo = dat.getCorreo();
        header.obtenerDatosPlanes(nombres, correo, correo1);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment = getSupportFragmentManager().findFragmentByTag("fragment_inicio");
        fragment = new Fragment_inicio();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();

        // Guardar una referencia al Fragment
        Fragment_inicio myFragment = (Fragment_inicio) fragment;

        // Acceder al Fragment más tarde
        myFragment.doSomething();
        fragmentContainer = findViewById(R.id.fragment_container);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Fragment fragment = null;
                        int id = menuItem.getItemId();
                        if (id == R.id.nav_inicio) {
                            fragment = new Fragment_inicio();
                        } else if (id == R.id.nav_planes) {
                            fragment = new Fragment_planes();
                        } else if (id == R.id.nav_logout) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                        if (fragment != null) {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.fragment_container, fragment);
                            ft.commit();
                        }
                        DrawerLayout drawer = findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });

    }
    private void setToolBar(){
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}