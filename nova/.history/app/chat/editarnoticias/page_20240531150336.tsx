'use client';

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaEdit, FaTrash, FaPlus } from 'react-icons/fa';
import withAuth from '../../components/HOC/withAuth';

interface User {
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

interface CompanyNews {
  id: number;
  title: string;
  newsContent: string;
  user: User;
  status: string;
  createdAt: string;
}

const TablaDinamica = () => {
  const [orgsData, setOrgsData] = useState<CompanyNews[]>([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [newCompanyNews, setNewCompanyNews] = useState({
    title: '',
    newsContent: '',
    user: '',
    status: 'drafted',
  });

  const adminId = 1;

  const fetchOrgsData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/companyNews/getAll');
      setOrgsData(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };
  useEffect(() => {
 
    fetchOrgsData();
  }, []);

  const handleEdit = async (companyNews: CompanyNews) => {
    const newStatus = companyNews.status === 'published' ? 'drafted' : 'published';
    try {

 
      const response = await axios.put(`http://localhost:8080/companyNews/2/${companyNews.id}?status=${newStatus}`);

      if (response.status === 200) {
          setOrgsData((prevOrgsData) =>
          prevOrgsData.map((news) =>
            news.id === companyNews.id ? { ...news, status: newStatus } : news
          )
        );
      }
    } catch (error) {
      console.error('Error updating status:', error);
    }
  };

  const handleDelete = async (companyNewsId: number) => {
    try {
      const response = await axios.delete(`http://localhost:8080/companyNews/${companyNewsId}`);
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
    setNewCompanyNews((prevNews) => ({
      ...prevNews,
      [name]: value,
    }));
  };

  const handleSubmit = async () => {
    const currentDate = new Date().toISOString();
    const newNewsItem = {
      ...newCompanyNews,
      user: { firstName: newCompanyNews.user }, // Assuming a simple user structure
      createdAt: currentDate,
    };
    try {
      const response = await axios.post(`http://localhost:8080/companyNews/${adminId}`, newNewsItem);
      setIsModalOpen(false);
      setLoading(true);
      fetchOrgsData();  
      if (response.status === 200) {
        setOrgsData((prevOrgsData) => [...prevOrgsData, response.data]);
        
        setNewCompanyNews({
          title: '',
          newsContent: '',
          user: '',
          status: 'drafted',
        });
      }
    } catch (error) {
      console.error('Error creating news:', error);
    }
  };

  return (
    <div className="relative h-screen flex justify-center items-center w-full">
      <img src="/images/enbg.png" className="w-full h-full" />

      <div className="absolute top-0 left-0 p-2">
        <a href="/chat">
          <img src="/images/n2.png" alt="Logo" className="w-[90px] h-[90px]" />
        </a>
      </div>

      <div className={`absolute bg-white p-8 rounded-lg shadow-md bg-transparent ${isModalOpen ? 'opacity-50' : ''}`}>
        <h1 className="text-xl font-semibold mb-4 text-center">Editar Noticias</h1>
        <table className="w-full overflow-x-auto text-center">
          <thead>
            <tr>
              <th className="px-12 py-10">Id</th>
              <th className="px-12 py-10">Título</th>
              <th className="px-12 py-10">Contenido</th>
              <th className="px-12 py-10">Usuario</th>
              <th className="px-12 py-10">Estatus</th>
              <th className="px-12 py-10">Fecha de Creación</th>
              <th className="px-12 py-10">Acciones</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan="7">Loading...</td>
              </tr>
            ) : (
              orgsData.map((companyNews) => (
                <tr key={companyNews.id}>
                  <td>{companyNews.id}</td>
                  <td>{companyNews.title}</td>
                  <td>{companyNews.newsContent}</td>
                  <td>{companyNews.user.firstName}</td>
                  <td>{companyNews.status}</td>
                  <td>{new Date(companyNews.createdAt).toLocaleDateString()}</td>
                  <td>
                    <button onClick={() => handleEdit(companyNews)} className="text-blue-500 hover:underline">
                      <FaEdit />
                    </button>
                    <button onClick={() => handleDelete(companyNews.id)} className="text-red-500 hover:underline ml-2">
                      <FaTrash />
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
        <button onClick={() => setIsModalOpen(true)} className="fixed bottom-10 right-10 text-green-500 hover:underline mt-4 flex items-center">
          <FaPlus className="mr-2" /> Add News
        </button>
      </div>

      {isModalOpen && (
        <div className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
          <div className="bg-white p-8 rounded-lg shadow-md w-1/3">
            <h2 className="text-xl font-semibold mb-4">Create New Company News</h2>
            <input
              type="text"
              name="title"
              value={newCompanyNews.title}
              onChange={handleInputChange}
              placeholder="Title"
              className="w-full mb-4 p-2 border border-gray-300 rounded"
            />
            <input
              type="text"
              name="newsContent"
              value={newCompanyNews.newsContent}
              onChange={handleInputChange}
              placeholder="Content"
              className="w-full mb-4 p-2 border border-gray-300 rounded"
            />
            <input
              type="text"
              name="user"
              value={newCompanyNews.user}
              onChange={handleInputChange}
              placeholder="User"
              className="w-full mb-4 p-2 border border-gray-300 rounded"
            />
            <select
              name="status"
              value={newCompanyNews.status}
              onChange={handleInputChange}
              className="w-full mb-4 p-2 border border-gray-300 rounded"
            >
              <option value="drafted">Drafted</option>
              <option value="published">Published</option>
            </select>
            <div className="flex justify-end">
              <button
                onClick={() => setIsModalOpen(false)}
                className="bg-gray-500 text-white px-4 py-2 rounded mr-2"
              >
                Cancel
              </button>
              <button
                onClick={handleSubmit}
                className="bg-blue-500 text-white px-4 py-2 rounded"
              >
                Submit
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default withAuth(TablaDinamica;
