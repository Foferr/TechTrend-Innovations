import Link from "next/link";
export default function Home() {
  return (
      //TODO-CHBOT-235 - Agregar transiciones a elementos
      //Agregar transiciones a los elementos de la pagina para que sean mas dinamicos y agradables a la vista
      <div className="relative flex flex-row space-x-52 bg-nova-yellow-500 pr-5 items-end justify-end min-h-screen overflow-hidden">
        <div className="logInBox w-full p-6 z-0 mb-auto mt-auto bg-nova-blue-500 rounded-md shadow-md lg:max-w-xl">
          <img className="w-72 min-w-px-70 ml-auto mr-auto" src="/images/NEORIS%20logo%20light%20(vector).svg"
               alt=""/>
          <h1 className="text-3xl mt-6 text-center text-neoris-white-100">Inicio de sesión</h1>
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
              <Link href="/chat">
                <button className="w-full px-4 py-2 tracking-wide bg-neoris-white-100 text-neoris-grey-100 rounded-md hover:bg-neoris-white-50 active:bg-neoris-grey-50 active:text-neoris-white-100">
                  <div>Iniciar sesión</div>
                </button>
              </Link>
            </div>
          </form>

          <p className="mt-4 text-sm text-center text-neoris-white-100">
            ¿No tienes cuenta?{" "}
            <Link
                href="/registro"
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

// TODO-CHBOT-231 - Rebranding landing page
// Rediseñar la landing page para ser mas dinamica y visualmente interesante
// <div className="relative flex flex-row bg-neoris-grey-100 items-center justify-start min-h-screen overflow-hidden">
//     <video autoPlay loop muted className="absolute -z-0 w-auto min-w-full min-h-full max-w-none" src="/images/121799-724719792.mp4"></video>
//     <div className="relative flex flex-col pl-24 space-y-8">
//         <div className="animatedText -mb-16">Nova</div>
//         <h2 className="text-neoris-white-100 text-2xl w-full">Conoce los productos que Neoris tiene que ofrecer</h2>
//     </div>
// </div>