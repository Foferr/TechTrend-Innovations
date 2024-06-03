"use client";

import Link from "next/link";
import Image from "next/image";
import "./styles.css";
import "../../globals.css";
import {Button} from "@nextui-org/button";
import React, { useState, useEffect } from 'react';
import axios from 'axios';



const Perfil: React.FC = () => {

    
    const [isOverlayOpen, setIsOverlayOpen] = useState(false);
    const [isClosing, setIsClosing] = useState(false);
    const [user, setUser] = useState({
        firstName:'',
        lastName:'',
        email:'',
        language:'',
        phone:'',
        userPassword:'',
        userType:'',
        birthday:''
    });
    const [statusMessage, setStatusMessage] = useState('');


    useEffect(() => {
        axios.get('http://localhost:8080/user/2')
            .then(response => {
                setUser(response.data);
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    const handleInputChange = (e: any) => {
        const { name, value } = e.target;
        setUser({
            ...user,
            [name]: value
        });
    };
    
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


    const handleConfirmClick = () => {
        axios.put('http://localhost:8080/user/editUser/2', user)
            .then(response => {
                setStatusMessage('Usuario editado correctamente!');
                setTimeout(() => {
                    setStatusMessage('');
                }, 2000); // 2 seconds delay
            })
            .catch(error => {
                console.error(error);
                setStatusMessage('Error updating user');
            });
    };

    const handleConfirmDelete = () => {
        axios.delete('http://localhost:8080/user/deleteUser/101')
            .then(response => {
                window.location.href = '/';
            })
            .catch(error => {
                console.error(error);
            });
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
                    <h1 className="text-nova-blue-500">First Name</h1>
                    <input type="text" id="firstName" name="firstName" value={user.firstName} className="text-nova-blue-500" onChange={handleInputChange}/>
                </div>
                <div className="forminnerDiv">
                    <h1 className="text-nova-blue-500">Last Name</h1>
                    <input type="text" id="lastName" name="lastName" value={user.lastName} className="text-nova-blue-500" onChange={handleInputChange}/>
                </div>
                <div className="forminnerDiv">
                    <h1 className="text-nova-blue-500">Contraseña</h1>
                    <input type="password" id="userPassword" name="userPassword" value={user.userPassword} className="text-nova-blue-500" onChange={handleInputChange}/>
                </div>
                <div className="forminnerDiv">
                    <h1 className="text-nova-blue-500">Correo</h1>
                    <input type="text" id="email" name="email" className="text-nova-blue-500" value={user.email} onChange={handleInputChange}/>
                </div>
                <div className="forminnerDiv">
                    <h1 className="text-nova-blue-500">Lenguaje Preferido</h1>
                    <select name="language" id="language" className="text-nova-blue-500" value={user.language} onChange={handleInputChange}>
                        <option value="es">Español</option>
                        <option value="en">Inglés</option>
                    </select>
                </div>


                <div className="buttonsDiv">
                        <Button onClick={handleConfirmClick} className="buttonClass Conf">
                            Confirmar
                        </Button>
                    
                    <Button className="buttonClass Del" onClick={isOverlayOpen ? handleCloseOverlay : handleOpenOverlay}>
                        Borrar
                    </Button>
                </div>
                {statusMessage && (
                        <div className="deleteMessage open">
                            {statusMessage}
                        </div>
                    )}
            </div>
            </div>
                {isOverlayOpen && (
                    <div className={`deleteMessage ${isOverlayOpen && !isClosing ? 'open' : 'close'}`}>
                        <h1 className="text-nova-blue-500">¿Estás seguro que quieres eliminar tu cuenta?</h1>

                        <div className="buttonsDiv2">
                                <Button onClick={handleConfirmDelete} className="buttonClass Del">
                                    Confirmar
                                </Button>
                            
                            <Button className="buttonClass Conf" onClick={isOverlayOpen ? handleCloseOverlay : handleOpenOverlay}>
                                Cancelar
                            </Button>
                        </div>
                    </div>
                )}

        </body>
    );
}
