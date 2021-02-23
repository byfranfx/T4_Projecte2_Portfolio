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
                Intent i = new Intent(this, Dashboard.class);
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