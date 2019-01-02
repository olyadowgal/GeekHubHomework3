package com.example.olya.homework3.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.olya.homework3.R
import com.example.olya.homework3.adapters.ChatAdapter
import com.example.olya.homework3.entities.UserMessage
import com.example.olya.homework3.livedata.SingleLiveEvent

class ChatViewModel : ViewModel(), ChatAdapter.Callback {

    val chatAdapter: ChatAdapter = ChatAdapter(this)
    private val _clickLiveEvent: SingleLiveEvent<Int> = SingleLiveEvent()
    val clickLiveEvent: LiveData<Int> = _clickLiveEvent


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

    fun onEditClicked (adapterPos: Int) {
        chatAdapter.startEdit(adapterPos)
    }

    fun onDeleteClicked (adapterPos: Int) {
        chatAdapter.removeMessage(adapterPos)

    }

    override fun callbackClickAction(adapterPosition: Int) {
        _clickLiveEvent.value = adapterPosition
    }



}