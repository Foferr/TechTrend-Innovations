// /app/components/LanguageToggle.tsx
import React from 'react';
import { useLanguage } from '../contexts/LanguageContext';

const LanguageToggleButton = () => {
  const { language, setLanguage } = useLanguage();

  const toggleLanguage = () => {
    setLanguage(language === 'es' ? 'en' : 'es');
  };

  return (
    <button
      onClick={toggleLanguage}
      className="fixed bottom-8 left-8 px-4 py-2 bg-blue-500 text-white font-semibold rounded-md shadow-lg hover:bg-blue-600 transition duration-300"
      style={}
    >
      {language === 'es' ? 'Switch to English' : 'Cambiar a Español'}
    </button>
  );
};

export default LanguageToggleButton;
