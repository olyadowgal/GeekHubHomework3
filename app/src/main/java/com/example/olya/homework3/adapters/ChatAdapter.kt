package com.example.olya.homework3.adapters

import android.support.v7.widget.RecyclerView
import android.system.Os.remove
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.olya.homework3.R
import com.example.olya.homework3.entities.UserMessage

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_MESSAGE_LEFT = 1
        const val VIEW_TYPE_MESSAGE_RIGHT = 2
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

    fun removeMessage(position: Int) {
        messages.removeAt(position)
        notifyItemRemoved(position)
    }

    inner abstract class ViewHolderChat(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {

        private val txtMessage : TextView = view.findViewById(R.id.txtMessage)

        init{
            view.setOnLongClickListener(this)
        }

        fun onBind(message: UserMessage) {
            txtMessage.text = message.text
        }

        override fun onLongClick(v: View?): Boolean {
            removeMessage(adapterPosition)
            return true
        }
    }

    inner class ViewHolderLeft(view: View) : ViewHolderChat(view)
    inner class ViewHolderRight(view: View) : ViewHolderChat(view)
}