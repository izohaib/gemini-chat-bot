package com.example.chatbot.data

import android.graphics.Bitmap

data class Message(
    val prompt: String,
    val image: Bitmap?,
    val isFromUser: Boolean,
)
