import Link from "next/link";
import localFont from "next/dist/compiled/@next/font/dist/local";
export default function Home() {
    return (
        // TODO-CHBOT-231 - Rebranding landing page
        // Rediseñar la landing page para ser mas dinamica y visualmente interesante
        <div
            className="relative flex flex-row bg-neoris-grey-100 items-center justify-start min-h-screen overflow-hidden">
            <video autoPlay loop muted className="absolute -z-0 w-auto min-w-full min-h-full max-w-none" src="/images/121799-724719792.mp4"></video>
            <div className="relative flex flex-col pl-24 space-y-8">
                <div className="animatedText -mb-16">Nova</div>
                <h2 className="text-neoris-white-100 text-2xl w-full">Conoce los productos que Neoris tiene que ofrecer</h2>
            </div>


        </div>
    );
}
