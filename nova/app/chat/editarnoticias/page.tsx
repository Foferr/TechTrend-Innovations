'use client';

import React, { useState, useEffect } from 'react';
import axios from 'axios';

interface CompanyNews {
  id: number;
  title: string;
  news_content: string;
  created_by_admin_id: number;
  status: 'published' | 'not published';
  created_at: string;
  updated_at: string;
}

const TablaDinamica = () => {
  const [datos, setDatos] = useState<CompanyNews[]>([]);

  useEffect(() => {
    axios.get('http://localhost:8080/companyNews/getAll')
      .then(response => {
        setDatos(response.data as CompanyNews[]);
      })
      .catch(error => {
        console.error('There was an error fetching the data:', error);
      });
  }, []);

  //if (!datos.length) return <p>Loading...</p>;

  return (
    <div className='container mx-auto'>
      <div className='overflow-auto'>
        <table className='min-w-full divide-y divide-gray-200'>
          <thead className='bg-gray-50'>
            <tr>
              {Object.keys(datos[0]).map((key, index) => (
                <th key={index} className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                  {key}
                </th>
              ))}
            </tr>
          </thead>
          <tbody className='bg-white divide-y divide-gray-200'>
            {datos.map((fila, index) => (
              <tr key={index}>
                {Object.values(fila).map((valor, index) => (
                  <td key={index} className='px-6 py-4 whitespace-nowrap'>
                    {valor}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default TablaDinamica;
