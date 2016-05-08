/*
 * Este proyecto pertenece a Martín Alejandro Fernández.
 * Cualquier edición del siguiente archivo, sin autorización
 * no esta permitida.
 */

package lib;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que representa un Sprite dentro del juego.
 * @author Martín Alejandro Fernández
 * @version 1.0
 * @since 1.0
 * @see <a href="http://www.MartinAlejandroFernandez.com">Pagina Web ...</a>
 */
public abstract class Sprite {
//CONSTANTES
    //GUI
    public static final String IMG_ESPECIE_HUMANO="/img/Humano.png";
    public static final String IMG_ESPECIE_GUSANO="/img/Gusano.png";
    public static final String IMG_ESPECIE_SPIDER="/img/Spider.png";
    public static final String IMG_ESPECIE_GARGOLA="/img/Gargola.png";
    public static final int FRAME_MAX=3;
    //ESPECIES
    public static final int ESPECIE_HUMANA=0;
    public static final int ESPECIE_GUSANO=1;
    public static final int ESPECIE_SPIDER=2;
    public static final int ESPECIE_GARGOLA=3;
    public static final int ESPECIE_DESCONOCIDA=-99;
    //MOVIMIENTO
    /**
     * Constante que indica la direccion hacia abajo del Sprite.
     */
    public static final int DIRECCION_ABAJO=0;
    /**
     * Constante que indica la direccion hacia la izquierda del Sprite.
     */
    public static final int DIRECCION_IZQUIERDA=1;
    /**
     * Constante que indica la direccion hacia la derecha del Sprite.
     */
    public static final int DIRECCION_DERECHA=2;
    /**
     * Constante que indica la direccion hacia arriba del Sprite.
     */
    public static final int DIRECCION_ARRIBA=3;
    /**
     * Vector constante que contiene las direcciones posibles para un movimiento.
     */
    public static final int DIRECCIONES[][]={{0,1},{-1,0},{1,0},{0,-1}};
    /**
     * Constante que indica el numero maximo de direcciones posibles.
     */
    public static final int DIRECCIONES_POSIBLES=DIRECCIONES.length;
    //HILO
    public static final int HILO_CORRIENDO=0;
    public static final int HILO_DETENIDO=1;
    public static final int HILO_PAUSADO=2;
//PROPIEDADES
    //AUDIO
    Sonido Audio=new Sonido();
    //GUI
    private int FrameActual;
    private ArrayList<Integer> ColaDeAnimacion=new ArrayList<>();
    //HILO
    private int EstadoDelHilo;
    //SPRITE
    private Tablero Tablero;
    private String Nombre;
    private int Vitalidad;
    private int PuntosDeDefensa;
    private int PuntosDeAtaque;
    private int X;
    private int Y;
    private int XAnterior;
    private int YAnterior;
    private int Direccion;

    
//CONSTRUCTORES
    /**
     * Método constructor por defecto.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param tablero Tablero en el que el Sprite se va a desplazar.
     * @see #Inicializar(Tablero, String)
     */
    public Sprite(Tablero tablero){Inicializar(tablero,"Sprite"+this.getClass().hashCode());}
    
    /**
     * Método constructor del Sprite con posibilidad de definir el NOMBRE.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param tablero Tablero en el que el Sprite se va a desplazar.
     * @param nombre Indica el nombre del Sprite para su descripción.
     * @see #Inicializar(Tablero, String)
     */
    public Sprite(Tablero tablero, String nombre) {Inicializar(tablero,nombre);}
    
//METODOS PRIVADOS
    /**
     * Método que inicializa todos los campos del Sprite.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param tablero Tablero en el que el Sprite se va a desplazar.
     * @param nombre Indica el nombre del Sprite para su descripción.
     * @see #Sprite(Tablero) 
     * @see #Sprite(Tablero, String) 
     */
    private void Inicializar(Tablero tablero, String nombre){
        this.EstadoDelHilo=Sprite.HILO_DETENIDO;
        this.Tablero=tablero;
        this.Nombre=nombre;
        this.Vitalidad=1;
        this.PuntosDeDefensa=1;
        this.PuntosDeAtaque=1;
        this.X = 1;
        this.Y = 1;
        this.XAnterior=this.X;
        this.YAnterior=this.Y;
        this.Direccion=Sprite.DIRECCION_DERECHA;
        this.FrameActual=0;
    }
    
