"use client";

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import withAuth from '../../components/HOC/withAuth';
import "./styles.css";
import Link from "next/link";
import { useRef } from 'react';
import { convertToSpeech } from '../txt2sp';
import LanguageToggleButton from '@/app/components/LanguageToggleButton';
import { useLanguage } from '@/app/contexts/LanguageContext';

const userType = localStorage.getItem('userType');

const Historial: React.FC = () => {
    const { language } = useLanguage();

    const noMessageText = language === 'es' ? 'Selecciona un chat para ver tu historial de conversaciones.' : 'Select a chat to see your conversation history';
    const titleText = language === 'es' ? 'T'

    const [data, setData] = useState([]);
    const [expandedChatId, setExpandedChatId] = useState<number | null>(null);
    const [messages, setMessages] = useState([]);
    const messagesContentRef = useRef<HTMLDivElement>(null);


    useEffect(() => {
        axios.get(`http://localhost:8080/chatHistory/user/${localStorage.getItem('userId')}`)
            .then((response) => {
                setData(response.data);
            });
    }, []);

    const handleRowClick = (chatId: number) => {
        if (expandedChatId === chatId) {
            setExpandedChatId(null);
            setMessages([]);
        } else {
            console.log(`Chat id: ${chatId}`)
            axios.get(`http://localhost:8080/messages/byChatId/${chatId}`)
            .then((response) => {
                setExpandedChatId(chatId);
                setMessages(response.data);
                
            });
        }
    };

    useEffect(() => {
        // Scroll to bottom of messages when messages state changes
        if (messagesContentRef.current) {
            messagesContentRef.current.scrollTo({
                top: messagesContentRef.current.scrollHeight,
                behavior: 'smooth'
            });
        }
    }, [messages]);

    return (
        <div className="cont">
            <div className="history">
                <Link href={userType === 'admin' ? '/admin' : '/chat '}>
                    <img
                        src="/images/VectorNovaLogoJT.svg"
                        alt="back button"
                        className="back-button w-[20vh]"
                        />
                </Link>
                <table>
                    <thead>
                        <tr>
                            <th>Título</th>
                        </tr>
                    </thead>
                    <tbody>
                        {data.map((chat: any) => (
                            <tr 
                                key={chat.id} 
                                onClick={() => handleRowClick(chat.id)} 
                                className={expandedChatId === chat.id ? 'selected' : ''}
                            >
                                <td>{chat.title}</td>
                            </tr>
                        )).reverse()}
                    </tbody>
                </table>
            </div>
            <div className="messages" id="messages" ref={messagesContentRef}>
                {expandedChatId ? (
                    <div>
                        <ul>
                            {messages.map((message: any) => (
                                <li
                                    key={message.id}
                                    className={`message ${message.senderType === 'user' ? 'user' : 'nova'}`}
                                >
                                    {message.messageContent}
                                    <button onClick={() => convertToSpeech(message.messageContent)}>
                                        <img className="pt-2"
                                             src="/images/tts.svg" alt="" />
                                    </button>
                                </li>
                            ))}
                        </ul>
                        
                    </div>
                ) : (
                    <div className="no-chat-message">
                        {noMessageText}
                    </div>
                )}
            </div>
            <LanguageToggleButton/>
        </div>
    );
}

export default withAuth(Historial);
