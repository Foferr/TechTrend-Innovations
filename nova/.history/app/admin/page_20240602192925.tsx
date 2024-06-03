"use client";
import React from "react";
import NavbarComponent from "../components/NavBar"; 
import Chat from "../chat/page";
import withAuth from '../components/HOC/withAuth';

<<<<<<< HEAD
const adminHome: React.FC = () => {
=======

>>>>>>> origin/main
    return (
        <div>
            <NavbarComponent/>
            <Chat/>
        </div>
    );
}

export default withAuth(adminHome);