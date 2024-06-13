describe('Home Page Tests', () => {
  beforeEach(() => {
    // Visita la página principal
    cy.visit('http://localhost:3000'); // Asegúrate de que esta es la URL correcta para tu aplicación
  });

  it('should display the correct landing title', () => {
    // Verifica que el título "Nova" esté presente
    cy.contains('div', 'Nova').should('be.visible');
  });

  it('should display the typewriter text in English by default', () => {
    // Verifica que el elemento con el id 'typewriter' contenga el texto correcto
    const words = [
      'Your trustworthy assistant',
      'Learn about Neoris products',
      'Information, one prompt away',
      'Powered by AI'
    ];

    // Espera que el typewriter tenga el texto correcto
    cy.get('#typewriter').should('exist').then(($typewriter) => {
      const text = $typewriter.text();
      expect(words).to.include(text);
    });
  });

  it('should display the chat button with correct text', () => {
    // Verifica que el botón de chat tenga el texto correcto
    cy.get('button').contains('Chat').should('be.visible');
  });

  it('should toggle language and update texts accordingly', () => {
    // Cambia el idioma y verifica los textos actualizados
    cy.get('button').contains('Español').click();

    // Verifica que el texto del botón de chat cambie a "Chatea"
    cy.get('button').contains('Chatea').should('be.visible');

    // Verifica los textos en español en el elemento 'typewriter'
    const palabras = [
      'Tu asistente de confianza',
      'Conoce los productos de Neoris',
      'Información a un mensaje de distancia',
      'Potenciado por AI'
    ];

    cy.get('#typewriter').should('exist').then(($typewriter) => {
      const text = $typewriter.text();
      expect(palabras).to.include(text);
    });
  });
});
