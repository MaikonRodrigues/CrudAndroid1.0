package com.example.maikon.crudandroid10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InicialActivity extends AppCompatActivity {
    Button btnInserir, btnDelet, btnBuscarCpf, btnBuscarTodos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        btnBuscarCpf   = (Button) findViewById(R.id.btnBuscCpf);
        btnDelet       = (Button)findViewById(R.id.btnDeletar);
        btnBuscarTodos = (Button) findViewById(R.id.btnListar);
        btnInserir     = (Button)findViewById(R.id.btnCriar);

        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(InicialActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        btnDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(InicialActivity.this, DeleteActivity.class);
                startActivity(it);
            }
        });

        btnBuscarCpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(InicialActivity.this, ListarActivity.class);
                startActivity(it);
            }
        });

        btnBuscarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(InicialActivity.this, ListarTodos.class);
                startActivity(it);
            }
        });
    }
}
