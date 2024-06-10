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
  );
};

export default LanguageToggleButton;
