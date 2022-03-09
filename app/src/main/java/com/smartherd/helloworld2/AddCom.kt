package com.smartherd.helloworld2

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.ActionBar
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_add_com.*
import java.util.jar.Manifest

class AddCom : AppCompatActivity() {

    //permission constants
    private val CAMERA_REQUEST_CODE = 100
    private val STORAGE_REQUEST_CODE = 101
    //image pick constants
    private val IMAGE_PICK_CAMERA_CODE = 102
    private val IMAGE_PICK_GALLERY_CODE = 103
    //arrays of permissions
    private lateinit var cameraPermission:Array<String> //camera and storage
    private lateinit var storagePermission:Array<String>    //only storage

    //variables that will contain data to save in database
    private var imageUri:Uri? = null
    private var name:String? =""
    private var address:String? =""
    private var type:String? =""
    private var dess: String? =""

    lateinit var dbHelper:DBHandlerCity

    //actionbar
    //private var actionBar:androidx.appcompat.app.ActionBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_com)

       // actionBar = supportActionBar
       // actionBar!!.title = "Add Comment"
      //  actionBar!!.setDisplayHomeAsUpEnabled(true)
     //   actionBar!!.setDisplayShowHomeEnabled(true)

        //init db helper class
        dbHelper = DBHandlerCity(this)

        //init permission arrays
        cameraPermission = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        storagePermission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        //click imageview to pick image
        addImageCom.setOnClickListener {
            //show image pick dialog
            imagePickDialog();
        }
        //click save button to save record
        btnSaveCom.setOnClickListener {
            inputData()
        }

        btnCancelCom.setOnClickListener {
            clearEdits()
            finish()
        }

    }
/*
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() //go back to previous activity
        return super.onSupportNavigateUp()
    }*/

    private fun inputData() {
        //get data
        name = "" + editPlaceName.text.toString().trim()
        address = "" + editAddress.text.toString().trim()
        type = "" + editType.text.toString().trim()
        dess = "" + editDescription.text.toString().trim()

        //save data to db
        val id = dbHelper.insertRecord(
            ""+name,
            ""+imageUri,
            ""+dess,
            ""+type,
            ""+address)
        Toast.makeText(this, "Record Added against ID $id", Toast.LENGTH_SHORT).show()
    }

    private fun imagePickDialog() {
        //option to display in dialog
        val options = arrayOf("Camera", "Gallery")
        //dialog
        val builder = AlertDialog.Builder(this)
        //title
        builder.setTitle("Pick Image From")
        //set items/options
        builder.setItems(options) {dialog, which ->
            //handle item clicks
            if(which == 0) {
                //camera clicked
                if(!checkCameraPermissions()) {
                    //permission not granted
                    requestCameraPermission()
                }
                else {
                    //permission already granted
                    pickFromCamera()
                }
            }
            else {
                //gallery clicked
                if(!checkStoragePermission()){
                    //permission not granted
                    requestStoragePermission()
                }
                else {
                    //permission already granted
                    pickFromGallery()
                }

            }
        }
        //show dialog
        builder.show()
    }

    private fun pickFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE)
    }

    private fun requestStoragePermission() {
        //request storage permission
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE)
    }

    private fun checkStoragePermission(): Boolean {
        //chech if storage permission is enabled or not
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun pickFromCamera() {
        //pick image from camera using Intent
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Image Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image Description")
        //put image uri
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //intent to open camera
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(
            cameraIntent,
            IMAGE_PICK_CAMERA_CODE
        )

    }

    private fun requestCameraPermission() {
        //request the camera permission
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE)
    }

    private fun checkCameraPermissions(): Boolean {
        //check if camera permissions are enabled or not also storage
        val results = ContextCompat.checkSelfPermission( this,
            android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val results1 = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

        return results && results1
    }


    private fun clearEdits() {
        editPlaceName.text.clear()
        editAddress.text.clear()
        editType.text.clear()
        editDescription.text.clear()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_REQUEST_CODE->{
                if (grantResults.isNotEmpty()){
                    //if allowed returns true otherwise false
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if(cameraAccepted && storageAccepted){
                        pickFromCamera()
                    }
                    else {
                        Toast.makeText(this, "Camera and Storage permissions are required", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            STORAGE_REQUEST_CODE->{
                if (grantResults.isNotEmpty()){
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (storageAccepted){
                        pickFromGallery()
                    }
                    else {
                        Toast.makeText(this, "Storage permissions is required", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //image picked from camera or gallery will be received here
        if(resultCode == Activity.RESULT_OK) {
            //image is picked
            if(requestCode == IMAGE_PICK_GALLERY_CODE) {
                //picked from gallery
                //crop image
                CropImage.activity(data!!.data)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this)
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE){
                    //picked from camera
                    //crop image
                    CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this)
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                //cropped image received
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    val resultUri = result.uri
                    imageUri = resultUri
                    //set image
                    addImageCom.setImageURI(resultUri)
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    //error
                    val error = result.error
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show()
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
