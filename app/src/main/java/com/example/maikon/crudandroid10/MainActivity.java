package com.example.maikon.crudandroid10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, ErrorListener {

    EditText          cpf, nome, endereco, telefone;
    Button            botInserir, btnIRtelaListar,  btnIRtelaExcluir, btnListarTodos, btnDeletar;
    ProgressDialog    progresso;
    RequestQueue      request;
    JsonObjectRequest jsonObjectReq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        cpf      = (EditText) findViewById(R.id.editTextCpf);
        nome     = (EditText) findViewById(R.id.editTextNome);
        endereco = (EditText) findViewById(R.id.editTextEndereco);
        telefone = (EditText) findViewById(R.id.editTextTelefone);

        botInserir       = (Button) findViewById(R.id.butInserir);
        btnIRtelaListar  = (Button)findViewById(R.id.btnIRtelaBuscar);
        btnIRtelaExcluir = (Button)findViewById(R.id.btnIRtelaExcluir);
        btnListarTodos   = (Button)findViewById(R.id.btnIRtelaLisTodos);
        btnDeletar       = (Button)findViewById(R.id.btnIRtelaDeleta);

        request = Volley.newRequestQueue(MainActivity.this);

        botInserir.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                carregarWebService();
            }
        });

        btnIRtelaListar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, ListarActivity.class);
                startActivity(it);
            }
        });

        btnListarTodos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, ListarTodos.class);
                startActivity(it);
            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, DeleteActivity.class);
                startActivity(it);
            }
        });

    }

    public void carregarWebService() {

        progresso = new ProgressDialog(this);
        progresso.setMessage("Carregando...");
        progresso.show();

        String url = "http://10.0.2.2/webservices/registro.php?cpf="+cpf.getText().toString()+"&nome="+nome.getText().toString()+"&endereco="+endereco.getText().toString() +"&telefone="+telefone.getText().toString()+" "; // armazena o caminho do webservice no servidor
        url.replace(" ", "%20"); //trata os espacos na url - primeiro campo o que sera substituido, segundo pelo que

        jsonObjectReq = new JsonObjectRequest(Request.Method.GET, url, null, this,this);
        request.add(jsonObjectReq);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        progresso.hide();
        Toast.makeText( getApplicationContext(), "Nao foi possivel conectar ao servidor", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        progresso.hide();
        Toast.makeText( getApplicationContext(), "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(MainActivity.this, MainActivity.class);
        startActivity(it);
    }
}
