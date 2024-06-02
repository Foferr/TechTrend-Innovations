import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from 'axios';
import "./styles3.css"; 

type User = {
  id: string;
  username: string;
  email: string;
  language: string;
  role: string;
};

const EditUser: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [user, setUser] = useState<User>({
    id: "",
    username: "",
    email: "",
    language: "",
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
      <h1>EDITAR USUARIO</h1>
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
          Lenguaje:
          <select name="language" value={user.language} onChange={handleChange}>
            <option value="Español">Español</option>
            <option value="Inglés">Inglés</option>
          </select>
        </label>
        <label>
          Rol:
          <select name="role" value={user.role} onChange={handleChange}>
            <option value="Usuario">Usuario</option>
            <option value="Administrador">Administrador</option>
          </select>
        </label>
        <button type="submit">Confirmar</button>
      </form>
    </div>
  );
};

export default EditUser;
