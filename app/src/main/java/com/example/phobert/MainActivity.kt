package com.example.phobert

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ các thành phần giao diện
        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button)
        textViewResult = findViewById(R.id.textViewResult)

        button.setOnClickListener {
            val text = editText.text.toString().trim()
            if (text.isNotEmpty()) {
                sendPredictionRequest(text)
            } else {
                Toast.makeText(this, "Vui lòng nhập văn bản", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendPredictionRequest(text: String) {
        val request = TextRequest(text)
        RetrofitClient.instance.predict(request).enqueue(object : Callback<SentimentResponse> {
            override fun onResponse(
                call: Call<SentimentResponse>,
                response: Response<SentimentResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    textViewResult.text = "Label: ${result?.label}\n" +
                            "Confidence: ${result?.confidence}\n" +
                            "All: ${result?.all}"
                } else {
                    textViewResult.text = "Lỗi: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<SentimentResponse>, t: Throwable) {
                textViewResult.text = "Yêu cầu thất bại: ${t.message}"
            }
        })
    }
}
