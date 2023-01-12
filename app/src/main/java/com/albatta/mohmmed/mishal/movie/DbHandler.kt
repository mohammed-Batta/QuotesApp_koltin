package com.albatta.mohmmed.mishal.movie

import android.content.AttributionSource
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.nio.file.attribute.AclEntryFlag

class DBHandler(context:Context, name: String?, factory: SQLiteDatabase.CursorFactory?)
    :SQLiteOpenHelper(context,name,factory,15) {



    override fun onCreate(x: SQLiteDatabase?) {
        x?.execSQL("create table Card(id integer primary key autoincrement,quote text,author text,flag text,position integer)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS " + "Card")
        onCreate(p0)
    }

    fun addCard(quote:String,author:String,flag:String,position:Int):Boolean{
        val db=this.writableDatabase
        val cv= ContentValues()
        cv.put("quote",quote)
        cv.put("author",author)
        cv.put("flag",flag)
        cv.put("position",position)
        val result=db.insert("Card",null,cv)
        return result !=-1L
    }

    fun getAllCards():ArrayList<Quote> {
        val myArray = ArrayList<Quote>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from Card", null)
        while (cursor.moveToNext()){
           val id=cursor.getInt(0)
            val quote=cursor.getString(1)
            val author=cursor.getString(2)
            val flag=cursor.getString(3)
            val position=cursor.getInt(4)
            myArray.add(Quote(id,quote, author,flag,position))
        }
        cursor.close()
        db.close()
        return myArray
    }

    fun delete(id: Int):Boolean{
        val s="Delete From Card where id = $id"
        val db=this.readableDatabase
        var re=false
        try {
            db.execSQL(s)
            re=true
        }catch (ex:Exception){
            Log.e("asd","Error,delete")
        }
        db.close()
        return re
    }

}