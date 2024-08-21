package com.zhalz.voyageoflife.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.zhalz.voyageoflife.R

class TextInputPassword @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    init {
        validatePassword()
    }

    private fun validatePassword() = addTextChangedListener(object : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(input: Editable) =
            if (input.length < 8) setError(context.getString(R.string.password_error), null)
            else setError(null)

    })


}