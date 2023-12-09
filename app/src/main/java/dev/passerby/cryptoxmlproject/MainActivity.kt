package dev.passerby.cryptoxmlproject

import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

        val lang = when(preferences.getInt("langId", 0)){
             1-> "ru"
             2-> "uk"
             3-> "es"
             4-> "kk"
             else -> "en"
        }

        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.setLocale(myLocale)
        res.updateConfiguration(conf, dm)

        setContentView(R.layout.activity_main)
    }
}