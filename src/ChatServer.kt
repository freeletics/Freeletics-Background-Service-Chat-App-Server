package com.checkify

import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import kotlinx.coroutines.channels.ClosedSendChannelException
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

/**
 * Class in charge of the logic of the chat server.
 * It contains handlers to events and commands to send messages to specific users in the server.
 */
class ChatServer {

    /**
     * Associates a session-id to a set of websockets.
     * Since a browser is able to open several tabs and windows with the same cookies and thus the same session.
     * There might be several opened sockets for the same client.
     */
    private val members: MutableList<WebSocketSession> = CopyOnWriteArrayList()

    /**
     * Handles that a member identified with a session id and a socket joined.
     */
    suspend fun memberJoin(socket: WebSocketSession) {
        members.add(socket)
        println("Memeber joined: $members")
    }

    /**
     * Handles that a [member] with a specific [socket] left the server.
     */
    suspend fun memberLeft(socket: WebSocketSession) {
        members.remove(socket)
        println("Memeber left: $members")
    }

    /**
     * Handles a [message] sent from a [sender] by notifying the rest of the users.
     */
    suspend fun messageExcludingSender(sender: WebSocketSession, message: String) {
        println("Sending $message")
        members.filter { it != sender }
            .forEach {
                it.send(Frame.Text(message))
            }
    }

    suspend fun broadcast(message: String) {
        println("broadcasting $message")
        members.forEach {
            it.send(Frame.Text(message))
        }
    }
}