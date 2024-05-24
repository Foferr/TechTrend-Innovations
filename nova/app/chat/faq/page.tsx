'use client';

import React, { useState, useEffect } from 'react';

export default function Page() {
  const [isScrolled, setIsScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      const scrollTop = window.scrollY;
      if (scrollTop > 100) {
        setIsScrolled(true);
      } else {
        setIsScrolled(false);
      }
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <div className={`relative h-screen w-screen overflow-y-auto ${isScrolled ? 'bg-yellow-900' : 'bg-yellow-500'}`}>
      <div
        className="absolute inset-0 bg-cover bg-center h-screen"
        style={{ backgroundImage: "url('/images/faq2.png')" }}
      />
      <br />
      <br />
      <br />
      <br />
      <div className='inset '>
        <h1 className='text-lg text-black'>Hola</h1>
      </div>
    </div>

  );
}
