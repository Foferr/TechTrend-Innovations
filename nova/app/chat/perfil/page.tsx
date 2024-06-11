"use client";

import Link from "next/link";
import Image from "next/image";
import "./styles.css";
import "../../globals.css";
import {Button} from "@nextui-org/button";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import withAuth from '../../components/HOC/withAuth';
import LanguageToggleButton from '@/app/components/LanguageToggleButton';
import { useLanguage } from '@/app/contexts/LanguageContext';

const userType = localStorage.getItem('userType');

const Perfil: React.FC = () => {
    const { language } = useLanguage();

    const namesText = language === 'es' ? 'Nombre/s' : 'Name/s';
    const lastNamesText = language === 'es' ? 'Apellido/s' : 'Last name/s';
    const emailLabelText = language === 'es' ? 'Correo::' : 'Email:';
    const passwordLabelText = language === 'es' ? 'Contraseña:' : 'Password';
    const languageTitleText = language === 'es' ? 'Lenguaje preferido' : 'Preferred language';
    const langOptionsText: { [key: string]: string[]} = {
        es: [
            'Español',
            'Inglés'
        ],
        en: [
            'Spanish',
            'English'
        ]
    }
    const deleteConfirmText = language === 'es' ? '¿Estás seguro que quieres eliminar tu cuenta?:' : 'Are you sure you want to delete your account?';
    const confirmText = language === 'es' ? 'Confirmar' : 'Confirm';
    const cancelText = language === 'es' ? 'Cancelar' : 'Cancel';
    const deleteText = language === 'es' ? 'Borrar' : 'Delete';


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
        axios.get(`http://localhost:8080/user/${localStorage.getItem('userId')}`)
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
        axios.put(`http://localhost:8080/user/editUser/${localStorage.getItem('userId')}`, user)
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
        axios.delete(`http://localhost:8080/user/deleteUser/${localStorage.getItem('userId')}`)
            .then(response => {
                window.location.href = '/';
                localStorage.clear();
            })
            .catch(error => {
                console.error(error);
            });
    };


    return (
        <body>
            <div className="allDiv" onClick={handleCloseOverlay}>
            <div className="upperDiv">
                <Link href={userType === 'admin' ? '/admin' : '/chat '}>
                    <img
                        className="w-[15vh]"
                        src="/images/VectorNovaLogoJT.svg"
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
                <div className="formflexDiv">
                    <div className="forminnerDiv">
                        <h1 className="text-nova-blue-500">{namesText}</h1>
                        <input type="text" id="firstName" name="firstName" value={user.firstName} className="text-nova-blue-500" onChange={handleInputChange}/>
                    </div>
                    <div className="forminnerDiv">
                        <h1 className="text-nova-blue-500">{lastNamesText}</h1>
                        <input type="text" id="lastName" name="lastName" value={user.lastName} className="text-nova-blue-500" onChange={handleInputChange}/>
                    </div>
                </div>
                <div className="formflexDiv">
                    <div className="forminnerDiv">
                        <h1 className="text-nova-blue-500">{passwordLabelText}</h1>
                        <input type="password" id="userPassword" name="userPassword" value={user.userPassword} className="text-nova-blue-500" onChange={handleInputChange}/>
                    </div>
                    <div className="forminnerDiv">
                        <h1 className="text-nova-blue-500">{emailLabelText}</h1>
                        <input type="text" id="email" name="email" className="text-nova-blue-500" value={user.email} onChange={handleInputChange}/>
                    </div>
                </div>

                <div className="forminnerDiv">
                    <h1 className="text-nova-blue-500">{languageTitleText}</h1>
                    <select name="language" id="language" className="text-nova-blue-500" value={user.language} onChange={handleInputChange}>
                        <option value="es">{langOptionsText[language][0]}</option>
                        <option value="en">{langOptionsText[language][1]}</option>
                    </select>
                </div>


                <div className="buttonsDiv">
                        <Button onClick={handleConfirmClick} className="buttonClass Conf">
                            {confirmText}
                        </Button>
                    
                    <Button className="buttonClass Del" onClick={isOverlayOpen ? handleCloseOverlay : handleOpenOverlay}>
                        {deleteText}
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
                        <h1 className="text-nova-blue-500">{deleteConfirmText}</h1>

                        <div className="buttonsDiv2">
                                <Button onClick={handleConfirmDelete} className="buttonClass Del">
                                    {confirmText}
                                </Button>
                            
                            <Button className="buttonClass Conf" onClick={isOverlayOpen ? handleCloseOverlay : handleOpenOverlay}>
                                {cancelText}
                            </Button>
                        </div>
                    </div>
                )}
            <LanguageToggleButton/>
        </body>
    );
}

export default withAuth(Perfil);