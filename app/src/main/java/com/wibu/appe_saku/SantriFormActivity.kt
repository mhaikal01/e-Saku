package com.wibu.appe_saku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SantriFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_santri_form)

        val form_type = intent.getIntExtra(EXTRA_FORM_TYPE, 0)

        // Capture the layout's TextView and set the string as its text
        val button = findViewById<Button>(R.id.formButton).apply {
            if (form_type == ADD_SANTRI) {
                text = "Tambah"
            } else if (form_type == EDIT_SANTRI) {
                text = "Edit"
            } else {
                text = "Error"
            }
        }
    }
}