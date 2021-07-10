package br.com.app.sextouApp.databinding

import br.com.app.sextouApp.utils.Validator
import com.google.android.material.textfield.TextInputLayout
import androidx.databinding.BindingAdapter


object BindingAdapters {
    @BindingAdapter("validator", "model", requireAll = true)
    @JvmStatic
    fun validate(input: TextInputLayout, validator: Validator, model: String?) {
        input.error = with(validator.validate(model, input.context.applicationContext)){
            if(input.isDirty && this != null) this else null
        }
    }
}