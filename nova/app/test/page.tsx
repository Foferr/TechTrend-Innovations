import Link from "next/link";
export default function Home() {
    return (
        //TODO-CHBOT-235 - Agregar transiciones a elementos
        //Agregar transiciones a los elementos de la pagina para que sean mas dinamicos y agradables a la vista
        <div className="relative flex flex-row bg-white justify-center min-h-screen overflow-hidden">
            <div className="landingTitle border w-full p-6 mb-auto mt-auto bg-white rounded-md shadow-2xl sm:max-w-xl">
                <img className="w-20 min-w-px-70 ml-auto mr-auto" src="/images/VectorNovaLogoBlue.svg"
                     alt=""/>
                <form className="mt-6">
                    <div className="mb-2 flex space-x-10">
                        <input
                            type="text"
                            placeholder="Nombres"
                            className="block  w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                        <input
                            type="text"
                            placeholder="Apellidos"
                            className="block  w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <div className="mb-2">
                        <input
                            type="email"
                            placeholder="Email"
                            className="block  w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <div className="mb-4">
                        <input
                            type="password"
                            placeholder="Contraseña"
                            className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <div className="mb-4">
                        <input
                            placeholder="País"
                            className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <div className="mb-4">
                        <select
                                className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40">
                            <option className="block text-sm font-semibold text-neoris-grey-100" value="spanish">Español</option>
                            <option className="block text-sm font-semibold text-neoris-grey-100" value="english">Inglés</option>
                        </select>
                    </div>
                    <div className="mb-2">
                        <input
                            type="tel"
                            placeholder="Teléfono"
                            className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <div className="mt-2">
                        <Link href="/chat">
                            <button className="w-full px-4 py-2 tracking-wide bg-nova-blue-500 text-white rounded-md hover:bg-nova-blue-100 active:bg-nova-blue-500">
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
        </div>
    );
}