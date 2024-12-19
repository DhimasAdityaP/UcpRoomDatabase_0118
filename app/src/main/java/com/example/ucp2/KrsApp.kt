package com.example.ucp2

import android.app.Application
import com.ucp2.dependenciesinjection.AppContainer

class KrsApp : Application(){
    //fungsinya untuk menyimpan instance containerapp
    lateinit var AppContainer: AppContainer
    override fun onCreate() {
        //membuat instance containerapp
        super.onCreate()
        AppContainer = AppContainer(this)
        //instance adalah object yang dibuat dari class
    }
}