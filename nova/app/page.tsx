"use client";

import Link from "next/link";
import React from 'react';
import { useEffect } from 'react';
import LanguageToggleButton from "./components/LanguageToggleButton";
import { useLanguage } from './contexts/LanguageContext';

export default function Home() {

  const { language } = useLanguage();


  const chatButtonText = language === 'es' ? 'Chatea' : 'Chat';

  // Removes all local storage data when user logs out
  useEffect(() => {
    localStorage.clear();
  });

  useEffect(() => {
    let i = 0;
    let j = 0;
    let currentWord = '';
    let isDeleting = false;
    let timeoutId: NodeJS.Timeout;

    const words: { [key: string]: string[] } = {
      es: [
        'Tu asistente de confianza',
        'Conoce los productos de Neoris',
        ''+'Información a un mensaje de distancia',
        'Potenciado por AI'
      ],
      en: [
        'Your trustworthy assistant',
        'Learn about Neoris products',
        'Information, one prompt away',
        'Powered by AI'
      ]
    };

    function typing() {
      currentWord = words[language][i];
      if (document.getElementById('typewriter') != null) {
        if (isDeleting) {
          document.getElementById('typewriter')!.textContent = currentWord.substring(0, j - 1);
          j--;
          if (j === 0) {
            isDeleting = false;
            i++;
            if (i === words[language].length) {
              i = 0;
            }
          }
        } else {
          document.getElementById('typewriter')!.textContent = currentWord.substring(0, j + 1);
          j++;
          if (j === currentWord.length) {
            isDeleting = true;
          }
        }
        timeoutId = setTimeout(typing, isDeleting && j === currentWord.length ? 1000 : 100);
      } else {
        // Resets variables to avoid glitching when reloading page
        i = 0;
        j = 0;
        currentWord = '';
        isDeleting = false;

        timeoutId = setTimeout(typing, 100);
      }
    }

    typing();

    return () => clearTimeout(timeoutId); // Clear timeout on unmount
  }, [language]); // Add language as a dependency


  return (
      <div>
        <div className="flex items-center h-screen bg-white justify-between">
          <div className="flex items-center pl-10">
            <img src="/images/VectorNovaLogoJT.svg" className="logInBoxGraphic top-0 left-8 absolute h-10 m-5"
                 alt="Nova Logo"/>
            <div className="flex-col">
              <div className="landingTitle text-9xl text-nova-blue-500 font-bold">Nova</div>
              <div id="typewriter"
                   className="landingTitle text-nova-blue-500 text-3xl min-h-9 border-r-[5px] bg-nova-yellow-500 border-nova-blue-500 pl-2 pr-2 w-fit "></div>
              <Link
                  href="/login"
              >
                <button
                    className="landingTitle mt-6 shadow-[inset_0_0_0_2px_#0045AF] px-10 py-3 rounded-full tracking-widest uppercase font-bold bg-transparent hover:bg-[#0045AF] hover:text-nova-yellow-500 dark:text-nova-blue-500 transition duration-200">
                  {chatButtonText}
                </button>
              </Link>

            </div>
          </div>
          <div className="landingTitle" >
            <img src="/images/VectorNovaLogoBlueNT.svg" className="h-[60vh] rotate mt-[30vh] mr-[30vh] max-sm:hidden"
                 alt="Nova Logo"/>
          </div>
        </div>
        <LanguageToggleButton/>
      </div>
  );
}