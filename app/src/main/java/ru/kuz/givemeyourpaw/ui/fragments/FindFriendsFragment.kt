package ru.kuz.givemeyourpaw.ui.fragments

import androidx.fragment.app.Fragment
import ru.kuz.givemeyourpaw.MainActivity
import ru.kuz.givemeyourpaw.R

class FindFriendsFragment : Fragment(R.layout.fragment_find_friends) {



	override fun onResume() {
		super.onResume()
		(activity as MainActivity).title = "Найди друга"

	}
}