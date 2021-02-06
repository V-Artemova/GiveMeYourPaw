package ru.kuz.givemeyourpaw.ui.utilits

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


//этот слушатель будет отрабатывать один раз из-за такого слушателя
class AppValueEventListener (val onSuccess : (DataSnapshot?) -> Unit) : ValueEventListener
{
    override fun onDataChange(snapshot: DataSnapshot) {
        onSuccess (snapshot)
    }

    override fun onCancelled(error: DatabaseError) {

    }

}