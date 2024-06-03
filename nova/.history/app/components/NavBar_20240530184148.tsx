"use client";
import "./styles.css";
import "../globals.css";
import React, { useState } from 'react';
import {Navbar} from "@material-tailwind/react";

export default function NavbarComponent(){
    return (
            <Navbar placeholder="" onPointerEnterCapture={() => {}} onPointerLeaveCapture={() => {}}>
                <div className = "flex justify-between items-center text-nova-blue-500 pl-[10%] pr-[10%] pt-[15px] pb-[15px] ">
                    <a href="/" className="link1 link"><img src="/images/VectorNovaLogoJT.svg" alt="" className="logo" /> </a>
                    <a href="/admin" className="link"><img src = "/images/VectorNovaLogoBlueNT.svg" className="logo2" />Nova</a>
                    <a href="/editarfaq" className="link"><img src = "/images/qalogo.svg" className="logo2" />Q & A</a>
                    <a href="/users" className="link"><img src = "/images/account.svg" className="logo2" />Users</a>
                    <a href="/editarnoticias" className="link"><img src = "/images/news.svg" className="logo2" />Noticias</a>
                    <a href="/stats" className="link"><img src = "/images/stats.svg" className="logo2" />Estadísticas</a>
                </div>
            </Navbar>
    );
}