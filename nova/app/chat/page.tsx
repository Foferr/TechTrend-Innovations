"use client";

import Link from "next/link";
import "./styles.css";
import "../globals.css";
import React, { useState, useEffect, useRef } from 'react';
import { convertToSpeech } from './txt2sp';
import Noticias from './noticias';
import { generatePrompts, generateTitle } from './chat';
import withAuth from '../components/HOC/withAuth';
import axios from "axios";

const deleteLocalStorage = () => {
    localStorage.clear();
};

const Chat: React.FC = () => {
    const [isOverlayOpen, setIsOverlayOpen] = useState(false);
    const [isClosing, setIsClosing] = useState(false);
    const [isProfileOpen, setIsProfileOpen] = useState(false);
    const chatContentRef = useRef<HTMLDivElement>(null);

    const handleOpenOverlay = () => {
        setIsOverlayOpen(true);
        setIsClosing(false);
    };

    const handleKeyDown = async (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            await handlePrompts();
        }
    }

    const handleCloseOverlay = () => {
        setIsClosing(true);
        setTimeout(() => {
            setIsOverlayOpen(false);
            setIsClosing(false);
        }, 300);
    };

    const handleToggleProfile = () => {
        setIsProfileOpen(!isProfileOpen);
    };

    const [messages, setMessages] = useState<{ user: boolean; text: string }[]>([]);
    
    const handlePrompts = async () => {
        if (messages.length === 0) {
            const inputUser = (document.getElementById("inputUser") as HTMLInputElement).value;
            
            await axios.post(`http://localhost:8080/chatHistory/user/${localStorage.getItem('userId')}`, {
                status: 'active',
                userId: localStorage.getItem('userId'),
                title: (await generateTitle(inputUser))
            });
        }

        const inputUser = (document.getElementById("inputUser") as HTMLInputElement).value;
        (document.getElementById("inputUser") as HTMLInputElement).value = "";
        setMessages([...messages, { user: true, text: inputUser }]);
        messages.push({ user: true, text: inputUser });

        const prompts = await generatePrompts(inputUser);
        setMessages([...messages, { user: false, text: prompts }]);
        messages.push({ user: false, text: prompts });

        const chatHistoryResponse = await axios.get(`http://localhost:8080/chatHistory/user/${localStorage.getItem('userId')}`);
        const latestChatHistoryId = chatHistoryResponse.data[chatHistoryResponse.data.length - 1].id;

        await axios.post(`http://localhost:8080/messages/postMessage/${latestChatHistoryId}`, {
            senderType: 'user',
            messageContent: inputUser
        })
        .catch (error => {
            console.error(error);
        }); 

        await axios.post(`http://localhost:8080/messages/postMessage/${latestChatHistoryId}`, {
            senderType: 'nova',
            messageContent: prompts
        })
        .catch (error => {
            console.error(error);
        });
    };

    useEffect(() => {
        if (chatContentRef.current) {
            chatContentRef.current.scrollTop = chatContentRef.current.scrollHeight;
        }
    }, [messages]);

    return (
        <div className="mainContent">
            <div className="chatSection">
                <div className="upperDiv">
                    <Link href="/">
                        <img
                            src="/images/VectorNovaLogoBlueNT.svg"
                            alt=""
                        />
                    </Link>
                    <button>
                        <h1>NOVA</h1>
                    </button>
                    <button onClick={isOverlayOpen ? handleCloseOverlay : handleOpenOverlay}>
                        <img
                            src="/images/threelines.png"
                            alt=""
                        />
                    </button>
                </div>
                <div className="chatSection2">
                    <div className="chatContent" ref={chatContentRef}>
                        {messages.map((message, index) => (
                            <div key={index} className={`message ${message.user ? 'user' : 'nova'}`}>
                                <p>{message.text}</p>
                                <button onClick={() => convertToSpeech(message.text)}>
                                    <img src="/images/speaker.png" alt="" />
                                </button>
                            </div>
                        ))}
                    </div>
                    <div className="chatFooter">
                        <img
                            src="/images/mic.png"
                            alt=""
                        />
                        <input type="text" placeholder="Habla con Nova, nuestro acompañante de IA" id="inputUser" onKeyDown={handleKeyDown} />
                        <button onClick={handlePrompts}>
                            <img
                                src="/images/send.png"
                                alt=""
                            />
                        </button>
                    </div>
                </div>
            </div>

            {isOverlayOpen && (
                <div className={`overlay ${isOverlayOpen && !isClosing ? 'open' : 'close'}`}>
                    <div className="upperDivOverlay">
                        <button onClick={isOverlayOpen ? handleCloseOverlay : handleOpenOverlay}>
                            <img
                                src="/images/threelines.png"
                                alt=""
                            />
                        </button>
                        <div className="profileIcon">
                            <button onClick={handleToggleProfile}>
                                <img
                                    src="/images/account.svg"
                                    alt="Profile"
                                />
                            </button>
                            {isProfileOpen && (
                            <div className="overlayLinks">
                                <Link href="/chat/perfil" className="overlayLinksContent" style={{marginTop: '15%'}}>Perfil</Link>
                                <Link href="/chat/historial" className="overlayLinksContent" >Historial</Link>
                                <Link href="/chat/faq" className="overlayLinksContent"> FAQ </Link>
                                <Link href="/" className="overlayLinksContent" onClick={deleteLocalStorage}>Sign Out</Link>
                            </div>
                            )}
                        </div>
                    </div>
                    <div className="overlayContent" id="overlayContent">
                        <Noticias />
                    </div>
                </div>
            )}
        </div>
    );
}

export default withAuth(Chat);
//export default Chat;