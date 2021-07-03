package br.com.app.sextouApp.utils

import android.util.Log
import android.util.Patterns

object Validators {
    fun emailValidator(email:String?): String? {
        return if(email == null) "Insira um e-mail." else if (!Patterns.EMAIL_ADDRESS.matcher(email).find()) "Insira um e-mail válido." else null
    }

    fun passwordValidator(password: String?): String? {
        return if(password == null) "Insira uma senha." else if (password.length < 6) "A senha deve ter no mínimo 6 caracteres." else null
    }

    fun nameValidator(name: String?): String? {
        return if(name == null) "Insira um nome." else if (name.length < 3) "O nome deve ter ao menos três caracteres." else null
    }

    fun cpfValidator(cpf:String?): String? {
        if (cpf == null) return "Insira um Cpf."
        if (cpf.length != 11) return "O Cpf deve ter 11 dígitos."
        if (!Regex("\\d+").matches(cpf)) return "O Cpf deve conter apenas números."
        if (cpf.all { it == cpf[0] }) return "O Cpf não pode conter todos os dígitos iguais."

        val dv1 = ((0..8).sumBy { (10-it) * cpf[it].toString().toInt() }).times(10).rem(11).let {
            if (it >= 10) 0 else it
        }

        val dv2 = ((0..8).sumBy { (11-it) * cpf[it].toString().toInt() }.let { ((it + dv1 * 2) * 10).rem(11) }).let {
            if (it >= 10) 0 else it
        }

        Log.i("AAAA", "Dvi: $dv1, dv2: $dv2")

        if(cpf[9].toString().toInt() != dv1 || cpf[10].toString().toInt() != dv2) return "Insira um Cpf válido."
        return null
    }

}

enum class Validator{
    EMAIL, NAME, PASSWORD, CPF;

    fun validate(value: String) : String?{
        return when(this){
            EMAIL -> Validators.emailValidator(value)
            NAME -> Validators.nameValidator(value)
            PASSWORD -> Validators.passwordValidator(value)
            CPF -> Validators.cpfValidator(value)
        }
    }
}