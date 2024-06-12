import { useLanguage } from '../contexts/LanguageContext';


export function convertToSpeech(userTextElement) {
  
    const { language, setLanguage } = useLanguage();
    const languageUtter = language === 'es' ? 'es-ES' : 'en-US';
    // const userText = userTextElement.innerText;
    const utterance = new SpeechSynthesisUtterance(userTextElement);

    if (window.speechSynthesis.speaking) {
      window.speechSynthesis.cancel();
    }
  
    // Optional: Customize voice and other properties
    utterance.voice = window.speechSynthesis
      .getVoices()
      .find((voice) => voice.lang === languageUtter); // Set a specific voice
    // utterance.rate = 1.0; // Adjust speech rate (default is 1.0)
  
    // Speak the text
    window.speechSynthesis.speak(utterance);
  
    // Log available voices
    // speechSynthesis.getVoices().forEach((voice) => console.log(voice.name));
  }
  