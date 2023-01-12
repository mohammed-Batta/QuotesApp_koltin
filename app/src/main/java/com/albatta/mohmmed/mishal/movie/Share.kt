package com.albatta.mohmmed.mishal.movie

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import java.io.ByteArrayOutputStream

 class Share(val context: Context) {

     fun getItemFromView(v: View): Bitmap? {
        var screenshot: Bitmap? = null
        try {
            v.setBackgroundResource(R.drawable.srcs)
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            v.draw(canvas)
            v.setBackgroundColor(Color.parseColor("#E7E4EF"))
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        return screenshot
    }


    @SuppressLint("SuspiciousIndentation")
     fun shareImageAndText(bitmap:Bitmap) {
        Thread{
                val intent =  Intent(Intent.ACTION_SEND)
                val uri = getImageUri(context, bitmap)
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                intent.type = "image/png"
                context.startActivity(Intent.createChooser(intent, "مشاركة السؤال"))
        }.start()
    }


     private fun getImageUri(inContext: Context, inImage: Bitmap?): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage?.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver, inImage, "IMG_" + System.currentTimeMillis(), null)
        return Uri.parse(path)
    }
}