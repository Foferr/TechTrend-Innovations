"use client"
import Link from "next/link";
import localFont from "next/dist/compiled/@next/font/dist/local";
import React, { useState } from 'react';
export default function Home() {
    return (
        // TODO-CHBOT-231 - Rebranding landing page
        // Rediseñar la landing page para ser mas dinamica y visualmente interesante
        <div className="h-screen w-screen bg-neoris-grey-100">
            <div className="navbar bg-neoris-grey-50 z-0">
                <nav>
                    <div className="flex w-full justify-between align-middle">
                        <div className="flex flex-wrap items-center p-3">
                            <a className="flex items-center space-x-3 rtl:space-x-reverse">
                                <img src="/images/VectorNovaLogo.svg" className="h-10" alt="Nova Logo" />
                                <span className="self-center text-2xl whitespace-nowrap dark:text-white">Nova</span>
                            </a>
                        </div>
                        <div className="flex items-center p-2">
                            <button className="bg-[#0066F9] hover:bg-blue-700 text-base text-white py-1 px-2 rounded">
                                Iniciar sesión
                            </button>
                        </div>
                    </div>
                </nav>
            </div>

            <div className="landingTitle flex flex-col items-center justify-center w-screen" >
                <div className="relative flex flex-col items-center justify-center space-y-2">
                    <div className="animatedText text-9xl">Nova</div>
                    <h2 className="text-neoris-white-100 text-lg w-full">Conoce los productos que Neoris tiene que ofrecer</h2>
                </div>
            </div>
        </div>
    );
}