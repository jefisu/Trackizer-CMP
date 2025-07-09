package com.jefisu.trackizer.core.ui

import com.jefisu.trackizer.core.util.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MessageManager {

    private val _message = MutableStateFlow<MessageUi?>(null)
    val message = _message.asStateFlow()

    fun showMessage(message: MessageUi) {
        _message.value = message
    }

    fun closeMessage() {
        _message.update { null }
    }
}

sealed class MessageUi(val text: UiText) : Message {
    class Success(text: UiText) : MessageUi(text)
    class Error(text: UiText) : MessageUi(text)
    class Warning(text: UiText) : MessageUi(text)
    class Help(text: UiText) : MessageUi(text)
}
