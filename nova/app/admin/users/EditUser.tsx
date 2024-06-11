import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from 'axios';
import {useLanguage} from "@/app/contexts/LanguageContext";
import LanguageToggleButton from "@/app/components/LanguageToggleButton";

type User = {
  id: string;
  username: string;
  email: string;
  language: string;
  country: string;
  role: string;
};

const EditUser: React.FC = () => {

  const { language } = useLanguage();

  const editUserTitleText = language === 'es' ? 'Editar usuario' : 'Edit user';
  const userLabelText = language === 'es' ? 'Usuario:' : 'User:';
  const emailLabelText = language === 'es' ? 'Correo::' : 'Email:';
  const languageLabelText = language === 'es' ? 'Lenguaje:' : 'Language:';
  const roleLabelText = language === 'es' ? 'Rol:' : 'Role:';
  const saveButtonText = language === 'es' ? 'Confirmar' : 'Confirm';
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

  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [user, setUser] = useState<User>({
    id: "",
    username: "",
    email: "",
    language: "",
    country: "",
    role: "",
  });

  useEffect(() => {
    axios.get(`http://localhost:8080/users/${id}`) 
      .then(response => {
        setUser(response.data);
      })
      .catch(error => {
        console.error("Error fetching user data:", error);
      });
  }, [id]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    axios.put(`http://localhost:8080/users/${id}`, user) 
      .then(() => {
        alert("Datos guardados con éxito!");
        navigate("/admin/users"); // Redirige a UsersTable después de confirmar
      })
      .catch(error => {
        console.error("Error updating user:", error);
      });
  };

  return (
    <div className="edit-user">
       <img
        src="public\images\VectorNovaLogoBlueNT.svg"
        className="sidebar-image"
      />
      <h1>{editUserTitleText}</h1>
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
            <option value="user">{roleOptionsText[language][0]}</option>
            <option value="admin">{roleOptionsText[language][1]}</option>
          </select>
        </label>
        <button type="submit">{saveButtonText}</button>
      </form>
      <LanguageToggleButton/>
    </div>
  );
};

export default EditUser;
