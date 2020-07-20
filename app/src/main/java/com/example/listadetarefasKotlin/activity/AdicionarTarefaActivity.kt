package com.example.listadetarefasKotlin.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listadetarefasKotlin.R
import com.example.listadetarefasKotlin.helper.TarefaDAO
import com.example.listadetarefasKotlin.model.Tarefa
import com.google.android.material.textfield.TextInputEditText

class AdicionarTarefaActivity : AppCompatActivity() {
    private var editTarefa: TextInputEditText? = null
    private var tarefaAtual: Tarefa? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_tarefa)
        editTarefa = findViewById(R.id.textTarefa)

        //Recuperar tarefa, caso seja edição
        tarefaAtual = intent.getSerializableExtra("tarefaSelecionada") as Tarefa?
        //Configurar tarefa na caixa de texto
        if (tarefaAtual != null) {
            editTarefa.setText(tarefaAtual!!.nomeTarefa)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_adicionar_tarefa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemSalvar -> {
                //Toast.makeText(AdicionarTarefaActivity.this,"Item salvar pressionado",Toast.LENGTH_LONG).show();

                //executa ação para o item salvar
                val tarefaDAO = TarefaDAO(applicationContext)
                if (tarefaAtual != null) { //edição
                    val nomeTarefa = editTarefa!!.text.toString()
                    if (!nomeTarefa.isEmpty()) {
                        val tarefa = Tarefa()
                        tarefa.nomeTarefa = nomeTarefa
                        tarefa.id = tarefaAtual!!.id
                        //atualizar no banco de dados
                        if (tarefaDAO.atualizar(tarefa)) {
                            finish()
                            Toast.makeText(applicationContext, "Sucesso ao atualizar tarefa", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, "Falha ao salvar tarefa", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else { //salvar
                    val nomeTarefa = editTarefa!!.text.toString()
                    if (!nomeTarefa.isEmpty()) {
                        val tarefa = Tarefa()
                        tarefa.nomeTarefa = nomeTarefa
                        if (tarefaDAO.salvar(tarefa)) {
                            finish()
                            Toast.makeText(applicationContext, "Sucesso ao salvar tarefa", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, "Falha ao salvar tarefa", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}