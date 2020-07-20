package com.example.listadetarefasKotlin.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper(context: Context?) : SQLiteOpenHelper(context, NOME_DB, null, VERSION) {
    //é chamado apenas na primeira vez q abrir o app
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        val sql = ("CREATE TABLE IF NOT EXISTS "
                + TABELA_TAREFAS + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL); ")
        try {
            sqLiteDatabase.execSQL(sql)
            Log.i("INFO DB", "Sucesso ao criar a tabela ")
        } catch (e: Exception) {
            Log.i("INFO DB", "Erro ao criar a tabela: " + e.message)
        }
    }

    //usado para atualizações do app
    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        //não é recomendado apagar tabelas pois o usuário perderia todos os seus dados
        val sql = "DROP TABLE IF EXISTS $TABELA_TAREFAS;"
        //String sql = "ALTER TABLE "+TABELA_TAREFAS+" ADD COLUMN status VARCHAR(1) ;";
        try {
            sqLiteDatabase.execSQL(sql)
            onCreate(sqLiteDatabase)
            Log.i("INFO DB", "Sucesso ao atualizar app ")
        } catch (e: Exception) {
            Log.i("INFO DB", "Erro ao atualizar app " + e.message)
        }
    }

    companion object {
        var VERSION = 1 //se eu lançasse uma segunda versao tem q trocar aqui para 2
        var NOME_DB = "DB_TAREFAS"
        @JvmField
        var TABELA_TAREFAS = "tarefas"
    }
}