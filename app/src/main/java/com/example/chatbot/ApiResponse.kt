package com.example.chatbot

import android.graphics.Bitmap
import com.example.chatbot.data.Message
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object ApiResponse {

    val apiKey = "AIzaSyB2bqTcx5_jaBgoRccHpfUNkiq_lkjUWNA"

    suspend fun getResponse(prompt: String): Message{

        val generativeModel = GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = apiKey,
        )

        try {
            val response = withContext(Dispatchers.IO){
                generativeModel.generateContent(prompt)
            }

            return Message(
                prompt = response.text ?: "error",
                image = null,
                isFromUser = false
            )
        } catch (e: Exception){
            return Message(
                prompt = "error",
                image = null,
                isFromUser = true
            )
        }



    }
    suspend fun getResponseWithImage(prompt: String, image: Bitmap): Message{

        val generativeModel = GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = apiKey,
        )

        val content = content {
            text(prompt)
            image(image)
        }


        try {
            val response = withContext(Dispatchers.IO){
                generativeModel.generateContent(content)
            }

            return Message(
                prompt = response.text ?: "error",
                image = null,
                isFromUser = false
            )
        } catch (e: Exception){
            return Message(
                prompt = "error",
                image = null,
                isFromUser = true
            )
        }


    }
}