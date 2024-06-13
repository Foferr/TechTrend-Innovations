describe('Login Page Tests', () => {
    before(() => {
      // Visita la página principal
      cy.visit('http://localhost:3000'); // Asegúrate de que esta es la URL correcta para tu aplicación
    });
  
    it('should navigate to the login page when clicking the chat button', () => {
      // Verifica que el botón de chat esté presente y haz clic en él
      cy.contains('button', 'Chat').click();
  
      // Verifica que la URL cambie a la página de login
      cy.url().should('include', '/login');
    });
  
    it('should login with correct credentials', () => {
      // Verifica que el campo de email esté presente y escribe el email
      cy.get('input[type="email"]').should('be.visible').type('admin@correo.com');
  
      // Verifica que el campo de contraseña esté presente y escribe la contraseña
      cy.get('input[type="password"]').should('be.visible').type('Contraseña#1');
  
      // Verifica que el botón de login esté presente y haz clic en él
      cy.contains('button', 'Iniciar Sesión').click();
  
      // Espera a que la solicitud de login sea procesada y verifica que la URL cambie según el tipo de usuario
      cy.url().should('include', '/chat'); // Cambia '/chat' según la redirección esperada después del login
    });
  });
  