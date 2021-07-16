package br.com.app.sextouApp.databinding

import android.R.attr.bitmap
import android.graphics.*
import android.graphics.drawable.LayerDrawable
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.BindingAdapter
import br.com.app.sextouApp.R
import br.com.app.sextouApp.utils.Validator
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.min


object BindingAdapters {
    @BindingAdapter("validator", "model", requireAll = true)
    @JvmStatic
    fun validate(input: TextInputLayout, validator: Validator, model: String?) {
        input.error = with(validator.validate(model, input.context.applicationContext)){
            if(input.isDirty && this != null) this else null
        }
    }

    @BindingAdapter("imageBitmap")
    @JvmStatic
    fun setImageBitmap(input: ImageView, imageBitmap: Bitmap?){
        var res = input.resources
        if (input.drawable == null){
            input.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.drawable_round_profile_pic, null))
        }
        if (imageBitmap != null){
            input.setImageDrawable(imageBitmap.toRoundBitmap().toDrawable(res))
            input.scaleType = ImageView.ScaleType.FIT_XY

        }
    }

    fun Bitmap.toRoundBitmap(): Bitmap{
        val bitmapWidth = min(this.width, this.height)
        val output = Bitmap.createBitmap(bitmapWidth, bitmapWidth, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val rect = Rect(0, 0, bitmapWidth, bitmapWidth)

        paint.color = Color.BLACK
        canvas.drawCircle(bitmapWidth.toFloat()/2, bitmapWidth.toFloat()/2, bitmapWidth.toFloat()/2, paint)
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(this, rect, rect, paint)
        return output
    }
}