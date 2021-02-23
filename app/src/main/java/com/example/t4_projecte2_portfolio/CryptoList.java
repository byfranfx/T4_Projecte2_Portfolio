package com.example.t4_projecte2_portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.t4_projecte2_portfolio.BDD.DBInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class CryptoList extends ListActivity {

    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_crypto_list);
        llistaContactes();
    }

    public void llistaContactes() {
        DBInterface bd;
        bd = new DBInterface(this);
        bd.obre();
        Cursor c = bd.obtenirAllCrypto();
        c.moveToFirst();
        ArrayList<HashMap<String, String>> llista = new ArrayList<HashMap<String, String>>();
        while (!c.isAfterLast()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", c.getString(0));
            map.put("nom", c.getString(1));
            map.put("email", c.getString(2));
            llista.add(map);
            c.moveToNext();
        }
        bd.tanca();
        adapter = new SimpleAdapter(this, llista, R.layout.activity_crypto_list,
                new String[]{"id", "nom", "email"}, new int[]{R.id.textViewLlistaID, R.id.textViewLlistaNom,
                R.id.textViewLlistaEmail});
        setListAdapter(adapter);
    }
}