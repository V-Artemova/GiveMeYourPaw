package ru.kuz.givemeyourpaw.ui.utilits

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import ru.kuz.givemeyourpaw.R
import java.util.zip.Inflater

fun showToasts (message : String) {
    Toast.makeText(APP_ACTIVITY, message,Toast.LENGTH_SHORT ).show()
}

fun AppCompatActivity.replaceActivity (activity: AppCompatActivity)
{
    var intent = Intent(this, activity::class.java)
    startActivity(intent)
}
fun AppCompatActivity.replaceFragment(fragment: Fragment,  addStack : Boolean = true) {
    if (addStack) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.dataContainer, fragment)
            .commit() //commit() - подтверление
    }
    else {
        supportFragmentManager.beginTransaction()
            .replace(R.id.dataContainer, fragment)
            .commit() //commit() - подтверление
    }

}
fun Fragment.replaceFragment(fragment:Fragment)
{
    fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(R.id.dataContainer,fragment)
        ?.commit()
}

fun hideKeyboard() {
    val imm: InputMethodManager =
        APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(APP_ACTIVITY.window.decorView.windowToken, 0)
}

fun ImageView.downloadAndSetImage (url:String) {
    Picasso.get()
        .load(url)//получаем картинку по url
        .fit()
        .placeholder(R.drawable.default_user) // картинка по умолчанию
        .into(this)// куда установить картинку, работает только с CircleImageView
}
