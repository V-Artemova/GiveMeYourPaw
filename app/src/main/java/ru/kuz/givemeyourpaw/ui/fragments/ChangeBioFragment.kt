package ru.kuz.givemeyourpaw.ui.fragments

import kotlinx.android.synthetic.main.fragment_change_bio.*
import ru.kuz.givemeyourpaw.R
import ru.kuz.givemeyourpaw.ui.utilits.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {
    override fun onResume() {
        super.onResume()
        settings_input_bio.setText(USER.bio)
    }
    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()
        REE_Database_Root.child(NODE_USERS).child(CURRENT_UID).child(CHILD_BIO).setValue(newBio)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToasts(getString(R.string.data_update))
                    USER.bio = newBio
                    fragmentManager?.popBackStack()
                }

            }
    }

}