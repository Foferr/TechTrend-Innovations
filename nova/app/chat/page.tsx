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
                <h1 style={{fontSize: '4em'}}>NOTICIAS</h1>
                <button>
                <Image 
                    src="/images/threelines.jpg"
                    alt=""
                    width={100}
                    height={100}
                />
                </button>
            </div>
        </body>
    );
}
