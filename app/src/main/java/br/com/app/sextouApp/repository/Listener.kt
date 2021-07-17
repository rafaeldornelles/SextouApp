package br.com.app.sextouApp.repository

interface Listener<T> {
    fun onError(message: String?)

    fun onSuccess(data: T)

}