'use client';

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaEdit, FaTrash, FaPlus } from 'react-icons/fa';
import NavbarComponent from "../components/NavBar";

interface Admin {
  firstName: string;
  lastName: string;
  language: string;
  birthday: string;
  email: string;
  userPassword: string;
  phone: string;
  userType: string;
  createdAt: string;
  id: number;
}

interface Faq {
  id: number;
  question: string;
  answer: string;
  admin: Admin;
  status: string;
  createdAt: string;
}

const TablaDinamica = () => {
  const [orgsData, setOrgsData] = useState<Faq[]>([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [newFaq, setNewFaq] = useState({
    question: '',
    answer: '',
    admin: '',
    status: 'drafted',
  });

  const adminId = 1;

  const fetchOrgsData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/FAQ/getAll');
      setOrgsData(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };
  useEffect(() => {
 
    fetchOrgsData();
  }, []);






  

  const handleEdit = async (FAQ: Faq) => {
    const newStatus = FAQ.status === 'published' ? 'drafted' : 'published';
    try {
   
        //const response = await axios.put('http://localhost:8080/FAQ/2/${FAQ.id}?status=${newStatus}');
        const response = await axios.put(`http://localhost:8080/FAQ/2/${FAQ.id}`,
        FAQ
        ,{ headers: {'Content-Type': 'application/json'} });

      if (response.status === 200) {
          setOrgsData((prevOrgsData) =>
          prevOrgsData.map((Faq) =>
            Faq.id === FAQ.id ? { ...Faq, status: newStatus } : Faq
          )
        );
      }
    } catch (error) {
      console.error('Error updating status:', error);
    }
  };








  const handleDelete = async (FAQid: number) => {
    try {
      const response = await axios.delete(`http://localhost:8080/FAQ/2/${FAQid}`);
      setLoading(true);
      fetchOrgsData();  
      if (response.status === 200) {
      //  setOrgsData((prevOrgsData) => prevOrgsData.filter((news) => news.id !== companyNewsId));
      }
    } catch (error) {
      console.error('Error deleting news:', error);
    }
    
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setNewFaq((prevFaq) => ({
      ...prevFaq,
      [name]: value,
    }));
  };

  const handleSubmit = async () => {
    const currentDate = new Date().toISOString();
    const newFaqItem = {
      ...newFaq,
      admin: { firstName: newFaq.admin }, // Assuming a simple user structure
      createdAt: currentDate,
    };
    try {
      const response = await axios.post(`http://localhost:8080/FAQ/2`, newFaqItem);
      setIsModalOpen(false);
      setLoading(true);
      fetchOrgsData();  
      if (response.status === 200) {
        setOrgsData((prevOrgsData) => [...prevOrgsData, response.data]);
        
        setNewFaq({
          question: '',
          answer: '',
          admin: '',
          status: 'drafted',
        });
      }
    } catch (error) {
      console.error('Error creating FAQ:', error);
    }
  };

  return (
    <div>
      <NavbarComponent />
      <div className='relative min-h-screen flex flex-col items-center bg-gray-100 py-8'>
        <div className={`relative w-11/12 bg-white p-8 rounded-lg shadow-md border-solid border border-gray-300 ${isModalOpen ? 'opacity-50' : ''}`}>
          <h1 className="text-2xl font-semibold mb-6 text-center">Editar FAQ</h1>
          <table className="w-full text-center border-collapse">
            <thead>
              <tr>
                <th className="border-b py-4">Id</th>
                <th className="border-b py-4">Pregunta</th>
                <th className="border-b py-4">Respuesta</th>
                <th className="border-b py-4">Usuario</th>
                <th className="border-b py-4">Estatus</th>
                <th className="border-b py-4">Fecha de Creación</th>
                <th className="border-b py-4">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {loading ? (
                <tr>
                  <td colSpan="7" className="py-6">
                    <div className="flex justify-center">
                      <svg className="animate-spin h-8 w-8 text-blue-500" viewBox="0 0 24 24">
                        <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                        <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8h8a8 8 0 11-8 8 8 8 0 01-8-8z"></path>
                      </svg>
                    </div>
                  </td>
                </tr>
              ) : (
                orgsData.map((FAQ) => (
                  <tr key={FAQ.id}>
                    <td className="border-b py-4">{FAQ.id}</td>
                    <td className="border-b py-4">{FAQ.question}</td>
                    <td className="border-b py-4">{FAQ.answer}</td>
                    <td className="border-b py-4">{FAQ.admin.firstName}</td>
                    <td className="border-b py-4">{FAQ.status}</td>
                    <td className="border-b py-4">{new Date(FAQ.createdAt).toLocaleDateString()}</td>
                    <td className="border-b py-4 flex justify-center space-x-2">
                      <button onClick={() => handleEdit(FAQ)} className="text-blue-500 hover:text-blue-700">
                        <FaEdit />
                      </button>
                      <button onClick={() => handleDelete(FAQ.id)} className="text-red-500 hover:text-red-700">
                        <FaTrash />
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
          <button onClick={() => setIsModalOpen(true)} className="fixed bottom-10 right-10 bg-green-500 text-white p-4 rounded-full shadow-lg hover:bg-green-700 flex items-center">
            <FaPlus className="mr-2" /> Agregar FAQ
          </button>
        </div>

        {isModalOpen && (
          <div className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
            <div className="bg-white p-8 rounded-lg shadow-md w-1/3">
              <h2 className="text-xl font-semibold mb-4">Crear Nuevo FAQ</h2>
              <input
                type="text"
                name="question"
                value={newFaq.question}
                onChange={handleInputChange}
                placeholder="Pregunta"
                className="w-full mb-4 p-2 border border-gray-300 rounded"
              />
              <input
                type="text"
                name="answer"
                value={newFaq.answer}
                onChange={handleInputChange}
                placeholder="Respuesta"
                className="w-full mb-4 p-2 border border-gray-300 rounded"
              />
              <input
                type="text"
                name="admin"
                value={newFaq.admin}
                onChange={handleInputChange}
                placeholder="Admin"
                className="w-full mb-4 p-2 border border-gray-300 rounded"
              />
              <select
                name="status"
                value={newFaq.status}
                onChange={handleInputChange}
                className="w-full mb-4 p-2 border border-gray-300 rounded"
              >
                <option value="drafted">Borrador</option>
                <option value="published">Publicado</option>
              </select>
              <div className="flex justify-end">
                <button
                  onClick={() => setIsModalOpen(false)}
                  className="bg-gray-500 text-white px-4 py-2 rounded mr-2 hover:bg-gray-700"
                >
                  Cancelar
                </button>
                <button
                  onClick={handleSubmit}
                  className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700"
                >
                  Enviar
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default TablaDinamica;
