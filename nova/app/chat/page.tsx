"use client";

import Link from "next/link";
import Image from "next/image";
import "./styles.css";
import "../globals.css";
import {Button} from "@nextui-org/button";
import React, { useState } from 'react';
import { convertToSpeech } from './txt2sp';

export default function Chat() {

    const [isOverlayOpen, setIsOverlayOpen] = useState(false);
    const [isClosing, setIsClosing] = useState(false);

    const handleOpenOverlay = () => {
        setIsOverlayOpen(true);
        setIsClosing(false);
    };

    const handleCloseOverlay = () => {
        setIsClosing(true);
        setTimeout(() => {
            setIsOverlayOpen(false);
            setIsClosing(false);
        }, 300);
    };


    const [isOptionsOpen, setIsOptionsOpen] = useState(false);

    const handleOpenOptions = () => {
        setIsOptionsOpen(true);
    };

    const handleCloseOptions = () => {
        setIsOptionsOpen(false);
    };

    return (
        <body>
            <div className="upperDiv">
                <Link href="/">
                <img
                    src="/Brand/Icon/Icon dark.png"
                    alt=""
                />   
                </Link>        
                <h1>NOTICIAS</h1>
                <button onClick={isOverlayOpen ? handleCloseOverlay : handleOpenOverlay}>
                    <img 
                        src="/images/threelines.png"
                        alt=""
                    />
                </button>
            </div>
            <div  onClick={handleCloseOverlay} style = {{height: '100%'}}>
                <h1>Chat</h1>
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
                        <h1 style= {{marginLeft: 'auto', marginRight: 'auto'}}>NOVA</h1>
                        <button onClick = {isOptionsOpen ? handleCloseOptions : handleOpenOptions}>
                            <img 
                                src="/images/accountIcon.png"
                                alt=""
                            />
                        </button>
                    </div>
                    {isOptionsOpen && (
                        <div className="overlayLinks">
                            <Link href="/chat/perfil" className="overlayLinksContent" style={{marginTop: '15%'}}>Perfil</Link>
                            <Link href="/chat/historial" className="overlayLinksContent" >Historial</Link>
                            <Link href="/" className="overlayLinksContent" >Sign Out</Link>
                        </div>
                    )}

                    <div className="overlayContent">
                        <div className="overlayContent Nova">
                            <p id="novaText">Hola, soy Nova en que lo puedo ayudar?</p>
                            <button onClick={() => convertToSpeech(document.getElementById("novaText"))}>
                                <img
                                    src="/images/speaker.png"
                                    alt=""
                                />
                            </button>
                        </div>
                        <div className="overlayContent User">
                            <p id="userText">Hola, quisiera saber las noticias del día</p>
                            <button onClick={() => convertToSpeech(document.getElementById("userText"))}>
                                <img
                                    src="/images/speaker.png"
                                    alt=""
                                />
                            </button>
                        </div>
                    </div>

                    <div className="overlayFooter">
                        <img
                            src="/images/mic.png"
                            alt=""
                        />
                        <input type="text" placeholder="Habla con Nova, nuestro acompañante de IA"/>
                        <img
                            src="/images/send.png"
                            alt=""
                        />
                        </div>
                </div>
                )}
        </body>
    );
}
