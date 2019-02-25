package com.example.maikon.crudandroid10;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class DeleteActivity extends AppCompatActivity implements Response.ErrorListener {

    EditText campocpf;
    Button btndeletar;

    ProgressDialog progresso;
    RequestQueue request;
    JsonObjectRequest jsonObjectReq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        campocpf = (EditText) findViewById(R.id.editTextCpf);
        btndeletar = (Button) findViewById(R.id.butDeletar);

        request = Volley.newRequestQueue(DeleteActivity.this);

        btndeletar.setOnClickListener(new View.OnClickListener() {
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

        String url = "http://10.0.2.2/webservices/apiDeleta.php?cpf="+campocpf.getText().toString(); // armazena o caminho do webservice no servidor
        jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, null, null,this);


        request.add(jsonObjectReq);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        progresso.hide();
                                                                        //+error.toString()
        Toast.makeText(getApplicationContext(), "Deletado com sucesso"  , Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());

        Intent it = new Intent(DeleteActivity.this, MainActivity.class);
        startActivity(it);
    }
}

