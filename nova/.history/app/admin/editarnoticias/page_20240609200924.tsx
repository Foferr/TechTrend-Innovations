'use client';

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaEdit, FaTrash, FaPlus } from 'react-icons/fa';
import NavbarComponent from "../../components/NavBar";
import withAuth from '../../components/HOC/withAuth';
import LanguageToggleButton from '@/app/components/LanguageToggleButton';
import { useLanguage } from '@/app/contexts/LanguageContext';


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

interface CompanyNews {
  id: number;
  title: string;
  newsContent: string;
  adminId: number,
  admin?: Admin;
  status: string;
  createdAt: string;
}

interface ColumnItems {
  id: string;
  titulo: string;
  answer: string;
  contenido: string;
  usuario: string;
  estatus: string;
  creacion: string;
  acciones: string;
}

const TablaDinamica = () => {
  const { language } = useLanguage();

  const columnNames: { [key: string]: ColumnItems } = {
    es: {
      id: 'ID',
      question: 'Pregunta',
      answer: 'Respuesta',
      username: 'Usuario',
      status: 'Estatus',
      creationDate: 'Fecha de Creación',
      actions: "Acciones"
    },
    en: {
      id: 'ID',
      question: 'Question',
      answer: 'Answer',
      username: 'User',
      status: 'Status',
      creationDate: 'Creation Date',
      actions: "Actions"
    }
  };

  const [orgsData, setOrgsData] = useState<CompanyNews[]>([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [newCompanyNews, setNewCompanyNews] = useState({
    title: '',
    newsContent: '',
    status: 'drafted',
  });

  const fetchOrgsData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/companyNews/getAll');
      const companyNewsList = response.data;

      const adminPromises = companyNewsList.map((companyNewsItem: CompanyNews) => 
        axios.get(`http://localhost:8080/user/${companyNewsItem.adminId}`).then(res => res.data)
      );

      const admins = await Promise.all(adminPromises);

      const companyNewsWithAdminData = companyNewsList.map((companyNewsItem: CompanyNews, index: number) => ({
        ...companyNewsItem,
        admin: admins[index],
      }));

      setOrgsData(companyNewsWithAdminData);
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
    const updatedCompanyNews = { ...companyNews, status: newStatus};
    try {
      const response = await axios.put(`http://localhost:8080/companyNews/${localStorage.getItem('userId')}/${companyNews.id}`, updatedCompanyNews);
      if (response.status === 201) {
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
      setOrgsData((prevOrgsData) => prevOrgsData.filter((news) => news.id !== companyNewsId));
      if (response.status === 200) {

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
    const newNewsItem = {
      ...newCompanyNews
    };
    try {
      const response = await axios.post(`http://localhost:8080/companyNews/${localStorage.getItem('userId')}`, newNewsItem);
      //WsetOrgsData((prevOrgsData) => [...prevOrgsData, response.data]);
      setIsModalOpen(false);
      fetchOrgsData();
      /*setNewCompanyNews({
        title: '',
        newsContent: '',
        user: '',
        status: 'drafted',
      });*/
      if (response.status === 200) {

      }
    } catch (error) {
      console.error('Error creating news:', error);
    }
  };

  return (
    <div>
      <NavbarComponent />
      <div className="relative min-h-screen flex flex-col items-center bg-gray-100 py-8">
        <div className={`relative w-11/12 bg-white p-8 rounded-lg shadow-md border-solid border border-gray-300 ${isModalOpen ? 'opacity-50' : ''}`}>
          <h1 className="text-2xl font-semibold mb-6 text-center">Editar Noticias</h1>
          <table className="w-full table-auto text-center border-collapse">
            <thead>
              <tr>
                <th className="border-b py-4">Id</th>
                <th className="border-b py-4">Título</th>
                <th className="border-b py-4">Contenido</th>
                <th className="border-b py-4">Usuario</th>
                <th className="border-b py-4">Estatus</th>
                <th className="border-b py-4">Fecha de Creación</th>
                <th className="border-b py-4">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {loading ? (
                <tr>
                  <td colSpan={7} className="py-6">
                    <div className="flex justify-center">
                      <svg className="animate-spin h-8 w-8 text-blue-500" viewBox="0 0 24 24">
                        <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                        <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8h8a8 8 0 11-8 8 8 8 0 01-8-8z"></path>
                      </svg>
                    </div>
                  </td>
                </tr>
              ) : (
                orgsData.map((companyNews) => (
                  <tr key={companyNews.id}>
                    <td className="border-b py-4 max-w-xs overflow-hidden">{companyNews.id}</td>
                    <td className="border-b py-4 max-w-xs overflow-hidden">{companyNews.title}</td>
                    <td className="border-b py-4 max-w-xs overflow-hidden">{companyNews.newsContent}</td>
                    <td className="border-b py-4 max-w-xs overflow-hidden">
                    {companyNews.admin ? `${companyNews.admin.firstName} ${companyNews.admin.lastName}` : 'Loading...'}
                      </td>
                    <td className="border-b py-4 max-w-xs overflow-hidden">{companyNews.status}</td>
                    <td className="border-b py-4 max-w-xs overflow-hidden">{new Date(companyNews.createdAt).toLocaleDateString()}</td>
                    <td className="border-b py-4 max-w-xs overflow-hidden">
                      <button onClick={() => handleEdit(companyNews)} className="text-blue-500 hover:text-blue-700">
                        <FaEdit />
                      </button>
                      <button onClick={() => handleDelete(companyNews.id)} className="text-red-500 hover:text-red-700">
                        <FaTrash />
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
          <button onClick={() => setIsModalOpen(true)} className="fixed bottom-10 right-10 bg-green-500 text-white p-4 rounded-full shadow-lg hover:bg-green-700 flex items-center">
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
              {/* <input
                type="text"
                name="user"
                value={newCompanyNews.user}
                onChange={handleInputChange}
                placeholder="User"
                className="w-full mb-4 p-2 border border-gray-300 rounded"
              /> */}
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
                  className="bg-gray-500 text-white px-4 py-2 rounded mr-2 hover:bg-gray-700"
                >
                  Cancel
                </button>
                <button
                  onClick={handleSubmit}
                  className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700"
                >
                  Submit
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
      <LanguageToggleButton/>
    </div>
  );
};

export default withAuth(TablaDinamica);
