package com.example.olya.homework3.adapters

import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.olya.homework3.R
import com.example.olya.homework3.R.id.txtMessage
import com.example.olya.homework3.entities.UserMessage

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val HEADER = 0
        const val HEADER_POSITION = 0
        const val HEADER_SIZE = 1
        const val VIEW_TYPE_MESSAGE_LEFT = 1
        const val VIEW_TYPE_MESSAGE_RIGHT = 2
    }

    private val messages: MutableList<UserMessage> = ArrayList()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderHeader -> holder.onBind()
            is ViewHolderLeft -> {
                holder.onBind(messages[position - HEADER_SIZE])
            }
            is ViewHolderRight -> {
                holder.onBind(messages[position - HEADER_SIZE])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            HEADER -> {
                return ViewHolderHeader(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.header,
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_MESSAGE_LEFT -> {
                return ViewHolderLeft(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_chat_left,
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_MESSAGE_RIGHT -> {
                return ViewHolderRight(
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
        return messages.size + HEADER_SIZE
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == HEADER_POSITION) {
            HEADER
        } else when (messages[position - HEADER_SIZE].userName) {
            "User 1" -> VIEW_TYPE_MESSAGE_LEFT
            "User 2" -> VIEW_TYPE_MESSAGE_RIGHT
            else -> throw IllegalArgumentException()
        }

    }

    fun add(message: UserMessage) {
        messages.add(message)
        notifyItemChanged(HEADER_POSITION)
        notifyItemInserted(messages.size)
    }

    fun removeMessage(position: Int) {
        messages.removeAt(position)
        notifyItemChanged(HEADER_POSITION)
        notifyItemRemoved(position + HEADER_SIZE)
    }


    //fun edit(parent: ViewGroup, position: Int) {
    //    LayoutInflater.from(parent.context).inflate(
    //        R.layout.item_chat_left,
    //        parent,
    //        false
    //    )
    //}

    abstract inner class ViewHolderChat(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {

        private val txtMessage: TextView = view.findViewById(R.id.txtMessage)

        init {
            view.setOnLongClickListener(this)
        }

        fun onBind(message: UserMessage) {
            txtMessage.text = message.text
        }

        override fun onLongClick(v: View?): Boolean {
            AlertDialog.Builder(v!!.context)
                .setTitle("What to do with this?")
                .setNegativeButton("Edit", null)
                .setPositiveButton("Delete") { _, _ -> removeMessage(adapterPosition - HEADER_SIZE) }
                .setNeutralButton("Cancel", null)
                .show()
            return true
        }
    }

    inner class ViewHolderLeft(view: View) : ChatAdapter.ViewHolderChat(view)
    inner class ViewHolderRight(view: View) : ChatAdapter.ViewHolderChat(view)

    inner class ViewHolderHeader(view: View) : RecyclerView.ViewHolder(view) {

        private val txtMessage: TextView = view.findViewById(R.id.txtMessage)
        fun onBind() {
            var user1 = 0
            var user2 = 0
            messages.forEach { i ->
                when (i.userName) {
                    "User 1" -> user1++
                    "User 2" -> user2++
                 }
            }
            txtMessage.text = "User 1 : $user1 User 2 : $user2"
        }
    }
}