'use client';

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaEdit, FaTrash, FaPlus } from 'react-icons/fa';
import NavbarComponent from '../../components/NavBar';
import withAuth from '../../components/HOC/withAuth';

interface User {
  id: string;
  username: string;
  email: string;
  language: string;
  role: string;
  firstName: string;
  lastName: string;
  phone: string;
  birthdate: string;
  userPassword: string;
}

const UsersTable: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [newUser, setNewUser] = useState<User>({
    id: '',
    username: '',
    email: '',
    language: 'Español',
    role: 'Usuario',
    firstName: '',
    lastName: '',
    phone: '',
    birthdate: '',
    userPassword: ''
  });
  const [editUser, setEditUser] = useState<User | null>(null);

  const fetchUsers = async () => {
    try {
      const response = await axios.get('http://localhost:8080/user');
      setUsers(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleAddClick = () => {
    setNewUser({
      id: '',
      username: '',
      email: '',
      language: 'Español',
      role: 'Usuario',
      firstName: '',
      lastName: '',
      phone: '',
      birthdate: '',
      userPassword: ''
    });
    setEditUser(null);
    setIsModalOpen(true);
  };

  const handleEditClick = async (id: string) => {
    try {
      const response = await axios.get(`http://localhost:8080/user/${id}`);
      setEditUser(response.data);
      setIsModalOpen(true);
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEditUser(null);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    if (editUser) {
      setEditUser((prevUser) => (prevUser ? { ...prevUser, [name]: value } : null));
    } else {
      setNewUser((prevUser) => ({
        ...prevUser,
        [name]: value,
      }));
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const payload = {
      firstName: editUser ? editUser.firstName : newUser.firstName,
      lastName: editUser ? editUser.lastName : newUser.lastName,
      language: editUser ? editUser.language : newUser.language,
      birthday: editUser ? editUser.birthdate : newUser.birthdate,
      email: editUser ? editUser.email : newUser.email,
      userPassword: editUser ? editUser.userPassword : newUser.userPassword,
      phone: editUser ? editUser.phone : newUser.phone,
      userType: editUser ? editUser.role : newUser.role,
    };

    try {
      if (editUser) {
        await axios.put(`http://localhost:8080/user/editUser/${editUser.id}`, payload);
        setUsers((prevUsers) => prevUsers.map((user) => (user.id === editUser.id ? { ...editUser, ...payload } : user)));
      } else {
        const response = await axios.post('http://localhost:8080/user/registerUser', payload);
        if (response.status === 200 || response.status === 201) {
          setUsers((prevUsers) => [...prevUsers, response.data]);
        }
      }
      fetchUsers();
      setIsModalOpen(false);
      setNewUser({
        id: '',
        username: '',
        email: '',
        language: 'Español',
        role: 'Usuario',
        firstName: '',
        lastName: '',
        phone: '',
        birthdate: '',
        userPassword: ''
      });
      setEditUser(null);
    } catch (error) {
      console.error('Error saving user:', error);
    }
  };

  const handleDeleteClick = async (id: string) => {
    try {
      await axios.delete(`http://localhost:8080/user/deleteUser/${id}`);
      setUsers((prevUsers) => prevUsers.filter((user) => user.id !== id));
    } catch (error) {
      console.error('Error deleting user:', error);
    }
  };

  return (
    <div>
      <NavbarComponent />
      <div className='relative min-h-screen flex flex-col items-center bg-gray-100 py-8'>
        <div className={`relative w-11/12 bg-white p-8 rounded-lg shadow-md border-solid border border-gray-300 ${isModalOpen ? 'opacity-50' : ''}`}>
          <h1 className="text-2xl font-semibold mb-6 text-center">Lista de Usuarios</h1>
          <table className="w-full text-center border-collapse">
            <thead>
              <tr>
                <th className="border-b py-4">Usuario</th>
                <th className="border-b py-4">Correo</th>
                <th className="border-b py-4">Lenguaje</th>
                <th className="border-b py-4">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {loading ? (
                <tr>
                  <td colSpan="4" className="py-6">
                    <div className="flex justify-center">
                      <svg className="animate-spin h-8 w-8 text-blue-500" viewBox="0 0 24 24">
                        <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                        <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8h8a8 8 0 11-8 8 8 8 0 01-8-8z"></path>
                      </svg>
                    </div>
                  </td>
                </tr>
              ) : (
                users.map((user) => (
                  <tr key={user.id}>
                    <td className="border-b py-4">{user.firstName} {user.lastName}</td>
                    <td className="border-b py-4">{user.email}</td>
                    <td className="border-b py-4">{user.language}</td>
                    <td className="border-b py-4 flex justify-center space-x-2">
                      <button onClick={() => handleEditClick(user.id)} className="text-blue-500 hover:text-blue-700">
                        <FaEdit />
                      </button>
                      <button onClick={() => handleDeleteClick(user.id)} className="text-red-500 hover:text-red-700">
                        <FaTrash />
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
          <button onClick={handleAddClick} className="fixed bottom-10 right-10 bg-green-500 text-white p-4 rounded-full shadow-lg hover:bg-green-700 flex items-center">
            <FaPlus className="mr-2" /> Agregar Usuario
          </button>
        </div>

        {isModalOpen && (
          <div className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
            <div className="bg-white p-8 rounded-lg shadow-md w-1/3">
              <h2 className="text-xl font-semibold mb-4">{editUser ? 'Editar Usuario' : 'Crear Nuevo Usuario'}</h2>
              <form onSubmit={handleSubmit}>
                <input
                  type="text"
                  name="firstName"
                  value={editUser ? editUser.firstName : newUser.firstName}
                  onChange={handleInputChange}
                  placeholder="Nombres"
                  className="w-full mb-4 p-2 border border-gray-300 rounded"
                />
                <input
                  type="text"
                  name="lastName"
                  value={editUser ? editUser.lastName : newUser.lastName}
                  onChange={handleInputChange}
                  placeholder="Apellidos"
                  className="w-full mb-4 p-2 border border-gray-300 rounded"
                />
                <input
                  type="email"
                  name="email"
                  value={editUser ? editUser.email : newUser.email}
                  onChange={handleInputChange}
                  placeholder="Correo electrónico"
                  className="w-full mb-4 p-2 border border-gray-300 rounded"
                />
                <input
                  type="password"
                  name="userPassword"
                  value={editUser ? editUser.userPassword : newUser.userPassword}
                  onChange={handleInputChange}
                  placeholder="Contraseña"
                  className="w-full mb-4 p-2 border border-gray-300 rounded"
                />
                <input
                  type="date"
                  name="birthdate"
                  value={editUser ? editUser.birthdate : newUser.birthdate}
                  onChange={handleInputChange}
                  className="w-full mb-4 p-2 border border-gray-300 rounded"
                />
                <input
                  type="text"
                  name="phone"
                  value={editUser ? editUser.phone : newUser.phone}
                  onChange={handleInputChange}
                  placeholder="Teléfono"
                  className="w-full mb-4 p-2 border border-gray-300 rounded"
                />
                <select
                  name="language"
                  value={editUser ? editUser.language : newUser.language}
                  onChange={handleInputChange}
                  className="w-full mb-4 p-2 border border-gray-300 rounded"
                >
                  <option value="Español">Español</option>
                  <option value="Inglés">Inglés</option>
                </select>
                <select
                  name="role"
                  value={editUser ? editUser.role : newUser.role}
                  onChange={handleInputChange}
                  className="w-full mb-4 p-2 border border-gray-300 rounded"
                >
                  <option value="Admin">Admin</option>
                  <option value="Usuario">Usuario</option>
                </select>
                <div className="flex justify-end">
                  <button
                    type="button"
                    onClick={handleCloseModal}
                    className="bg-gray-500 text-white px-4 py-2 rounded mr-2 hover:bg-gray-700"
                  >
                    Cancelar
                  </button>
                  <button
                    type="submit"
                    className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700"
                  >
                    Enviar
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default withAuth(UsersTable);





