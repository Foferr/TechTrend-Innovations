"use client";

import "./styles.css";
import "../../globals.css";
import React, { useState } from 'react';
import axios from 'axios';
import withAuth from "@/app/components/HOC/withAuth";

const Historial: React.FC = () => {

    return (
        <body>
            <div>
                <h1>Historial</h1>
            </div>
        </body>
    );
}

export default withAuth(Historial);