/*
 * Este proyecto pertenece a Martín Alejandro Fernández.
 * Cualquier edición del siguiente archivo, sin autorización
 * no esta permitida.
 */

package lib;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/**
 * Descripcion ...
 * @author Martín Alejandro Fernández
 * @version 1.0
 * @see <a href="http://www.MartinAlejandroFernandez.com">Página Web Proximamente...</a>
 */
public class TempSprite extends Thread {
    //public static final String IMG[]={"/img/blood1.png",
    
    private final Graphics2D G;
    private final int X;
    private final int Y;
    private int Frame=1;
    
    public TempSprite(Graphics g, int x, int y){
        this.G=(Graphics2D)g;
        this.X=x;
        this.Y=y;
    }
    
    public void Pintar(){
        //Dimensiones
            int guiAlto=(int)GUI.PPC;
            int guiAncho=(int)GUI.PPC;
            int x=(this.X-GUI.ScrollX)*GUI.PPC;
            int y=(this.Y-GUI.ScrollY)*GUI.PPC;
            int imgAlto=60;
            int imgAncho=60;
            
            //URL Imagen=getClass().getResource("/img/blood"+this.frame+".png");
            //URL Imagen=getClass().getResource();
            //Dibujo el frame
            this.G.getPaint();
            this.G.drawImage(new ImageIcon(getClass().getResource("/img/blood"+this.Frame+".png")).getImage(),
                        x, y, x+guiAncho, y+guiAlto, 
                        0, 0 ,imgAncho ,imgAlto, null);
    }
    
    @Override
    public void run(){
        this.Frame=1;
        while(true){//this.Frame<=4){
            //try {
                this.Pintar();
                //Thread.sleep(1000);
                this.Frame=((this.Frame+1)%4);
            //} catch (InterruptedException ex) {}
        }
    }
}
