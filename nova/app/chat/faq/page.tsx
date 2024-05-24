'use client';

import { useState, useEffect } from 'react';

export default function Page() {
  const [showArrow, setShowArrow] = useState(true);

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY === 0) {
        setShowArrow(true);
      } else {
        setShowArrow(false);
      }
    };

    window.addEventListener('scroll', handleScroll);
    
    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <div style={{ overflow: 'hidden' }}>
      <section style={{
        height: '100vh',
        padding: '20px',
        position: 'relative',
        color: 'white',
        fontSize: '2em',
        fontWeight: 'bold',
        background: 'linear-gradient(to bottom, #1e4373, #16335a, #10244b, #0a1638)'
      }}>
        <a href="/chat" style={{
          position: 'absolute',
          top: '20px',
          left: '20px'
        }}>
          <img src="/images/n1.png" alt="Logo" style={{ height: '110px', width: '110px' }} />
        </a>
        <img src="/images/faq6.png" alt="FAQ" style={{ height: '750px', width: '750px', margin: 'auto' }} />
        {showArrow && (
          <div style={{
            position: 'absolute',
            bottom: '20px',
            right: '20px',
            cursor: 'pointer'
          }} onClick={() => window.scrollTo({ top: window.innerHeight, behavior: 'smooth' })}>
            <img src="/images/down.png" alt="Scroll Down" style={{ height: '50px', width: '50px' }} />
          </div>
        )}
      </section>
      <section style={{
        height: '100vh',
        padding: '20px',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        color: 'white',
        fontSize: '2em',
        fontWeight: 'bold',
        background: 'linear-gradient(to bottom, #0a1638, #08122f, #050d24, #030816)'
      }}>
        Second Section
      </section>
    </div>
  );
}
