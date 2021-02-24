package com.example.t4_projecte2_portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.t4_projecte2_portfolio.functions.AddNewCoin;
import com.example.t4_projecte2_portfolio.functions.AddNewTransaction;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView t1 = (TextView) findViewById(R.id.textViewDashboardUser);
        TextView t2 = (TextView) findViewById(R.id.textViewDashboardPorfolio);

        Bundle b1 = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();

        if(b1.getString("key1") != null)
        {
            String s1 = b1.getString("key1");
            t1.setText("User id: " + s1 + ".");
        }

        if(b2.getString("key2") != null)
        {
            String s2 = b2.getString("key2");
            t2.setText("Portfolio id: " + s2 + ".");
        }

        /*
        String s1 = b1.getString("key1");
        String s2 = b2.getString("key2");

        t1.setText("User id: " + s1 + ".");
        t2.setText("Portfolio id: " + s2 + ".");*/
    }

    // Menu Dashboard
    public void Dashboard_AddCrypto(View view) {
        finish();
        Bundle b1 = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();

        String s1 = b1.getString("key1");
        String s2 = b2.getString("key2");
        Intent i = new Intent(this, AddNewCoin.class);
        i.putExtra("key1", s1);
        i.putExtra("key2", s2);
        startActivity(i);
    }

    public void Dashboard_CryptoList(View view) {
        finish();

        Bundle b1 = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();

        String s1 = b1.getString("key1");
        String s2 = b2.getString("key2");

        Intent i = new Intent(this, CryptoList.class);
        i.putExtra("key1", s1);
        i.putExtra("key2", s2);
        startActivity(i);
    }

    public void Dashboard_AddTransaction(View view) {
        finish();

        Bundle b1 = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();

        String s1 = b1.getString("key1");
        String s2 = b2.getString("key2");

        Intent i = new Intent(this, AddNewTransaction.class);
        i.putExtra("key1", s1);
        i.putExtra("key2", s2);
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
            Dashboard_AddCrypto(null);
            return true;
        }
        if (id == R.id.D2) {
            Dashboard_CryptoList(null);
            return true;
        }
        if (id == R.id.D3) {
            Dashboard_AddTransaction(null);
            return true;
        }
        return super.onOptionsItemSelected(opcionMenu);
    }
}