package com.tolstoy.zurichat.ui.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tolstoy.zurichat.R

class ProfileActivity: AppCompatActivity() {

    private lateinit var savedName : TextView
    private lateinit var savedAbout : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        savedName = findViewById(R.id.saved_name)
        savedAbout = findViewById(R.id.saved_about)

        val about = findViewById<ImageView>(R.id.edit_about)
        val camera = findViewById<ImageView>(R.id.img_camera)
        val userName = findViewById<ImageView>(R.id.edit_name)
        val dialog = CreateDialog(layoutInflater,this)

        val editNameDialog = dialog.createEditNameDialog(savedName)
        val editAboutDialog = dialog.createEditAboutDialog(savedAbout)

        userName.setOnClickListener {
            editNameDialog.show()
        }

        about.setOnClickListener {
            editAboutDialog.show()
        }

        camera.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view=layoutInflater.inflate(R.layout.dialog_layout,null)
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()

        }
    }

}