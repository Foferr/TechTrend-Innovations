import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./styles1.css";
import NavbarComponent from "../components/NavBar";


type User = {
  id: string;
  username: string;
  email: string;
  language: string;
  role: string;
};

const UsersTable: React.FC = () => {
  let navigate = useNavigate();
  const [users, setUsers] = useState<User[]>([]);

  useEffect(() => {
    axios.get('http://localhost:8080/users') 
      .then(response => {
        setUsers(response.data);
      })
      .catch(error => {
        console.error("Error fetching users:", error);
      });
  }, []);

  const handleAddClick = () => {
    navigate("/agregar-usuario");
  };

  const handleEditClick = (userId: string) => {
    navigate(`/editar-usuario/${userId}`);
  };

  return (
    <div className="main-container">
      <img
        src="public\images\VectorNovaLogoBlueNT.svg"
        className="sidebar-image"
      />
      <div className="container">
        <div className="header">
          <h1>LISTA DE USUARIOS</h1>
          <button onClick={handleAddClick}>Agregar</button>
        </div>
        <table>
          <thead>
            <tr>
              <th>User</th>
              <th>Correo</th>
              <th>Lenguaje</th>
              <th>Editar</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.username}</td>
                <td>{user.email}</td>
                <td>{user.language}</td>
                <td>
                  <button onClick={() => handleEditClick(user.id)}>
                    Editar
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default UsersTable;
