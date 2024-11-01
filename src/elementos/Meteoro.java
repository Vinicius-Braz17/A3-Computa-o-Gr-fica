package elementos;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.gl2.GLUT;
import java.util.Random;

/**
 *
 * @author profslpa
 */
public class Meteoro {
    
    public void gerarMeteoro(GL2 gl, float posicaoXMeteoro, float posicaoYMeteoro) {
         GLUT glut = new GLUT();
         
         gl.glPushMatrix();

        // Transformações (opcional):
            gl.glTranslatef(posicaoXMeteoro, posicaoYMeteoro, 15.0f);

        // Desenhando a esfera
            gl.glColor3f(1.0f, 0.5f, 0.0f); // Define a cor da esfera
            glut.glutSolidSphere(20.0f, 10, 10); // Cria uma esfera sólida

        gl.glPopMatrix();
        
    }
    
    
    
}
