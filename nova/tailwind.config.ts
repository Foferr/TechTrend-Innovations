import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        'neoris-grey': {
          50: 'rgb(74,81,87)',
          100: 'rgb(29,37,45)'
        },

        'neoris-white': {
          50: 'rgb(240,240,238)',
          100: 'rgb(217,217,214)'
        },

        'nova-yellow': {
          100: '#F2EADA',
          200: '#F2DAAA',
          300: '#F8CF7C',
          400: '#F8C663',
          500: '#FFBB33',
          600: '#CC8F14',
          700: '#996B0F',
          800: '#66470A',
          900: '#4D3300'
        },

        'nova-blue': {
          100: '#2279FF',
          500: '#0045AF'
        },
      },
      backgroundImage: {
      },
    },
  },
  plugins: [],
};
export default config;
