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
        <body>
            <div className="container">
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
                            <React.Fragment key={chat.id}>
                                <tr onClick={() => handleRowClick(chat.id)}>
                                    <td>{chat.id}</td> 
                                    <td>{chat.createdAt}</td>
                                    <td>{chat.status}</td>
                                </tr>
                                {expandedChatId === chat.id && (
                                    <tr>
                                        <td colSpan={3} className="messages-cell">
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
                                        </td>
                                    </tr>
                                )}
                            </React.Fragment>
                        )).reverse()}
                    </tbody>
                </table>
            </div>
        </body>
    );
}

export default withAuth(Historial);
