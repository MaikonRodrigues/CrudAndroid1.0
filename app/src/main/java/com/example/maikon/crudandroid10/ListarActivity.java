package com.example.maikon.crudandroid10;

import android.app.ProgressDialog;
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
    RelativeLayout layout001, layout002, layout003,layout01, layout02, layout03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        cpf      = (TextView) findViewById(R.id.textViewCpf);       cpfEdit      = (EditText) findViewById(R.id.editTextCpf);
        nome     = (TextView) findViewById(R.id.textViewNome);      nomeEdit     = (EditText) findViewById(R.id.editTextNome);
        endereco = (TextView) findViewById(R.id.textViewEndereco);  enderecoEdit = (EditText) findViewById(R.id.editTextEndereco);
        telefone = (TextView) findViewById(R.id.textViewTelef);     telefEdit    = (EditText) findViewById(R.id.editTextTelefone);

        pegaCpf   = (EditText) findViewById(R.id.editTextCpf);
        butBuscar = (Button)findViewById(R.id.butBuscar);
        btnEditar = (Button)findViewById(R.id.butEditar);

        layout001 = (RelativeLayout) findViewById(R.id.layout001); layout01 = (RelativeLayout) findViewById(R.id.layout1);
        layout002 = (RelativeLayout) findViewById(R.id.layout002); layout02 = (RelativeLayout) findViewById(R.id.layout2);
        layout003 = (RelativeLayout) findViewById(R.id.layout003); layout03 = (RelativeLayout) findViewById(R.id.layout3);

        request = Volley.newRequestQueue(ListarActivity.this);

        butBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarWebService();

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout001.setVisibility(View.INVISIBLE);    layout01.setVisibility(View.INVISIBLE);
                layout002.setVisibility(View.INVISIBLE);    layout02.setVisibility(View.INVISIBLE);
                layout003.setVisibility(View.INVISIBLE);    layout03.setVisibility(View.INVISIBLE);

                cpfEdit.setText(pegaCpf.getText().toString());
                nomeEdit.setText(nome.getText().toString());
                enderecoEdit.setText(endereco.getText().toString());
                telefEdit.setText(telefone.getText().toString());


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
        btnEditar.setVisibility(View.VISIBLE);
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


            layout001.setVisibility(View.VISIBLE);
            layout002.setVisibility(View.VISIBLE);
            layout003.setVisibility(View.VISIBLE);

        } catch (JSONException e){
            e.printStackTrace();
        }

    }
}
