package com.example.t4_projecte2_portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.t4_projecte2_portfolio.functions.AddNewCoin;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    // Menu Dashboard
    public void Dashboard_Add(View view) {
        finish();
        Intent i = new Intent(this, AddNewCoin.class);
        startActivity(i);
    }

    public void Dashboard_CryptoList(View view) {
        finish();
        Intent i = new Intent(this, CryptoList.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem opcionMenu) {

        int id = opcionMenu.getItemId();
        // dAñadir Digimon
        if (id == R.id.D1) {
            Dashboard_Add(null);
            return true;
        }
        if (id == R.id.D2) {
            Dashboard_CryptoList(null);
            return true;
        }
        return super.onOptionsItemSelected(opcionMenu);
    }
}