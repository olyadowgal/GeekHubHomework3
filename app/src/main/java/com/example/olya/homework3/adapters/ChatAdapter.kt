package com.example.olya.homework3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.olya.homework3.R
import com.example.olya.homework3.entities.Message

class ChatAdapter(private val callback: Callback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val HEADER = 0
        const val HEADER_POSITION = 0
        const val HEADER_SIZE = 1
        const val VIEW_TYPE_MESSAGE_LEFT = 1
        const val VIEW_TYPE_MESSAGE_RIGHT = 2
        const val VIEW_TYPE_MESSAGE_EDIT = 3
    }

    private val _messages: MutableList<Message> = ArrayList()
    val messages: List<Message> = _messages

    var editPosition: Int? = null
        set(value) {
            val prev = field
            field = value
            prev?.let { notifyItemChanged(toAdapterPosition(it)) }
            value?.let { notifyItemChanged(toAdapterPosition(it)) }
        }

    interface Callback {

        fun onItemLongClicked(position: Int)
        fun onEditFinished(position: Int, text: String)


    }

    fun toItemsPosition(position: Int): Int {
        return position - HEADER_SIZE
    }

    fun toAdapterPosition(position: Int): Int {
        return position + HEADER_SIZE
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderHeader -> holder.onBind()
            is ViewHolderEdit -> {
                holder.onBind(_messages[toItemsPosition(position)])
            }
            is ViewHolderLeft -> {
                holder.onBind(_messages[toItemsPosition(position)])
            }
            is ViewHolderRight -> {
                holder.onBind(_messages[toItemsPosition(position)])
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
        return _messages.size + HEADER_SIZE
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == HEADER_POSITION) {
            HEADER
        } else if (position == editPosition?.let { it + HEADER_SIZE }) {
            return VIEW_TYPE_MESSAGE_EDIT
        } else when (_messages[toItemsPosition(position)].userId) {
            1 -> VIEW_TYPE_MESSAGE_LEFT
            2 -> VIEW_TYPE_MESSAGE_RIGHT
            else -> throw IllegalArgumentException()
        }
    }

    fun setItems(messages: List<Message>) {
        this._messages.addAll(messages)
    }

    fun add(message: Message) {
        _messages.add(message)
        notifyItemChanged(HEADER_POSITION)
        notifyItemInserted(_messages.size)
    }

    fun removeMessage(position: Int) {
        _messages.removeAt(position)
        notifyItemChanged(HEADER_POSITION)
        notifyItemRemoved(toAdapterPosition(position))
    }

    fun setMessage(position: Int, message: Message, notify: Boolean = true) {
        _messages[position] = message
        if (notify) {
            notifyItemChanged(toAdapterPosition(position))
        }
    }

    abstract inner class ViewHolderChat(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {

        private val txtMessage: TextView = view.findViewById(R.id.txtMessage)

        init {
            view.setOnLongClickListener(this)
        }

        fun onBind(message: Message) {
            txtMessage.text = message.messageText
        }

        override fun onLongClick(v: View): Boolean {
            callback.onItemLongClicked(toItemsPosition(adapterPosition))
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
            _messages.forEach { i ->
                when (i.userId) {
                    1 -> user1++
                    2 -> user2++
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
            callback.onEditFinished(
                toItemsPosition(adapterPosition),
                editMessage.text.toString()
            )
        }


        fun onBind(message: Message) {
            editMessage.setText(message.messageText)
        }

    }
}