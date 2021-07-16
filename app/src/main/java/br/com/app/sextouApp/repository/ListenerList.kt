package br.com.app.sextouApp.repository

interface ListenerList<T> {

    fun onError(message: String?)

    fun onSuccess(list: List<T>)

    fun onReload(list: List<T>)
}