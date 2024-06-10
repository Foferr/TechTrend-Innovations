// /app/components/LanguageToggle.tsx
import React from 'react';
import { useLanguage } from '../contexts/LanguageContext';

const LanguageToggleButton = () => {
  const { language, setLanguage } = useLanguage();

  const toggleLanguage = () => {
    setLanguage(language === 'es' ? 'en' : 'es');
  };

  return (
    <button onClick={toggleLanguage} style={{ position: 'fixed', bottom: 10, right: 10 }}>
      {language === 'es' ? 'Switch to English' : 'Cambiar a Español'}
    </button>
    <button onClick={toggleLanguage} className="landingTitle mt-6 shadow-[inset_0_0_0_2px_#0045AF] px-10 py-3 rounded-full tracking-widest uppercase font-bold bg-transparent hover:bg-[#0045AF] hover:text-nova-yellow-500 dark:text-nova-blue-500 transition duration-200">
        {language === 'es' ? 'Switch to Enligsh'}
    </button>
  );
};

export default LanguageToggleButton;
