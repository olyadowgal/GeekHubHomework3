package com.example.olya.homework3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.olya.homework3.R
import com.example.olya.homework3.entities.UserMessage

class ChatAdapter(private val callback: Callback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val HEADER = 0
        const val HEADER_POSITION = 0
        const val HEADER_SIZE = 1
        const val VIEW_TYPE_MESSAGE_LEFT = 1
        const val VIEW_TYPE_MESSAGE_RIGHT = 2
        const val VIEW_TYPE_MESSAGE_EDIT = 3
    }

    private val messages: MutableList<UserMessage> = ArrayList()
    private var editPosition: Int? = null

    interface Callback {

        fun callbackClickAction(adapterPosition : Int)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderHeader -> holder.onBind()
            is ViewHolderEdit -> {
                holder.onBind(messages[position - HEADER_SIZE])
            }
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
            VIEW_TYPE_MESSAGE_EDIT -> {
                return ViewHolderEdit(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_edit,
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
        } else if (position == editPosition?.let { it + HEADER_SIZE }) {
            return VIEW_TYPE_MESSAGE_EDIT
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

    fun startEdit(position: Int) {
        val prevEditPosition = editPosition
        editPosition = position
        prevEditPosition?.let { notifyItemChanged(it + HEADER_SIZE) }
        notifyItemChanged(position + HEADER_SIZE)
    }

    fun endEdit(newText: String) {
        editPosition?.let {
            messages[it].text = newText
            notifyItemChanged(it + HEADER_SIZE)
        }
        editPosition = null
    }

    abstract inner class ViewHolderChat(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {

        private val txtMessage: TextView = view.findViewById(R.id.txtMessage)

        init {
            view.setOnLongClickListener(this)
        }

        fun onBind(message: UserMessage) {
            txtMessage.text = message.text
        }

        override fun onLongClick(v: View): Boolean {
            callback.callbackClickAction(adapterPosition)
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
            txtMessage.text = "User 1 : $user1   User 2 : $user2"
        }
    }

    inner class ViewHolderEdit(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val editMessage: EditText = view.findViewById(R.id.edit_message)
        private val buttonOk: Button = view.findViewById(R.id.btn_ok)

        init {
            buttonOk.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            endEdit(editMessage.text.toString())
        }


        fun onBind(message: UserMessage) {
            editMessage.setText(message.text)
        }

    }
}