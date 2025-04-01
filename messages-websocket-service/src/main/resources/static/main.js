'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        // Use the correct WebSocket URL
        var socket = new SockJS('http://localhost:9912/ws');
        stompClient = Stomp.over(socket);

        // Enable debug logging
        stompClient.debug = function(str) {
            console.log(str);
        };

        // Connect with headers
        var headers = {
            'X-Requested-With': 'XMLHttpRequest'
        };

        stompClient.connect(headers, 
            function(frame) {
                console.log('Connected: ' + frame);
                onConnected();
            },
            function(error) {
                console.error('STOMP error:', error);
                onError(error);
            }
        );
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to your personal topic (using username)
    console.log("hio")
    stompClient.subscribe('/topic/' + username + '.messages', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/chat",
        {},
        JSON.stringify({
            "id": "msg123",
            "friendChat": 12345,
            "sender": "john_doe",
            "recipient": "jane_smith",
            "content": "Hello, how are you?",
            "time": "2024-03-09T14:30:00",
            "status": "SENT"
        })
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {

        console.log("123")
        stompClient.send("/chat", {}, JSON.stringify({
            "id": "msg123",
            "friendChat": 12345,
            "sender": "john_doe",
            "recipient": "jane_smith",
            "content": "Hello, how are you?",
            "time": "2024-03-09T14:30:00",
            "status": "SENT"
        }));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');
    messageElement.classList.add('chat-message');

    var avatarElement = document.createElement('i');
    var avatarText = document.createTextNode(message.sender[0]);
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(message.sender);

    messageElement.appendChild(avatarElement);

    var usernameElement = document.createElement('span');
    var usernameText = document.createTextNode(message.sender);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    // Add timestamp
    var timeElement = document.createElement('span');
    var time = new Date(message.time).toLocaleTimeString();
    var timeText = document.createTextNode(time);
    timeElement.appendChild(timeText);
    timeElement.classList.add('message-time');
    messageElement.appendChild(timeElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
