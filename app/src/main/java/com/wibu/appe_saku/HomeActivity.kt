package com.wibu.appe_saku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

const val EXTRA_FORM_TYPE = "form_type"
const val ADD_SANTRI = 1
const val EDIT_SANTRI = 2

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun addSantri(view: View) {
        val type = EDIT_SANTRI
        val intent = Intent(this, SantriFormActivity::class.java).apply {
            putExtra(EXTRA_FORM_TYPE, type)
        }
        startActivity(intent)
    }
}