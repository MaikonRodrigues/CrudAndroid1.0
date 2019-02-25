package com.example.maikon.crudandroid10;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button butBuscar;
    EditText pegaCpf;
    ProgressDialog    progresso;
    RequestQueue request;
    JsonObjectRequest jsonObjectReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        cpf      = (TextView) findViewById(R.id.textViewCpf);
        nome     = (TextView) findViewById(R.id.textViewNome);
        endereco = (TextView) findViewById(R.id.textViewEndereco);
        telefone = (TextView) findViewById(R.id.textViewTelef);

        pegaCpf   = (EditText) findViewById(R.id.editTextCpf);
        butBuscar = (Button)findViewById(R.id.butBuscar);

        request = Volley.newRequestQueue(ListarActivity.this);

        butBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarWebService();
            }
        });
    }

    private void carregarWebService() {
        progresso = new ProgressDialog(this);
        progresso.setMessage("Carregando...");
        progresso.show();

        String url = "http://10.0.2.2/webservices/consultarCurso.php?cpf="+pegaCpf.getText().toString()+" "; // armazena o caminho do webservice no servidor
        url.replace(" ", "%20"); //trata os espacos na url - primeiro campo o que sera substituido, segundo pelo que

        jsonObjectReq = new JsonObjectRequest(Request.Method.GET, url, null, this,this);
        request.add(jsonObjectReq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progresso.hide();

        Toast.makeText(getApplicationContext(), "Não foi possível efetuar a consulta " +error.toString() , Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progresso.hide();

        Toast.makeText(getApplicationContext(), "Busca concluida" , Toast.LENGTH_SHORT).show();
        PessoaClass pessoa = new PessoaClass();
        JSONArray json = response.optJSONArray("pessoa"); //passo o objeto para ter acesso as instancias
        JSONObject jsonObject = null; //agora extrairemos as informacoes

        try {

            jsonObject = json.getJSONObject(0); //pegando a posicao 0 do array = cpf

            nome.setText("Nome: " + jsonObject.optString("nome"));
            endereco.setText("Endereco: " + jsonObject.optString("endereco"));
            telefone.setText("Telefone: " + jsonObject.optString("telefone"));


            nome.setVisibility(View.VISIBLE);
            endereco.setVisibility(View.VISIBLE);
            telefone.setVisibility(View.VISIBLE);

        } catch (JSONException e){
            e.printStackTrace();
        }

    }
}
