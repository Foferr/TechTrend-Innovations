import Link from "next/link";
import localFont from "next/dist/compiled/@next/font/dist/local";
export default function Home() {
    return (

        <div
            className="relative flex flex-row space-x-52 bg-neoris-grey-100 items-center justify-start min-h-screen overflow-hidden">

            <div className="relative flex flex-col pl-20 space-y-8">
                <div className="animatedText -mb-16">Nova</div>
                <h2 className="text-neoris-white-100 font-semibold">Conoce los productos que Neoris tiene que ofrecer</h2>
            </div>

        </div>
    );
}
