package com.example.maikon.crudandroid10;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static com.example.maikon.crudandroid10.R.layout.activity_listar_todos;

public class Pes_Adapter extends RecyclerView.Adapter<Pes_Adapter.PessoaHolder> {

    List<PessoaClass> listPessoas;

    public Pes_Adapter(List<PessoaClass> listaPessoas){
        this.listPessoas = listaPessoas;
    }
    @NonNull
    @Override
    public PessoaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista =  LayoutInflater.from(parent.getContext()).inflate(activity_listar_todos, parent, false);

        RecyclerView.LayoutParams layoutParams =
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        vista.setLayoutParams(layoutParams);
        return new PessoaHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PessoaHolder holder, int position) {
        String var = String.valueOf(listPessoas.get(position).getCpf());
        String var2 = String.valueOf(listPessoas.get(position).getTelefone());
        holder.campoCadastro.setText("Cadastro "+(position+1));
        holder.campoCpf.setText("CPF :" +var);
        holder.campoCpf.setVisibility(View.VISIBLE);
        holder.campoNome.setText("Nome: " +listPessoas.get(position).getNome());
        holder.campoNome.setVisibility(View.VISIBLE);
        holder.campoEndereco.setText("Endereco: "+listPessoas.get(position).getEndereco());
        holder.campoEndereco.setVisibility(View.VISIBLE);
        holder.campoTelefone.setText("Telefone: "+var2);
        holder.campoTelefone.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return listPessoas.size();
    }

    public class PessoaHolder extends RecyclerView.ViewHolder {

        TextView campoCpf, campoNome, campoEndereco, campoTelefone, campoCadastro;

        public PessoaHolder(@NonNull View itemView) {
            super(itemView);
            campoCadastro = (TextView) itemView.findViewById(R.id.textViewCadastro);
            campoCpf      = (TextView) itemView.findViewById(R.id.campoCpf);
            campoNome     = (TextView) itemView.findViewById(R.id.campoNome);
            campoEndereco = (TextView) itemView.findViewById(R.id.campoEndereco);
            campoTelefone = (TextView) itemView.findViewById(R.id.campoTelefone);
        }
    }
}
