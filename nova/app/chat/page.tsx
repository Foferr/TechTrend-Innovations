"use client";

import Link from "next/link";
import "./styles.css";
import "../globals.css";
import React, { useState } from 'react';
import { convertToSpeech } from './txt2sp';
import Noticias from './noticias';
import { generatePrompts } from './chat';




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
    
    const [messages, setMessages] = useState<{ user: boolean; text: string }[]>([]);

    const handlePrompts = async () => {
        const inputUser = (document.getElementById("inputUser") as HTMLInputElement).value;
        (document.getElementById("inputUser") as HTMLInputElement).value = "";
        setMessages([...messages, { user: true, text: inputUser }]);
        messages.push({ user: true, text: inputUser });
        const prompts = await generatePrompts(inputUser);
        setMessages([...messages, { user: false, text: prompts }]);
        console.log(prompts);
    };

    // async function postData<T>(url: string, data: T): Promise<Response> {
    //     const response = await fetch(url, {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/json', // Adjust content type if necessary
    //         },
    //         body: JSON.stringify(data),
    //     });

    //     if (!response.ok) {
    //         throw new Error(`API request failed with status ${response.status}`);
    //     }

    //     return response;
    // }


    // const handleCreateUserOverlay = () => {
    //     const inputUser = (document.getElementById("inputUser") as HTMLInputElement).value;
    //     if (inputUser?.trim() !== "") {
    
    //         var input = (document.getElementById("inputUser") as HTMLInputElement).value;
    //         const user = {
    //             inputs: "<s>[INST]" + input + "[/INST]</s>",
    //         };
    
    //         setMessages([...messages, { user: true, text: inputUser }]);
    //         messages.push({ user: true, text: inputUser });
    //         (document.getElementById("inputUser") as HTMLInputElement).value = "";
    //         const apiUrl = 'https://fs73t61itorn1e6w.us-east-1.aws.endpoints.huggingface.cloud';
    
    //         setMessages([...messages, { user: false, text: 'Generando respuesta...' }]);
    //         postData(apiUrl, user)
    //             .then(async (response) => {
    //                 const data = await response.json();
    
    //                 // console.log('API response:', data);
    //                 const processedData = JSON.stringify(data)
    //                     .replace(/<s>\[INST\](.*?)\[\/INST\]<\/s>/g, '')
    //                     .trim()
    //                     .replace(/\\n/g, '')
    //                     // .replace(/{/g, '')
    //                     // .replace(/}/g, '')
    //                     // .replace(/\[/g, '')
    //                     // .replace(/\]/g, '')
    //                     // .replace("generated_text", '')

    //                 console.log(processedData);

    //                 setMessages([...messages, { user: false, text: processedData }]);
                        

    //             })
    //             .catch((error) => {
    //                 console.error('API request error:', error);
    //             });
    
    //     }
    // };
    

    return (
        <body>
            <div className="upperDiv">
                <Link href="/">
                <img
                    src="/Brand/Icon/Icon dark.png"
                    alt=""
                />   
                </Link>
                <button>     
                <h1>NOTICIAS</h1>
                </button>
                <button onClick={isOverlayOpen ? handleCloseOverlay : handleOpenOverlay}>
                    <img 
                        src="/images/threelines.png"
                        alt=""
                    />
                </button>
            </div>
            <div onClick={() => {handleCloseOverlay(); handleCloseOptions();}} style={{height: '575px', margin: 50, backgroundColor: 'black', borderRadius: 20}}>
                <Noticias/>
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

                    <div className="overlayContent" id = "overlayContent">
                        <div className="chatContent">
                            {messages.map((message, index) => (
                                <React.Fragment key={index}>
                                    <div className={`overlayContent ${message.user ? "User" : "Nova"}`}>
                                        <p>{message.text}</p>
                                        <button onClick={() => convertToSpeech(message.text)}>
                                            <img src="/images/speaker.png" alt="" />
                                        </button>
                                    </div>
                                </React.Fragment>
                            ))}
                        </div>
                    </div>

                    <div className="overlayFooter">
                        <img
                            src="/images/mic.png"
                            alt=""
                        />
                        <input type="text" placeholder="Habla con Nova, nuestro acompañante de IA" id = "inputUser"/>
                        <button onClick={handlePrompts}>
                        <img
                            src="/images/send.png"
                            alt=""
                        />
                        </button>
                        </div>
                </div>
                
                )}
        </body>
    );
}
