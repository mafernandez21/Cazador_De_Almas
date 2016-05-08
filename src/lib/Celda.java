/*
 * Este proyecto pertenece a Martín Alejandro Fernández.
 * Cualquier edición del siguiente archivo, sin autorización
 * no esta permitida.
 */

package lib;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/**
 * Clase que representa cada celda del tablero
 * @author Martín Alejandro Fernández
 * @version 1.0
 * @see <a href="http://www.MartinAlejandroFernandez.com">Página Web Proximamente...</a>
 */
public class Celda {
    //GUI
    public static final String IMG_TIPO_ENTRADA="/img/Entrada.png"; //getClass().getResource(Celda.IMG_TIPO_ENTRADA)
    public static final String IMG_TIPO_SALIDA="/img/Salida.png"; //getClass().getResource(Celda.IMG_TIPO_ENTRADA)
    public static final String IMG_TIPO_PARED="/img/Pared.png"; //getClass().getResource(Celda.IMG_TIPO_ENTRADA)
    public static final String IMG_TIPO_CAMINO="/img/Camino.png"; //getClass().getResource(Celda.IMG_TIPO_ENTRADA)
    
    public static final int TIPO_ENTRADA=0;
    public static final int TIPO_SALIDA=1;
    public static final int TIPO_PARED=2;
    public static final int TIPO_CAMINO=3;
    public static final int TIPO_DESCONOCIDO=-99;
    
    private int X;
    private int Y;
    private int Tipo;
    private Sprite Ocupante;

//CONSTRUCTORES

    /**
     * Constructor de la celda que la inicializa con el Tipo=CELDA_TIPO_CAMINO  y el Ocupante=null
     * @author Martín Alejandro Fernández
     * @version 1.0
     */
    public Celda(){
        this.X=0;
        this.Y=0;
        this.Tipo=Celda.TIPO_CAMINO;
        this.Ocupante=null;
    }
    
    /**
     * Constructor de la celda que la inicializa con el Ocupante=null y el Tipo recibido como parámetro.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param tipo Entero que indica el tipo de material por ejemplo: 0-Pared,1-Entrada,2-Salida,3-Tierra,4-Agua,5-Aire,6-Fuego,etc.
     */
    public Celda(int tipo){
        this.X=0;
        this.Y=0;
        this.Tipo=tipo;
        this.Ocupante=null;
    }

//PRIVADOS
    /**
     * Método que configura la coordenada X de la celda.
     * @param x Valor de tipo entero que indica la coordenada.
     */ 
    private void setX(int x){this.X = x;}
    
    /**
     * Método que configura la coordenada Y de la celda.
     * @param y Valor de tipo entero que indica la coordenada.
     */ 
    private void setY(int y){this.Y = y;}
    
//METODOS PUBLICOS    
    /**
     * Método que configura la coordenada de una celda.
     * @param x Valor de la coordenada X @see setX.
     * @param y Valor de la coordenada Y @see setY.
     */
    public void setCoordenada(int x, int y){
        this.setX(x);
        this.setY(y);
    }
    
    /**
     * Método para configurar el material de la celda.
     * @param tipo Entero que indica el tipo de material por ejemplo: 0-Pared,1-Entrada,2-Salida,3-Tierra,4-Agua,5-Aire,6-Fuego,etc.
     * @author Martín Alejandro Fernández
     * @version 1.0
     */
    public void setTipo(int tipo) {this.Tipo = tipo;}

    /**
     * Método que setea si la celda está ocupada con un sprite o no.
     * @param ocupante Es el sprite que está ocupando la celda en ese momento.
     * @author Martín Alejandro Fernández
     * @version 1.0
     */
    public void setOcupante(Sprite ocupante) {this.Ocupante = ocupante;}

    /**
     *Método que devuelve la celda actual
     * @author Martín Alejandro Fernández
     * @version 1.0
     *@return Devuelve una objeto tipo Celda*/
    public Celda getCelda(){return this;}
    
    /**
     * Método que devuelve el tipo de material de la celda.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @return Entero que indica el tipo de material por ejemplo: 0-Pared,1-Entrada,2-Salida,3-Tierra,4-Agua,5-Aire,6-Fuego,etc.
     */
    public int getTipo() {return this.Tipo;}

    /**
     * Método que devuelve el ocupante de la celda.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @return Devuelve un objeto Sprite si está ocupada, en caso contrario devuelve null.
     */
    public Sprite getOcupante() {return this.Ocupante;}

    public int getX() {return this.X;}

    public int getY() {return this.Y;}
    
    public void Pintar(Graphics2D g) {
        int x=GUI.OffSetX+(GUI.PPC*(this.X-GUI.ScrollX));
        int y=GUI.OffSetY+(GUI.PPC*(this.Y-GUI.ScrollY));
        int Ancho=GUI.PPC;
        int Alto=GUI.PPC;
        
        switch(this.getTipo()){
            case Celda.TIPO_ENTRADA://Entrada
                g.drawImage(new ImageIcon(getClass().getResource(Celda.IMG_TIPO_ENTRADA)).getImage(),
                    x, y, x+Ancho, y+Alto, 
                    0, 0,90 , 90, null);
            break;
            case Celda.TIPO_SALIDA://Salida
                g.drawImage(new ImageIcon(getClass().getResource(Celda.IMG_TIPO_SALIDA)).getImage(),
                    x, y, x+Ancho, y+Alto, 
                    0, 0,90 , 90, null);
            break;
            case Celda.TIPO_PARED://Pared
                g.drawImage(new ImageIcon(getClass().getResource(Celda.IMG_TIPO_PARED)).getImage(),
                    x, y, x+Ancho, y+Alto, 
                    0, 0,90 , 90, null);
            break;
            case Celda.TIPO_CAMINO://Camino
                g.drawImage(new ImageIcon(getClass().getResource(Celda.IMG_TIPO_CAMINO)).getImage(),
                    x, y, x+Ancho, y+Alto, 
                    0, 0,90 , 90, null);
            break;
            default:
                g.setColor(Color.BLACK);
                g.drawRect(x, y, Ancho, Alto);
                g.setColor(Color.MAGENTA);
                g.fillRect(x+1, y+1, Ancho-2, Alto-2);
            break;
        }
    }
}
