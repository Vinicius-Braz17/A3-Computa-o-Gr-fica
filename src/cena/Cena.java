package cena;

import elementos.Nave;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT; //primitivas 3D
import elementos.Meteoro;
import elementos.Textura;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author Kakugawa
 */
public class Cena implements GLEventListener{
    Random random = new Random();
    private float xMin, xMax, yMin, yMax, zMin, zMax;            
    public int tonalizacao = GL2.GL_SMOOTH;
    public int mode = GL2.GL_FILL;
    private TextRenderer textRenderer;
    private float coordenadas[][] = new float[300][3];
    public float nave_position_x = 0;
    public float nave_position_y = 0;
    private float coordenadasMeteoros[][] = new float[12][2];
    private float speed_meteoro = 0.25f;
    private boolean fire_active = false;
    private int pontuation = 0;
    public String tela = "inicio";
    Meteoro met = new Meteoro();
    private Textura textura = new Textura(1);;
    
    public void start() {
        speed_meteoro = 1.0f;
        pontuation = 0;
        nave_position_x = 0;
        nave_position_y = 0;
    }
    
    
    TimerTask ativarFogo = new TimerTask() {
            @Override
            public void run() {
                fire_active = !fire_active;
            }
    };
    
    TimerTask desativarFogo = new TimerTask() {
            @Override
            public void run() {
                fire_active = !fire_active;
            }
    };
    
    TimerTask increaseSpeed = new TimerTask() {
            @Override
            public void run() {
                speed_meteoro += 0.0125;
                System.out.println("Velocidade: " + speed_meteoro);
            }
    };
    
    
    Timer timer = new Timer();

        
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena        
        GL2 gl = drawable.getGL().getGL2();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -100;
        xMax = yMax = zMax = 100;  
        
        Random random = new Random();
        
        for (int i = 0; i < coordenadas.length; i++) {
                coordenadas[i][0] = (float)(random.nextInt(199) - 99);
                coordenadas[i][1] = (float)(random.nextInt(199) - 99);
                coordenadas[i][2] = 8;
        }
        
        coordenadasMeteoros[0][0] = (float)(random.nextInt(180) - 100);
        coordenadasMeteoros[0][1] = 120;
        for (int i = 1; i < coordenadasMeteoros.length; i++) {
                coordenadasMeteoros[i][0] = (float)(random.nextInt(180) - 100);
                coordenadasMeteoros[i][1] = coordenadasMeteoros[i - 1][1] + 75;
                
                System.out.println("X, Y: " + coordenadasMeteoros[i][0] + ", " + coordenadasMeteoros[i][1] );
        }
        
        // Inicia o timer após 0 segundos e repete a cada 2 segundos (2000 ms)
        timer.scheduleAtFixedRate(ativarFogo, 0, 2500);
        timer.scheduleAtFixedRate(desativarFogo, 0, 1000);
        timer.scheduleAtFixedRate(increaseSpeed, 0, 2000);
        
        textRenderer = new TextRenderer(new Font("Comic Sans MS Negrito", Font.PLAIN, 15));
        //Habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
        
