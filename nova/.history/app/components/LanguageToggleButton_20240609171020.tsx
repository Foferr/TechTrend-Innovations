// /app/components/LanguageToggle.tsx
import React from 'react';
import { useLanguage } from '../contexts/LanguageContext';

const LanguageToggleButton = () => {
  const { language, setLanguage } = useLanguage();

  const toggleLanguage = () => {
    setLanguage(language === 'es' ? 'en' : 'es');
  };

  return (
    <button onClick={toggleLanguage} className="">
        {language === 'es' ? 'Switch to Enligsh' : 'Cambiar a Español'}
    </button>
  );
};

export default LanguageToggleButton;
