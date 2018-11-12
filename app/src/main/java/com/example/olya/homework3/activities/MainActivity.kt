package com.example.olya.homework3.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.olya.homework3.R
import com.example.olya.homework3.adapters.ChatAdapter
import com.example.olya.homework3.entities.UserMessage
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
         val TAG = MainActivity::class.java.simpleName
    }

    private val chatAdapter : ChatAdapter = ChatAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chatView.layoutManager = LinearLayoutManager(this)
        chatView.adapter = this.chatAdapter
        btn_ok.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_ok -> {
                Log.d(TAG, "on ok clicked")
                val editTextMes = edit_message.text.toString()
                val checkedId = userRadioGroup.checkedRadioButtonId
                if (checkedId == R.id.rbtn1) {
                    val userMessage = UserMessage("User 1", editTextMes)
                        chatAdapter.add(userMessage)
                }
                if (checkedId == R.id.rbtn2) {
                    val userMessage = UserMessage("User 2", editTextMes)
                    chatAdapter.add(userMessage)

                }
            }
        }
    }


}
