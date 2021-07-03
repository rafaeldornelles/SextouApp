package br.com.app.sextouApp.databinding

import br.com.app.sextouApp.utils.Validator
import com.google.android.material.textfield.TextInputLayout
import androidx.databinding.BindingAdapter


object BindingAdapters {
    @BindingAdapter("emailValidator")
    @JvmStatic
    fun setEmail(input: TextInputLayout, validator: Validator) {
        input.error = with(validator.validate(input.editText?.text.toString())){
            if(input.isDirty && this != null) this else null
        }
    }
}