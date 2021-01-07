package com.wibu.appe_saku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wibu.appe_saku.adapter.SantriRecyclerAdapter
import com.wibu.appe_saku.db.DatabaseHandler
import com.wibu.appe_saku.model.Santri

const val EXTRA_FORM_TYPE = "form_type"
const val ADD_SANTRI = "Tambah"
const val EDIT_SANTRI = "Edit"

class SantriListActivity : AppCompatActivity() {
    var santriRecyclerAdapter: SantriRecyclerAdapter? = null;
    var recyclerView: RecyclerView? = null
    var dbHandler: DatabaseHandler? = null
    var listSantri: List<Santri> = ArrayList()
    var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_santri_list)

        recyclerView = findViewById(R.id.recycler_view)
        santriRecyclerAdapter = SantriRecyclerAdapter(santriList = listSantri, context = applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        (recyclerView as RecyclerView).layoutManager = linearLayoutManager
//        initDB()
    }

    override fun onResume() {
        super.onResume()
        initDB()
    }

    fun initDB() {
        dbHandler = DatabaseHandler(this)
        listSantri = (dbHandler as DatabaseHandler).santri()
        santriRecyclerAdapter = SantriRecyclerAdapter(santriList = listSantri, context = applicationContext)
        (recyclerView as RecyclerView).adapter = santriRecyclerAdapter
    }

    fun addSantri(view: View) {
        val type = ADD_SANTRI
        val intent = Intent(this, SantriFormActivity::class.java)
            .apply {
                putExtra(EXTRA_FORM_TYPE, type)
            }
        startActivity(intent)
    }
}