package ru.kuz.givemeyourpaw.ui.utilits

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ru.kuz.givemeyourpaw.models.User

lateinit var AUTH: FirebaseAuth
lateinit var REE_Database_Root: DatabaseReference
lateinit var USER: User
lateinit var CURRENT_UID: String
lateinit var REE_Storage_Root: StorageReference

const val FOLDER_PROFILE_IMAGE = "profile_image"

const val NODE_USERS = "users"
const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_FULLNAME = "fullname"
const val CHILD_CITY = "city"
const val CHILD_BIO = "bio"
const val CHILD_PHOTO_URL = "photoUrl"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REE_Database_Root = FirebaseDatabase.getInstance().reference
    USER = User()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
    REE_Storage_Root = FirebaseStorage.getInstance().reference
}

inline fun putUrlToDatabase(
    url: String,
    crossinline function: () -> Unit
) { // мы не создаем функцию мы выполняем код
    REE_Database_Root.child(NODE_USERS).child(CURRENT_UID)
        .child(CHILD_PHOTO_URL).setValue(url)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToasts(it.message.toString()) }

}

inline fun getUrlFromStorage(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl.addOnSuccessListener { function(it.toString()) } // скачиваем ссылку, то есть it -> это ссылка
        .addOnFailureListener { showToasts(it.message.toString()) }

}

inline fun putImageToStorage(uri: Uri, path: StorageReference, crossinline function: () -> Unit) {
    path.putFile(uri).addOnSuccessListener { function() }
        .addOnFailureListener { showToasts(it.message.toString()) }

}

inline fun initUser(crossinline function: () -> Unit) {
    REE_Database_Root.child(NODE_USERS).child(CURRENT_UID)
        .addListenerForSingleValueEvent(AppValueEventListener {
            USER = it?.getValue(User::class.java)
                ?: User()// инициализация нул полей,?: если не нулл -> то что слева, если нулл -> то что справа
            function()
        })
}
