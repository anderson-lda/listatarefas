package com.example.listadetarefasKotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listadetarefasKotlin.R
import com.example.listadetarefasKotlin.adapter.TarefaAdapter
import com.example.listadetarefasKotlin.helper.RecyclerItemClickListener
import com.example.listadetarefasKotlin.helper.TarefaDAO
import com.example.listadetarefasKotlin.model.Tarefa
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var tarefaAdapter: TarefaAdapter? = null
    private var listaTarefas: List<Tarefa> = ArrayList()
    private var tarefaSelecionada: Tarefa? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //configurar recyclerView
        recyclerView = findViewById(R.id.recyclerView)

        //DbHelper db = new DbHelper(getApplicationContext());
        //ContentValues cv = new ContentValues();
        //cv.put("nome","Teste");
        //db.getWritableDatabase().insert("tarefas",null,cv);

        //adicionar evento de clique
        recyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(
                        applicationContext,
                        recyclerView,
                        object : RecyclerItemClickListener.OnItemClickListener {
                            override fun onItemClick(view: View?, position: Int) {
                                //Log.i("clique","onItemClick"); //comentário aparece aqui, não lá

                                //Recuperar tarefa para edição
                                val tarefaSelecionada = listaTarefas[position]
                                //Enviar tarefa para a tela 'adicionar tarefa'
                                val intent = Intent(this@MainActivity, AdicionarTarefaActivity::class.java)
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada)
                                startActivity(intent)
                            }

                            override fun onLongItemClick(view: View?, position: Int) {
                                //Log.i("clique","onLongItemClick");

                                //Recuperar tarefa para deletar
                                tarefaSelecionada = listaTarefas[position]
                                val dialog = AlertDialog.Builder(this@MainActivity)
                                //configurar título e mensagem
                                dialog.setTitle("Confirmar exclusão: ")
                                dialog.setMessage("Deseja excluir a tarefa: " + tarefaSelecionada!!.nomeTarefa + "?")
                                dialog.setPositiveButton("Sim") { dialogInterface, i ->
                                    val tarefaDAO = TarefaDAO(applicationContext)
                                    if (tarefaDAO.deletar(tarefaSelecionada)) {
                                        carregarListaTarefas()
                                        Toast.makeText(applicationContext, "Sucesso ao excluir tarefa", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(applicationContext, "Erro ao deletar tarefa", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                dialog.setNegativeButton("Não", null)
                                //exibir dialog
                                dialog.create()
                                dialog.show()
                            }

                            override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {}
                        }
                )
        )
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(applicationContext, AdicionarTarefaActivity::class.java)
            startActivity(intent)
        }
    }

    fun carregarListaTarefas() {

        //Listar tarefas

        //tarefas estáticas
        /*Tarefa tarefa1 = new Tarefa();
        tarefa1.setNomeTarefa("Ir ao mercado");
        listaTarefas.add(tarefa1);
        Tarefa tarefa2 = new Tarefa();
        tarefa2.setNomeTarefa("Ir ao mercado");
        listaTarefas.add(tarefa2);*/
        val tarefaDAO = TarefaDAO(applicationContext)
        listaTarefas = tarefaDAO.listar()

        //configurar adapter
        tarefaAdapter = TarefaAdapter(listaTarefas)

        //configurar recyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayout.VERTICAL))
        recyclerView!!.adapter = tarefaAdapter
    }

    override fun onStart() {
        carregarListaTarefas()
        super.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}