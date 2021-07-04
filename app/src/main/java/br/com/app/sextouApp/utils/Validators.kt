package br.com.app.sextouApp.utils

import android.content.Context
import android.util.Patterns
import br.com.app.sextouApp.R

object Validators {
    fun emailValidator(email:String?, context: Context): String? {
        return when{
            email == null -> context.getString(R.string.validation_email_null)
            !Patterns.EMAIL_ADDRESS.matcher(email).find() -> context.getString(R.string.validation_email_invalid)
            else -> null
        }
    }

    fun passwordValidator(password: String?, context: Context): String? {
        return when {
            password == null -> context.getString(R.string.validation_password_null)
            password.length < 6 -> context.getString(R.string.validation_password_minsize)
            else -> null
        }
    }

    fun nameValidator(name: String?, context: Context): String? {
        return when {
            name == null -> context.getString(R.string.validation_name_null)
            name.length < 3 -> context.getString(R.string.validation_name_minsize)
            else -> null
        }
    }

    fun cpfValidator(cpf:String?, context: Context): String? {
        if (cpf == null) return context.getString(R.string.validation_cpf_null)
        if (cpf.length != 11) return context.getString(R.string.validation_cpf_minsize)
        if (!Regex("\\d+").matches(cpf)) return context.getString(R.string.validation_cpf_num)
        if (cpf.all { it == cpf[0] }) return context.getString(R.string.validation_cpf_equaldigits)
        val dv1 = ((0..8).sumOf { (10-it) * cpf[it].toString().toInt() }).times(10).rem(11).let {
            if (it >= 10) 0 else it
        }
        val dv2 = ((0..8).sumOf { (11-it) * cpf[it].toString().toInt() }.let { ((it + dv1 * 2) * 10).rem(11) }).let {
            if (it >= 10) 0 else it
        }
        if(cpf[9].toString().toInt() != dv1 || cpf[10].toString().toInt() != dv2) return context.getString(
                    R.string.validation_cpf_valid)
        return null
    }

}

enum class Validator{
    EMAIL, NAME, PASSWORD, CPF;

    fun validate(value: String?, context:Context) : String?{
        return when(this){
            EMAIL -> Validators.emailValidator(value, context)
            NAME -> Validators.nameValidator(value, context)
            PASSWORD -> Validators.passwordValidator(value, context)
            CPF -> Validators.cpfValidator(value, context)
        }
    }
}