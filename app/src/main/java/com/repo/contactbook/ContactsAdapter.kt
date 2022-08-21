package com.repo.contactbook

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class ContactsAdapter(private val context: Context, private val contactList: ArrayList<Heirarchy>):
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>(), Filterable {

    var contactFilterList = ArrayList<Heirarchy>()

    init {
        contactFilterList = contactList
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var contactName: TextView = itemView.tv_name
        var designationName: TextView = itemView.tv_designation
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.row_item, parent,
            false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return contactFilterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // initializing our views
        holder.contactName.text = contactList[position].contactName
        holder.designationName.text = contactList[position].designationName
        val contactNum = contactList[position].contactNumber

        // adding click listener for our calling image view
        holder.itemView.iv_phone.setOnClickListener {
            // calling a method to make a call
            makeCall(contactNum)
        }

        // adding click listener for our message image view
        holder.itemView.iv_message.setOnClickListener {
            // calling a method to send message
            sendMessage(contactNum)
        }
    }

    // method to filter by contactName
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charSearch = charSequence.toString()
                if (charSearch.isEmpty()) {
                    contactFilterList = contactList
                } else {
                    val resultList = ArrayList<Heirarchy>()
                    for (rows in contactList) {
                        if (rows.contactName.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(rows)
                        }
                    }
                    contactFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = contactFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                contactFilterList = results?.values as ArrayList<Heirarchy>
                notifyDataSetChanged()
            }
        }
    }

    private fun sendMessage(contactNumber: String?) {
        // calling an intent to send sms by passing contact number
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$contactNumber"))
        intent.putExtra("sms_body", "Enter your message")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    private fun makeCall(contactNumber: String?) {
        // calling an intent to make call by passing contact number
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$contactNumber")
        callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(callIntent)
    }
}