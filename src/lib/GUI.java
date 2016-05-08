/*
 * Este proyecto pertenece a Martín Alejandro Fernández.
 * Cualquier edición del siguiente archivo, sin autorización
 * no esta permitida.
 */

package lib;

/**
 * Descripcion ...
 * @author Martín Alejandro Fernández
 * @version 1.0
 * @see <a href="http://www.MartinAlejandroFernandez.com">Página Web Proximamente...</a>
 */
public final class GUI{
    public static final long CPS = 60; //Cuadros por segundos
    public static final int RESOLUCION_ANCHO = 1200;
    public static final int RESOLUCION_ALTO = 700;
    public static final int CELDAS_VISIBLES = 10;
    public static final int PPC = (GUI.RESOLUCION_ALTO-100)/GUI.CELDAS_VISIBLES; //Pixels por Celdas
    public static int ScrollX = 0;
    public static int ScrollY = 0;
    public static final int OffSetX=300;
    public static final int OffSetY=0;
}