        met.setTexture(textura, gl);

    }

    @Override
    public void display(GLAutoDrawable drawable) {  
        //obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();                
        //objeto para desenho 3D
        GLUT glut = new GLUT();
        Nave nave = new Nave();
        
        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);        
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);       
        gl.glLoadIdentity(); //ler a matriz identidade
        
        // iluminacaoDifusa(gl);
        iluminacaoEspecular(gl);
        ligaLuz(gl);
        
        // Desenhar estrelas
        desenharEstrelas(gl);
            
            if (tela == "inicio") {
                gl.glPushMatrix();
                        gl.glTranslatef(-30.0f, 0.0f, 0);
                        gl.glColor3f(1.0f, 1.0f, 1.0f);
                        drawText(gl, ("Aperte ESPAÇO para jogar!"), 0.0f, 0.0f);
                    gl.glPopMatrix();
            }
            
            if (tela == "jogando") {
                // Desenhar nave
                gl.glPushMatrix();
                    gl.glTranslatef(nave_position_x, nave_position_y, 0);
                    nave.desenharNave(gl, fire_active);
                gl.glPopMatrix();

                // Gerar meteoro
                MeteorosBehavior(gl, met);
                //met.gerarMeteoro(gl, 0, 60);
                
                // Verificar colisão
                if (verificarColisao()) {System.out.println("Bateu");   }

                gl.glPushMatrix();
                    gl.glTranslatef(-15.0f, 80.0f, 0);
                        gl.glColor3f(1.0f, 1.0f, 1.0f);
                    drawText(gl, ("Pontuação: " + pontuation), 0.0f, 0.0f);
                gl.glPopMatrix();


                gl.glFlush();      
            }
            
            if (tela == "derrota") {
                
                    gl.glPushMatrix();
                        gl.glTranslatef(-15.0f, 80.0f, 0);
                        gl.glColor3f(1.0f, 1.0f, 1.0f);
                        drawText(gl, ("Pontuação: " + pontuation), 0.0f, 0.0f);
                        
                        gl.glTranslatef(0.0f, -80.0f, 0);
                        drawText(gl, ("Game over! "), 0.0f, 0.0f);
                        
                        gl.glTranslatef(-25.0f, -15.0f, 0);
                        drawText(gl, ("Aperte ESPAÇO para novar novamente"), 0.0f, 0.0f);
                    gl.glPopMatrix();
                    
                    coordenadasMeteoros[0][0] = (float)(random.nextInt(180) - 100);
                    coordenadasMeteoros[0][1] = 120;
                    for (int i = 1; i < coordenadasMeteoros.length; i++) {
                    coordenadasMeteoros[i][0] = (float)(random.nextInt(180) - 100);
                    coordenadasMeteoros[i][1] = coordenadasMeteoros[i - 1][1] + 75;

                }
            }


        
    }
    
    public boolean verificarColisao() {
    float fogueteX = nave_position_x;
    float fogueteY = nave_position_y;
    float fogueteZ = 0.0f;
    
        for (int i = 0; i < coordenadasMeteoros.length; i++) {
            float meteoroX = coordenadasMeteoros[i][0];
            float meteoroY = coordenadasMeteoros[i][1]; 
            float meteoroZ = 0.0f;

            float raioNave = 6.75f; // Distância p colisão nave
            float raioMeteoro = 20.0f; // Defina o raio do meteoro

            // Distância entre os centros dos objetos
            float distancia = (float) Math.sqrt(
                    Math.pow(fogueteX - meteoroX, 2) +
                    Math.pow(fogueteY - meteoroY, 2) +
                    Math.pow(fogueteZ - meteoroZ, 2)
            );
            
            if (distancia < (raioNave + raioMeteoro)) {tela = "derrota";}
                
        }
        return false;
    }
    
    public void MeteorosBehavior(GL2 gl, Meteoro met) {
        for (int i = 0; i < coordenadasMeteoros.length; i++) {
                met.gerarMeteoro(gl, coordenadasMeteoros[i][0], coordenadasMeteoros[i][1]);
                coordenadasMeteoros[i][1] -= speed_meteoro;
                
                if (coordenadasMeteoros[i][1] <= -120) {
                    coordenadasMeteoros[i][0] = (float)(random.nextInt(180) - 100);
                    coordenadasMeteoros[i][1] = 780;
                    pontuation++;
                }
            }
       
    }
    
    public void desenharEstrelas(GL2 gl) {
        gl.glBegin(GL2.GL_POINTS);
            gl.glColor3f(1.0f, 1.0f, 1.0f); // Cor branca
            for (int i = 0; i < coordenadas.length; i++) {
                gl.glVertex3f(coordenadas[i][0], coordenadas[i][1], coordenadas[i][2]);
                coordenadas[i][1] -= 0.15;
                
                if (coordenadas[i][1] <= -100) {
                    coordenadas[i][1] = 100;
                }
            }
            
        gl.glEnd();
    }
    
        
    private void drawText(GL2 gl, String text, float x, float y) {
        GLUT glut = new GLUT();
        gl.glRasterPos2f(x, y);
        for (char c : text.toCharArray()) {
            glut.glutBitmapCharacter(GLUT.BITMAP_TIMES_ROMAN_24, c);
        }
    }
    
    private void esfera(GLUT glut) {
        glut.glutSolidSphere(60, 15, 15);
    }

    public void iluminacaoEspecular(GL2 gl){
        // habilita a luz de n�mero 0
        gl.glEnable(GL2.GL_LIGHT0);
        
        //float luzAmbiente[] = {0.2f, 0.2f, 0.2f, 1.0f}; //cor
        float luzEspecular[]={1.0f, 1.0f, 1.0f, 1.0f}; //cor
        float posicaoLuz[]={-100.0f, 80.0f, 10.0f, 1.0f}; //pontual
                
        //intensidade da reflexao do material        
        int especMaterial = 128; 
        //define a concentracao do brilho
    	gl.glMateriali(GL2.GL_FRONT, GL2.GL_SHININESS, especMaterial);

    	//define a reflect�ncia do material
    	gl.glMaterialfv(  GL2.GL_FRONT, GL2.GL_SPECULAR, luzEspecular, 0);

        //define os par�metros de luz de n�mero 0 (zero)
    	//gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
    	gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, luzEspecular, 0);
    	gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }
    
    public void iluminacaoDifusa(GL2 gl) {
        // habilita a luz de n�mero 1
        gl.glEnable(GL2.GL_LIGHT1);
        
        float luzDifusa[] = {1.0f, 1.0f, 1.0f, 1.0f}; //cor
        float posicaoLuz[] = {-100.0f, 50.0f, 10.0f, 1.0f}; //1.0 pontual

        //define os par�metros de luz de n�mero 0 (zero)
        gl.glPushMatrix();
            //gl.glRotatef(45, 0.0f, 1.0f, 0.0f);
            gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, luzDifusa, 0);
            gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, posicaoLuz, 0);
        gl.glPopMatrix();
        
    }

    
    public void ligaLuz(GL2 gl) {
        // habilita a defini��o da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        // habilita o uso da ilumina��o na cena
        gl.glEnable(GL2.GL_LIGHTING);
        //Especifica o Modelo de tonalizacao a ser utilizado 
        //GL_FLAT -> modelo de tonalizacao flat 
        //GL_SMOOTH -> modelo de tonaliza��o GOURAUD (default)        
        gl.glShadeModel(tonalizacao);
    }

    public void desligaluz(GL2 gl) {
        //desabilita o ponto de luz
        gl.glDisable(GL2.GL_LIGHT0);
        gl.glDisable(GL2.GL_LIGHT1);
        //desliga a iluminacao
        gl.glDisable(GL2.GL_LIGHTING);
    }
    
    public void desligaEspecular(GL2 gl) {
        //desabilita o ponto de luz
        gl.glDisable(GL2.GL_LIGHT0);
    }
    
    public void desligaDifusa(GL2 gl) {
        //desabilita o ponto de luz
        gl.glDisable(GL2.GL_LIGHT1);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();  
        
        //evita a divisao por zero
        if(height == 0) height = 1;
        //calcula a proporcao da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;
        
        //seta o viewport para abranger a janela inteira
        //gl.glViewport(0, 0, width, height);
                
        //ativa a matriz de projecao
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); //ler a matriz identidade

        //projecao ortogonal sem a correcao do aspecto
        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);
        
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //ler a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}         
}
