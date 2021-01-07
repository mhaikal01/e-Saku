package com.wibu.appe_saku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.wibu.appe_saku.db.DatabaseHandler
import com.wibu.appe_saku.model.Santri
import kotlinx.android.synthetic.main.activity_money.*
import kotlinx.android.synthetic.main.activity_santri_form.*

class MoneyActivity : AppCompatActivity() {
    var dbHandler: DatabaseHandler? = null
    var transactitionType = ""
    var firstMoneyNominal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money)

        initDB()
        initOperations()
    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        if (intent != null) {
            val santri: Santri = dbHandler!!.getSantri(intent.getIntExtra("Id", 0))
            tvMoney.text = "Rp. ${santri.money}"
            if (intent.getStringExtra("money_type") == "ambil") {
                transactitionType = "ambil"
                moneyBtn.text = "Ambil"
                firstMoneyNominal = santri.money
            } else if (intent.getStringExtra("money_type") == "simpan") {
                transactitionType = "simpan"
                moneyBtn.text = "Simpan"
            }
        }

    }

    private fun initOperations() {
        moneyBtn.setOnClickListener {
            var success = false
            if (transactitionType == "simpan") {
                val santri = Santri()
                santri.id = intent.getIntExtra("Id", 0)
                santri.money = Integer.parseInt(edtMoney.text.toString())
                success = dbHandler?.updateSantri(santri) as Boolean
            } else if (transactitionType == "ambil") {
                val santri = Santri()
                santri.id = intent.getIntExtra("Id", 0)
                val ambilNominal = Integer.parseInt(edtMoney.text.toString())
                if (ambilNominal > firstMoneyNominal) {
                    errorMoneyDialog()
                } else {
                    santri.money = firstMoneyNominal - ambilNominal
                    success = dbHandler?.updateSantri(santri) as Boolean
                }
            }

            if (success)
                finish()
        }
    }

    fun errorMoneyDialog() {
        val dialog = AlertDialog.Builder(this).setTitle("Info")
            .setMessage("Nominal Uang Tidak Mencukupi")
            .setPositiveButton("OK") { dialog, i ->
                dialog.dismiss()
            }
        dialog.show()
    }
}