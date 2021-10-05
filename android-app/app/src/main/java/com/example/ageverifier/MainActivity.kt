package com.example.ageverifier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result.Failure
import com.github.kittinunf.result.Result.Success
import kotlinx.android.synthetic.main.activity_main.button
import kotlinx.android.synthetic.main.activity_main.editTextNumber
import kotlinx.android.synthetic.main.activity_main.statusTextView

data class ResponseFromAPI(val isValid: Boolean)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            setText("")

            val age = editTextNumber.text.toString().toInt()
            callService(age)
        }
    }

    private fun callService(ageText: Int) {
        "https://age-verifier.herokuapp.com/age/verifier".httpPost()
            .body("""{"age":$ageText}""")
            .responseObject<ResponseFromAPI> { request, response, result ->

                when (result) {
                    is Success -> {
                        if (result.get().isValid) {
                            setText("You can drink 🎉")
                        } else {
                            setText("You can't drink yet :(")
                        }
                    }
                    is Failure -> {
                        setText(result.getException().message!!)
                    }
                }

            }
    }

    fun setText(text: String) {
        runOnUiThread {
            //Lambda
            statusTextView.text = text

        }
    }
}
