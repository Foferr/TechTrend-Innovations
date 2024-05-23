"use client";

import Link from "next/link";
import "./styles.css";
import "../globals.css";
import React, { useState } from 'react';
import {Navbar,
    Typography
} from "@material-tailwind/react";

export default function adminHome(){
    return (
        <div>

            <Navbar color="blue" placeholder="" onPointerEnterCapture={() => {}} onPointerLeaveCapture={() => {}}>
                <div className = "flex justify-between items-center text-nova-blue-500 pl-[10%] pr-[10%]">
                    <img src="/images/VectorNovaLogoJT.svg" alt="" className="logo" />
                    <a href="/admin/chat" className="link"><img src = "/images/VectorNovaLogoBlueNT.svg" />Nova</a>
                    <a href="/admin/qa" className="link"><img src = "/images/qalogo.svg" />Q & A</a>
                    <a href="/admin/users" className="link"><img src = "/images/account.svg" />Users</a>
                    <a href="/admin/noticias" className="link"><img src = "/images/news.svg" />Noticias</a>
                    <a href="/admin/stats" className="link"><img src = "/images/stats.svg" />Estadísticas</a>
                </div>
            </Navbar>
        <div className = "outerDiv">


            <Link href="/" className = "logo">
                <img
                    src="/images/VectorNovaLogoJT.svg"
                    alt=""
                />
            </Link>
            <div className = "innerDiv">
                <div className = "innerUpperDiv">
                <Link href="/admin/chat">
                    <div className = "innerButton">
                        <img src="/images/VectorNovaLogoBlueNT.svg" alt="" className = "icon NovaLogo"/>
                        <p>Nova</p>
                    </div>
                </Link>
                <Link href="/admin/qa">
                    <div className = "innerButton">
                        <img src="images/qalogo.svg" alt="" className = "icon"/>
                        <p>Q & A</p>
                    </div>
                </Link>
                </div>
                <div className = "innerLowerDiv">
                <Link href="/admin/users">
                    <div className = "innerButton">
                        <img src="images/account.svg" alt="" className = "icon"/>
                        <p>Users</p>
                    </div>
                </Link>
                <Link href="/admin/noticias">
                    <div className = "innerButton">
                        <img src="images/news.svg" alt="" className = "icon"/>
                        <p>Noticias</p>
                    </div>
                </Link>
                <Link href="/admin/stats">
                    <div className = "innerButton">
                        <img src="images/stats.svg" alt="" className = "icon"/>
                        <p>Estadísticas</p>
                    </div>
                </Link>
                </div>

                </div>
        </div>
                    </div>
    );
}