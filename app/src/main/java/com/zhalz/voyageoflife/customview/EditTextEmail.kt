package com.zhalz.voyageoflife.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns.EMAIL_ADDRESS
import com.google.android.material.textfield.TextInputEditText

class EditTextEmail @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    init {
        validateEmail()
    }

    private fun validateEmail() = addTextChangedListener(object : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(input: Editable) =
            if (!EMAIL_ADDRESS.matcher(input).matches()) setError("Please enter a valid email address", null)
            else setError(null)

    })


}