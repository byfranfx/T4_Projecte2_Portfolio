package com.example.t4_projecte2_portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t4_projecte2_portfolio.BDD.DBInterface;

import java.util.List;

public class ItemList extends AppCompatActivity implements View.OnClickListener {

    private TextView t1, t2, t3, u1, u2;
    private Button delete, update;

    private DBInterface bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        //
        Bundle b1 = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();

        String s1 = b1.getString("key1");
        String s2 = b2.getString("key2");
        //

        t1 = (TextView) findViewById(R.id.textViewItemList1);
        t2 = (TextView) findViewById(R.id.textViewItemList2);
        t3 = (TextView) findViewById(R.id.textViewItemList3);

        // update
        u1 = (TextView) findViewById(R.id.editTextItemListPrice);
        u2 = (TextView) findViewById(R.id.editTextItemListQuantity);

        Bundle b = getIntent().getExtras();

        if (b.getString("ky0") != null) {
            String sx0 = b.getString("ky0");
        }
        if (b.getString("ky1") != null) {
            String sx1 = b.getString("ky1");
            t1.setText(sx1);
        }
        /*if (b.getString("ky2") != null) { // actual id
            String sx2 = b.getString("ky2");
            //t2.setText("Price per coin: " + sx2 + " $");
        }*/
        if (b.getString("ky3") != null) {
            String sx3 = b.getString("ky3");
            //t3.setText(sx3);
            t2.setText("Price per coin: " + sx3 + " $");
        }
        if (b.getString("ky4") != null) {
            String sx4 = b.getString("ky4");
            t3.setText(sx4);
        }

        update = (Button) findViewById(R.id.buttonItemListUpdateSender);
        update.setOnClickListener(this);

        delete = (Button) findViewById(R.id.buttonItemListDeleteSender);
        delete.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == delete) {

            Bundle b = getIntent().getExtras();

            String s0 = b.getString("ky0");
            String s1 = b.getString("ky1");
            String s2 = b.getString("ky2");
            String s3 = b.getString("ky3");
            String s4 = b.getString("ky4");

            int f0 = Integer.parseInt(s0);
            int f2 = Integer.parseInt(s2);
            int f3 = Integer.parseInt(s3);
            int f4 = Integer.parseInt(s4);

            bd = new DBInterface(this);
            bd.obre();
            if (bd.eliminarT(f0, s1, f2 ,f3, f4) != -1) {
                Toast.makeText(getApplicationContext(), "Eliminado correctament.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error, no se ha elimiando.", Toast.LENGTH_SHORT).show();
            }
            bd.tanca();
            //finish();
            returnLoggin(null);
        }
        if (v == update) {

            Bundle b = getIntent().getExtras();

            String s0 = b.getString("ky0");
            String s1 = b.getString("ky1");
            String s2 = b.getString("ky2");
            String x1 = u1.getText().toString();
            String x2 = u2.getText().toString();

            int f1 = Integer.parseInt(s0);
            int f2 = Integer.parseInt(s2);
            int i1 = Integer.parseInt(x1);
            int i2 = Integer.parseInt(x2);

            bd = new DBInterface(this);
            bd.obre();
            if (bd.updateT(f1, s1, f2, i1, i2) != -1) {
                Toast.makeText(getApplicationContext(), "Update realizado correctament.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error, no se ha realizado el update.", Toast.LENGTH_SHORT).show();
            }
            bd.tanca();
            //finish();
            returnLoggin(null);
        }

    }
    // Menu dashboard
    public void returnLoggin(View view) {
        finish();

        Intent intent = this.getIntent();
        String s1 = intent.getStringExtra("key1");
        String s2 = intent.getStringExtra("key2");

        Intent i = new Intent(this, Dashboard.class);

        i.putExtra("key1", s1);
        i.putExtra("key2", s2);

        startActivity(i);
    }


}