package ru.kuz.givemeyourpaw.models

data class User(
    val id: String = "",
    var city:String ="Улан-Удэ",
    var bio: String = "",
    var fullname: String = "",
    var phone:String="",
    var photoUrl: String = "empty"

)