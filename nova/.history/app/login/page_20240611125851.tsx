"use client";

import Link from "next/link";
import { useState, FormEvent } from 'react';
import getUser from '../components/HOC/getUser';
import { useLanguage } from '../contexts/LanguageContext';
import LanguageToggleButton from "../components/LanguageToggleButton";

export default function Home() {
    const { language } = useLanguage();

    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const loginButtonText = language === 'es' ? 'Iniciar Sesión' : 'Login';
    const noAccountText = language === 'es' ? '¿No tienes cuenta?' : "Don't have an account yet?"
    const registerText = language === 'es' ? 'Registrate' : 'Sign Up';
    const signInWithText = language === 'es' ? 'O inicia sesión con' : 'Or sign in with';
    const emailText = language === 'es' ? 'Correo Electrónico' : 'Email';
    const passwordText = language === 'es' ? 'Contraseña' : 'Password';


    const handleLogin = async (e: FormEvent) => {
        e.preventDefault();

        try {
            console.log('just before login request');
            console.log('email: ' + email);
            console.log('password: ' + password);
            const response = await fetch('http://localhost:8090/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password }),
            });

            if(response.ok) {
                const data = await response.json();
                console.log('access token is: ');
                console.log(data.accessToken);
                console.log('refresh token is: ');
                console.log(data.refreshToken);
                localStorage.setItem('accessToken', data.accessToken);
                const user = getUser();
                localStorage.setItem('refreshToken', data.refreshToken);
                if (user && user.userType) {
                    localStorage.setItem('userType', user?.userType);
                    localStorage.setItem('userId', user?.userId.toString());
                    const userId = user?.userId.toString();
                    const userResponse = await fetch(`http://localhost:8080/user/${userId}`, {
                        headers: {
                            'Authorization': `Bearer ${data.accessToken}`,
                        },
                    });
                    if (user?.userType === 'admin') {
                        window.location.href = '/admin';
                    } else {
                        window.location.href = '/chat';
                    }
                }
            } else {
                console.error('Login failed');
            }
        } catch (error) {
            console.error('Login failed', error);
        }
    };

    return (
        //TODO-CHBOT-235 - Agregar transiciones a elementos
        //Agregar transiciones a los elementos de la pagina para que sean mas dinamicos y agradables a la vista
        <div className="relative flex flex-row bg-white justify-center min-h-screen overflow-hidden">
            <div className="landingTitle border w-full p-6 mb-auto mt-auto bg-white rounded-md shadow-2xl sm:max-w-xl">
                <img className="w-40 min-w-px-70 ml-auto mr-auto" src="/images/VectorNovaLogoJT.svg"
                     alt=""/>
                <form className="mt-6" onSubmit={handleLogin}>
                    <div className="mb-4">
                        <input
                            type="email"
                            placeholder={emailText}
                            className="block  w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>
                    <div className="mb-2">
                        <input
                            type="password"
                            placeholder={passwordText}
                            className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                    <div className="mt-2">
                            <button type="submit" className="w-full px-4 py-2 tracking-wide bg-nova-blue-500 text-white rounded-md hover:bg-nova-blue-100 active:bg-nova-blue-500">
                                <div>{loginButtonText}</div>
                            </button>
                    </div>
                </form>

                <p className="mt-4 text-sm text-center text-nova-blue-500">
                {noAccountText}{" "}
                    <Link
                        href="/registro"
                        className="font-medium text-blue-600 hover:underline"
                    >
                        {registerText}
                    </Link>
                </p>
            </div>
            <LanguageToggleButton/>
        </div>
    );
}

// TODO-CHBOT-231 - Rebranding landing page
// Rediseñar la landing page para ser mas dinamica y visualmente interesante
// <div className="relative flex flex-row bg-neoris-grey-100 items-center justify-start min-h-screen overflow-hidden">
//     <video autoPlay loop muted className="absolute -z-0 w-auto min-w-full min-h-full max-w-none" src="/images/121799-724719792.mp4"></video>
//     <div className="relative flex flex-col pl-24 space-y-8">
//         <div className="animatedText -mb-16">Nova</div>
//         <h2 className="text-neoris-white-100 text-2xl w-full">Conoce los productos que Neoris tiene que ofrecer</h2>
//     </div>
// </div>