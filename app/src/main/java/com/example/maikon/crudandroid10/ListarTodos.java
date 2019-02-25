package com.example.maikon.crudandroid10;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;

public class ListarTodos extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RecyclerView recyclerPessoas;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<PessoaClass> listaPessoas;
    ProgressDialog progresso;
    RequestQueue request;
    JsonObjectRequest jsonObjectReq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);

        recyclerPessoas = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerPessoas.setLayoutManager(layoutManager);

        listaPessoas = new ArrayList<>();

        recyclerPessoas.setLayoutManager(new LinearLayoutManager(ListarTodos.this));
        recyclerPessoas.setHasFixedSize(true);

        request = Volley.newRequestQueue(getApplicationContext());
        carregarWebService();
    }

    private void carregarWebService() {

        progresso = new ProgressDialog(this);
        progresso.setMessage("Carregando...");
        progresso.show();

        String url = "http://10.0.2.2/webservices/consultarLista.php"; // armazena o caminho do webservice no servidor
        url.replace(" ", "%20"); //trata os espacos na url - primeiro campo o que sera substituido, segundo pelo que

        jsonObjectReq = new JsonObjectRequest(Request.Method.GET, url, null, this,this);
        request.add(jsonObjectReq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progresso.hide();

        Toast.makeText(getApplicationContext(), "Não foi possível listar " +error.toString() , Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {
        progresso.hide();

        Toast.makeText(getApplicationContext(), "Busca concluida" , Toast.LENGTH_SHORT).show();
        PessoaClass pessoa = null;
        JSONArray json = response.optJSONArray("pessoa"); //passo o objeto para ter acesso as instancias


        try {

            for(int i = 0; i<json.length(); i++){
                pessoa = new PessoaClass();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                pessoa.setCpf(jsonObject.optInt("cpf"));
                pessoa.setNome(jsonObject.optString("nome"));
                pessoa.setEndereco(jsonObject.optString("endereco"));
                pessoa.setTelefone(jsonObject.optInt("telefone"));

                listaPessoas.add(pessoa);

            }

            progresso.hide();
            Pes_Adapter adapter = new Pes_Adapter(listaPessoas);
            recyclerPessoas.setAdapter(adapter);

        } catch (JSONException e){
            progresso.hide();

            Toast.makeText(getApplicationContext(), "Não foi possível listar "+response , Toast.LENGTH_SHORT).show();

        }

    }
}
