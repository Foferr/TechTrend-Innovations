"use client"

import Link from "next/link";
import axios from 'axios';
import { useState } from 'react';
import { LocalDate, DateTimeFormatter } from 'js-joda';
import { useLanguage } from '../contexts/LanguageContext';
import LanguageToggleButton from "../components/LanguageToggleButton";
import { useRouter } from 'next/navigation';

export default function Register() {
    const router = useRouter();

    const { language } = useLanguage();

    const nombresTitleText = language === 'es' ? 'Nombre/s' : 'First Name/s';
    const apellidosTitleText = language === 'es' ? 'Apellido/s' : 'Last Name/s';
    const nombresExampleText = language === 'es' ? 'Miguel Angel' : 'Michael';
    const apellidosExampleText = language === 'es' ? 'Perez Juarez' : 'Smith Jones';
    const dateOfBirthText = language === 'es' ? 'Fecha de Nacimiento' : 'Date of Birth';
    const emailTitleText = language === 'es' ? 'Correo Electrónico' : 'Email';
    const emailExampleText = language === 'es' ? 'persona@ejemplo.com' : 'person@example.com';
    const phoneTitleText = language === 'es' ? 'Teléfono' : 'Phone Number';
    const passwordTitleText = language === 'es' ? 'Contraseña' : 'Password';
    const passwordExampleText = language === 'es' ? 'ej: Seguridad$1' : 'ex: Secured$1';
    const countryTitleText = language === 'es' ? 'País' : 'Country';
    const countryExampleText = language === 'es' ? 'ej: México' : 'ex: United States';
    const langTitleText = language === 'es' ? 'Idioma' : 'Language';
    const langOptionsText: { [key: string]: string[]} = {
        es: [
            'Español',
            'Inglés'
        ],
        en: [
            'Spanish',
            'English'
        ]
    }
    const registerButtonText = language === 'es' ? 'Registrar' : 'Sign Up';
    const hasAccountText = language === 'es' ? '¿Ya tienes cuenta?' : 'Already have an account?';
    const loginLinkText = language === 'es' ? 'Inicia Sesión' : 'Sign In';
    const oAuthOptionsText = language === 'es' ? 'O registrate con' : 'Or sign up with';
    const confirmedMessage = language === 'es' ? 'Usuario registrado correctamente' : 'User registered successfully';

    const validatePassword = (password: any) => {
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if (password === undefined) {
            return language === 'es' ? 'La contraseña está vacía' : 'The password is empty';
        }
        if (password.length < 8) {
            return language === 'es' ? 'La contraseña debe tener al menos 8 caracteres' : 'The password must be at least 8 characters long';
        }
        const numberRegex = /\d/;
        if (!numberRegex.test(password)) {
            return language === 'es' ? 'La contraseña debe tener al menos un número' : 'The password must have at least one number';
        }
        const upperCaseRegex = /[A-Z]/;
        if (!upperCaseRegex.test(password)) {
            return language === 'es' ? 'La contraseña debe tener al menos una mayúscula' : 'The password must have at least one uppercase letter';
        }
        const lowerCaseRegex = /[a-z]/;
        if (!lowerCaseRegex.test(password)) {
            return language === 'es' ? 'La contraseña debe tener al menos una minúscula' : 'The password must have at least one lowercase letter';
        }
        const specialCharRegex = /[#@$!%*?&]/;
        if (!specialCharRegex.test(password)) {
            return language === 'es' ? 'La contraseña debe tener al menos un caracter especial' : 'The password must have at least one special character';
        }
        passwordRegex.test(password);

        return null;
    };
    

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [userPassword, setuserPassword] = useState('');
    const [country, setCountry] = useState('');
    const [lang, setLang] = useState('es');
    const [phone, setPhone] = useState('');
    const [birthday, setBirthday] = useState(LocalDate.now());
    const [userType, setUserType] = useState('user');

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const passwordError = validatePassword(userPassword);
        if (passwordError) {
            alert(passwordError);
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/user/registerUser', {
                firstName: firstName,
                lastName: lastName,
                language: lang,
                birthday: birthday,
                email: email,
                userPassword: userPassword,
                phone: phone,
                country: country,
                userType: userType
            });
            alert(confirmedMessage);
            router.push('/login');
            // console.log(response.data); // Handle successful registration
            // console.log(firstName);
        } catch (error) {
            console.error(error); // Handle registration error
            // console.log(firstName);
            // console.log(lastName);
            // console.log(email);
            // console.log(userPassword);
            // console.log(lang);
            // console.log(phone);
            // console.log(birthday);
            // console.log(userType);
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
                    <div className="mb-2 flex space-x-5">
                        <div className="mb-2 flex-grow">
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
                        <div className="mb-2 flex-grow">
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
                    <div className="mb-2 flex-grow">
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
                    <div className="mb-2 flex space-x-5 flex-grow">

                    <div className="mb-2 flex-grow">
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
                    <div className="mb-2 flex-grow">
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
                    </div>
                    <div className="mb-2 flex space-x-5 flex-grow">
                    <div className="mb-2 flex-grow">
                        <label
                            htmlFor="password"
                            className="block text-sm font-semibold text-nova-blue-500"
                        >
                            {passwordTitleText}
                        </label>
                        <input
                            type="password"
                            placeholder={passwordExampleText}
                            value={userPassword}
                            onChange={(e) => setuserPassword(e.target.value)}
                            className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <div className="mb-2 flex-grow">
                        <label
                            htmlFor="country"
                            className="block text-sm font-semibold text-nova-blue-500"
                        >
                            {countryTitleText}
                        </label>
                        <input
                            placeholder={countryExampleText}
                            value={country}
                            onChange={(e) => setCountry(e.target.value)}
                            className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    </div>
                    <div className="mb-2 flex-grow">
                        <label
                            htmlFor="birthD"
                            className="block text-sm font-semibold text-nova-blue-500"
                        >
                            {langTitleText}
                        </label>
                        <select
                            value={lang}
                            onChange={(e) => setLang(e.target.value)}
                            className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40">
                            <option className="block text-sm font-semibold text-neoris-grey-100"
                                    value="es">{langOptionsText[language][0]}
                            </option>
                            <option className="block text-sm font-semibold text-neoris-grey-100"
                                    value="en">{langOptionsText[language][1]}
                            </option>
                        </select>
                    </div>
                    
                    <div className="mt-10">
                            <button
                                className="w-full px-4 py-2 tracking-wide bg-nova-blue-500 text-white rounded-md hover:bg-nova-blue-100 active:bg-nova-blue-500">
                                <div>{registerButtonText}</div>
                            </button>
                    </div>
                </form>

                <p className="mt-4 text-sm text-center text-nova-blue-500">
                    {hasAccountText}{" "}
                    <Link
                        href="/login"
                        className="font-medium text-blue-600 hover:underline"
                    >
                        {loginLinkText}
                    </Link>
                </p>

            </div>
            <LanguageToggleButton/>
        </div>
    );
}