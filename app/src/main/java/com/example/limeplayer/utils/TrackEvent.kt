package com.example.limeplayer.utils

import com.example.limeplayer.BaseApplication
import com.google.firebase.analytics.FirebaseAnalytics

object TrackEvent {
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(BaseApplication.application)
}