package com.checkify

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

fun main() {

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://0.0.0.0:8080/ws")
        .build()

    val listener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            println("Received $text")
        }
    }
    val ws = client.newWebSocket(request, listener)

    println("sending")
    ws.send("Hello")
    ws.send("World")
}