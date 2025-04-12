package com.example.phobert

data class TextRequest(val text: String)

data class SentimentResponse(
    val label: String,
    val confidence: Float,
    val all: Map<String, Float>
)
