"use client";
import "../globals.css";
import React, { useState } from 'react';
import NavbarComponent from "../components/NavBar";
import Chat from "../chat/page";

const admin adminHome(){
    return (
        <div>
            <NavbarComponent />
            <Chat />
        </div>
    );
}