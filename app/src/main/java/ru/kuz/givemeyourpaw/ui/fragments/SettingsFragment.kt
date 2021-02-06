package ru.kuz.givemeyourpaw.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.PopupMenu
import com.google.firebase.storage.StorageReference
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import ru.kuz.givemeyourpaw.MainActivity
import ru.kuz.givemeyourpaw.R
import ru.kuz.givemeyourpaw.activities.RegisterActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import ru.kuz.givemeyourpaw.ui.utilits.*


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private lateinit var mNewCity: String

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).title = getString(R.string.settings_title)
        setHasOptionsMenu(true)
        initFields()

    }

    private fun initFields() {
        settings_username.text = USER.fullname
        settings_city.text = USER.city
        settings_phone_number.text = USER.phone
        settings_bio.text = USER.bio
        settings_user_photo.downloadAndSetImage(USER.photoUrl)
        settings_btn_change_city.setOnClickListener {
            changeCity()
        }
        settings_btn_change_bio.setOnClickListener {
            replaceFragment(ChangeBioFragment())
        }
        settings_change_photo.setOnClickListener {
            changePhotoUser()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }

    private fun changeCity() {
        val popMenu = PopupMenu(activity as MainActivity, settings_btn_change_city)
        popMenu.menuInflater.inflate(R.menu.settings_menu_change_city, popMenu.menu)
        popMenu.setOnMenuItemClickListener { item ->

            when (item!!.itemId) {
                R.id.ulan_ude -> {
                    mNewCity = getString(R.string.settings_city_ulan_ude)

                }

                R.id.gusinoozersk -> {
                    mNewCity = getString(R.string.settings_city_gusinoozersk)
                }
            }
            updateCity()
            settings_city.setText(mNewCity)

            true
        }
        popMenu.show()
    }

    private fun updateCity() {
        REE_Database_Root.child(NODE_USERS).child(CURRENT_UID).child(CHILD_CITY)
            .setValue(mNewCity)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToasts(getString(R.string.data_update))
                } else {
                    showToasts(it.exception?.message.toString())
                }
            }
    }


    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1, 1) // чтобы можно было фотку вырезать только кввадратом
            .setRequestedSize(600, 600) // размер кропера
            .setCropShape(CropImageView.CropShape.OVAL) // чтобы была овальная фотка
            .start(APP_ACTIVITY, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REE_Storage_Root.child(FOLDER_PROFILE_IMAGE)
                .child(CURRENT_UID)
            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabase(it) {
                        settings_user_photo.downloadAndSetImage(it)
                        showToasts(getString(R.string.data_update))
                        USER.photoUrl = it
                        APP_ACTIVITY.mAppDrawer.updateHeader() // обновляем HEADER

                    }
                }
            }
        }
    }


}