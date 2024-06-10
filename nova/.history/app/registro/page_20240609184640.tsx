"use client"

import Link from "next/link";
import axios from 'axios';
import { useState } from 'react';
import { LocalDate, DateTimeFormatter } from 'js-joda';
import { useLanguage } from '../contexts/LanguageContext';
import LanguageToggleButton from "../components/LanguageToggleButton";

export default function Register() {
    const { language } = useLanguage();

    const nombresTitleText = language === 'es' ? 'Nombre/s' : 'First Name/s';
    const apellidosTitleText = language === 'es' ? 'Apellido/s' : 'Last Name/s';
    const nombresExampleText = language === 'es' ? 'Miguel Angel' : 'Michael';
    const apellidosExampleText = language === 'es' ? 'Perez Juarez' : 'Smith Jones';
    const dateOfBirthText = language === 'es' ? 'Fecha de Nacimiento' : 'Date of Birth';
    const emailTitleText = language === 'es' ? 'Correo Electrónico' : 'Email';
    const emailExampleText = language === 'es' ? 'persona@ejemplo.com' : 'person@example.com';
    const phoneTitleText = language === 'es' ? 'Teléfono' : 'Phone Number';
    const phoneTitleText = language === 'es' ? 'Teléfono' : 'Phone Number';
    

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [userPassword, setuserPassword] = useState('');
    const [country, setCountry] = useState('');
    const [lang, setLang] = useState('spanish');
    const [phone, setPhone] = useState('');
    const [birthday, setBirthday] = useState(LocalDate.now());
    const [userType, setUserType] = useState('base_user');

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/user/registerUser', {
                firstName: firstName,
                lastName: lastName,
                email: email,
                userPassword: userPassword,
                lang: lang,
                phone: phone,
                birthday: birthday,
                country: country,
                userType: userType
            });
            console.log(response.data); // Handle successful registration
            console.log(firstName);
        } catch (error) {
            console.error(error); // Handle registration error
            console.log(firstName);
            console.log(lastName);
            console.log(email);
            console.log(userPassword);
            console.log(lang);
            console.log(phone);
            console.log(birthday);
            console.log(userType);
        }
    };

    return (
        //TODO-CHBOT-235 - Agregar transiciones a elementos
        //Agregar transiciones a los elementos de la pagina para que sean mas dinamicos y agradables a la vista
        <div className="relative flex flex-row bg-white justify-center min-h-screen overflow-hidden">
            <div className="landingTitle border w-full p-6 mb-auto mt-auto bg-white rounded-md shadow-2xl sm:max-w-xl">
                <img className="w-24 min-w-px-70 ml-auto mr-auto" src="/images/VectorNovaLogoJT.svg"
                     alt=""/>
                <form className="mt-6"
                      onSubmit={handleSubmit}
                >
                    <div className="mb-2 flex space-x-10">
                        <div>
                            <label
                                htmlFor="firstName"
                                className="block text-sm font-semibold text-nova-blue-500"
                            >
                                {nombresTitleText}
                            </label>
                            <input
                                type="text"
                                placeholder={nombresExampleText}
                                value={firstName}
                                onChange={(e) => setFirstName(e.target.value)}
                                className="block  w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                            />
                        </div>
                        <div>
                            <label
                                htmlFor="lastName"
                                className="block text-sm font-semibold text-nova-blue-500"
                            >
                                {apellidosTitleText}
                            </label>
                            <input
                                type="text"
                                placeholder={apellidosExampleText}
                                value={lastName}
                                onChange={(e) => setLastName(e.target.value)}
                                className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                            />
                        </div>

                    </div>
                    <div className="mb-2">
                        <label
                            htmlFor="birthD"
                            className="block text-sm font-semibold text-nova-blue-500"
                        >
                            {dateOfBirthText}
                        </label>
                        <input
                            type="date"
                            value={birthday.toString()}
                            onChange={
                                (e) => {
                                    setBirthday(LocalDate.parse(e.target.value, DateTimeFormatter.ISO_LOCAL_DATE))
                                }
                            }
                            className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <div className="mb-2">
                        <label
                            htmlFor="email"
                            className="block text-sm font-semibold text-nova-blue-500"
                        >
                            {emailTitleText}
                        </label>
                        <input
                            type="email"
                            placeholder={emailExampleText}
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className="block  w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <div className="mb-2">
                        <label
                            htmlFor="phone"
                            className="block text-sm font-semibold text-nova-blue-500"
                        >
                            {phoneTitleText}
                        </label>
                        <input
                            type="tel"
                            placeholder="+1234567890"
                            value={phone}
                            onChange={(e) => setPhone(e.target.value)}
                            className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <div className="mb-2">
                        <label
                            htmlFor="password"
                            className="block text-sm font-semibold text-nova-blue-500"
                        >
                            Contraseña
                        </label>
                        <input
                            type="password"
                            placeholder="Contraseña"
                            value={userPassword}
                            onChange={(e) => setuserPassword(e.target.value)}
                            className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <div className="mb-2">
                        <label
                            htmlFor="country"
                            className="block text-sm font-semibold text-nova-blue-500"
                        >
                            País
                        </label>
                        <input
                            placeholder="País"
                            value={country}
                            onChange={(e) => setCountry(e.target.value)}
                            className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <div className="mb-2">
                        <label
                            htmlFor="birthD"
                            className="block text-sm font-semibold text-nova-blue-500"
                        >
                            Idioma
                        </label>
                        <select
                            value={lang}
                            onChange={(e) => setLang(e.target.value)}
                            className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40">
                            <option className="block text-sm font-semibold text-neoris-grey-100"
                                    value="es">Español
                            </option>
                            <option className="block text-sm font-semibold text-neoris-grey-100"
                                    value="en">Inglés
                            </option>
                        </select>
                    </div>
                    <div className="mt-10">
                        <Link href="/chat">
                            <button
                                className="w-full px-4 py-2 tracking-wide bg-nova-blue-500 text-white rounded-md hover:bg-nova-blue-100 active:bg-nova-blue-500">
                                <div>Registrar</div>
                            </button>
                        </Link>
                    </div>
                </form>

                <p className="mt-4 text-sm text-center text-nova-blue-500">
                    ¿Ya tienes cuenta?{" "}
                    <Link
                        href="/login"
                        className="font-medium text-blue-600 hover:underline"
                    >
                        Inicia sesión
                    </Link>
                </p>

                <div className="relative flex pt-10 pb-5 items-center">
                    <div className="flex-grow border-t border-nova-blue-500"></div>
                    <span className="flex-shrink mx-4 text-neoris-grey-100">O registrate con</span>
                    <div className="flex-grow border-t border-nova-blue-500"></div>
                </div>

                <div className="mt-1 grid grid-cols-3 gap-3">
                    <div>
                        <a href="#"
                           className="w-full flex items-center justify-center px-8 py-3 rounded-md shadow-sm text-sm font-medium bg-nova-blue-500 hover:bg-nova-blue-100 active:bg-nova-blue-500">
                            <img className="h-5 w-5" src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/2021_Facebook_icon.svg/512px-2021_Facebook_icon.svg.png?20220821121039"
                                 alt=""/>
                        </a>
                    </div>
                    <div>
                        <a href="#"
                           className="w-full flex items-center justify-center px-8 py-3 rounded-md shadow-sm text-sm font-medium bg-nova-blue-500 hover:bg-nova-blue-100 active:bg-nova-blue-500">
                            <img className="h-5 w-5" src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/120px-Google_%22G%22_logo.svg.png?20230822192911"
                                 alt=""/>
                        </a>
                    </div>
                    <div>
                        <a href="#"
                           className="w-full flex items-center justify-center px-8 py-3 rounded-md shadow-sm text-sm font-medium bg-nova-blue-500 hover:bg-nova-blue-100 active:bg-nova-blue-500">
                            <img className="h-5 w-5" src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/X_logo_2023.svg/300px-X_logo_2023.svg.png?20230819000805"
                                 alt=""/>
                        </a>
                    </div>
                </div>
            </div>
            <LanguageToggleButton/>
        </div>
    );
}