import NextAuth from 'next-auth';
import GoogleProvider from 'next-auth/providers/google';
import CredentialsProvider from 'next-auth/providers/credentials';
import axios from 'axios';

const handler = NextAuth({
    providers: [
        GoogleProvider({
            clientId: process.env.GOOGLE_CLIENT_ID as string,
            clientSecret: process.env.GOOGLE_CLIENT_SECRET as string,
        }),
        CredentialsProvider({
            name: 'Credentials',
            credentials: {
                email: { label: "Email", type: "email" },
                password: { label: "Password", type: "password" }
            },
            authorize: async (credentials) => {
                try {
                    const response = await axios.post('http://localhost:8090', {
                        email: credentials?.email,
                        password: credentials?.password
                    });
                    const user = response.data.user;
                    if (user) {
                        return user;
                    }
                } catch (error) {
                    console.error('Login error:', error);
                }
                return null;
            }
        })
    ],
    callbacks: {
        async jwt({ token, user }) {
            // Add user data to the token after logging in
            if (user) {
                token.accessToken = user.accessToken;
                token.id = user.id;
                token.email = user.email;
            }
            return token;
        },
        async session({ session, token }) {
            // Send token to the client
            session.accessToken = token.accessToken;
            session.user.id = token.id;
            session.user.email = token.email;
            return session;
        }
    },
    pages: {
        signIn: '/auth/signin', // Custom sign-in page
    },
    session: {
        strategy: 'jwt',
    },
    secret: process.env.NEXTAUTH_SECRET,
});

export { handler as GET, handler as POST };
