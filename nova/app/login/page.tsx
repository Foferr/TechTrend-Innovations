import Link from "next/link";
import localFont from "next/dist/compiled/@next/font/dist/local";
export default function Home() {
    return (
        <div className="relative flex flex-row space-x-52 bg-gradient-to-t from-neoris-grey-100 to-neoris-white-100 items-center justify-start min-h-screen overflow-hidden">
            <div className="relative flex flex-col pl-20 space-y-8">
                <img className="h-4/6 w-4/6" src="/images/Nova.svg"
                    alt=""></img>
                <h2 className="text-neoris-white-100">Conoce los productos que Neoris tiene que ofrecer</h2>
            </div>

        </div>
    );
}
