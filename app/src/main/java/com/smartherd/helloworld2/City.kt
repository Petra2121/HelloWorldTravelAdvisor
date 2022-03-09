package com.smartherd.helloworld2


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_city.*

class City : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        // get data from putExtra intent
        var intent = intent
        var aTitle = intent.getStringExtra("iTitle")
        var aDescr = intent.getStringExtra("iDescr")
        val aDescription = intent.getStringExtra("iDescription")
        val aImageView = intent.getIntExtra("iImageView", 0)

        // set title in another activity
        actionBar?.title = aTitle
        a_title.text = aTitle
        a_description.text = aDescription
        desc_city.text = aDescr
        imageView.setImageResource(aImageView)

        city_button.setOnClickListener {
        val intent = Intent(this, CommentSection::class.java)
        startActivity(intent)
        }
    }
}
