package com.example.t4_projecte2_portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.t4_projecte2_portfolio.BDD.DBInterface;

public class Register extends AppCompatActivity {

    private DBInterface bd;
    private EditText nickname, password;
    private Button registerSender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nickname = findViewById(R.id.editTextRegisterNickname);
        password = findViewById(R.id.editTextRegisterPassword);
        registerSender = (Button) findViewById(R.id.buttonRegisterSend);

        registerSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nickname.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Error, el campo nickname o contraseña esta vacio!", Toast.LENGTH_SHORT).show();
                } else {
                    bd = new DBInterface(getApplicationContext());
                    bd.obre();
                    // insertar Usuari
                    if (bd.registerUser(nickname.getText().toString(), password.getText().toString()) != -1) {
                        Toast.makeText(getApplicationContext(), "Usuari Creat correctament " + bd.obtenirUserID(nickname.getText().toString()), Toast.LENGTH_SHORT).show();
                        // insertar Portfolio
                        if (bd.crearPortfolio(bd.obtenirUserID(nickname.getText().toString())) != -1) {
                            Toast.makeText(getApplicationContext(), "Portfolio Creat correctament " + bd.obtenirPortfolioID(bd.obtenirUserID(nickname.getText().toString())), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error, el portfolio no se ha creado!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error, el usuario no se ha creado!", Toast.LENGTH_SHORT).show();
                    }
                    bd.tanca();
                    //finish();
                    returnLoggin(null);
                }
            }
        });
    }

    // Menu Loggin
    public void returnLoggin(View view) {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}