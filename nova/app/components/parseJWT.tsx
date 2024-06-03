import jwt from 'jsonwebtoken';
import React, { useEffect, useState } from 'react';

const ParseJWT: React.FunctionComponent = () => {
    const [parsedToken, setParsedToken] = useState<any>(null);

    const parseJWT = (token: string) => {
        try {
            const decoded = jwt.decode(token);
            return decoded;
        } catch (e) {
            console.error('Failed to decode JWT:', e);
            return null;
        }
    };

    useEffect(() => {
        const token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJub3ZhLWp3dCIsInN1YiI6ImFjY2Vzcy10b2tlbiIsImdyb3VwcyI6WyJhZG1pbiJdLCJleHAiOjE3MTczODQwOTU1ODQsInR5cGUiOiJhY2Nlc3MiLCJ1c2VySWQiOjEsInVzZXJUeXBlIjoiYWRtaW4iLCJpYXQiOjE3MTczODA0OTUsImp0aSI6ImZiMjBiMDg2LTQzNTAtNDY5ZS04MzQxLWQ5MjBiODZiYzQ1MyJ9.tFAAiofGXMW_mPU8yYebBIrAJe_FezgTzME8cj8dHHjp7cpnLtiKUuAr-F8u58USzxLZXJDIj9CD-q6hxW1w_kteh6JipZkARAFdTaclPpsPEz7XW6J2FDpLbDJZrJHiQtpsj3ymvdWY4lyg5auDtHGbudzc3WjlOdLTSumi5kGpr3QZvonwPL_ZSyZq1wJkox1HsWKB-ZJ-JnQtKvmMdv11INGDPO5qgp7eg-RWagL5xlxnAIu0ejjf2XyxPVUFsnK4hYAhcfyubFPGBnXxebIdfQYF8sm_T9FWvWTSEsa--wgGtqGfS8HLiPfey-qnvV5mo16zqK49UJ3u3XEQpQ';
        const decoded = parseJWT(token);
        setParsedToken(decoded);

        console.log('Parsed Token:', decoded);

    }, []);

    return null;
};

export default ParseJWT;
