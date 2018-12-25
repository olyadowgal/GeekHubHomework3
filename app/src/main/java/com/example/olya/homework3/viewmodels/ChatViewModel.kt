package com.example.olya.homework3.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.olya.homework3.R
import com.example.olya.homework3.adapters.ChatAdapter
import com.example.olya.homework3.entities.UserMessage

class ChatViewModel : ViewModel() {

    init {

    }

    val chatAdapter: ChatAdapter = ChatAdapter()

    fun onSendMessageClicked(checkedRadioButtonId: Int, text: String) {
        when (checkedRadioButtonId) {
            R.id.rbtn1 -> {
                val messageUser = UserMessage("User 1", text)
                chatAdapter.add(messageUser)
            }
            R.id.rbtn2 -> {
                val messageUser = UserMessage("User 2", text)
                chatAdapter.add(messageUser)
            }
        }
    }


}