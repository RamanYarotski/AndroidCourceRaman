package com.homework.hw5_2

object ContactList {
    private var contactList: ArrayList<Contact> = ArrayList()

    fun getContactList(): ArrayList<Contact> {
        return contactList
    }

    fun addContact(contact: Contact) {
        contactList.add(contact)
    }

    fun setContact(position: Int, contact: Contact) {
        contactList[position] = contact
    }

    fun getContact(position: Int): Contact {
        return contactList[position]
    }
}