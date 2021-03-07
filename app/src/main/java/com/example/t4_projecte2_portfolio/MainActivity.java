package com.example.t4_projecte2_portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t4_projecte2_portfolio.BDD.DBInterface;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DBInterface bd;
    private Button logginSender;
    private EditText nickname, password;
    private String NOM_PREFENCIES = "LOGIN_PARAM";
    private EditText user, contraseña;
    private CheckBox recordar;
    private Bitmap bitmap;
    private ByteArrayOutputStream bos;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.editTextLogginNickname);
        //contraseña = findViewById(R.id.editTextLogginPassword);
        SharedPreferences pref = getSharedPreferences(NOM_PREFENCIES, MODE_PRIVATE);
        user.setText(pref.getString("user", ""));
        //
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btc);
        bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] BTC = bos.toByteArray();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.eth);
        bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] ETH = bos.toByteArray();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ltc);
        bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] LTC = bos.toByteArray();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ada);
        bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] ADA = bos.toByteArray();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dot);
        bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] DOT = bos.toByteArray();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xrp);
        bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] XRP = bos.toByteArray();

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
                    if (bd.addNewCoin("BTC", "bitcoin", BTC, 50000) != -1) {
                        if (bd.addNewCoin("ETH", "ethereum", ETH, 5000) != -1) {
                            if (bd.addNewCoin("LTC", "litecoin", LTC, 350) != -1) {
                                if (bd.addNewCoin("ADA", "cardano", ADA, 1) != -1) {
                                    if (bd.addNewCoin("DOT", "polkadot", DOT, 30) != -1) {
                                        if (bd.addNewCoin("XRP", "ripple", XRP, 2) != -1) {
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
            recordar = findViewById(R.id.recordar);
            if(recordar.isChecked())
            {
                SharedPreferences settings = getSharedPreferences(NOM_PREFENCIES, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                user = (EditText) findViewById(R.id.editTextLogginNickname);
                editor.putString("user", user.getText().toString());
                editor.commit();
            }
            else
            {
                SharedPreferences settings = getSharedPreferences(NOM_PREFENCIES, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("user");
            }
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