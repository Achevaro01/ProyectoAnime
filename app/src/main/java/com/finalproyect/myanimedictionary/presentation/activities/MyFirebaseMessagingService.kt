package com.finalproyect.myanimedictionary.presentation.activities

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

open class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        Looper.prepare()

        Handler().post {
            Toast.makeText(baseContext, p0.notification?.title, Toast.LENGTH_SHORT).show()
        }

        Looper.loop()
    }

}