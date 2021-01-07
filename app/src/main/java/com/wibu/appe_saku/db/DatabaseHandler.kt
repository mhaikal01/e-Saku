package com.wibu.appe_saku.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.wibu.appe_saku.model.Santri
import java.util.*

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DatabaseHandler.DB_NAME, null, DatabaseHandler.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $NAME TEXT, $MONEY INTEGER);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addSantri(santri: Santri): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, santri.name)
        values.put(MONEY, santri.money)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedId", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    fun getSantri(_id: Int): Santri {
        val santri = Santri()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        santri.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        santri.name = cursor.getString(cursor.getColumnIndex(NAME))
        santri.money = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MONEY)))
        cursor.close()
        return santri
    }

    fun santri(): List<Santri> {
        val santriList = ArrayList<Santri>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val santri = Santri()
                    santri.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    santri.name = cursor.getString(cursor.getColumnIndex(NAME))
                    santri.money = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MONEY)))
                    santriList.add(santri)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return santriList
    }

    fun updateSantri(santri: Santri): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        if (santri.name.isNotEmpty()) {
            values.put(NAME, santri.name)
        }

        values.put(MONEY, santri.money)

        val _success =
            db.update(TABLE_NAME, values, ID + "=?", arrayOf(santri.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteSantri(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteAllSantri(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun getAllMoney(): Int {
        val db = writableDatabase
        val selectQuery = "SELECT SUM($MONEY) as $MONEY FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        val money = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MONEY)))
        cursor.close()
        return money
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "ESaku"
        private val TABLE_NAME = "Santri"
        private val ID = "Id"
        private val NAME = "Name"
        private val MONEY = "Money"
    }
}