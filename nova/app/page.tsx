import Link from "next/link";
export default function Home() {
  return (
      <div className="relative flex flex-col p-32 items-end justify-center min-h-screen overflow-hidden">
        <div className="w-full p-6 bg-neoris-grey-100 rounded-md shadow-md lg:max-w-xl">
          <h1 className="text-3xl font-bold text-center ttext-neoris-white-100">Inicio de sesión</h1>
          <form className="mt-6">
            <div className="mb-4">
              <label
                  htmlFor="email"
                  className="block text-sm font-semibold text-neoris-white-100"
              >
                Usuario
              </label>
              <input
                  type="email"
                  className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border rounded-md focus:border-gray-400 focus:ring-gray-300 focus:outline-none focus:ring focus:ring-opacity-40"
              />
            </div>
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
            <div className="mt-2">
              <button className="w-full px-4 py-2 tracking-wide bg-neoris-white-100 text-neoris-grey-100 rounded-md hover:bg-neoris-white-50 active:bg-neoris-grey-50 active:text-neoris-white-100">
                Iniciar sesión
              </button>
            </div>
          </form>

          <p className="mt-4 text-sm text-center text-neoris-white-100">
            ¿No tienes cuenta?{" "}
            <Link
                href="/signup"
                className="font-medium text-blue-600 hover:underline"
            >
              Registrate
            </Link>
          </p>

          <div className="relative flex pt-10 pb-5 items-center">
            <div className="flex-grow border-t border-gray-400"></div>
            <span className="flex-shrink mx-4 text-neoris-white-100 text-opacity-50">O continua con</span>
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
