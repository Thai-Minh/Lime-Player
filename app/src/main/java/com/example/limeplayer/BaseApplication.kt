package com.example.limeplayer

import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp

class BaseApplication : MultiDexApplication() {
    companion object {
        lateinit var application: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}