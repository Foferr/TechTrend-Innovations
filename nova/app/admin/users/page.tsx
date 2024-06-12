"use client";

import React, { useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import UsersTable from "./UsersTable";
import AgregarUsuario from "./AgregarUsuario";
import EditUser from "./EditUser";
import NavbarComponent from "../../components/NavBar";


type User = {
  id: string;
  username: string;
  email: string;
  country: string;
  language: string;
  role: string;
};

const App: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);

  const getUserById = (id: string) => {
    return users.find((user) => user.id === id);
  };

  const updateUser = (updatedUser: User) => {
    const newUsers = users.map((user) =>
      user.id === updatedUser.id ? updatedUser : user
    );
    setUsers(newUsers);
  };

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/admin/users" element={<UsersTable users={users} />} />
        <Route
          path="/admin/users/agregar-usuario"
          element={
            <AgregarUsuario
              onAddUser={(user: User) => setUsers([...users, user])}
            />
          }
        />
        <Route
          path="/admin/users/editar-usuario/:id"
          element={
            <EditUser getUserById={getUserById} updateUser={updateUser} />
          }
        />
      </Routes>
    </BrowserRouter>
  );
};

export default App;