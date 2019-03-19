# What is this?
This is a very very very basic webserver for an android app focusing on background service. 

**The whole project is for educational purpose only** 

You can think of it as one single global chat room. 
A client can basically broadcast messages to all others in this single global chat room.
Additionally, images (photos) can be uploaded. 
This are also treated as any other message and are broadcasted to others.
The server holds no history of messages nor are uploaded images stored persistent.

# Hosting

The server instance runs on  https://chat-android-service.herokuapp.com/ 

# API

- As it is a chat app, you can send any message to the server through a WebSocket and receive messages from others.
url: `/ws`
- You can add an image to the chat by `POST /images` as mutlipart. The server expects to be one part of the multipart to be the image and another part  named `uuid` a unique identifier for the image which will also be used as the file name. Returns http 200 if uploading works. Also it broadcasts through the websocket the following message to let all clients know that a image is available:
    ```json
     {
        "type": "image",
        "imageUrl": "/images/Some-UUID-Specified-By-The-Uploader.png"
     }
    ```
- You can load an image via `GET /images/Some-UUID-Specified-By-The-Uploader.png`

You can see an example usage of the API in [WebSocketClient.kt](https://github.com/freeletics/Freeletics-Background-Service-Chat-App-Server/blob/master/src/WebSocketClient.kt) and [UploadClient.kt](https://github.com/freeletics/Freeletics-Background-Service-Chat-App-Server/blob/master/src/UploadClient.kt)