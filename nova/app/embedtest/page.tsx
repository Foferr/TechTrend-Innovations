"use client";
import withAuth from '../components/HOC/withAuth';
import NavBarComponent from '../components/NavBar';


const embed: React.FC = () => {
    return (

        <div>
            <NavBarComponent />
            <iframe className="w-full h-screen"
                    src="https://lookerstudio.google.com/embed/reporting/c46b5ab3-d017-47eb-83da-c32c64a57bc7/page/Rov1D"
                    allowFullScreen
                    sandbox="allow-storage-access-by-user-activation allow-scripts allow-same-origin allow-popups allow-popups-to-escape-sandbox"></iframe>
        </div>
    );
}

export default withAuth(embed);