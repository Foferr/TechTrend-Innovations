"use client";
import React from "react";
import NavbarComponent from "../components/NavBar"; 
import Chat from "../chat/page";

export default function chatAdmin(){
    return (
        <div>
            <NavbarComponent/>
            <Chat/>
        </div>
    );
}