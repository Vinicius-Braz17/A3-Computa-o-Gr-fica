package elementos;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.gl2.GLUT;
/**
 *
 * @author Vinicius Braz
 */
public class Nave {
    public void desenharNave(GL2 gl, boolean fire) {
        GLUT glut = new GLUT();
        
         // Cone
        gl.glPushMatrix();
            gl.glColor3f(0.3f, 1.0f, 0.4f); // Cor verde
            gl.glTranslatef(0.0f, 0.0f, 15.0f);
            gl.glRotatef(-82, 1.0f, 0.0f, 0.0f);
            glut.glutSolidCone(5.0, 12, 50, 50); // (raio da base, altura, fatias, pilhas)
        gl.glPopMatrix();

        // Cilindro
        gl.glPushMatrix();
            gl.glColor3f(1.0f, 1.0f, 1.0f); // Cor branca
            gl.glTranslatef(0.0f, 0.0f, 15.0f);
            gl.glRotatef(98, 1.0f, 0.0f, 0.0f);
            glut.glutSolidCylinder(5.0, 16.0, 50, 50); // (raio, altura, fatias, pilhas)
        gl.glPopMatrix();
        
        // Circulo Janela
        gl.glPushMatrix();
            gl.glTranslatef(0.0f, -5.5f, 20.0f); // Translada para a posição desejada
            gl.glColor3f(1.0f, 1.0f, 1.0f); // Define a cor do círculo (branco)

            int numLados = 100; // Número de lados do polígono (mais lados = círculo mais suave)
            float raio = 2.5f; // Raio do círculo

            gl.glBegin(GL2.GL_POLYGON);
            for (int i = 0; i < numLados; i++) {
                double angulo = 2 * Math.PI * i / numLados;
                float x = (float) (raio * Math.cos(angulo));
                float y = (float) (raio * Math.sin(angulo));
                gl.glVertex2f(x, y);
            }
            gl.glEnd();
        gl.glPopMatrix();

        // Circulo vidro janela
        gl.glPushMatrix();
            gl.glTranslatef(0.0f, -5.5f, 30.0f); // Translada para a posição desejada
            gl.glColor3f(0.0f, 0.5f, 1.0f); // Define a cor do círculo (branco)

            raio = 1.5f; // Raio do círculo

            gl.glBegin(GL2.GL_POLYGON);
            for (int i = 0; i < numLados; i++) {
                double angulo = 2 * Math.PI * i / numLados;
                float x = (float) (raio * Math.cos(angulo));
                float y = (float) (raio * Math.sin(angulo));
                gl.glVertex2f(x, y);
            }
            gl.glEnd();
        gl.glPopMatrix();

        // Triângulo asa esquerda
        gl.glPushMatrix();
            gl.glColor3f(0.15f, 0.4f, 1.0f); // Cor verde
            gl.glTranslatef(-5.0f, -15.5f, 10.0f);
            gl.glRotatef(-82, 1.0f, 0.0f, 0.0f);
            glut.glutSolidCone(5.0, 14, 50, 50); // (raio da base, altura, fatias, pilhas)
        gl.glPopMatrix();
        
        
        // Triângulo asa direita
        gl.glPushMatrix();
            gl.glColor3f(0.15f, 0.4f, 1.0f); // Cor verde
            gl.glTranslatef(5.0f, -15.5f, 10.0f);
            gl.glRotatef(-82, 1.0f, 0.0f, 0.0f);
            glut.glutSolidCone(5.0, 14, 50, 50); // (raio da base, altura, fatias, pilhas)
        gl.glPopMatrix();
        
        // Chamas
        if (fire) {
        gl.glPushMatrix();
            gl.glColor3f(0.75f, 0.2f, 0.0f); // Cor verde
            gl.glTranslatef(-2.5f, -15.5f, 10.0f);
            gl.glRotatef(82, 1.0f, 0.0f, 0.0f);
            glut.glutSolidCone(2.5, 8, 3, 3); // (raio da base, altura, fatias, pilhas)
        gl.glPopMatrix();
        
         gl.glPushMatrix();
            gl.glColor3f(0.75f, 0.2f, 0.0f); // Cor verde
            gl.glTranslatef(2.5f, -15.5f, 10.0f);
            gl.glRotatef(82, 1.0f, 0.0f, 0.0f);
            glut.glutSolidCone(2.5, 8, 3, 3); // (raio da base, altura, fatias, pilhas)
        gl.glPopMatrix();
        
        gl.glPushMatrix();
            gl.glColor3f(1.0f, 0.7f, 0.0f); // Cor verde
            gl.glTranslatef(0.0f, -15.5f, 12.0f);
            gl.glRotatef(82, 1.0f, 0.0f, 0.0f);
            glut.glutSolidCone(3.5, 10, 3, 3); // (raio da base, altura, fatias, pilhas)
        gl.glPopMatrix();
        }
    }   
}