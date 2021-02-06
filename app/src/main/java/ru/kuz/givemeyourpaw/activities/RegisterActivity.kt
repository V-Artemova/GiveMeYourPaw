package ru.kuz.givemeyourpaw.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.kuz.givemeyourpaw.R
import ru.kuz.givemeyourpaw.databinding.ActivityRegisterBinding
import ru.kuz.givemeyourpaw.ui.fragments.EnterPhoneNumberFragment
import ru.kuz.givemeyourpaw.ui.utilits.initFirebase
import ru.kuz.givemeyourpaw.ui.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_enter_phone_number)
        replaceFragment(EnterPhoneNumberFragment(), false)
    }

}