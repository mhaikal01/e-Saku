package com.wibu.appe_saku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.wibu.appe_saku.db.DatabaseHandler
import com.wibu.appe_saku.model.Santri
import kotlinx.android.synthetic.main.activity_santri.*
import kotlinx.android.synthetic.main.activity_santri_form.*

class SantriActivity : AppCompatActivity() {
    var dbHandler: DatabaseHandler? = null
    var idSantri: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_santri)
//        initDB()
    }

    override fun onResume() {
        super.onResume()
        initDB()
    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        if (intent != null) {
            val santri: Santri = dbHandler!!.getSantri(intent.getIntExtra("Id", 0))
            idSantri = santri.id
            tvId.text = "ID: ${santri.id}"
            tvName.text = santri.name
            tvMoney.text = "Rp. ${santri.money}"
        }
    }

    fun ambilMoney(view: View) {
        val type = "ambil"
        val intent = Intent(this, MoneyActivity::class.java)
            .apply {
                putExtra("money_type", type)
                putExtra("Id", idSantri)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        startActivity(intent)
    }

    fun simpanMoney(view: View) {
        val type = "simpan"
        val intent = Intent(this, MoneyActivity::class.java)
            .apply {
                putExtra("money_type", type)
                putExtra("Id", idSantri)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        startActivity(intent)
    }

    fun editSantri(view: View) {
        val type = EDIT_SANTRI
        val intent = Intent(this, SantriFormActivity::class.java)
            .apply {
                putExtra(EXTRA_FORM_TYPE, type)
                putExtra("Id", idSantri)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        startActivity(intent)
    }

    fun deleteSantri(view: View) {
        val dialog = AlertDialog.Builder(this).setTitle("Info")
            .setMessage("Hapus data santri? semua nominal uang juga akan terhapus")
            .setPositiveButton("YA") { dialog, i ->
                val success = dbHandler?.deleteSantri(intent.getIntExtra("Id", 0)) as Boolean
                if (success)
                    finish()
                dialog.dismiss()
            }
            .setNegativeButton("TIDAK") { dialog, i ->
                dialog.dismiss()
            }
        dialog.show()
    }
}