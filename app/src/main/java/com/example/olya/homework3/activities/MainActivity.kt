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
import android.support.v7.widget.DividerItemDecoration
import android.support.v4.content.ContextCompat
import android.graphics.drawable.Drawable




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
        val dividerDrawable: Drawable = ContextCompat.getDrawable(this, R.drawable.divider_default)!!
        chatView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply { setDrawable(dividerDrawable) })
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
