"use client";
import "./styles.css";
import "../globals.css";
import React, { useState } from 'react';
import {Navbar} from "@material-tailwind/react";

export default function NavbarComponent(){
    return (
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
    );
}