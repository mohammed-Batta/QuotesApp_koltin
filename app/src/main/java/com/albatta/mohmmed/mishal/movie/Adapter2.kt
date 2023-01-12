package com.albatta.mohmmed.mishal.movie

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.albatta.mohmmed.mishal.movie.MainActivity.Companion.Db
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item2.view.*
import java.util.logging.Handler
import kotlin.collections.ArrayList

class Adapter2(val context: Context, val quotesModel: ArrayList<Quote>,val listener: Onclick):RecyclerView.Adapter<Adapter2.ViewHolders>() {

    inner class ViewHolders(itemView: View): RecyclerView.ViewHolder(itemView) {
        val quote2: TextView = itemView.quote2
        val author2: TextView = itemView.author2
        val fab2:ImageView=itemView.fab2
        val shareSaved:ImageView=itemView.shareSaved
        val delete:ImageView=itemView.delete
        val rvItemHolder2:LinearLayout=itemView.rvItemHolder2
        val lin2:LinearLayout=itemView.lin2

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {
        val root= LayoutInflater.from(context).inflate(R.layout.item2, parent, false)
        return ViewHolders(root)

    }


    override fun getItemCount() = quotesModel.size
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolders, position: Int) {

        val list:Quote=quotesModel[position]
        holder.quote2.text=list.quote
        holder.author2.text=list.author
        holder.fab2.setOnClickListener {
            listener.onClick(list.quote.toString())
            holder.fab2.setImageResource(R.drawable.ic_baseline_check_24)
            val secondsDelayed = 1
            android.os.Handler(Looper.getMainLooper()).postDelayed(
                {
                    holder.fab2.setImageResource(R.drawable.ic_baseline_content_copy_24)
                }, secondsDelayed * 500.toLong())
        }


        holder.delete.setOnClickListener {
            Db.delete(list.id!!)
           quotesModel.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,quotesModel.size)
            notifyDataSetChanged()
        }

        holder.shareSaved.setOnClickListener {
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                val share = Share(context = context)
                holder.lin2.visibility = View.INVISIBLE
                val ready = share.getItemFromView(holder.rvItemHolder2)
                holder.lin2.visibility = View.VISIBLE
                share.shareImageAndText(ready!!)
            },  100.toLong())
        }
    }


}