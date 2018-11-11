package com.example.olya.homework3.adapters

import android.os.Build.VERSION_CODES.P
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.olya.homework3.R
import com.example.olya.homework3.R.id.textUser1
import com.example.olya.homework3.entities.UserMessage
import kotlinx.android.synthetic.main.item_chat_left.view.*
import kotlinx.android.synthetic.main.item_chat_right.view.*
import java.lang.IllegalArgumentException

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_MESSAGE_LEFT = 1;
        const val VIEW_TYPE_MESSAGE_RIGHT = 2;
    }

    private val messages: MutableList<UserMessage> = ArrayList()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ViewHolderLeft -> {
                holder.onBind(messages[position])
            }
            is ViewHolderRight -> {
                holder.onBind(messages[position])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_MESSAGE_LEFT -> { return ViewHolderLeft(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_chat_left,
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_MESSAGE_RIGHT -> { return ViewHolderRight(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_chat_right,
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException()
        }
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        when (messages[position].userName) {
            "User 1" -> return VIEW_TYPE_MESSAGE_LEFT
            "User 2" -> return VIEW_TYPE_MESSAGE_RIGHT
            else -> throw IllegalArgumentException()
        }

    }

    fun add(message: UserMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    class ViewHolderLeft(view: View) : RecyclerView.ViewHolder(view) {
        private val textUser1 = view.textUser1

        fun onBind(message: UserMessage) {
            textUser1.text = message.text
        }
    }
    class ViewHolderRight(view: View) : RecyclerView.ViewHolder(view) {
        private val textUser2 = view.textUser2

        fun onBind(message: UserMessage) {
            textUser2.text = message.text
        }
    }
}