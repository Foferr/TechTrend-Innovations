"use client";
import React from "react";
import NavbarComponent from "../components/NavBar"; 
import Chat from "../chat/page";
import withAuth from '../components/HOC/withAuth';
import LanguageToggleButton from "../components/LanguageToggleButton";


const adminHome: React.FC = () => {
    return (
        <div>
            <NavbarComponent/>
            <Chat/>
            <LanguageToggleButton></LanguageToggleButton>
        </div>
    );
}

export default withAuth(adminHome);