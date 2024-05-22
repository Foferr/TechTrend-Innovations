"use client";

import React from 'react'
import Link from "next/link";
import axios from 'axios';
import { useState } from 'react';
import { useEffect } from 'react';

const FAQComponent = () => {
  const [faqs, setFaqs] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/FAQ/getAll')
      .then(response => {
        setFaqs(response.data);
      })
      .catch(error => console.error('Hubo un error al obtener los datos:', error));
  }, []);

  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap -mx-2 overflow-auto">
        {faqs.map((faq, index) => (
          <div key={index} className="w-full md:w-1/2 lg:w-1/3 px-2 py-4">
            <div className="bg-white p-4 shadow rounded">
              <h2 className="text-lg font-semibold mb-2">{faq.pregunta}</h2>
              <p className="text-gray-700">{faq.respuesta}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FAQComponent;

