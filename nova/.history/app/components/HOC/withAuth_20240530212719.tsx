import { useRouter } from 'next/navigation';
import { useEffect, ComponentType, useState } from 'react';

const withAuth = <P extends object>(WrappedComponent: ComponentType<P>): ComponentType<P> => {
    const Wrapper = (props: P) => {
        const router = useRouter();
        const [loading, setLoading] = useState(true);
        const [isClient, setIsClient] = useState(false);

        useEffect(() => {
            // Check if we are in the client-side
            setIsClient(true);
        }, []);

        useEffect(() => {
            if (isClient) {
                const checkAuth = async () => {
                    const accessToken = localStorage.getItem('accessToken');
                    const refreshToken = localStorage.getItem('refreshToken');

                    if (!accessToken) {
                        router.push('/login');
                        return;
                    }

                    const isTokenValid = await validateToken(accessToken);

                    if (!isTokenValid && refreshToken) {
                        const tokens = await refreshAccessToken(refreshToken);
                        if (tokens) {
                            localStorage.setItem('accessToken', tokens.accessToken);
                            localStorage.setItem('refreshToken', tokens.refreshToken);
                        } else {
                            router.push('/login');
                            return;
                        }
                    } else if (!isTokenValid) {
                        router.push('/login');
                        return;
                    }

                    setLoading(false);
                };

                checkAuth();
            }
        }, [isClient, router]);

        const validateToken = async (token: string): Promise<boolean> => {
            try {
                const response = await fetch('http://localhost:8090/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ token }),
                });
                return response.ok;
            } catch (error) {
                console.error('Token validation failed', error);
                return false;
            }
        };

        const refreshAccessToken = async (refreshToken: string): Promise<{ accessToken: string, refreshToken: string } | null> => {
            try {
                const response = await fetch('http://localhost:8090/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ refreshToken }),
                });

                if (response.ok) {
                    const data = await response.json();
                    return { accessToken: data.accessToken, refreshToken: data.refreshToken };
                } else {
                    console.error('Failed to refresh token');
                    return null;
                }
            } catch (error) {
                console.error('Failed to refresh token', error);
                return null;
            }
        };

        if (!isClient || loading) {
            return <div>Loading...</div>;
        }

        return <WrappedComponent {...props} />;
    };

    return Wrapper;
};

export default withAuth;
