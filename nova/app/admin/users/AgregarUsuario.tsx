import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import LanguageToggleButton from '@/app/components/LanguageToggleButton';
import { useLanguage } from '@/app/contexts/LanguageContext';

type User = {
  username: string;
  email: string;
  password: string;
  birthdate: string;
  language: string;
  country: string;
  role: string;
};

const AgregarUsuario: React.FC = () => {
  const { language } = useLanguage();

  const addUserTitleText = language === 'es' ? 'Agregar usuario' : 'Add user';
  const userLabelText = language === 'es' ? 'Usuario:' : 'User:';
  const emailLabelText = language === 'es' ? 'Correo::' : 'Email:';
  const passwordLabelText = language === 'es' ? 'Contraseña:' : 'Password:';
  const birthdayLabelText = language === 'es' ? 'Fecha de nacimiento:' : 'Birthday:';
  const languageLabelText = language === 'es' ? 'Lenguaje:' : 'Language:';
  const roleLabelText = language === 'es' ? 'Rol:' : 'Role:';
  const saveButtonText = language === 'es' ? 'Guardar usuario' : 'Save user';
  const countryLabelText = language === 'es' ? 'País' : 'Country';
  const langOptionsText: { [key: string]: string[]} = {
    es: [
      'Español',
      'Inglés'
    ],
    en: [
      'Spanish',
      'English'
    ]
  }

  const roleOptionsText: { [key: string]: string[]} = {
    es: [
      'Admin',
      'Usuario'
    ],
    en: [
      'Admin',
      'User'
    ]
  }

  const [user, setUser] = useState<User>({
    username: "",
    email: "",
    password: "",
    birthdate: "",
    language: "es",
    country: "",
    role: "user",
  });
  const navigate = useNavigate();

  const handleChange = (
    event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    setUser({ ...user, [event.target.name]: event.target.value });
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    axios.post('http://localhost:8080/users', user) 
      .then(response => {
        console.log('Usuario agregado:', response.data);
        navigate("/admin/users"); // Navegar de vuelta a la tabla de usuarios
      })
      .catch(error => {
        console.error("Error al agregar usuario:", error);
      });
  };

  return (
    <div className="form-container">
      <h1>{addUserTitleText}</h1>
      <form onSubmit={handleSubmit}>
        <label>
          {userLabelText}
          <input
            type="text"
            name="username"
            value={user.username}
            onChange={handleChange}
          />
        </label>
        <label>
          {emailLabelText}
          <input
            type="email"
            name="email"
            value={user.email}
            onChange={handleChange}
          />
        </label>
        <label>
          {passwordLabelText}
          <input
            type="password"
            name="password"
            value={user.password}
            onChange={handleChange}
          />
        </label>
        <label>
          {birthdayLabelText}
          <input
            type="date"
            name="birthdate"
            value={user.birthdate}
            onChange={handleChange}
          />
        </label>
        <label>
          {countryLabelText}
          <input
            type="text"
            name="country"
            value={user.country}
            onChange={handleChange}
          />
        </label>
        <label>
          {languageLabelText}
          <select name="language" value={user.language} onChange={handleChange}>
            <option value="es">{langOptionsText[language][0]}</option>
            <option value="en">{langOptionsText[language][1]}</option>
          </select>
        </label>
        <label>
          {roleLabelText}
          <select name="role" value={user.role} onChange={handleChange}>
            <option value="admin">{roleOptionsText[language][0]}</option>
            <option value="user">{roleOptionsText[language][1]}</option>
          </select>
        </label>
        <button type="submit">{saveButtonText}</button>
      </form>
      <LanguageToggleButton/>
    </div>
  );
};

export default AgregarUsuario;
