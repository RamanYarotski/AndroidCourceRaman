package com.homework.hw5_2

class Contact(
         var name: String, var info: String, var infoType: InfoType) {

    var image: Int
        private set

    private fun setImage(infoType: InfoType): Int {
        if (infoType == InfoType.PHONE_NUMBER) {
            image = R.drawable.ic_baseline_contact_phone_24
        } else if (infoType == InfoType.EMAIL) {
            image = R.drawable.ic_baseline_contact_mail_24
        }
        return image
    }

    init {
        image = setImage(infoType)
    }

    enum class InfoType { PHONE_NUMBER, EMAIL }
}


