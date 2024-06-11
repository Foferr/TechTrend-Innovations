'use client';

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import withAuth from '../../components/HOC/withAuth';
import Link from 'next/link';


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
  adminId: number;
  admin?: User;
  status: string;
  createdAt: string;
}

const userType = localStorage.getItem('userType');

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
    <div>
      <Link href={userType === 'admin' ? '/admin' : '/chat '}>
      <img src="/images/VectorNovaLogoJT.svg" alt="Logo" className="h-16 w-auto mx-auto my-4" />
      </Link>
      <div className="bg-white p-8 border rounded-lg w-3/4 mx-auto shadow-lg">
        <h1 className="text-center font-bold text-nova-yellow-500 text-2xl mb-6">FAQ</h1>
        {faqs.map((faq, index) => (
          <div key={index} className="border-b border-gray-300 py-4">
            <div
              className="text-nova-blue-500 font-bold cursor-pointer flex justify-between items-center"
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
    </div>
  );
}

export default withAuth(FAQ);
