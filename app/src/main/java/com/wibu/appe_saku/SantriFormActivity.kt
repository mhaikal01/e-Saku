package com.wibu.appe_saku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.wibu.appe_saku.db.DatabaseHandler
import com.wibu.appe_saku.model.Santri
import kotlinx.android.synthetic.main.activity_santri_form.*

class SantriFormActivity : AppCompatActivity() {
    var dbHandler: DatabaseHandler? = null
    var isEditMode = false
    var santriMoney = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_santri_form)

        val form_type = intent.getStringExtra(EXTRA_FORM_TYPE)

        findViewById<Button>(R.id.formButton).apply {
            if (form_type == ADD_SANTRI) {
                text = "Tambah"
            } else if (form_type == EDIT_SANTRI) {
                text = "Edit"
            } else {
                text = "Error"
            }
        }

        initDB()
        initOperations()
    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        if (intent != null && intent.getStringExtra(EXTRA_FORM_TYPE) == EDIT_SANTRI) {
            isEditMode = true
            val santri: Santri = dbHandler!!.getSantri(intent.getIntExtra("Id", 0))
            edtName.setText(santri.name)
            santriMoney = santri.money
        }
    }

    private fun initOperations() {
        formButton.setOnClickListener {
            var success = false
            if (!isEditMode) {
                val santri: Santri = Santri()
                santri.name = edtName.text.toString()
                santri.money = 0
                success = dbHandler?.addSantri(santri) as Boolean
            } else {
                val santri: Santri = Santri()
                santri.id = intent.getIntExtra("Id", 0)
                santri.name = edtName.text.toString()
                santri.money = santriMoney
                success = dbHandler?.updateSantri(santri) as Boolean
            }

            if (success)
                finish()
        }
    }
}