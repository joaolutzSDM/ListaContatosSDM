package br.edu.ifsp.scl.sdm.listacontatossdm.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.edu.ifsp.scl.sdm.listacontatossdm.R;
import br.edu.ifsp.scl.sdm.listacontatossdm.model.Contato;

import static br.edu.ifsp.scl.sdm.listacontatossdm.view.ListaContatosActivity.EDITAR_CONTATO;

public class ContatoActivity extends AppCompatActivity {

    private EditText nomeEditText;
    private EditText enderecoEditText;
    private EditText telefoneEditText;
    private EditText emailEditText;

    private Button cancelarButton;
    private Button salvarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        nomeEditText = findViewById(R.id.nomeEditText);
        enderecoEditText = findViewById(R.id.enderecoEditText);
        telefoneEditText = findViewById(R.id.telefoneEditText);
        emailEditText = findViewById(R.id.emailEditText);

        cancelarButton = findViewById(R.id.cancelarButton);
        salvarButton = findViewById(R.id.salvarButton);

        // setando listeners dos botões

        cancelarButton.setOnClickListener(cancelarClickListener);
        salvarButton.setOnClickListener(salvarClickListener);

        String subtitulo;
        Contato contato = (Contato) getIntent().getSerializableExtra(ListaContatosActivity.CONTATO_EXTRA);

        if(contato != null) {
            boolean edicao = getIntent().getBooleanExtra(ListaContatosActivity.EDITAR_CONTATO, false);

            if(edicao) {
                //Modo edição
                subtitulo = "Editar contato";
                modoDetalheEdicao(contato, true);
            } else {
                //Modo detalhes
                subtitulo = "Detalhes do contato";
                modoDetalheEdicao(contato, false);
            }
        } else {
            //Modo cadastro
            subtitulo = "Novo contato";
        }
        // Setando subtítulo
        getSupportActionBar().setSubtitle(subtitulo);
    }

    private void modoDetalheEdicao(Contato contato, boolean edicao) {
        nomeEditText.setText(contato.getNome());
        nomeEditText.setEnabled(edicao);
        enderecoEditText.setText(contato.getEndereco());
        enderecoEditText.setEnabled(edicao);
        telefoneEditText.setText(contato.getTelefone());
        telefoneEditText.setEnabled(edicao);
        emailEditText.setText(contato.getEmail());
        emailEditText.setEnabled(edicao);
        if (edicao) {
            salvarButton.setVisibility(View.VISIBLE);
        } else {
            salvarButton.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener cancelarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setResult(RESULT_CANCELED);
            finish();
        }
    };

    private View.OnClickListener salvarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Contato novoContato = getContato();
            Intent resultadoIntent = new Intent();
            resultadoIntent.putExtra(ListaContatosActivity.CONTATO_EXTRA, novoContato);
            int position = getIntent().getIntExtra(ListaContatosActivity.CONTATO_POSITION, -1);
            resultadoIntent.putExtra(ListaContatosActivity.CONTATO_POSITION, position);
            setResult(RESULT_OK, resultadoIntent);
            finish();
        }
    };

    private Contato getContato() {
        Contato novoContato = new Contato();
        novoContato.setNome(nomeEditText.getText().toString());
        novoContato.setEndereco(enderecoEditText.getText().toString());
        novoContato.setTelefone(telefoneEditText.getText().toString());
        novoContato.setEmail(emailEditText.getText().toString());
        return novoContato;
    }

}
