package com.example.chatbot

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.data.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {
    private val _chatList = MutableStateFlow<List<Message>>(emptyList())
    val chatList = _chatList.asStateFlow()

    private val _prompt = MutableStateFlow<String>("")
    val prompt = _prompt.asStateFlow()

    private val _image = MutableStateFlow<Bitmap?>(null)
    val image = _image.asStateFlow()

    fun updatePrompt(newPrompt: String){
        _prompt.value = newPrompt
    }

    fun updateImage(newImage: Bitmap?){
        _image.value = newImage
    }

    private fun addPrompt(prompt: String, image: Bitmap?){
        _chatList.update {
            it.toMutableList().apply {
                add(0, Message(prompt, image, true))
            }
        }
    }



    private fun getResponse(prompt: String){
        viewModelScope.launch {
            val response = ApiResponse.getResponse(prompt)
            _chatList.update {
                it.toMutableList().apply {
                    add(0,response)
                }
            }
        }
    }
    private fun getResponseWithImage(prompt: String, image: Bitmap){
        viewModelScope.launch {
            val response = ApiResponse.getResponseWithImage(prompt, image)
            _chatList.update {
                it.toMutableList().apply {
                    add(0,response)
                }
            }
        }
    }

    fun sendPrompt(){
        val currentPrompt = _prompt.value
        val currentImage = _image.value

        if (currentPrompt.isNotBlank()) {
            addPrompt(currentPrompt, currentImage)

            if (currentImage != null) {
                getResponseWithImage(currentPrompt, currentImage)
            } else {
                getResponse(currentPrompt)
            }

            _prompt.value = ""
            _image.value = null
        }
    }

}