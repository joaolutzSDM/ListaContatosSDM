package br.edu.ifsp.scl.sdm.listacontatossdm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.scl.sdm.listacontatossdm.R;
import br.edu.ifsp.scl.sdm.listacontatossdm.adapter.ListaContatosAdapter;
import br.edu.ifsp.scl.sdm.listacontatossdm.model.Contato;

public class ListaContatosActivity extends AppCompatActivity {

    private ListView listaContatosListView;
    private List<Contato> listaContatos;

    //Adapter que preenche a ListView
    private ListaContatosAdapter listaContatosAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);

        listaContatosListView = findViewById(R.id.listaContatosListView);

        listaContatos = new ArrayList<>();
        preencheListaContatos();

        //ArrayAdapter<Contato> listaContatosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaContatos);
        listaContatosAdapter = new ListaContatosAdapter(this, listaContatos);
        listaContatosListView.setAdapter(listaContatosAdapter);

        registerForContextMenu(listaContatosListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contexto, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo infoMenu = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Contato contato = listaContatos.get(infoMenu.position);

        switch(item.getItemId()) {
            case R.id.editarContatoMenuItem:
                return true;
            case R.id.ligarParaContatoMenuItem:
                return true;
            case R.id.verEnderecoContatoMenuItem:
                return true;
            case R.id.enviarEmailContatoMenuItem:
                return true;
            case R.id.removerContatoMenuItem:
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.configuracaoMenuItem:
                return true;
            case R.id.novoContatoMenuItem:
                return true;
            case R.id.sairMenuItem:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void preencheListaContatos() {
        for(int i = 0;i < 20;i++) {
            listaContatos.add(new Contato(
                    "C" + i,"IFSP","1234","@ifsp"
            ));
        }
    }

}