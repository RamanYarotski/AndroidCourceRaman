package com.homework.hw4_2_kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customView: CustomButtonView = findViewById(R.id.customView)
        customView.kp = object:myClicklListener{
            override fun action() {
                TODO("Not yet implemented")
            }
        }
        val switchMaterial = findViewById<SwitchMaterial>(R.id.toastOrSnackBarSwitch)

        fun congratulation() {
            if (customView.isVictory()) {
                Snackbar.make(customView,
                        "=== ПОЗДРАВЛЯЮ ВЫ ПОБЕДИЛИ ! ===",
                        Snackbar.LENGTH_LONG).setTextColor(ContextCompat.getColor(this,
                        R.color.red)).show()
            }
        }

        customView.setOnClickListener {
            if (!switchMaterial.isChecked) {
                Toast.makeText(applicationContext,
                        customView.coordinates(),
                        Toast.LENGTH_SHORT).show()
                congratulation()
            } else {
                val snackBar = Snackbar.make(it, customView.coordinates().toString(),
                        Snackbar.LENGTH_SHORT)
                when (customView.indicator()) {
                    1 -> snackBar.setTextColor(customView.firstQuarterColor())
                    2 -> snackBar.setTextColor(customView.secondQuarterColor())
                    3 -> snackBar.setTextColor(customView.thirdQuarterColor())
                    4 -> snackBar.setTextColor(customView.fourthQuarterColor())
                    5 -> snackBar.setTextColor(ContextCompat.getColor(this, R.color.white))
                    6 -> snackBar.setTextColor(ContextCompat.getColor(this, R.color.yellow))
                }
                snackBar.show()
                congratulation()
            }
        }
    }
}


