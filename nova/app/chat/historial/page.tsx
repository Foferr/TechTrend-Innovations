"use client";

import "./styles.css";
import "../../globals.css";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import withAuth from "@/app/components/HOC/withAuth";

const Historial: React.FC = () => {


    const [data, setData] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/chatHistory/user/1')
            .then((response) => {
                setData(response.data);
            });
    }, []);

    return (
        <body>
            <div className="container">
                <h1>Historial de chats</h1>
                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Fecha</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        {data.map((chat: any) => (
                            <tr key={chat.id}>
                                <td>{chat.id}</td> 
                                <td>{chat.createdAt}</td>
                                <td>{chat.status}</td>
                            </tr>
                        )).reverse()}
                    </tbody>
                </table>
            </div>
        </body>
    );
}

export default withAuth(Historial);