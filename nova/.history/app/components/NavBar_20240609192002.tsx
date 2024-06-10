"use client";
import "./styles.css";
import "../globals.css";
import React, { useState } from 'react';
import {Navbar} from "@material-tailwind/react";
import withAuth from '../components/HOC/withAuth';
import { useLanguage } from '../contexts/LanguageContext';

interface NavItems {
    home: string;
    admin: string;
    faq: string;
    
}

const NavbarComponent: React.FC = () => { 
    const { language } = useLanguage();

    const navItems = {
        es:
    }

    return (
            <Navbar placeholder="" onPointerEnterCapture={() => {}} onPointerLeaveCapture={() => {}}>
                <div className = "flex justify-between items-center text-nova-blue-500 pl-[10%] pr-[10%] pt-[15px] pb-[15px] ">
                    <a href="/" className="link1 link"><img src="/images/VectorNovaLogoJT.svg" alt="" className="logo" /> </a>
                    <a href="/admin" className="link"><img src = "/images/VectorNovaLogoBlueNT.svg" className="logo2" />Nova</a>
                    <a href="/admin/editarfaq" className="link"><img src = "/images/qalogo.svg" className="logo2" />Q & A</a>
                    <a href="/admin/users" className="link"><img src = "/images/account.svg" className="logo2" />Users</a>
                    <a href="/admin/editarnoticias" className="link"><img src = "/images/news.svg" className="logo2" />Noticias</a>
                    <a href="/embedtest" className="link"><img src = "/images/stats.svg" className="logo2" />Estadísticas</a>
                </div>
            </Navbar>
    );
}

export default withAuth(NavbarComponent);