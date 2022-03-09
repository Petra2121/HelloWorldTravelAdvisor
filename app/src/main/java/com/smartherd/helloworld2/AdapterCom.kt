package com.smartherd.helloworld2

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//(val arrayList: ArrayList<ModelRecord>, context : Context)

class AdapterCom() : RecyclerView.Adapter<AdapterCom.HolderRecord>() {

    private var context: Context?=null
    private var recordList:ArrayList<ModelRecord>?=null

    constructor(context: Context?, recordList: ArrayList<ModelRecord>?) : this(){
        this.context = context
        this.recordList = recordList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderRecord {
        //inflate the layout onecom
        return HolderRecord(
            LayoutInflater.from(context).inflate(R.layout.activity_onecom, parent, false)
        )
    }

    override fun getItemCount(): Int {
        //return items
        return recordList!!.size
    }

    override fun onBindViewHolder(holder: HolderRecord, position: Int) {
        //get data
        val model = recordList!![position]
        val id = model.placeID
        val name = model.placeName
        val address = model.placeAddress
        val desc = model.placeDescription
        val image = model.placeImage
        val type = model.placeType

        //set data to views
        holder.c_name.text = name
        holder.c_address.text = address
        holder.c_desc.text = desc
        holder.c_type.text = type

        if(image == "null") {
            holder.c_image.setImageResource(R.drawable.ic_camera_black)
        }
        else {
            holder.c_image.setImageURI(Uri.parse(image))
        }

        /*
        holder.moreBtn.setOnClickListener {
            //delete and edit
        }*/

    }

    inner class HolderRecord(itemView: View):RecyclerView.ViewHolder(itemView) {
     //views from row
        var c_image:ImageView = itemView.findViewById(R.id.c_image)
        var c_name:TextView = itemView.findViewById(R.id.c_placename)
        var c_type:TextView = itemView.findViewById(R.id.c_type)
        var c_address:TextView = itemView.findViewById(R.id.c_address)
        var c_desc:TextView = itemView.findViewById(R.id.c_description)
    }

}