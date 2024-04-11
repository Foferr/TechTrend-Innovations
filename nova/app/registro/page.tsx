import Link from "next/link";
export default function Register() {
    return (
        <div className="relative flex flex-row space-x-52 bg-gradient-to-t from-neoris-grey-100 to-neoris-white-100 pr-5 items-end justify-end min-h-screen overflow-hidden">
            <img className="absolute -top-80 right-60 w-full" src="/images/Pattern%20full%20light.svg" alt=""/>
            <div className="w-full p-6 z-0 mb-auto mt-auto bg-neoris-grey-100 rounded-md shadow-md lg:max-w-xl">
                <img className="w-72 min-w-px-70 ml-auto mr-auto" src="/images/NEORIS%20logo%20light%20(vector).svg"
                     alt=""/>
                <h1 className="text-3xl mt-6 text-center text-neoris-white-100">Registro</h1>
                    <form className="mt-6">
                        <div className="flex space-x-3">
                                <div className="mb-4">
                                    <label
                                        htmlFor="user"
                                        className="block text-sm font-semibold text-neoris-white-100"
                                    >
                                        Usuario
                                    </label>
                                    <input
                                        type="text"
                                        className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                                    />
                                </div>
                                <div className="mb-2">
                                    <label
                                        htmlFor="email"
                                        className="block text-sm font-semibold text-neoris-white-100"
                                    >
                                        Correo electrónico
                                    </label>
                                    <input
                                        type="email"
                                        className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                                    />
                                </div>
                        </div>
                        <div className="flex space-x-3">
                                <div className="mb-2">
                                    <label
                                        htmlFor="password"
                                        className="block text-sm font-semibold text-neoris-white-100"
                                    >
                                        Contraseña
                                    </label>
                                    <input
                                        type="password"
                                        className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                                    />
                                </div>
                                <div className="mb-2">
                                    <label
                                        htmlFor="password"
                                        className="block text-sm font-semibold text-neoris-white-100"
                                    >
                                        Confirmar contraseña
                                    </label>
                                    <input
                                        type="password"
                                        className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
                                    />
                                </div>
                        </div>
                        <div className="mb-2">
                            <label
                                htmlFor="lenguage"
                                className="block text-sm font-semibold text-neoris-white-100"
                            >
                                Lenguaje
                            </label>
                            <select id="language" name="languages"
                                className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40">
                                <option className="block text-sm font-semibold text-neoris-grey-100" value="spanish">Español</option>
                                <option className="block text-sm font-semibold text-neoris-grey-100" value="english">Inglés</option>
                            </select>
                        </div>
                        <div className="mt-2">
                            <Link href="/">
                                <button className="w-full px-4 py-2 tracking-wide bg-neoris-white-100 text-neoris-grey-100 rounded-md hover:bg-neoris-white-50 active:bg-neoris-grey-50 active:text-neoris-white-100">
                                    <div>Registrar</div>
                                </button>
                            </Link>
                        </div>
                    </form>

                <p className="mt-4 text-sm text-center text-neoris-white-100">
                    ¿Tienes cuenta?{" "}
                    <Link
                        href="/"
                        className="font-medium text-blue-600 hover:underline"
                    >
                        Inicia sesión
                    </Link>
                </p>

                <div className="relative flex pt-10 pb-5 items-center">
                    <div className="flex-grow border-t border-gray-400"></div>
                    <span className="flex-shrink mx-4 text-neoris-white-100 text-opacity-50">O registrate con</span>
                    <div className="flex-grow border-t border-gray-400"></div>
                </div>

                <div className="mt-1 grid grid-cols-3 gap-3">
                    <div>
                        <a href="#"
                           className="w-full flex items-center justify-center px-8 py-3 rounded-md shadow-sm text-sm font-medium bg-neoris-white-100 hover:bg-neoris-white-50 active:bg-neoris-grey-50 active:text-neoris-white-100">
                            <img className="h-5 w-5" src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/2021_Facebook_icon.svg/512px-2021_Facebook_icon.svg.png?20220821121039"
                                 alt=""/>
                        </a>
                    </div>
                    <div>
                        <a href="#"
                           className="w-full flex items-center justify-center px-8 py-3 rounded-md shadow-sm text-sm font-medium bg-neoris-white-100 hover:bg-neoris-white-50 active:bg-neoris-grey-50 active:text-neoris-white-100">
                            <img className="h-5 w-5" src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/120px-Google_%22G%22_logo.svg.png?20230822192911"
                                 alt=""/>
                        </a>
                    </div>
                    <div>
                        <a href="#"
                           className="w-full flex items-center justify-center px-8 py-3 rounded-md shadow-sm text-sm font-medium bg-neoris-white-100 hover:bg-neoris-white-50 active:bg-neoris-grey-50 active:text-neoris-white-100">
                            <img className="h-5 w-5" src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/X_logo_2023.svg/300px-X_logo_2023.svg.png?20230819000805"
                                 alt=""/>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    );
}
