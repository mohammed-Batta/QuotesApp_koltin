package com.albatta.mohmmed.mishal.movie

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.*


class MainActivity : AppCompatActivity() {


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var Db: DBHandler
    }


    lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RequestManager(this).getAllQuotes(listener)
        Db = DBHandler(this, "null", null)
    }
    private val listener:QuotesResponseListener = object :QuotesResponseListener{
        @RequiresApi(Build.VERSION_CODES.M)
        override fun didFetch(response: ArrayList<QuotesModel>, manager: String) {
            try {
                progressBar.visibility = GONE
                rv.layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = Adapter(this@MainActivity,isSaved(response), onclick)
                rv.adapter = adapter
                rv.setHasFixedSize(true)
            }catch (E:Exception){
                Toast.makeText(this@MainActivity, ""+E.message, Toast.LENGTH_SHORT).show()}
        }

        override fun didError(manager: String) {
          progressBar.visibility=GONE
            rv.visibility= GONE
            imageView3.visibility= VISIBLE
           imageView3.setAnimation(R.raw.no)
            imageView3.repeatCount = LottieDrawable.INFINITE
           imageView3.playAnimation()


        }
    }
    private val onclick= object :Onclick{
        override fun onClick(text: String) {
            val service=getSystemService(CLIPBOARD_SERVICE)as ClipboardManager
            val clip=ClipData.newPlainText("copied",text)
            service.setPrimaryClip(clip)
            Toast.makeText(this@MainActivity, "copied !", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
           R.id.markerItem ->{
               startActivity(Intent(this,Saved::class.java))
           }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isSaved(response:ArrayList<QuotesModel>):ArrayList<QuotesModel>{
        val list2=Db.getAllCards()
        for (i in list2.indices){
            if (list2[i].flag=="true"){
                response[list2[i].position!!].flag="true"
            }
        }
        return response
    }

}