package com.example.t4_projecte2_portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t4_projecte2_portfolio.BDD.DBInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DBInterface bd;
    private Button logginSender;
    private EditText nickname, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // automatice
        Cursor c;
        bd = new DBInterface(getApplicationContext());
        bd.obre();
        String a = "root";
        c = bd.logginUsuari(a, a);
        if (c.getCount() != 0) {
            Toast.makeText(getApplicationContext(), "has done!", Toast.LENGTH_SHORT).show();
        } else {
            if(bd.registerUser(a,a) != -1) {
                if(bd.crearPortfolio(bd.obtenirUserID("root")) != -1) {
                    if (bd.addNewCoin("BTC", "bitcoin", null, 50000) != -1) {
                        if (bd.addNewCoin("ETH", "ethereum", null, 5000) != -1) {
                            if (bd.addNewCoin("LTC", "litecoin", null, 350) != -1) {
                                if (bd.addNewCoin("ADA", "cardano", null, 1) != -1) {
                                    if (bd.addNewCoin("DOT", "polkadot", null, 30) != -1) {
                                        if (bd.addNewCoin("XRP", "ripple", null, 2) != -1) {
                                            if (bd.crearTransaction(1, "BTC", 9000,1) != -1) {
                                                if (bd.crearTransaction(1, "ETH", 1700,10) != -1) {
                                                    if (bd.crearTransaction(1, "LTC", 30,30) != -1) {
                                                        if (bd.crearTransaction(1, "ADA", 1,4000) != -1) {
                                                            if (bd.crearTransaction(1, "DOT", 4,500) != -1) {
                                                                if (bd.crearTransaction(1, "XRP", 1,80000) != -1) {
                                                                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        bd.tanca();
        //

        nickname = findViewById(R.id.editTextLogginNickname);
        password = findViewById(R.id.editTextLogginPassword);

        logginSender = (Button) findViewById(R.id.buttonLogginSender);
        logginSender.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == logginSender) {
            Cursor c;
            bd = new DBInterface(this.getApplicationContext());
            bd.obre();
            String id_nickname = nickname.getText().toString();
            String id_password = password.getText().toString();
            c = bd.logginUsuari(id_nickname, id_password);
            if (c.getCount() != 0) {
                // recullir id user & portfolio per enviarlo al dashboard.
                String sUser =  String.valueOf(bd.obtenirUserID(id_nickname));
                String sPortfolio =  String.valueOf(bd.obtenirPortfolioID2(bd.obtenirUserID(id_nickname)));
                //
                Intent i = new Intent(this, Dashboard.class);
                i.putExtra("key1",sUser);
                i.putExtra("key2",sPortfolio);
                startActivity(i);
            }
            bd.tanca();
        }
    }


    // Menu Loggin
    public void Main_Loggin(View view) {
        finish();
        Intent i = new Intent(this, Register.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_loggin, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem opcionMenu) {

        int id = opcionMenu.getItemId();
        // dAñadir Digimon
        if (id == R.id.R1) {
            Main_Loggin(null);
            return true;
        }
        return super.onOptionsItemSelected(opcionMenu);
    }
}