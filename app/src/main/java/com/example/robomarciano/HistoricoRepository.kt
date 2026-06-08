package com.example.robomarciano

import android.content.Context

class HistoricoRepository(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun carregar(): MutableList<String> {
        val salvo = preferences.getString(KEY_COMMANDS, "").orEmpty()
        if (salvo.isBlank()) {
            return mutableListOf()
        }

        return salvo.split(SEPARATOR)
            .filter { it.isNotBlank() }
            .toMutableList()
    }

    fun salvar(comandos: List<String>) {
        preferences.edit()
            .putString(KEY_COMMANDS, comandos.joinToString(SEPARATOR))
            .apply()
    }

    fun limpar() {
        preferences.edit()
            .remove(KEY_COMMANDS)
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "robo_marciano_prefs"
        private const val KEY_COMMANDS = "historico_comandos"
        private const val SEPARATOR = "\u001F"
    }
}