    /**
     * Metodo que Calcula la direccion del Sprite en base a su posición Actual y Anterior.
     * Setea el campo direccion del Sprite con alguno de los siguientes valores: 
     * {@link #DIRECCION_DERECHA}, {@link #DIRECCION_IZQUIERDA}, {@link #DIRECCION_ABAJO}, {@link #DIRECCION_ARRIBA} 
     * @author Martín Alejandro Fernández
     * @version 1.0
     */    
    private void CalcularDireccion(){
        if(this.getX()>this.getXAnterior()){this.Direccion=Sprite.DIRECCION_DERECHA;}
        else{
            if(this.getX()<this.getXAnterior()){this.Direccion=Sprite.DIRECCION_IZQUIERDA;}
            else{
                if(this.getY()>this.getYAnterior()){this.Direccion=Sprite.DIRECCION_ABAJO;}
                else{
                    if(this.getY()<this.getYAnterior()){this.Direccion=Sprite.DIRECCION_ARRIBA;}
                }
            }
        }
    }
    
    /**
     * Método que setea el campo Ocupante de la Celda Actual y la Anterior en null para que este disponible para ser ocupada.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @see Tablero.getCelda
     * @see Tablero.setOcupante
     */
    private void DesocuparCelda(){
        this.Tablero.getCelda(this.getXAnterior(), this.getYAnterior()).setOcupante(null);
        this.Tablero.getCelda(this.getX(), this.getY()).setOcupante(null);
    }
    
    /**
     * Método que setea el campo Ocupante de la Celda Actual con un valor distinto del null.
     * @author Martín Alejandro Fernández
     * @version 1.0 
     * @see Tablero.getCelda
     * @see Tablero.setOcupante
     */
    private void OcuparCelda(){this.Tablero.getCelda(this.getX(), this.getY()).setOcupante(this);}
    
    /**
     * Método que setea el campo X y XAnterior del  Sprite, esto permite que el Sprite "se mueva".
     * Por otro lado también llama a los metodos {@link #DesocuparCelda} , {@link OcuparCelda} y {@link #CalcularDireccion}. 
     * @author Martín Alejandro Fernández
     * @param x Es un valor de tipo entero que representa el nuevo valor de la coordenada.
     * @version 1.0 
     * @see #DesocuparCelda
     * @see #OcuparCelda
     * @see #CalcularDireccion
     */
    private void setX(int x){
        if(0<=x&&x<=this.getTablero().getAncho()){
            this.DesocuparCelda();
            this.XAnterior=this.X;
            this.X = x;
            this.OcuparCelda();
            this.CalcularDireccion();
        }
    }
    
    /**
     * Método que setea el campo Y y YAnterior del  Sprite, esto permite que el Sprite "se mueva".
     * Por otro lado también llama a los metodos {@link #DesocuparCelda} , {@link OcuparCelda} y {@link #CalcularDireccion}. 
     * @author Martín Alejandro Fernández
     * @param y Es un valor de tipo entero que representa el nuevo valor de la coordenada.
     * @version 1.0 
     * @see #DesocuparCelda
     * @see #OcuparCelda
     * @see #CalcularDireccion
     */
    private void setY(int y){
        if(0<=y&&y<=this.getTablero().getAlto()){
            this.DesocuparCelda();
            this.YAnterior=this.Y;
            this.Y = y;
            this.OcuparCelda();
            this.CalcularDireccion();
        }
    }
    
//METODOS PROTECTED
    /**
     * Método que setea el campo vitalidad del  Sprite, esto permite que el Sprite "viva" o "se muera".
     * El valor seteado debe ser mayor que cero.
     * @author Martín Alejandro Fernández
     * @version 1.0 
     * @param vitalidad Es un valor entero mayor que cero.
     */
    protected void setVitalidad(int vitalidad){if(vitalidad>=0){this.Vitalidad = vitalidad;}else{this.Vitalidad=0;}}
    
