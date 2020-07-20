package com.example.listadetarefasKotlin.helper

import com.example.listadetarefasKotlin.model.Tarefa

interface ITarefaDAO {
    fun salvar(tarefa: Tarefa?): Boolean
    fun atualizar(tarefa: Tarefa?): Boolean
    fun deletar(tarefa: Tarefa?): Boolean
    fun listar(): List<Tarefa?>?
}