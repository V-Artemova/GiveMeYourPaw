package ru.kuz.givemeyourpaw.ui.fragments

import androidx.fragment.app.Fragment
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*
import ru.kuz.givemeyourpaw.MainActivity
import ru.kuz.givemeyourpaw.R
import ru.kuz.givemeyourpaw.activities.RegisterActivity
import ru.kuz.givemeyourpaw.ui.utilits.*

class EnterCodeFragment(val phoneNumber: String, val id: String) : Fragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        register_input_code.addTextChangedListener(AppTextWatcher {

            val string: String = register_input_code.text.toString()
            if (string.length == 6) {
                enterCode()

            }
        })
    }

    private fun enterCode() {
        val code = register_input_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code) // передали  id и 123456
        AUTH.signInWithCredential(credential).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber
                dateMap[CHILD_CITY]=USER.city

                REE_Database_Root.child(NODE_USERS).child(uid).updateChildren(dateMap)
                    .addOnCompleteListener()
                    { task2 ->
                        if (task2.isSuccessful) {
                            showToasts("Добро пожаловать")
                            (activity as RegisterActivity).replaceActivity(MainActivity()) // переходим c регистрации к MainActivity (чаты)
                        } else (task2.exception?.message.toString())

                    }


            } else showToasts(task.exception?.message.toString()) // выводим СМС об ошибке, если что-то не так

        }
    }

}

