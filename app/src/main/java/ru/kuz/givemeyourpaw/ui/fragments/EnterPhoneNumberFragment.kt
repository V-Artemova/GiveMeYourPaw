package ru.kuz.givemeyourpaw.ui.fragments

import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_phone_number.*
import ru.kuz.givemeyourpaw.MainActivity
import ru.kuz.givemeyourpaw.R
import ru.kuz.givemeyourpaw.activities.RegisterActivity
import ru.kuz.givemeyourpaw.ui.utilits.*
import java.util.concurrent.TimeUnit

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {
    private lateinit var mPhoneNumber: String


    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onStart() {
        super.onStart()
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) { //  если все хорошо, правильная верификация
// credential - объект, позволяющий произвести создание нового пользователя, запускается, если верификация уже была пройдена заранее
                AUTH.signInWithCredential(credential).addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        (activity as RegisterActivity).replaceActivity(MainActivity()) // переходим c регистрации к MainActivity (чаты)
                    } else showToasts(task.exception?.message.toString()) // выводим СМС об ошибке, если что-то не так
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) { // если не прошла верификация
                showToasts(p0.message.toString())
            }

            override fun onCodeSent(
                id: String,
                Token: PhoneAuthProvider.ForceResendingToken
            ) { // когда отправляется СМС
                // Token - нужен когда поменяем ориентацию экрана
                replaceFragment(EnterCodeFragment(mPhoneNumber, id))
            }
        }
        register_btn_next.setOnClickListener() { sendCode() }

    }

    private fun sendCode() {
        if (register_input_phone_number.text.toString().isEmpty()) {
            showToasts(getString(R.string.register_error_enter_phone_number))

        } else {
            authUser()

        }
    }

    private fun authUser() {
        mPhoneNumber = register_input_phone_number.text.toString()
        USER.phone = mPhoneNumber
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNumber, 60, TimeUnit.SECONDS, activity as RegisterActivity, mCallback
        )

    }
/*
	implementation 'com.google.firebase:firebase-auth:20.0.2'
*/


}