    /**
     * Método que simula el proceso de la muerte del Sprite llamando secuencialmente a los metodos: 
     * {@link #SonidoDetener()},{@link #SonidoMuerte()},{@link #DesocuparCelda()} y {@link #DetenerHiloSprite()}. 
     * @author Martín Alejandro Fernández
     * @version 1.0 
     * @see #SonidoDetener
     * @see #SonidoMuerte
     * @see #DesocuparCelda
     * @see #DetenerHiloSprite
     */
    protected void Morir(){
        //new TempSprite(this.Tablero.getCanvas(),this.getX(),this.getY()).start();
        this.SonidoDetener();
        this.SonidoMuerte();
        this.DesocuparCelda();
        this.DetenerHiloSprite();
    }

    protected void setPuntosDeDefensa(int PuntosDeDefensa) {if(PuntosDeDefensa>=0){this.PuntosDeDefensa=PuntosDeDefensa;}else{this.PuntosDeDefensa=0;}}

    protected void setPuntosDeAtaque(int PuntosDeAtaque) {if(PuntosDeAtaque>=0){this.PuntosDeAtaque = PuntosDeAtaque;}else{this.PuntosDeAtaque=0;}}
    
//METODOS PUBLICOS
    
    public void PausarHiloSprite(){this.EstadoDelHilo=Sprite.HILO_PAUSADO;}
    
    public void DetenerHiloSprite(){this.EstadoDelHilo=Sprite.HILO_DETENIDO;}
    
    public void CorrerHiloSprite(){this.EstadoDelHilo=Sprite.HILO_CORRIENDO;}
    
    public boolean EstaElHiloDetenido(){return this.EstadoDelHilo==Sprite.HILO_DETENIDO;}
    
    public boolean EstaElHiloPausado(){return this.EstadoDelHilo==Sprite.HILO_PAUSADO;}
    
    public boolean EstaElHiloCorriendo(){return this.EstadoDelHilo==Sprite.HILO_CORRIENDO;}
    
    public void setTablero(Tablero tablero){this.Tablero = tablero;}

    public Tablero getTablero(){return this.Tablero;}
    
    public void setNombre(String nombre){this.Nombre = nombre;}
    
    public String getNombre(){return this.Nombre;}
    
    public int getVitalidad(){return this.Vitalidad;}

    public int getPuntosDeDefensa() {return this.PuntosDeDefensa;}

    public int getPuntosDeAtaque() {return this.PuntosDeAtaque;}
    
    public int getX(){return this.X;}
    
    public int getY(){return this.Y;}
    
    public int getXAnterior(){return this.XAnterior;}
    
    public int getYAnterior(){return this.YAnterior;}

    public void setDireccion(int xAct,int yAct,int xSig,int ySig){
        if(xSig>xAct){this.Direccion=Sprite.DIRECCION_DERECHA;}
        else{
            if(xSig<xAct){this.Direccion=Sprite.DIRECCION_IZQUIERDA;}
            else{
                if(ySig>yAct){this.Direccion=Sprite.DIRECCION_ABAJO;}
                else{
                    if(ySig<yAct){this.Direccion=Sprite.DIRECCION_ARRIBA;
                    }
                }
            }
        }
    }
    
    public int getDireccion(){return this.Direccion;}
    
