package com.paymorecase.domain.service

interface AudioPlayer {
    fun playBeep()
    fun announceText(text: String)
    fun shutdown()
}