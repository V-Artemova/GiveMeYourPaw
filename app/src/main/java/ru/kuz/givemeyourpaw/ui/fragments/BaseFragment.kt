package ru.kuz.givemeyourpaw.ui.fragments

import androidx.fragment.app.Fragment
import ru.kuz.givemeyourpaw.MainActivity

open class BaseFragment ( layout:Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).mAppDrawer.disableDrawer() // отключаем драйвер во всех фрагментах, кроме найди друга
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).mAppDrawer.enableDrawer() // отключаем драйвер во всех фрагментах, кроме найди друга

    }


}