package com.albatta.mohmmed.mishal.movie

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.albatta.mohmmed.mishal.movie.MainActivity.Companion.Db
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.item.view.*
import java.util.logging.Handler
import kotlin.collections.ArrayList

class Adapter(private val c: Context, private val quotesModel: List<QuotesModel>, private val listener: Onclick)
    :RecyclerView.Adapter<Adapter.ViewHolders>() {

    inner class ViewHolders(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quote: TextView = itemView.quote
        val author: TextView = itemView.author
        val fab: ImageView = itemView.fab
        val marker: ImageView = itemView.marker
        val share: ImageView = itemView.share
        val rvItemHolder: LinearLayout = itemView.rvItemHolder
        val lin: LinearLayout = itemView.lin
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {
        val root = LayoutInflater.from(c).inflate(R.layout.item, parent, false)
        return ViewHolders(root)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        val list: QuotesModel = quotesModel[position]


        holder.quote.text = list.text
        holder.author.text = list.author
        holder.fab.setOnClickListener {
            listener.onClick(list.text.toString())
            holder.fab.setImageResource(R.drawable.ic_baseline_check_24)
            val secondsDelayed = 1

            android.os.Handler(Looper.getMainLooper()).postDelayed({
                holder.fab.setImageResource(R.drawable.ic_baseline_content_copy_24)
            }, secondsDelayed * 500.toLong())
        }

        if (list.flag == "true") {
            holder.marker.setImageResource(R.drawable.t_24)
        } else {
            holder.marker.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
        }


        holder.marker.setOnClickListener {
            if (list.flag == "false") {
                val test =
                    Db.addCard(list.text.toString(), list.author.toString(), "true", position)
                if (test) {
                    list.flag = "true"
                    holder.marker.setImageResource(R.drawable.t_24)
                    Toast.makeText(c, "Saved to My ِِArchives", Toast.LENGTH_SHORT).show()
                }
            } else
                Toast.makeText(c, "this item is already in My Archives", Toast.LENGTH_SHORT).show()

        }
        holder.share.setOnClickListener {
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                    val share = Share(context = c)
                    holder.lin.visibility = INVISIBLE
                    val ready = share.getItemFromView(holder.rvItemHolder)
                    holder.lin.visibility = VISIBLE
                    share.shareImageAndText(ready!!)
            },  100.toLong())
        }

    }
    override fun getItemCount() = quotesModel.size
}

