"use client";

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import withAuth from "@/app/components/HOC/withAuth";
import "./styles.css";

const Historial: React.FC = () => {
    const [data, setData] = useState([]);
    const [expandedChatId, setExpandedChatId] = useState<number | null>(null);
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/chatHistory/user/1')
            .then((response) => {
                setData(response.data);
            });
    }, []);

    const handleRowClick = (chatId: number) => {
        if (expandedChatId === chatId) {
            setExpandedChatId(null);
            setMessages([]);
        } else {
            axios.get(`http://localhost:8080/messages/byChatId/${chatId}`)
                .then((response) => {
                    console.log(response.data);
                    setExpandedChatId(chatId);
                    setMessages(response.data);
                });
        }
    };

    return (
        <div className="container">
            <div className="history">
                <h1>Historial de chats</h1>
                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Fecha</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        {data.map((chat: any) => (
                            <tr 
                                key={chat.id} 
                                onClick={() => handleRowClick(chat.id)} 
                                className={expandedChatId === chat.id ? 'selected' : ''}
                            >
                                <td>{chat.id}</td>
                                <td>{chat.createdAt}</td>
                                <td>{chat.status}</td>
                            </tr>
                        )).reverse()}
                    </tbody>
                </table>
            </div>
            <div className="messages">
                {expandedChatId && (
                    <div>
                        <h2>Messages</h2>
                        <ul>
                            {messages.map((message: any) => (
                                <li
                                    key={message.id}
                                    className={`message ${message.senderType === 'user' ? 'user' : 'nova'}`}
                                >
                                    {message.messageContent}
                                </li>
                            ))}
                        </ul>
                    </div>
                )}
            </div>
        </div>
    );
}

export default withAuth(Historial);
