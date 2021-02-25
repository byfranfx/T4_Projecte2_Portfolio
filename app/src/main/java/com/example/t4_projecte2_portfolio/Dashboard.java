package com.example.t4_projecte2_portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.t4_projecte2_portfolio.BDD.DBInterface;
import com.example.t4_projecte2_portfolio.functions.AddNewCoin;
import com.example.t4_projecte2_portfolio.functions.AddNewTransaction;

import java.util.ArrayList;
import java.util.HashMap;

public class Dashboard extends AppCompatActivity {

    private ListAdapter adapter;
    private ListView listView;
    private TextView t1, t2, USD;
    private DBInterface bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        listView = findViewById(R.id.lvDashboard);
        t1 = (TextView) findViewById(R.id.textViewDashboardUser);
        t2 = (TextView) findViewById(R.id.textViewDashboardPorfolio);
        USD = (TextView) findViewById(R.id.textViewDashboardUSD);

        llistaContactes();

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

        /*bd = new DBInterface(this);
        bd.obre();
        String investment =  String.valueOf(bd.getInvestment());
        USD.setText(investment+ " $");
        bd.tanca();*/
    }


    // Llista
    public void llistaContactes() {

        Bundle b2 = getIntent().getExtras();
        String s2 = b2.getString("key2");
        int foo;
        try {
            foo = Integer.parseInt(s2);
        }
        catch (NumberFormatException e)
        {
            foo = 0;
        }

        bd = new DBInterface(this);
        bd.obre();

        String investment =  String.valueOf(bd.getInvestment(foo));
        USD.setText(investment+ " $");

        Cursor c = bd.obtenirAllTransaction(foo);
        c.moveToFirst();
        ArrayList<HashMap<String, String>> llista = new ArrayList<HashMap<String, String>>();
        while (!c.isAfterLast()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("nom", c.getString(1));
            map.put("email", c.getString(2));
            map.put("other", c.getString(3));
            llista.add(map);
            c.moveToNext();
        }
        bd.tanca();
        adapter = new SimpleAdapter(this, llista, R.layout.llista,
                new String[]{"nom", "email", "other"}, new int[]{R.id.textView14,
                R.id.textView15, R.id.textView16});
        //setListAdapter(adapter);
        listView.setAdapter(adapter);

    }




    // Menu Dashboard
    public void Dashboard_AddCrypto(View view) {
        //finish();

        Intent intent = this.getIntent();
        String s1 = intent.getStringExtra("key1");
        String s2 = intent.getStringExtra("key2");

        Intent i = new Intent(this, AddNewCoin.class);

        i.putExtra("key1", s1);
        i.putExtra("key2", s2);

        startActivity(i);
    }

    public void Dashboard_CryptoList(View view) {
        //finish();

        Intent intent = this.getIntent();
        String s1 = intent.getStringExtra("key1");
        String s2 = intent.getStringExtra("key2");

        Intent i = new Intent(this, CryptoList.class);

        i.putExtra("key1", s1);
        i.putExtra("key2", s2);

        startActivity(i);
    }

    public void Dashboard_AddTransaction(View view) {
        //finish();

        Intent intent = this.getIntent();
        String s1 = intent.getStringExtra("key1");
        String s2 = intent.getStringExtra("key2");

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