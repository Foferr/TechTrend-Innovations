"use client"
import Link from "next/link";
import localFont from "next/dist/compiled/@next/font/dist/local";
import React, { useState } from 'react';
export default function Home() {
    const words:string[] = ['Tu asistente de confianza', 'Conoce los productos de Neoris', '' +
    'Información a un mensaje de distancia', 'Potenciado por AI']
    let i = 0;
    let j = 0;
    let currentWord = '';
    let isDeleting = false;

    function typing() {
        currentWord = words[i];

        if (isDeleting) {
            document.getElementById('typewriter').textContent = currentWord.substring(0, j-1);
            j--;
            if (j == 0){
                isDeleting = false;
                i++;
                if (i == words.length) {
                    i = 0;
                }
            }

        } else if(document.getElementById('typewriter') != null) {
            document.getElementById('typewriter').textContent = currentWord.substring(0, j+1);
            j++
            if (j == currentWord.length) {
                isDeleting = true;
            }
        }
        setTimeout(typing, 100);
    }
    typing();


    return (
        <div>
            <div className="grid grid-cols-5 h-screen">
                <div className="flex items-center pl-10 bg-nova-yellow-500 col-span-3">
                    <img src="/images/VectorNovaLogoBlue.svg" className="top-0 left-0 absolute h-16 m-5" alt="Nova Logo" />
                    <div className="flex-col">
                        <div className="text-9xl text-nova-blue-500 font-bold">Nova</div>
                        <div id="typewriter" className="text-nova-blue-500 text-3xl min-h-9 border-r-[5px] border-nova-blue-500 pl-2 w-fit pr-2"></div>
                    </div>
                </div>
                <div className="bg-nova-blue-500 col-span-2"></div>
            </div>
        </div>





        // TODO-CHBOT-231 - Rebranding landing page
        // Rediseñar la landing page para ser mas dinamica y visualmente interesante
        // <div className="h-screen w-screen bg-neoris-grey-100">
        //     <div className="navbar bg-neoris-grey-50 z-0">
        //         <nav>
        //             <div className="flex w-full justify-between align-middle">
        //                 <div className="flex flex-wrap items-center p-3">
        //                     <a className="flex items-center space-x-3 rtl:space-x-reverse">
        //                         <img src="/images/VectorNovaLogo.svg" className="h-16" alt="Nova Logo" />
        //                         <span className="self-center text-2xl whitespace-nowrap dark:text-white">Nova</span>
        //                     </a>
        //                 </div>
        //                 <div className="flex items-center p-2">
        //                     <button className="bg-[#0066F9] hover:bg-blue-700 text-base text-white py-1 px-2 rounded">
        //                         Iniciar sesión
        //                     </button>
        //                 </div>
        //             </div>
        //         </nav>
        //     </div>
        //
        //     <div className="landingTitle flex flex-col items-center justify-center pt-10 w-screen" >
        //         <div className="flex flex-col items-center justify-center space-y-2">
        //             <div className="animatedText text-9xl">Nova</div>
        //             <h2 className="text-neoris-white-100 text-lg w-full">Conoce los productos que Neoris tiene que ofrecer</h2>
        //         </div>
        //     </div>
        // </div>
    );
}