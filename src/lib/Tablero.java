/*
 * Este proyecto pertenece a Martín Alejandro Fernández.
 * Cualquier edición del siguiente archivo, sin autorización
 * no esta permitida.
 */

package lib;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Clase que representa el tablero del juego (Mundo).
 * @author Martín Alejandro Fernández
 * @version 1.0
 * @see <a href="http://www.MartinAlejandroFernandez.com">Página Web Proximamente...</a>
 */
public class Tablero {
    private Graphics2D Canvas;
    private String Nombre;
    private int Ancho;
    private int Alto;
    private Celda [][]Celdas;

//CONSTRUCTORES
    public Tablero(){
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Construyendo el Tablero");}
        this.Nombre="Tablero"+String.valueOf(this.hashCode());
        this.Alto=10;
        this.Ancho=10;
        this.InicializarCeldas();
    }
    
    /**
     * Método Constructor del tablero apartir de un archivo.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param Canvas Objeto de tipo Graphics donde se pinta el tablero (Canvas)
     * @param RutaArchivo Ruta del archivo con la información del tablero.
     */
    public Tablero(String RutaArchivo){
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Construyendo el Tablero");}
        this.CargarDatosArchivos(RutaArchivo);
        this.setCanvas(Canvas);
    }
    
//METODOS PRIVADOS    
    /**
     * Método que inicializa la matriz de celdas.
     * @author Martín Alejandro Fernández
     * @version 1.0
     */
    private void InicializarCeldas(){
        this.Celdas=new Celda[this.getAncho()+1][this.getAlto()+1];
        for(int i=0;i<=this.getAncho();i++){
            for(int j=0;j<=this.getAlto();j++){
                this.Celdas[i][j]=new Celda();
                this.Celdas[i][j].setCoordenada(i, j);
                this.Celdas[i][j].setTipo(0);
                this.Celdas[i][j].setOcupante(null);
            }
        }
    }    

    private void ImprimirCeldas(){
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println(this.getNombre());}
        for (int i=0;i<=this.getAncho();i++){
            for (int j=0;j<=this.getAlto();j++){
                System.out.print(this.getMaterialCelda(i, j)+"\t");
            }
        System.out.print("\n");
        }
    }
   
    private BufferedReader AbrirArchivoParaLectura(String Ruta){
        // Apertura del fichero y creacion de BufferedReader para poder
        // hacer una lectura comoda (disponer del metodo readLine()).
        try{
            InputStream input = getClass().getResourceAsStream(Ruta);
            InputStreamReader ArchivoLectura = new InputStreamReader(input);
            BufferedReader BufferArchivo= new BufferedReader(ArchivoLectura);
            return BufferArchivo;
            }catch(NullPointerException e1){System.out.println(">>> Error IO el recurso no existe ("+e1.getMessage()+") <<<"); 
            }catch(NumberFormatException e2){System.out.println(">>> Error NumberFormat ("+e2.getMessage()+") <<<"); 
            }catch(NoSuchElementException e3){System.out.println(">>> Error NoSuchElement ("+e3.getMessage()+") <<<");
            }
        return null;
    } 
   
    /**
     * Método que lee el archivo con la información del tablero a crear.
     * @param Ruta Ruta de donde se encuentra el archivo.
     */
    private void CargarDatosArchivos(String Ruta){        
        // Apertura del fichero y creacion de BufferedReader para poder
        // hacer una lectura comoda (disponer del metodo readLine()).
        // Lectura del fichero
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Cargando datos desde archivo");}
        BufferedReader Archivo = this.AbrirArchivoParaLectura(Ruta);
        String LineaFila;
        int i=0;
        int j=0;
        StringTokenizer TokenCeldas;
        if(Archivo!=null){
            try{
                if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Leyendo el archivo");}
                this.Nombre=Archivo.readLine();
                this.Ancho=Integer.parseInt(Archivo.readLine());
                this.Alto=Integer.parseInt(Archivo.readLine());
                this.InicializarCeldas();
                while((LineaFila=Archivo.readLine())!=null && j<=this.getAlto()){
                    //Detecto las comas (separadores).
                    TokenCeldas = new StringTokenizer(LineaFila, ",");
                    for(i=0; i<=this.getAncho();i++){
                        this.Celdas[i][j].setCoordenada(i, j);
                        int tipo=Integer.parseInt(TokenCeldas.nextToken());
                        if(tipo==Celda.TIPO_ENTRADA||
                           tipo==Celda.TIPO_SALIDA||
                           tipo==Celda.TIPO_CAMINO||
                           tipo==Celda.TIPO_PARED
                           ){this.Celdas[i][j].setTipo(tipo);}
                        else{this.Celdas[i][j].setTipo(Celda.TIPO_DESCONOCIDO);}
                    }
                    j++;
                }
            }catch(IOException e1){System.out.println(">>> Error en el Formato de Archivo!!! ("+e1.getMessage()+") <<<");
            }catch(NumberFormatException e2){System.out.println(">>> Error en el Formato de Archivo!!! ("+e2.getMessage()+") <<<");
            }catch(NoSuchElementException e3){System.out.println(">>> Error en el Formato de Archivo!!! ("+e3.getMessage()+") <<<");} 
        }
        this.CerrarArchivoDeLectura(Archivo);
    }    
    
    private boolean CerrarArchivoDeLectura(BufferedReader LectorArchivo){
        // Cerramos el fichero, para asegurarnos
        // que se cierra tanto si todo va bien como si salta 
        // una excepcion.
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Cerrando el archivo de datos");}
        try{
            if(LectorArchivo!=null){LectorArchivo.close();return true;}                  
        }catch (IOException e){return false;}
        return true;
    }
    
