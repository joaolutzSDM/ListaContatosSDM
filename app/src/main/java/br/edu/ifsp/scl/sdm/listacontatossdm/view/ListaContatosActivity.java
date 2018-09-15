package br.edu.ifsp.scl.sdm.listacontatossdm.view;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.scl.sdm.listacontatossdm.R;
import br.edu.ifsp.scl.sdm.listacontatossdm.adapter.ListaContatosAdapter;
import br.edu.ifsp.scl.sdm.listacontatossdm.model.Contato;

public class ListaContatosActivity extends AppCompatActivity {

    //Request Code para abertura da tela Contato Activity- MODO Novo Contato
    private final int NOVO_CONTATO_REQUEST_CODE = 0;
    private final int EDITAR_CONTATO_REQUEST_CODE = 1;

    //CONSTANTE para passar parametros para tela ContatoActivity - MODO Detalhes
    public static final String CONTATO_EXTRA = "CONTATO_EXTRA";
    public static final String EDITAR_CONTATO = "EDITAR_CONTATO";
    public static final String CONTATO_POSITION = "CONTATO_POSITION";

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

        //ArrayAdapter<Contato> listaContatosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaContatos);
        listaContatosAdapter = new ListaContatosAdapter(this, listaContatos);
        listaContatosListView.setAdapter(listaContatosAdapter);

        listaContatosListView.setOnItemClickListener(onContatoClickListener);

        registerForContextMenu(listaContatosListView);
    }

    private AdapterView.OnItemClickListener onContatoClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Contato contato = (Contato) parent.getAdapter().getItem(position);
            Intent detalhesContatoIntent = new Intent(ListaContatosActivity.this, ContatoActivity.class);

            detalhesContatoIntent.putExtra(CONTATO_EXTRA, contato);
            startActivity(detalhesContatoIntent);
        }
    };

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
                Intent editarContatoIntent = new Intent(ListaContatosActivity.this, ContatoActivity.class);
                editarContatoIntent.putExtra(CONTATO_EXTRA, contato);
                editarContatoIntent.putExtra(EDITAR_CONTATO, true);
                editarContatoIntent.putExtra(CONTATO_POSITION, infoMenu.position);
                startActivityForResult(editarContatoIntent, EDITAR_CONTATO_REQUEST_CODE);
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
                //abrindo tela de novo contato
                Intent novoContatoIntent = new Intent("NOVO_CONTATO_ACTION");
                startActivityForResult(novoContatoIntent, NOVO_CONTATO_REQUEST_CODE);
                return true;
            case R.id.sairMenuItem:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case NOVO_CONTATO_REQUEST_CODE:
                if(resultCode == RESULT_OK) {
                    //recupera o contato do Intent data
                    Contato novoContato = (Contato) data.getSerializableExtra(CONTATO_EXTRA);
                    //Atualizo a lista
                    if(novoContato != null) {
                        listaContatos.add(novoContato);
                        //listaContatosAdapter.add(novoContato);
                        //Notifico adapter
                        listaContatosAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Contato cadastrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(resultCode == RESULT_CANCELED) {
                        Toast.makeText(this, "Cadastro cancelado", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case EDITAR_CONTATO_REQUEST_CODE:
                if(resultCode == RESULT_OK) {
                    //recupera o contato do Intent data
                    Contato contatoEditado = (Contato) data.getSerializableExtra(CONTATO_EXTRA);
                    //Atualizo a lista
                    if(contatoEditado != null) {
                        //listaContatosAdapter.
                        //Notifico adapter
                        int position = data.getIntExtra(CONTATO_POSITION, -1);
                        listaContatos.set(position, contatoEditado);
                        listaContatosAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Contato editado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(resultCode == RESULT_CANCELED) {
                        Toast.makeText(this, "Cadastro cancelado", Toast.LENGTH_SHORT).show();
                    }
                }
        }


    }

}