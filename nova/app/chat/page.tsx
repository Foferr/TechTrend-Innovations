"use client";

import Link from "next/link";
import Image from "next/image";
import "./styles.css";
import "../globals.css";
import {Button} from "@nextui-org/button";
import React, { useState } from 'react';

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
                        <h1>NOVA</h1>
                    </div>
                </div>
                )}
        </body>
    );
}
