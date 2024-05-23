"use client";

import Link from "next/link";
import Image from "next/image";
import "./styles.css";
import "../../globals.css";
import {Button} from "@nextui-org/button";
import React, { useState } from 'react';
import axios from 'axios';

axios.get('http://localhost:8080/user/2')
  .then(response => {
    console.log(response.data);
  })
  .catch(error => {
    console.error(error);
  });


export default function Perfil() {
    
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
            <div className="allDiv" onClick={handleCloseOverlay}>
            <div className="upperDiv">
                <Link href="/chat">
                    <img 
                        src="/images/VectorNovaLogoBlue.svg"
                        alt=""
                        />
                </Link>
            </div>
            <div className="profileDiv">
                <img
                    src="/images/accountEdit.svg"
                    alt=""
                    className="accountIcon"
                    />
            </div>
            <div className="formDiv">
                <div className="forminnerDiv">
                    <h1 className="text-nova-blue-500">User</h1>
                    <input type="text" id="user" name="user" className="text-nova-blue-500" />
                </div>
                <div className="forminnerDiv">
                    <h1 className="text-nova-blue-500">Contraseña</h1>
                    <input type="password" id="password" name="password" className="text-nova-blue-500" />
                </div>
                <div className="forminnerDiv">
                    <h1 className="text-nova-blue-500">Correo</h1>
                    <input type="text" id="email" name="email" className="text-nova-blue-500" />
                </div>
                <div className="forminnerDiv">
                    <h1 className="text-nova-blue-500">Lenguaje Preferido</h1>
                    <select name="language" id="language" className="text-nova-blue-500">
                        <option value="es">Español</option>
                        <option value="en">Inglés</option>
                    </select>
                </div>


                <div className="buttonsDiv">
                    <Link href="/chat" className="buttonClass Conf">
                        <Button>
                            Confirmar
                        </Button>
                    </Link>

                    
                    <Button className="buttonClass Del" onClick={isOverlayOpen ? handleCloseOverlay : handleOpenOverlay}>
                        Borrar
                    </Button>
                </div>
            </div>
            </div>
                {isOverlayOpen && (
                    <div className={`deleteMessage ${isOverlayOpen && !isClosing ? 'open' : 'close'}`}>
                        <h1 className="text-nova-blue-500">¿Estás seguro que quieres eliminar tu cuenta?</h1>

                        <div className="buttonsDiv2">
                            <Link href="/" className="buttonClass Del">
                                <Button>
                                    Confirmar
                                </Button>
                            </Link>

                            
                            <Button className="buttonClass Conf" onClick={isOverlayOpen ? handleCloseOverlay : handleOpenOverlay}>
                                Cancelar
                            </Button>
                        </div>
                    </div>
                )}

        </body>
    );
}
