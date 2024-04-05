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
      },
      backgroundImage: {
      },
    },
  },
  plugins: [],
};
export default config;
