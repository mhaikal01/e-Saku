package com.wibu.appe_saku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.wibu.appe_saku.db.DatabaseHandler
import com.wibu.appe_saku.model.Santri
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_santri.*


class HomeActivity : AppCompatActivity() {
    var dbHandler: DatabaseHandler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onResume() {
        super.onResume()
        initDB()
    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        if (intent != null) {
            val money: Int = dbHandler!!.getAllMoney()
            tvHomeMoney.text = "Rp. $money"
        }
    }

    fun toSantri(view: View) {
        val intent = Intent(this, SantriListActivity::class.java)
        startActivity(intent)
    }
}