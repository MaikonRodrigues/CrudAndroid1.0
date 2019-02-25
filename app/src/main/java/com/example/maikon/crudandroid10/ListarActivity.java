package com.example.maikon.crudandroid10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListarActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    TextView cpf, nome, endereco, telefone;
    Button butBuscar, btnEditar;
    EditText pegaCpf, cpfEdit, nomeEdit, enderecoEdit, telefEdit;
    ProgressDialog    progresso;
    RequestQueue request;
    JsonObjectRequest jsonObjectReq;
    RelativeLayout layout001, layout002, layout003,layout01, layout02, layout03, layout04;
    int controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        controle = 0;
                                                                    cpfEdit      = (EditText) findViewById(R.id.editCpf);
        nome     = (TextView) findViewById(R.id.textViewNome);      nomeEdit     = (EditText) findViewById(R.id.editNome);
        endereco = (TextView) findViewById(R.id.textViewEndereco);  enderecoEdit = (EditText) findViewById(R.id.editEndereco);
        telefone = (TextView) findViewById(R.id.textViewTelef);     telefEdit    = (EditText) findViewById(R.id.editTelefone);

        pegaCpf   = (EditText) findViewById(R.id.editTextCpf);
        butBuscar = (Button)findViewById(R.id.butBuscar);
        btnEditar = (Button)findViewById(R.id.butEditar);

        layout001 = (RelativeLayout) findViewById(R.id.layout001); layout01 = (RelativeLayout) findViewById(R.id.layout1);
        layout002 = (RelativeLayout) findViewById(R.id.layout002); layout02 = (RelativeLayout) findViewById(R.id.layout2);
        layout003 = (RelativeLayout) findViewById(R.id.layout003); layout03 = (RelativeLayout) findViewById(R.id.layout3);
                                                                    layout04 = (RelativeLayout) findViewById(R.id.layout4);

        request = Volley.newRequestQueue(ListarActivity.this);

        butBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controle == 1){
                    carregarWebService("update");
                }else{
                    carregarWebService("buscar");
                }


            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout001.setVisibility(View.INVISIBLE);    layout01.setVisibility(View.VISIBLE);
                layout002.setVisibility(View.INVISIBLE);    layout02.setVisibility(View.VISIBLE);
                layout003.setVisibility(View.INVISIBLE);    layout03.setVisibility(View.VISIBLE);
                                                            layout04.setVisibility(View.VISIBLE);

                cpfEdit.setText(pegaCpf.getText().toString());
                nomeEdit.setText(nome.getText().toString());
                enderecoEdit.setText(endereco.getText().toString());
                telefEdit.setText(telefone.getText().toString());
                butBuscar.setText("Salvar");
                controle = 1;
            }
        });

    }

    private void carregarWebService(String verifica) {

        if (verifica == "buscar"){
            progresso = new ProgressDialog(this);
            progresso.setMessage("Carregando...");
            progresso.show();

            String url = "http://10.0.2.2/webservices/consultarCurso.php?cpf="+pegaCpf.getText().toString()+" "; // armazena o caminho do webservice no servidor
            url.replace(" ", "%20"); //trata os espacos na url - primeiro campo o que sera substituido, segundo pelo que

            jsonObjectReq = new JsonObjectRequest(Request.Method.GET, url, null, this,this);
            request.add(jsonObjectReq);
            btnEditar.setVisibility(View.VISIBLE);
        }else{
            progresso = new ProgressDialog(this);
            progresso.setMessage("Carregando...");
            progresso.show();

            String url = "http://10.0.2.2/webservices/apiUpdate.php?cpf="+cpfEdit.getText().toString()+"&nome="+nomeEdit.getText().toString()+"&endereco="
                    +enderecoEdit.getText().toString() +"&telefone="+telefEdit.getText().toString(); // armazena o caminho do webservice no servidor
            url.replace(" ", "%20"); //trata os espacos na url - primeiro campo o que sera substituido, segundo pelo que

            jsonObjectReq = new JsonObjectRequest(Request.Method.GET, url, null, this,this);
            request.add(jsonObjectReq);

        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (controle == 1){
            progresso.hide();
            Toast.makeText(getApplicationContext(), "Não foi possível atualizar " + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR", error.toString());
        }else {
            progresso.hide();
            Toast.makeText(getApplicationContext(), "Não foi possível efetuar a consulta " + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR", error.toString());
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        if (controle == 1){
            progresso.hide();
            Toast.makeText(getApplicationContext(), "Atualizacao concluida", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(ListarActivity.this, MainActivity.class);
            startActivity(it);
        }else {
            progresso.hide();

            Toast.makeText(getApplicationContext(), "Busca concluida", Toast.LENGTH_SHORT).show();
            PessoaClass pessoa = new PessoaClass();
            JSONArray json = response.optJSONArray("pessoa"); //passo o objeto para ter acesso as instancias
            JSONObject jsonObject = null; //agora extrairemos as informacoes

            try {

                jsonObject = json.getJSONObject(0); //pegando a posicao 0 do array = cpf

                nome.setText(jsonObject.optString("nome"));
                endereco.setText(jsonObject.optString("endereco"));
                telefone.setText(jsonObject.optString("telefone"));


                layout001.setVisibility(View.VISIBLE);
                layout002.setVisibility(View.VISIBLE);
                layout003.setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
