/*
 * Este proyecto pertenece a Martín Alejandro Fernández.
 * Cualquier edición del siguiente archivo, sin autorización
 * no esta permitida.
 */

package lib;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Descripcion ...
 * @author Martín Alejandro Fernández
 * @version 1.0
 * @see <a href="http://www.MartinAlejandroFernandez.com">Página Web Proximamente...</a>
 */
public class TestSprite extends JPanel{
    private Tablero MiTablero;
    private Humano MiHumano;
    private NoHumano MiNoHumano;
    
    public TestSprite(){
        this.MiTablero = new Tablero(CONSTANTES.RUTA_MAPA_NIVEL_0);
        this.MiHumano = new Humano(MiTablero);
        
        this.MiNoHumano = new NoHumano(MiTablero,Sprite.ESPECIE_GARGOLA,"gus");
        
        this.MiHumano.CorrerHiloSprite();
        this.MiNoHumano.setCoordenada(0, 1);
        new Thread(this.MiHumano).start();
        
        this.MiNoHumano.CorrerHiloSprite();
        this.MiNoHumano.setCoordenada(8, 6);
        new Thread(this.MiNoHumano).start();
        
        
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                MiHumano.SueltaTecla(e);
//                if(e.getKeyCode()==KeyEvent.VK_SPACE){MiHumano.Animar(false);}
            }

            @Override
            public void keyPressed(KeyEvent e) {
//                System.out.println("PresionoTecla");
                MiHumano.PresionaTecla(e);
//                MiHumano.Animar(false);
//                if(e.getKeyCode()==KeyEvent.VK_SPACE){MiHumano.Animar(true);}
            }
        });
        setFocusable(true);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d=(Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        this.MiTablero.setCanvas(g2d);
        this.MiTablero.Pintar(g2d);
        this.MiHumano.Pintar();
        this.MiNoHumano.Pintar();
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Creando interfase");}
        JFrame Ventana=new JFrame();
        Ventana.setTitle("Test de Sprites");
        Ventana.setSize(GUI.RESOLUCION_ANCHO, GUI.RESOLUCION_ALTO);
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Creando Motor");}
        TestSprite Canvas=new TestSprite();
        
        
        Ventana.add(Canvas);
        Ventana.setVisible(true);
        
        while(true){
            Canvas.repaint();
        }
        
        }
}
