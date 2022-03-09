package com.smartherd.helloworld2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.activity_recyclerview.*
import java.util.*

class CommentSection : AppCompatActivity() {

    //db helper
    lateinit var dbHelper: DBHandlerCity

    private val TYPE_FIRST = "${Constants.C_NAME} DESC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        //init db helper
        dbHelper = DBHandlerCity(this)

        loadRecords()

        fab.setOnClickListener {
            val i = Intent (this, AddCom::class.java)
            startActivity(i)
        }

    }

    private fun loadRecords() {
        val adapterRecords = AdapterCom (this, dbHelper.getAllRecords(TYPE_FIRST))

        rv_com.adapter = adapterRecords
    }

    private fun searchRecords(query:String) {
        val adapterRecords = AdapterCom (this, dbHelper.searchRecords(query))

        rv_com.adapter = adapterRecords
    }

    override fun onResume() {
        loadRecords()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menucom, menu)

        val item = menu?.findItem(R.id.searchCom)

        if(item != null) {

            val searchView = item.actionView as SearchView

            searchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    //search as you type
                    if (newText != null) {
                        searchRecords(newText)
                    }
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    //search when search button on keyboard is clicked
                    if (query != null) {
                        searchRecords(query)
                    }
                    return true
                }
            })

        }
       return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}