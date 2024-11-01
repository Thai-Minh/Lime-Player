package com.example.limeplayer

import androidx.multidex.MultiDexApplication

class BaseApplication : MultiDexApplication() {
    companion object {
        lateinit var application: BaseApplication
    }
}