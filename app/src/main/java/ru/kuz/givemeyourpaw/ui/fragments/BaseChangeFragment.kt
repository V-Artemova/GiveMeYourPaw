package ru.kuz.givemeyourpaw.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import ru.kuz.givemeyourpaw.MainActivity
import ru.kuz.givemeyourpaw.R
import ru.kuz.givemeyourpaw.ui.utilits.APP_ACTIVITY
import ru.kuz.givemeyourpaw.ui.utilits.hideKeyboard

open class BaseChangeFragment (layout:Int) : Fragment(layout) {
    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true) //для того чтобы включить меню в контексте
        (activity as MainActivity).mAppDrawer.disableDrawer() // отключаем драйвер во всех фрагментах, кроме найди друга
        hideKeyboard()
    }

    override fun onStop() {
        super.onStop()


    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            R.id.settings_confirm_change ->change()
        }
        return true
    }

   open  fun change() {
    }

}