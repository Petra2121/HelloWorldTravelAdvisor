package com.smartherd.helloworld2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import kotlin.Exception

class DBHandlerCity (context : Context?) :
    SQLiteOpenHelper(context, Constants.DB_NAME, null, Constants.DB_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Constants.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS" + Constants.TABLE_NAME)
        onCreate(db)
    }

    //insert record to db
    fun insertRecord(
        name:String?,
        image:String?,
        desc:String?,
        type:String?,
        address:String?
    ):Long{
        //get writabale database because we need to write data
        val db = this.writableDatabase
        val values = ContentValues()
        //id will be inserted automatically as we set AUTOINCREMENT
        values.put(Constants.C_NAME, name)
        values.put(Constants.C_IMAGE, image)
        values.put(Constants.C_TYPE, type)
        values.put(Constants.C_ADDRESS, address)
        values.put(Constants.C_DESC, desc)

        //inserting row it will return record id of saved record
        val id = db.insert(Constants.TABLE_NAME, null, values)

        db.close()
        return id
    }
    //get all data
    fun getAllRecords(orderBy:String):ArrayList<ModelRecord> {
        val recordList = ArrayList<ModelRecord>()

        val selectQuery = "SELECT * FROM ${Constants.TABLE_NAME} ORDER BY $orderBy"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val modelRecord = ModelRecord(
                    ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDRESS)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_DESC)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_TYPE)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE))
                )
                //add record to list
                recordList.add(modelRecord)
            }while (cursor.moveToNext())
        }
        db.close()
        return recordList
    }
    //search data
    fun searchRecords(query:String):ArrayList<ModelRecord>{

        val recordList = ArrayList<ModelRecord>()

        val selectQuery = "SELECT * FROM ${Constants.TABLE_NAME} WHERE ${Constants.C_TYPE} LIKE % '$query' %"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val modelRecord = ModelRecord(
                     ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                     ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                     ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDRESS)),
                     ""+cursor.getString(cursor.getColumnIndex(Constants.C_DESC)),
                     ""+cursor.getString(cursor.getColumnIndex(Constants.C_TYPE)),
                     ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE))
                )
                recordList.add(modelRecord)
            }while (cursor.moveToNext())
        }
        db.close()
        return recordList

    }
}