"use client";

import Link from "next/link";
import "./styles.css";
import "../globals.css";
import React, { useState } from 'react';

export default function adminHome(){
    return (
        <div className = "outerDiv">
            <Link href="/" className = "logo">
                <img
                    src="/Brand/Logo/NEORIS logo light.png"
                    alt=""
                />
            </Link>
            <div className = "innerDiv">
                <div className = "innerUpperDiv">
                <Link href="/admin/chat">
                    <div className = "innerButton">
                        <img src="/images/novaLogo.png" alt="" className = "icon"/>
                        <p>Nova</p>
                    </div>
                </Link>
                <Link href="/admin/qa">
                    <div className = "innerButton">
                        <img src="images/qalogo.png" alt="" className = "icon"/>
                        <p>Q & A</p>
                    </div>
                </Link>
                </div>
                <div className = "innerLowerDiv">
                <Link href="/admin/users">
                    <div className = "innerButton">
                        <img src="images/accountIcon.png" alt="" className = "icon"/>
                        <p>Users</p>
                    </div>
                </Link>
                <Link href="/admin/noticias">
                    <div className = "innerButton">
                        <img src="images/newsLogo.png" alt="" className = "icon"/>
                        <p>Noticias</p>
                    </div>
                </Link>
                <Link href="/admin/stats">
                    <div className = "innerButton">
                        <img src="images/statslogo.png" alt="" className = "icon"/>
                        <p>Estadísticas</p>
                    </div>
                </Link>
                </div>

                </div>
        </div>
    );
}