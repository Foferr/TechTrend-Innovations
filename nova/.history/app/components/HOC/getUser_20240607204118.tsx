// components/HOC/getUser.ts
import jwt from 'jsonwebtoken';

export const decodeToken = (token: string) => {
    try {
        if (!token) {
            return null;
        }
        const decoded = jwt.decode(token);
        return decoded;
    } catch (error) {
        console.error('Invalid token', error);
        return null;
    }
};

const getUser = () => {
    const token = localStorage.getItem('accessToken');
    if (token) {
        const decoded = decodeToken(token);
        if (decoded) {
            const { userId, userType } = decoded as { userId: number; userType: string };
            return { userId, userType };
        }
    }
    return null;
};

export default getUser;
