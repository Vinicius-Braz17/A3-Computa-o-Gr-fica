package elementos;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;

public class Meteoro {
    
    Textura texture;
    
    public void setTexture(Textura texture, GL2 gl) {
        this.texture = texture;
        
        //é geração de textura automática
        texture.setAutomatica(true);
         
        //habilita os filtros
        texture.setFiltro(GL2.GL_LINEAR);
        texture.setModo(GL2.GL_MODULATE);
        texture.setWrap(GL2.GL_REPEAT);
        
    }    
    
    public void gerarMeteoro(GL2 gl, float posicaoXMeteoro, float posicaoYMeteoro) {
        GLUT glut = new GLUT();
        
        gl.glMatrixMode(GL2.GL_TEXTURE);
           gl.glLoadIdentity();   
           float x = 0.04f;
           float y = x;
           gl.glScalef(x, y, 1);        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        
        texture.gerarTextura(gl, "src/elementos/meteoro.bmp", 0);
        
        gl.glPushMatrix();
            gl.glColor3f(1.0f, 1.0f, 1.0f);
            gl.glTranslatef(posicaoXMeteoro, posicaoYMeteoro, 15.0f);
            gl.glRotatef(90, 1, 0, 0);
            glut.glutSolidSphere(20.0f, 60, 60); 
        gl.glPopMatrix();
        
        texture.desabilitarTextura(gl, 0);

        
    }
}