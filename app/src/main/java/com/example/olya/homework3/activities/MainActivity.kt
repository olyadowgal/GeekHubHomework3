package com.example.olya.homework3.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olya.homework3.R
import com.example.olya.homework3.viewmodels.ChatViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private val viewModel: ChatViewModel by lazy {
        ViewModelProviders.of(this).get(ChatViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chatView.layoutManager = LinearLayoutManager(this)
        chatView.adapter = viewModel.chatAdapter
        btn_ok.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_ok -> {
                viewModel.onSendMessageClicked(
                    checkedRadioButtonId = userRadioGroup.checkedRadioButtonId,
                    text = edit_message.text.toString()
                )
            }
        }
    }
}