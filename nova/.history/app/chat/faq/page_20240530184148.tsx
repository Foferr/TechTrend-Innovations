'use client';

import React, { useState, useEffect } from 'react';
import axios from 'axios';

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

interface FAQ {
  question: string;
  answer: string;
  admin: User;
  status: string;
  createdAt: string;
}

const FAQ: React.FC = () => {
  const [faqs, setFaqs] = useState<FAQ[]>([]);
  const [activeIndex, setActiveIndex] = useState<number | null>(null);

  useEffect(() => {
    axios.get('http://localhost:8080/FAQ/getAll')
      .then(response => {
        setFaqs(response.data);
      })
      .catch(error => {
        console.error("There was an error fetching the FAQs!", error);
      });
  }, []);

  const toggleAnswer = (index: number) => {
    setActiveIndex(index === activeIndex ? null : index);
  };

  return (
    <div className="bg-white p-8 rounded-lg w-3/4 mx-auto shadow-lg">
      <h1 className="text-center text-orange-500 text-2xl mb-6">FAQ</h1>
      {faqs.map((faq, index) => (
        <div key={index} className="border-b border-gray-300 py-4">
          <div
            className="text-blue-500 font-bold cursor-pointer flex justify-between items-center"
            onClick={() => toggleAnswer(index)}
          >
            {faq.question}
            <span className="text-lg transition-transform duration-300">
              {activeIndex === index ? '▲' : '▼'}
            </span>
          </div>
          {activeIndex === index && (
            <div className="text-black mt-2 pl-4">
              {faq.answer}
            </div>
          )}
        </div>
      ))}
    </div>
  );
}

export default FAQ;