//METODOS PUBLICOS

    public String getNombre(){return this.Nombre;}
    
    public int getAncho() {return (Ancho-1);}

    public int getAlto() {return (Alto-1);}
    
    /**
     * Método para verificar si una coordenada esta dentro del tablero de juego (Mundo).
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param x Coordenada x a verificar.
     * @param y Coordenada y a verificar.
     * @return Verdadero si la coordenada es interna (no se excede de los límites), caso contrario retorna Falso.
     */
    public boolean EsInterna(int x, int y){
            if(x<0||this.getAncho()<x){return false;}
            if(y<0||this.getAlto()<y){return false;}
        return true;
    }

    public Celda getCelda(int x, int y){
        if(this.EsInterna(x, y)){return Celdas[x][y].getCelda();}
        else{return null;}
    }

    public int getMaterialCelda(int x, int y) {
        if(this.EsInterna(x, y)){return Celdas[x][y].getCelda().getTipo();}
        else{return Celda.TIPO_DESCONOCIDO;}
    }
    
    public Sprite getOcupanteCelda(int x, int y) {
        if(this.EsInterna(x, y)){return Celdas[x][y].getCelda().getOcupante();}
        else{return null;}
    }
    
    public void ScrollDerecha(){
        if((GUI.ScrollX+GUI.CELDAS_VISIBLES-1)<this.getAncho()){
            GUI.ScrollX++;
        }
    }
    
    public void ScrollIzquierda(){
        if(GUI.ScrollX>0){
            GUI.ScrollX--;
        }
    }   

    public void ScrollAbajo(){
        if((GUI.ScrollY+GUI.CELDAS_VISIBLES-1)<this.getAlto()){
            GUI.ScrollY++;
        }
    }
    
    public void ScrollArriba(){
        if(GUI.ScrollY>0){
            GUI.ScrollY--;
        }
    } 
    
    public Graphics2D getCanvas(){return this.Canvas;}
    
    public void setCanvas(Graphics2D Canvas){this.Canvas=Canvas;}
    
    public void Pintar(Graphics2D g){
        this.setCanvas(g);
        this.PintarFondo(g);
        try{
            if(this.getCanvas()!=null){
                for(int i=GUI.ScrollX;i<(GUI.ScrollX+GUI.CELDAS_VISIBLES);i++){
                    for(int j=GUI.ScrollY;j<(GUI.ScrollY+GUI.CELDAS_VISIBLES);j++){
                        this.Celdas[i][j].getCelda().Pintar(this.getCanvas());
                    }
                }
            }
        }catch(ArrayIndexOutOfBoundsException npe){}
    }
    
    public void PintarFondo(Graphics2D g) {
        int x=0;
        int y=0;
        int Ancho=GUI.RESOLUCION_ANCHO;
        int Alto=GUI.RESOLUCION_ALTO;
        g.drawImage(new ImageIcon(getClass().getResource("/img/FondoTablero.png")).getImage(),
                    x, y, x+Ancho, y+Alto, 
                    0, 0,771 , 500, null);
    }
    
//PRUEBAS
    public static void main(String[] args){
        JFrame v=new JFrame("Test");
        v.setTitle("JuegoXSimple Versión Desarrollo");
        v.setSize(GUI.RESOLUCION_ANCHO, GUI.RESOLUCION_ALTO);
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p=new JPanel();
        Tablero MiTablero=new Tablero(CONSTANTES.RUTA_MAPA_NIVEL_0);
        v.add(p);
        v.setVisible(true);
        
        MiTablero.ImprimirCeldas();
        
        for(int i=0;i<10;i++){
            MiTablero.ScrollDerecha();
            try{
                
                MiTablero.Pintar((Graphics2D)p.getGraphics());
                p.repaint();
                Thread.sleep(1000);
            }catch(Exception e){}
        }
    }
}
