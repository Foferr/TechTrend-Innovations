"use client";
import "../globals.css";
import React, { useState } from 'react';
import NavbarComponent from "../components/NavBar";
import Chat from "../chat/page";
import withAuth from '../components/HOC/withAuth'

const adminHome: React.FC = () => {
    return (
        <div>
            <NavbarComponent />
            <Chat />
        </div>
    );
}

export default withAuth(adminHome);