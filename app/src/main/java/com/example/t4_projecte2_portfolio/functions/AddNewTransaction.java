package com.example.t4_projecte2_portfolio.functions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t4_projecte2_portfolio.BDD.DBInterface;
import com.example.t4_projecte2_portfolio.Dashboard;
import com.example.t4_projecte2_portfolio.R;

public class AddNewTransaction extends AppCompatActivity {

    private EditText priceBuy, quantity;
    private DBInterface bd;
    private Button AddNewTransactionSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_transaction);


        //
        Bundle b1 = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();

        String s1 = b1.getString("key1");
        String s2 = b2.getString("key2");
        int foo = Integer.parseInt(s2);


        //
        priceBuy = findViewById(R.id.editTextAddNewTransactionPriceBuy);
        quantity = findViewById(R.id.editTextAddNewTransactionQuantity);

        AddNewTransactionSender = (Button) findViewById(R.id.buttonAddNewTransactionSender);
        AddNewTransactionSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x1 = priceBuy.getText().toString();
                String x2 = quantity.getText().toString();
                int i1 = Integer.parseInt(x1);
                int i2 = Integer.parseInt(x2);
                bd = new DBInterface(getApplicationContext());
                bd.obre();
                // insertar Usuari
                if (bd.crearTransaction(foo, "BTC", i1, i2) != -1) {
                    Toast.makeText(getApplicationContext(), "La transaccion se ha realizado correctamente. " +
                            "\n Porttfolio_id: " + foo +
                            "\n price_buy: " + i1 +
                            "\n quantity: " + i2, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error, la transaccion no se ha realizado!", Toast.LENGTH_SHORT).show();
                }
                bd.tanca();
                //finish();
                returnLoggin(null);
            }
        });
    }

    // Menu Dashboard
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