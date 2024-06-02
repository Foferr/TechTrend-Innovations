import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import "./styles2.css";

type User = {
  username: string;
  email: string;
  password: string;
  birthdate: string;
  language: string;
  role: string;
};

const AgregarUsuario: React.FC = () => {
  const [user, setUser] = useState<User>({
    username: "",
    email: "",
    password: "",
    birthdate: "",
    language: "Español",
    role: "Usuario",
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
      <h1>Agregar Usuario</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Usuario:
          <input
            type="text"
            name="username"
            value={user.username}
            onChange={handleChange}
          />
        </label>
        <label>
          Correo:
          <input
            type="email"
            name="email"
            value={user.email}
            onChange={handleChange}
          />
        </label>
        <label>
          Contraseña:
          <input
            type="password"
            name="password"
            value={user.password}
            onChange={handleChange}
          />
        </label>
        <label>
          Fecha de nacimiento:
          <input
            type="date"
            name="birthdate"
            value={user.birthdate}
            onChange={handleChange}
          />
        </label>
        <label>
          Lenguaje:
          <select name="language" value={user.language} onChange={handleChange}>
            <option value="Español">Español</option>
            <option value="Inglés">Inglés</option>
          </select>
        </label>
        <label>
          Rol:
          <select name="role" value={user.role} onChange={handleChange}>
            <option value="Admin">Admin</option>
            <option value="Usuario">Usuario</option>
          </select>
        </label>
        <button type="submit">Guardar Usuario</button>
      </form>
    </div>
  );
};

export default AgregarUsuario;
