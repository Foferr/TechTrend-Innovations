import Link from "next/link";
import Image from "next/image";
import "./styles.css";
import "../globals.css";

export default function Chat() {
    return (
        <body>
            <div className="upperDiv">
                <Image
                    src="/Brand/Icon/Icon dark.png"
                    alt=""
                    width={100}
                    height={100}
                />           
                <h1>Noticias</h1>
                <img src="/images/threelines.jpg" alt="" className="w-10"/>
            </div>
        </body>
    );
}
