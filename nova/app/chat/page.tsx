"use client";

import Link from "next/link";
import "./styles.css";
import "../globals.css";
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

    async function postData<T>(url: string, data: T): Promise<Response> {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // Adjust content type if necessary
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error(`API request failed with status ${response.status}`);
        }

        return response;
    }


    const user = {
        inputs : "<s>[INST] What is Neoris? [/INST]</s>",
    };

    const apiUrl = 'https://fs73t61itorn1e6w.us-east-1.aws.endpoints.huggingface.cloud';

    postData(apiUrl, user)
        .then(async (response) => {
            const data = await response.json();

            console.log('API response:', data);
            // @ts-ignore
            document.getElementById("novaText").textContent = JSON.stringify(data)
        })
        .catch((error) => {
            console.error('API request error:', error);
        });

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
            <div onClick={() => {handleCloseOverlay(); handleCloseOptions();}} style={{height: '575px', margin: 50, backgroundColor: 'black', borderRadius: 20}}>
                <br></br>
                <h1 style = {{fontSize: '20px', color: 'white', marginLeft: '30px'}}> +                      -</h1>
                <div style = {{margin: 30, backgroundColor: 'black', height: '100px', display: 'flex'}}>
                    <img src="/images/Neoris1.png" alt="Neoris" style={{height: '100%', width: 'auto'}} />
                    <div style = {{marginLeft: '15px'}}>
                        <h1 style = {{color: 'white'}}> Neoris saca nuevo producto</h1>
                        <p style = {{color: 'white'}}> Hola </p>
                    </div>
                </div>
                <h1 style = {{color: 'white', textAlign: 'center'}}>
                    __________________________________________________________________________________________________________________________________________
                </h1>
                <div style = {{margin: 30, backgroundColor: 'black', height: '100px', display: 'flex'}}>
                    <img src="/images/Neoris4.png" alt="Neoris" style={{height: '100%', width: 'auto'}} />
                    <div style = {{marginLeft: '15px'}}>
                        <h1 style = {{color: 'white'}}> Entrevista con Neoris</h1>
                        <p style = {{color: 'white'}}> Hola </p>
                    </div>
                </div>
                <h1 style = {{color: 'white', textAlign: 'center'}}>
                    __________________________________________________________________________________________________________________________________________
                </h1>
                <div style = {{margin: 30, backgroundColor: 'black', height: '100px', display: 'flex'}}>
                    <img src="/images/Neoris3.png" alt="Neoris" style={{height: '100%', width: 'auto'}} />
                    <div style = {{marginLeft: '15px'}}>
                        <h1 style = {{color: 'white'}}> Visita a Neoris</h1>
                        <p style = {{color: 'white'}}> Hola </p>
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
                            <div id="novaText"></div>
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
