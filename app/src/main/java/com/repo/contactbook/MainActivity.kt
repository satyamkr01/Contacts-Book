package com.repo.contactbook

import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://demo2143341.mockable.io/"

class MainActivity : AppCompatActivity() {

    lateinit var contactsAdapter : ContactsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                contactsAdapter.filter.filter(newText)
                return false
            }

        })

        recyclerview_contact.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_contact.layoutManager = linearLayoutManager
        getContacts()
    }

    private fun getContacts() {
        val retrofitBuild = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ContactsApi::class.java)

        val retrofitData = retrofitBuild.getContacts()
        retrofitData.enqueue(object : Callback<Contacts> {

            override fun onResponse(call: Call<Contacts>, response: Response<Contacts>) {
                val responseBody =
                    response.body()?.dataObject?.get(0)?.myHierarchy!![0].heirarchyList
                contactsAdapter = ContactsAdapter(baseContext, responseBody as ArrayList<Heirarchy>)
                recyclerview_contact.adapter = contactsAdapter
                contactsAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Contacts>, t: Throwable) {
                d("MainActivity","onFailure: "+t.message)
                Toast.makeText(applicationContext,"Something went wrong.",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

}