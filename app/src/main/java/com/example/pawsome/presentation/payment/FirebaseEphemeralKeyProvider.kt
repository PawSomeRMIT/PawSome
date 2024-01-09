package com.example.pawsome.presentation.payment

import com.stripe.android.EphemeralKeyProvider
import com.stripe.android.EphemeralKeyUpdateListener

class FirebaseEphemeralKeyProvider: EphemeralKeyProvider {
    override fun createEphemeralKey(
        apiVersion: String,
        keyUpdateListener: EphemeralKeyUpdateListener
    ) {
        val data = hashMapOf(
            "api_version" to apiVersion
        )


    }
}