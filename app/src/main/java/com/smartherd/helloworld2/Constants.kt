package com.smartherd.helloworld2

object Constants {
    //db name
    const val DB_NAME = "RECORD_DB"
    //db version
    const val DB_VERSION = 1
    //table name
    const val TABLE_NAME = "RECORDS_TABLE"
    //columns/fields of table
    const val C_ID = "ID"
    const val C_NAME = "NAME"
    const val C_IMAGE = "IMAGE"
    const val C_TYPE = "TYPE"
    const val C_DESC = "DESCRIPTION"
    const val C_ADDRESS = "ADDRESS"

    //create table query
    const val CREATE_TABLE = (
            "CREATE TABLE " + TABLE_NAME + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_NAME + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_DESC + " TEXT,"
            + C_TYPE + " TEXT,"
            + C_ADDRESS + " TEXT"
            + ")"
            )
}