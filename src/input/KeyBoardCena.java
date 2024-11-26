package input;
import cena.Cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;
/**
 *
 * @author Kakugawa
 */
public class KeyBoardCena implements KeyListener{
    private Cena cena;
    
    public KeyBoardCena(Cena cena){
        this.cena = cena;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        
        switch (e.getKeyCode()) {
            case 151:
                if (cena.nave_position_x == 95.0) {
                    break;
                } else {
                    cena.nave_position_x += 5;
                    break; 
                }
            case 149:
                if (cena.nave_position_x == -95.0) {
                    break;
                } else {
                    cena.nave_position_x -= 5;
                    break; 
                }
            case 152:
                if (cena.nave_position_y == -85.0) {
                    break;
                } else {
                    cena.nave_position_y -= 5;
                    break; 
                }
            case 150:
                if (cena.nave_position_y == 0.0) {
                    break;
                } else {
                    cena.nave_position_y += 5;
                    break; 
                }
            case 32:
                if (cena.tela != "jogando") {cena.start();};
                cena.tela = "jogando"; 
                break;
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
