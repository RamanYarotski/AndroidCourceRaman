package com.homework.hw11


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.homework.hw11.ContactListActivity.ListContactActionListener

class ContactListAdapter(private val listContactActionListener: ListContactActionListener)
    : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>(), Filterable {
    private val contactList: ArrayList<Contact> = ArrayList()
    private val contactListFull: ArrayList<Contact> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_of_recycler,
                parent, false)
        return ContactViewHolder(view, listContactActionListener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position])
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun getFilter(): Filter {
        return filter
    }

    fun addItems(contacts: ArrayList<Contact>) {
        contactListFull.clear()
        contactListFull.addAll(contacts)
        contactList.clear()
        contactList.addAll(contacts)
        notifyDataSetChanged()
    }

    private val filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<Contact> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(contactListFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (contact in contactListFull) {
                    if (contact.name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(contact)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            contactList.clear()
            contactList.addAll(results.values as ArrayList<Contact>)
            notifyDataSetChanged()
        }
    }

    inner class ContactViewHolder(
            contactView: View, listContactActionListener: ListContactActionListener)
        : RecyclerView.ViewHolder(contactView) {
        private val imageView: ImageView = contactView.findViewById(R.id.contactImage)
        private val nameView: TextView = contactView.findViewById(R.id.contactName)
        private val infoView: TextView = contactView.findViewById(R.id.contactInfo)

        fun bind(contact: Contact) {
            imageView.setImageResource(contact.image)
            nameView.text = contact.name
            infoView.text = contact.info
        }

        init {
            itemView.setOnClickListener {
                listContactActionListener.onContactClicked(layoutPosition)
            }
        }
    }

}