    /**
     * Método que se encarga de atacar a otro sprite. Para realizar el ataque 
     * controla:<BR>
     * - Que el defensor no sea null.<BR>
     * - Que la diferencia entre los puntos de ataque y los puntos de defenza sea mayor que cero.<BR>
     * - Que el Defensor (Enemigo) y el atacante esten vivos.<BR>
     * Si el ataque se puede realizar, este consiste en disminuir los puntos de 
     * vitalidad del Enemigo usando el método {@link #setVitalidad(int)}. <BR>
     * Antes de iniciar el ataque, reproduce el sonido de ataque {@link #SonidoAtaque} y si se puede atacar reproduce
     * el sonido de un ataque recibido {@link SonidoAtaqueRecibido}
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param Enemigo Sprite que representa el enemigo.
     * @return Retorna Verdadero si el ataque se realizó caso contrario retorna Falso.
     * @see #SonidoAtaque
     * @see #SonidoAtaqueRecibido
     * @see #setVitalidad(int)
     */
    public boolean Atacar (Sprite Enemigo) {
        int PuntosDeAgresion;
        this.SonidoAtaque();
        if(Enemigo!=null){
            PuntosDeAgresion = this.getPuntosDeAtaque()-Enemigo.getPuntosDeDefensa();
            if(PuntosDeAgresion>0&&Enemigo.EstaVivo()&&this.EstaVivo()){
                if(CONSTANTES.MENSAJE_CONSOLA){System.out.println(">>>"+this.getNombre()+" está atacando a "+Enemigo.getNombre()+"<<<");}
                Enemigo.SonidoAtaqueRecibido();
                Enemigo.setVitalidad(Enemigo.getVitalidad()-PuntosDeAgresion);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Método que simplifica el llamado al método {@link #Atacar(lib.Sprite)}, tomando 
     * el enemigo que se encuentra a la derecha.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @return Verdadero si el ataque se pudo realizar caso contrario devuelve Falso.
     * @see lib.Tablero#getOcupanteCelda(int, int) 
     * @see #Atacar(lib.Sprite)
     * @see #AtacarIzquierda()
     * @see #AtacarArriba()
     * @see #AtacarAbajo()
     */
    public boolean AtacarDerecha(){return this.Atacar(this.getTablero().getOcupanteCelda(this.getX()+1, this.getY()));}
    
    /**
     * Método que simplifica el llamado al método {@link #Atacar(lib.Sprite)}, tomando 
     * el enemigo que se encuentra a la izquierda.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @return Verdadero si el ataque se pudo realizar caso contrario devuelve Falso.
     * @see lib.Tablero#getOcupanteCelda(int, int) 
     * @see #Atacar(lib.Sprite)
     * @see #AtacarDerecha()
     * @see #AtacarArriba()
     * @see #AtacarAbajo()
     */
    public boolean AtacarIzquierda(){return this.Atacar(this.getTablero().getOcupanteCelda(this.getX()-1, this.getY()));}
    
    /**
     * Método que simplifica el llamado al método {@link #Atacar(lib.Sprite)}, tomando 
     * el enemigo que se encuentra abajo.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @return Verdadero si el ataque se pudo realizar caso contrario devuelve Falso.
     * @see lib.Tablero#getOcupanteCelda(int, int) 
     * @see #Atacar(lib.Sprite)
     * @see #AtacarDerecha()
     * @see #AtacarIzquierda()
     * @see #AtacarArriba()
     */    
    public boolean AtacarAbajo(){return this.Atacar(this.getTablero().getOcupanteCelda(this.getX(), this.getY()+1));}
        
    /**
     * Método que simplifica el llamado al método {@link #Atacar(lib.Sprite)}, tomando 
     * el enemigo que se encuentra arriba.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @return Verdadero si el ataque se pudo realizar caso contrario devuelve Falso.
     * @see lib.Tablero#getOcupanteCelda(int, int) 
     * @see #Atacar(lib.Sprite)
     * @see #AtacarDerecha()
     * @see #AtacarIzquierda()
     * @see #AtacarAbajo()
     */
    public boolean AtacarArriba(){return this.Atacar(this.getTablero().getOcupanteCelda(this.getX(), this.getY()-1));}
    
    public void setFrameSiguiente(){this.FrameActual=(((this.getFrameActual()+1)%Sprite.FRAME_MAX));}
    
    public void setFrameActual(int f){this.FrameActual=f;}
    
    public int getFrameActual(){return this.FrameActual;}

    public Integer getAnimacionDeCola() {
        int FrameAnimacion;
        if(!ColaDeAnimacion.isEmpty()){
            FrameAnimacion=ColaDeAnimacion.get(0);
            ColaDeAnimacion.remove(0);
        }else{FrameAnimacion=this.getFrameActual();}
        return FrameAnimacion;
    }

    public void setAnimacionEnCola(Integer FrameAnimacion) {this.ColaDeAnimacion.add(FrameAnimacion);}
    
    public void LimpiarColaDeAnimacion(){this.ColaDeAnimacion.clear();}
        
    /**
     * Método que se encarga verificar si un Sprite tiene un valor de vitalidad 
     * mayor que cero (está Vivo).<BR> 
     * Si {@link #Vitalidad}>0 entonces ocupa una celda del tablero usando el metodo 
     * {@link #OcuparCelda()}, caso contrario llama al metodo {@link #Morir()} y retorna Falso.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @return Retorna Verdadero si el valor de {@link #Vitalidad} es mayor que cero caso contrario retorna Falso.
     */
    public boolean EstaVivo(){
        if(this.Vitalidad > 0){
            this.OcuparCelda();
            return true;
        }else{
            this.Morir();
            return false;
        }
    }

    /**
     * Método encargado de posicionar el Sprite en una coordenada (X,Y).
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param x Coordenada X del nuevo punto.
     * @param y Coordenada Y del nuevo punto.
     * @see #setX(int)
     * @see #setY(int) 
     */
    public void setCoordenada(int x, int y){this.setX(x);this.setY(y);}
    
    /**
     * Método encargado de desplazar el Sprite a una coordenada (X,Y+1)
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @see #setCoordenada(int, int) 
     */
    public void MoverAbajo(){this.setCoordenada(this.getX(),this.getY()+1);}
    
    /**
     * Método encargado de desplazar el Sprite a una coordenada (X-1,Y)
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @see #setCoordenada(int, int) 
     */
    public void MoverIzquierda(){this.setCoordenada(this.getX()-1,this.getY());}
        
    /**
     * Método encargado de desplazar el Sprite a una coordenada (X+1,Y)
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @see #setCoordenada(int, int) 
     */
    public void MoverDerecha(){this.setCoordenada(this.getX()+1,this.getY());}
    
    /**
     * Método encargado de desplazar el Sprite a una coordenada (X,Y-1)
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @see #setCoordenada(int, int) 
     */
    public void MoverArriba(){this.setCoordenada(this.getX(),this.getY()-1);}
        
    /**
     * Método encargado de desplazar el Sprite en una dirección aleatoria.<BR>
     * En un principio intenta un movimiento aleatorio en cualquier dirección, si no puede moverse en la dirección elegida, busca una direccion alternativa.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @return Verdadero si se pudo mover, caso contrario retorna Falso.
     * @see #setCoordenada(int, int) 
     * @see #DIRECCIONES
     * @see java.util.Random#nextInt() 
     */
    public boolean MovimientoAletorio(){
        int direccion=new Random().nextInt(Sprite.DIRECCIONES_POSIBLES); //Genera un numero aleatorio en una de las direcciones diponibles.
        int XNueva=this.getX()+Sprite.DIRECCIONES[(direccion%Sprite.DIRECCIONES_POSIBLES)][0];
        int YNueva=this.getY()+Sprite.DIRECCIONES[(direccion%Sprite.DIRECCIONES_POSIBLES)][1];
        if(this.EsPosibleElMovimiento(XNueva,YNueva)){
            this.setCoordenada(XNueva, YNueva);
            return true;
        }else{
            for(int i=1;i<Sprite.DIRECCIONES_POSIBLES;i++){
                XNueva=this.getX()+Sprite.DIRECCIONES[((direccion+i)%Sprite.DIRECCIONES_POSIBLES)][0];
                YNueva=this.getY()+Sprite.DIRECCIONES[((direccion+i)%Sprite.DIRECCIONES_POSIBLES)][1];
                if(this.EsPosibleElMovimiento(XNueva,YNueva)){
                    this.setCoordenada(XNueva, YNueva);
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Método para mostrar informacion Actual del Sprite por consola
     * @author Martín Alejandro Fernández
     * @version 1.0
     */
    public void ImprimirEstadoSprite(){
        String Mensaje;
        Mensaje="Mapa Actual="+this.getTablero().getNombre()+
                "\nJugador="+this.getNombre()+
                "\nHilo Corriendo?="+this.EstaElHiloCorriendo()+
                "\nVitalidad="+this.getVitalidad()+
                "\nDefensa="+this.getPuntosDeDefensa()+
                "\nAtaque="+this.getPuntosDeAtaque()+
                "\nPosición Actual=("+this.getX()+","+this.getY()+")"+
                "\nPosición Anterior=("+this.getXAnterior()+","+this.getYAnterior()+")"+
                "\nDireccion="+this.getDireccion()+
                "\nFrame="+this.getFrameActual();
        System.out.println(Mensaje);
    }
    
    /**
     * Método para mostrar informacion Actual del Sprite por pantalla dentro de la GUI
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param g Objeto Graphics2D en donde se va a pintar la información.
     * @param c Objeto Color, es el color elegido para pintar la información.
     * @param x Entero que indica la posición X inicial de donde se pinta la información.
     * @param y Entero que indica la posición Y inicial de donde se pinta la información.
     */
    public void PintarEstadoSprite(Graphics2D g, Color c, int x, int y){
        int FuenteTam=20;
        String FuenteNombre="Bauhaus 93";
        String Mensaje[]={
            "Mapa Actual="+this.getTablero().getNombre(),
            "Jugador="+this.getNombre(),
            "Hilo Corriendo?="+this.EstaElHiloCorriendo(),
            "Vitalidad="+this.getVitalidad(),
            "Ataque="+this.getPuntosDeAtaque(),
            "Defensa="+this.getPuntosDeDefensa(),
            "Posición Actual=("+this.getX()+","+this.getY()+")",
            "Posición Anterior=("+this.getXAnterior()+","+this.getYAnterior()+")",
            "Direccion="+this.getDireccion(),
            "Frame="+this.getFrameActual()
        };
        
        y=GUI.RESOLUCION_ALTO/2;//-((Mensaje.length+1)*FuenteTam);
        
        g.setColor(c);
        g.setFont(new Font(FuenteNombre,Font.BOLD,FuenteTam));
        for(int i=0;i<=(Mensaje.length-1);i++){
            g.drawString(Mensaje[i],x,y+(FuenteTam*(i+1)));
        }
    }
    
//METODOS ABSTRACTOS
    
    /**
     * Método que determina si una coordenada puede ser ocupada o no por el sprite.
     * Básicamente determina si es HABITABLE y si NO está OCUPADA.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param x Coordenada X del nuevo punto.
     * @param y Coordenada Y del nuevo punto.
     * @return Verdadero si el sprite puede ocupar la coordenada, caso contrario devuelve falso.
     */
    public abstract boolean EsPosibleElMovimiento(int x, int y);
    /**
     * Método que determina el comportamiento del sprite dentro del juego.
     * Este método debe considerar como un Sprite se mueve en el tablero, y como interactua con los restantes Sprites.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param t Tablero que se comparte con los restantes Sprites.
     */
    public abstract void IA(Tablero t);
    /**
     * Método que pinta el Sprite en pantalla dentro de la GUI
     * @author Martín Alejandro Fernández
     * @version 1.0
     */
    public abstract void Pintar();
    public abstract void SonidoAtaque();
    public abstract void SonidoAtaqueRecibido();
    public abstract void SonidoMuerte();
    public abstract void SonidoDetener();
    /**
     * Método que se encarga de responder a los eventos del teclado, 
     * especificamente cuando se suelta una tecla.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param e Evento ({@link KeyEvent}) del teclado. 
     */
    public abstract void SueltaTecla(KeyEvent e);//Método que recibe los eventos del teclado.
    /**
     * Método que se encarga de responder a los eventos del teclado, 
     * especificamente cuando se presiona una tecla.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param e Evento ({@link KeyEvent}) del teclado. 
     */
    public abstract void PresionaTecla(KeyEvent e);
    /**
     * Método que se encarga de responder a los eventos del teclado, 
     * especificamente cuando se presiona y suelta una tecla.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param e Evento ({@link KeyEvent}) del teclado. 
     */
    public abstract void PresionaYSueltaTecla(KeyEvent e);   
}
