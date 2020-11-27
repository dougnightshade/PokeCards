package com.manickchand.pokecards.utils

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.util.Log

class PokeCardsService : Service() {

    override fun onBind(p0: Intent?) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logService()
        return START_STICKY
    }

    private fun logService(){
        Handler().postDelayed({
            Log.i("Pokecards", "Service pokecards")
            logService()
        }, 3000)
    }
}