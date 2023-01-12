package com.albatta.mohmmed.mishal.movie

import android.content.ClipData
import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.albatta.mohmmed.mishal.movie.MainActivity.Companion.Db
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_saved.*

class Saved : AppCompatActivity() {

    private lateinit var adapter: Adapter2
    lateinit var list:ArrayList<Quote>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)
        title="My ِِArchives"
        try {
            rv2.layoutManager = GridLayoutManager(this, LinearLayoutManager.VERTICAL)
            list = Db.getAllCards()
            adapter = Adapter2(this, list, listener)
            rv2.adapter = adapter
            rv2.setHasFixedSize(true)
        }catch (E:Exception){
            Toast.makeText(this, ""+E.message, Toast.LENGTH_LONG).show()}
    }
    private val listener:Onclick=object :Onclick{
        override fun onClick(text: String) {
            val service=getSystemService(CLIPBOARD_SERVICE)as ClipboardManager
            val clip= ClipData.newPlainText("copied",text)
            service.setPrimaryClip(clip)
            Toast.makeText(this@Saved, "copied !", Toast.LENGTH_SHORT).show()
        }

    }
}