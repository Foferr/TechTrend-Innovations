"use client";
import "../globals.css";
import React, { useState } from 'react';
import NavbarComponent from "../components/NavBar";
import withAuth from '../components/HOC/withAuth';


const qa: React.FC = () => {
    return (
        <div>
            <NavbarComponent />
            <h1>QA</h1>
        </div>
    );
}

export default withAuth(qa);