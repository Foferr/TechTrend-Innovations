"use client";

import Link from "next/link";
import Image from "next/image";
import "./styles.css";
import "../../globals.css";
import {Button} from "@nextui-org/button";
import React, { useState } from 'react';

export default function Perfil() {
    

    return (
        <body>
            <div className="upperDiv">
                <Link href="/chat">
                    <img 
                        src="/Brand/Icon/Icon dark.png"
                        alt=""
                        />
                </Link>
            </div>
            <div className="profileDiv">
                <img
                    src="/images/accountIcon.png"
                    alt=""
                    className="accountIcon"
                    />
                <img
                    src="/images/camara.png"
                    alt=""
                    className="cameraIcon"
                    />
            </div>
            <div className="formDiv">
                <div className="forminnerDiv">
                    <h1>User</h1>
                    <input type="text" id="user" name="user" />
                </div>
                <div className="forminnerDiv">
                    <h1>Contraseña</h1>
                    <input type="password" id="password" name="password" />
                </div>
                <div className="forminnerDiv">
                    <h1>Correo</h1>
                    <input type="text" id="email" name="email" />
                </div>
                <div className="forminnerDiv">
                    <h1>Lenguaje Preferido</h1>
                    <select name="language" id="language">
                        <option value="es">Español</option>
                        <option value="en">Inglés</option>
                    </select>
                </div>

                <div className="buttonsDiv">
                    <Button className="buttonClass Conf">
                        Confirmar
                    </Button>
                    <Button className="buttonClass Del">
                        Borrar
                    </Button>
                    </div>
            </div>

        </body>
    );
}
