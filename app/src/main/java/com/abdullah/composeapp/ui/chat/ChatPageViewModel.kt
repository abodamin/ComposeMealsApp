package com.abdullah.composeapp.ui.chat

import androidx.lifecycle.ViewModel
import com.abdullah.composeapp.ui.models.ChatUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ChatPageViewModel @Inject constructor(): ViewModel() {

    val messagesList = MutableStateFlow(emptyList<ChatUiModel>())
    fun sendMessage(msg: String, author: ChatUiModel.Author){
        messagesList.value += ChatUiModel(messages = listOf(ChatUiModel.Message(msg, author)), addressee = author)
    }
}