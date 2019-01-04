package com.example.olya.homework3.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.olya.homework3.R
import com.example.olya.homework3.adapters.ChatAdapter
import com.example.olya.homework3.applicaitons.AppClass.Companion.messageRepository
import com.example.olya.homework3.entities.Message
import com.example.olya.homework3.livedata.SingleLiveEvent

class ChatViewModel : ViewModel(), ChatAdapter.Callback {

    val chatAdapter: ChatAdapter = ChatAdapter(this)
    private val _clickLiveEvent: SingleLiveEvent<Int> = SingleLiveEvent()
    val clickLiveEvent: LiveData<Int> = _clickLiveEvent

    init {
        messageRepository.selectAllAsync {
            chatAdapter.setItems(it)
        }
    }


    fun onSendMessageClicked(checkedRadioButtonId: Int, text: String) {
        when (checkedRadioButtonId) {
            R.id.rbtn1 -> {
                val messageUser = Message(userId = 1, messageText = text)
                messageRepository.insertAsync(messageUser)
                chatAdapter.add(messageUser)
            }
            R.id.rbtn2 -> {
                val messageUser = Message(userId = 2, messageText = text)
                messageRepository.insertAsync(messageUser)
                chatAdapter.add(messageUser)
            }
        }
    }

    //region Dialog

    fun onEditClicked(position: Int) {
        chatAdapter.editPosition = position
    }

    fun onDeleteClicked(position: Int) {
        val message = chatAdapter.messages[position]
        messageRepository.deleteAsync(message) {
            chatAdapter.removeMessage(position)
        }

    }
    //endregion

    //region Adapter.Callback

    override fun onItemLongClicked(position: Int) {
        _clickLiveEvent.value = position
    }

    override fun onEditFinished(position: Int, text: String) {
        val newMessage = chatAdapter.messages[position].copy(
            messageText = text
        )
        messageRepository.updateAsync(newMessage) {
            chatAdapter.setMessage(position,newMessage)
            chatAdapter.editPosition = null
        }

    }
    //endregion


}