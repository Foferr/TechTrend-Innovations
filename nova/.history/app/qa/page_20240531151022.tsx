"use client";
import "../globals.css";
import React, { useState } from 'react';
import NavbarComponent from "../components/NavBar";


const qa: React.FC = () => {
    return (
        <div>
            <NavbarComponent />
            <h1>QA</h1>
        </div>
    );
}

export default withAuth(qa);