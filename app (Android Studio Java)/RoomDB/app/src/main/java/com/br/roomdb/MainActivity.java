package com.br.roomdb;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        read();
    }

    private void read() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper.getAll(items -> {
            Adapeter adapeter = new Adapeter(items);
            recyclerView.setAdapter(adapeter);
            adapeter.notifyDataSetChanged();
        });
    }

    public void adicionarOpen(View view) {
        FrameLayout telaAdicionar = findViewById(R.id.telaAdicionar);
        if (telaAdicionar.getVisibility() == View.GONE) {
            telaAdicionar.setVisibility(View.VISIBLE);
        } else {
            telaAdicionar.setVisibility(View.GONE);
        }
    }
    public void adicionar(View view) {
        EditText nome = findViewById(R.id.inputAdicionarNome);
        EditText quantidade = findViewById(R.id.inputAdicionarQuantidade);
        dbHelper.insert(nome.getText().toString(), Integer.parseInt(quantidade.getText().toString()));
        read();
        adicionarOpen(null);
    }

    private int idClick;
    public void editarOpen(View view) {
        FrameLayout telaEditar = findViewById(R.id.telaEditar);
        if (telaEditar.getVisibility() == View.GONE) {
            idClick = Integer.parseInt(view.getTag().toString());
            telaEditar.setVisibility(View.VISIBLE);
        } else {
            telaEditar.setVisibility(View.GONE);
        }
    }
    public void editar(View view) {
        EditText nome = findViewById(R.id.inputEditarNome);
        EditText quantidade = findViewById(R.id.inputEditarQuantidade);
        dbHelper.update(idClick, nome.getText().toString(), Integer.parseInt(quantidade.getText().toString()));
        read();
        editarOpen(null);
    }

    public void deletar(View view) {
        dbHelper.deleteById(idClick);
        read();
        editarOpen(null);
    }
}