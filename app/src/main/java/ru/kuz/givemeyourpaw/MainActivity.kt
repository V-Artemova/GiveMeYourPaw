package ru.kuz.givemeyourpaw

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.theartofdev.edmodo.cropper.CropImage
import ru.kuz.givemeyourpaw.activities.RegisterActivity
import ru.kuz.givemeyourpaw.databinding.ActivityMainBinding
import ru.kuz.givemeyourpaw.models.User
import ru.kuz.givemeyourpaw.ui.fragments.FindFriendsFragment
import ru.kuz.givemeyourpaw.ui.objects.AppDrawer
import ru.kuz.givemeyourpaw.ui.utilits.*


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToolbar: Toolbar
    lateinit var mAppDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFirebase() // 1. FIREBASE
        initUser { //2.USER
            initFields()
            initFunc()

        }
        // все делается один раз
    }


    private fun initFunc() { // функциональность активити - нарисовать mToolbar, mHeader
        if (AUTH.currentUser != null) {// если поолтзователь авторизован
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(FindFriendsFragment(), false)
        } else { // если не авторизован
            replaceActivity(RegisterActivity())
        }
    }


    private fun initFields() { // инициализация объектов mBinding, mDrawer, mHeader,mToolbar
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)

    }


}