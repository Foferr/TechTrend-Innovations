import React, { useState, useEffect } from 'react';
import jwt from 'jsonwebtoken';

const parseJWT = (token: string) => {
    try {
        const decoded = jwt.decode(token);
        return decoded;
    } catch (e) {
        console.error('Failed to decode JWT:', e);
        return null;
    }
};

const ParseJWT: React.FunctionComponent = () => {
    const [parsedToken, setParsedToken] = useState<any>(null);

    useEffect(() => {
        const token = localStorage.getItem('accessToken') || '';
        const decoded = parseJWT(token);
        setParsedToken(decoded);

        console.log('Parsed Token:', decoded);

    }, []);

    return null;
};

export default ParseJWT;
