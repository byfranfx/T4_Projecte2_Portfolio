package com.example.t4_projecte2_portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private TextView t1, t2, USD1, USD2, USD3;
    private DBInterface bd;
    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        listView = findViewById(R.id.lvDashboard);
        t1 = (TextView) findViewById(R.id.textViewDashboardUser);
        t2 = (TextView) findViewById(R.id.textViewDashboardPorfolio);
        USD1 = (TextView) findViewById(R.id.textViewDashboardUSD1);
        USD2 = (TextView) findViewById(R.id.textViewDashboardUSD2);
        USD3 = (TextView) findViewById(R.id.textViewDashboardUSD3);

        llistaDashboard();

        Bundle b1 = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();

        if (b1.getString("key1") != null) {
            String s1 = b1.getString("key1");
            t1.setText("User id: " + s1 + ".");
        }

        if (b2.getString("key2") != null) {
            String s2 = b2.getString("key2");
            t2.setText("Portfolio id: " + s2 + ".");
        }

        // item click
        in = new Intent(this, ItemList.class);
        listView.setOnItemClickListener(listClick);

    }

    private AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {

            Bundle b1 = getIntent().getExtras();
            Bundle b2 = getIntent().getExtras();

            String s1 = b1.getString("key1");
            String s2 = b2.getString("key2");

            HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);

            String iv0 = map.get("v0");
            String iv1 = map.get("v1");
            String iv2 = map.get("v2");
            String iv3 = map.get("v3");
            String iv4 = map.get("v4");

            in.putExtra("ky0", iv0);
            in.putExtra("ky1", iv1);
            in.putExtra("ky2", iv2);
            in.putExtra("ky3", iv3);
            in.putExtra("ky4", iv4);

            in.putExtra("key1", s1);
            in.putExtra("key2", s2);

            startActivity(in);

        }
    };


    // Llista
    public void llistaDashboard() {

        Bundle b2 = getIntent().getExtras();
        String s2 = b2.getString("key2");
        int foo;
        try {
            foo = Integer.parseInt(s2);
        } catch (NumberFormatException e) {
            foo = 0;
        }

        bd = new DBInterface(this);
        bd.obre();

        String investment1 = String.valueOf(bd.getInvestment(foo));
        USD3.setText("Invested: " + investment1 + " $");

        String investment3 = String.valueOf(bd.getResultado(foo));
        USD1.setText(investment3 + " $");

        //String investment2 =  String.valueOf(bd.getActualValue(foo));
        int calc = bd.getResultado(foo) - bd.getInvestment(foo);
        USD2.setText("ROE€: " + calc + " $");


        Cursor c = bd.obtenirAllTransaction(foo);
        c.moveToFirst();
        ArrayList<HashMap<String, String>> llista = new ArrayList<HashMap<String, String>>();
        while (!c.isAfterLast()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("v0", c.getString(0));
            map.put("v1", c.getString(1));
            map.put("v2", c.getString(2));
            map.put("v3", c.getString(3));
            map.put("v4", c.getString(4));
            llista.add(map);
            c.moveToNext();
        }
        bd.tanca();
        adapter = new SimpleAdapter(this, llista, R.layout.llista,
                new String[]{"v0", "v1", "v3", "v4"}, new int[]{R.id.textViewDashboardPorfolio, R.id.textView14,
                R.id.textView16, R.id.textView15});
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

    public void Dashboard_Exit(View view) {
        finish();
        Intent i = new Intent(this, MainActivity.class);
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
        if (id == R.id.D4) {
            Dashboard_Exit(null);
            return true;
        }
        return super.onOptionsItemSelected(opcionMenu);
    }
}