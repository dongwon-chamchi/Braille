package com.dongkun.braille.util

import android.widget.Toast
import com.dongkun.braille.MyApplication

class Util {
    companion object {
        fun showNotification(msg: String) {
            Toast.makeText(MyApplication.applicationContext(), msg, Toast.LENGTH_SHORT).show()
        }
    }
}
