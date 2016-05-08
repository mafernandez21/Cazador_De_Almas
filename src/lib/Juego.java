/*
 * Este proyecto pertenece a Martín Alejandro Fernández.
 * Cualquier edición del siguiente archivo, sin autorización
 * no esta permitida.
 */

package lib;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Martín Alejandro Fernández
 */
public class Juego extends JPanel implements Runnable{
    
    //JUEGO
    public static final int CORRIENDO=0;
    public static final int DETENIDO=1;
    public static final int PAUSADO=2;
    public static final int MAX_NUM_NIVELES=6;
    public static final int TIEMPO_MAX=240;
    public static final int CONTEO_PREVIO=0;
    public static final int DIFICULTAD_NULA=0; 
    public static final int DIFICULTAD_MINIMA=1;
    public static final int DIFICULTAD_MEDIA=2;
    public static final int DIFICULTAD_MAXIMA=3;
        
    private Cronometro Reloj;
    private int Estado;
    private int NivelActual;
    private String NombreJugador;
    private int CantidadDeEnemigosRestantes;
    private int PuntuacionActual;
    private int PuntuacionTotal;
    private long Tiempo;
    private int Dificultad;
    private final JFrame Ventana;
    private int CantidadDeEnemigosTotal;
    private long TiempoDeRetardo;
    private Tablero Tablero;
    private Humano Humano;
    private ArrayList<NoHumano> NoHumanos;
    private Thread HiloJugador;
    private ArrayList<Thread> HilosEnemigos;
    private Sonido Musica=new Sonido();

    
    
    
    
    
    
    
    
    public Juego(JFrame mVentana, String NombreJugador){
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Creando Objetos");}
        this.Ventana=mVentana;
        this.NoHumanos=new ArrayList<>();
        this.HilosEnemigos=new ArrayList<>();
        this.PuntuacionTotal=0;
        this.NombreJugador=NombreJugador;
        this.Reloj=new Cronometro();
        this.Reloj.Iniciar();
        
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Preparando Teclado");}
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if(Estado==Juego.CORRIENDO){Humano.SueltaTecla(e);}
                switch(e.getKeyCode()){
                    case KeyEvent.VK_P:
                        PausarJuego();
                    break;
                    case KeyEvent.VK_ESCAPE:
                        PausarJuego();
                        if(JOptionPane.showConfirmDialog(Ventana,"¿Detener el Juego?", "Finalizar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
                            DetenerJuego();
                            break;
                        }
                        PausarJuego();
                    break;
                    case KeyEvent.VK_PLUS:
                        Tablero.ScrollDerecha();
                    break;
                    case KeyEvent.VK_MINUS:
                        Tablero.ScrollIzquierda();
                    break;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(Estado==Juego.CORRIENDO){Humano.PresionaTecla(e);}
            }
        });
        setFocusable(true);
    }
    
    public void PrepararVentana(JFrame Ventana){
        Image Icono=Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/Icono.png"));
        Ventana.setIconImage(Icono);
        Ventana.setTitle("Cazador de Almas");
        Ventana.setSize(GUI.RESOLUCION_ANCHO, GUI.RESOLUCION_ALTO);
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void MostrarVentana(JFrame Ventana){Ventana.setVisible(true);}
    
    public void OcultarVentana(JFrame Ventana){Ventana.setVisible(false);}
   
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d=(Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        if(this.Estado==Juego.CORRIENDO){
            this.Tablero.Pintar(g2d);
            this.Humano.Pintar();
            for(int i=(NoHumanos.size()-1);i>=0;i--){
                this.NoHumanos.get(i).Pintar();
            }
            
        }
        
        this.PintarEstadoJuego(g2d);
    }
     
    public void PintarEstadoJuego(Graphics2D g){
        int FuenteTam=20;
        String FuenteNombre="Bauhaus 93";
        int x=0;//(GUI.PPC*(GUI.CELDAS_VISIBLES));
        int y=0;//GUI.RESOLUCION_ALTO/2;//-((Mensaje.length+1)*FuenteTam);
        
        String MensajeT1="",MensajeT2="",MensajeT3="";
            switch(this.Estado){
                case Juego.CORRIENDO:MensajeT1="Juego Corriendo";break;
                case Juego.PAUSADO:MensajeT1="Juego Pausado";break;
                case Juego.DETENIDO:MensajeT1="Juego Detenido";break;
            }
            
            if(this.TiempoDeRetardo > 0){
                MensajeT2="FPS = "+(1000/this.TiempoDeRetardo);
            }else{
                MensajeT2="FPS = ---";
            }
            
            if(this.Humano.TieneLaLlave()){
                MensajeT3="Ya puedes Salir!!!";
            }else{
                MensajeT3="Debes capturar todas las almas";
            }
            
            String Mensaje[]={
                "Nivel Actual = "+(this.NivelActual),
                "Dificultad = "+(this.Dificultad),
                "Tiempo = "+(this.Tiempo),
                "Total de Almas = "+(this.CantidadDeEnemigosTotal),
                "Almas Restantes = "+(this.CantidadDeEnemigosRestantes),
                "\n",
                "Jugador = "+(this.NombreJugador),
                "Vitalidad = "+this.Humano.getVitalidad(),
                MensajeT3,
                "Puntuacion Actual = "+(this.PuntuacionActual),
                "Puntuacion Total = "+(this.PuntuacionTotal)
            };

        g.setColor(Color.WHITE);
        g.setFont(new Font(FuenteNombre,Font.BOLD,FuenteTam));
        for(int i=0;i<=(Mensaje.length-1);i++){
            g.setColor(Color.WHITE);
            switch(i){
                case 2:
                    if(this.Tiempo>=(Juego.TIEMPO_MAX-40)){g.setColor(Color.RED);}
                break;
                case 7:
                    if(this.Humano.getVitalidad()<=50){g.setColor(Color.RED);}
                break;
                case 8:
                    if(this.Humano.TieneLaLlave()){g.setColor(Color.GREEN);}
                break;
            }
            g.drawString(Mensaje[i],x,y+(FuenteTam*(i+1)));
        }
    }
    
    public void CorrerJuego(){
        this.Reloj.Correr();
        this.Estado=Juego.CORRIENDO;
    }
    
    public void PausarJuego(){
        this.Reloj.Pausar();
        if(Estado==Juego.PAUSADO){
            this.Reloj.Correr();
            Estado=Juego.CORRIENDO;
        }else{
            if(Estado==Juego.CORRIENDO){
                this.Reloj.Pausar();
                Estado=Juego.PAUSADO;
            }
        }
    }
    
    public void DetenerJuego(){
        this.Reloj.Detener();
        this.Estado=Juego.DETENIDO;
    }
    
    public void ConteoPrevio(){
        int Conteo=Juego.CONTEO_PREVIO;
        while(Conteo>0){
            if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Iniciando en " +Conteo);}
            Conteo--;
            try {
                Thread.sleep((Conteo*500));
            } catch (InterruptedException ex) {}
        }
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Iniciado!!!");}
    }
    
    
    public void CargarParametros(int Nivel, int Dificultad){
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Cargando Parametros del Nivel "+Nivel+", con Dificultad "+Dificultad);}
        this.PuntuacionActual=0;
        this.NivelActual=(Nivel%(Juego.MAX_NUM_NIVELES+1));
        this.Dificultad=Dificultad;        
        this.CantidadDeEnemigosTotal=1+(this.NivelActual*5)+(5*(this.Dificultad-1));//5+(5*(this.NivelActual-1))+(5*this.Dificultad);
    }
    
    public void CargarLaberinto(){
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Cargando Laberinto del Nivel "+this.NivelActual);}
        switch(this.NivelActual){
            case 0:this.Tablero = new Tablero(CONSTANTES.RUTA_MAPA_NIVEL_0);break;
            case 1:this.Tablero = new Tablero(CONSTANTES.RUTA_MAPA_NIVEL_1);break;
            case 2:this.Tablero = new Tablero(CONSTANTES.RUTA_MAPA_NIVEL_2);break;
            case 3:this.Tablero = new Tablero(CONSTANTES.RUTA_MAPA_NIVEL_3);break;
            case 4:this.Tablero = new Tablero(CONSTANTES.RUTA_MAPA_NIVEL_4);break;
            case 5:this.Tablero = new Tablero(CONSTANTES.RUTA_MAPA_NIVEL_5);break;  
            case 6:this.Tablero = new Tablero(CONSTANTES.RUTA_MAPA_NIVEL_6);break;
        }
    }
    
    public void CargarEnemigos(){
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Creando Enemigos del Nivel "+this.NivelActual);}
        this.NoHumanos.clear();
        this.HilosEnemigos.clear();
                
        for(int idx=0;idx<this.CantidadDeEnemigosTotal;idx++){
            switch(this.NivelActual){
                case 0:
                case 1:
                case 2:
                    this.NoHumanos.add(idx,new NoHumano(this.Tablero,Sprite.ESPECIE_GUSANO,"NH"+String.valueOf(idx+1)+"dMin"));
                break;
                case 3:
                case 4:
                    this.NoHumanos.add(idx,new NoHumano(this.Tablero,Sprite.ESPECIE_GUSANO,"NH"+String.valueOf(idx+1)+"dMin"));
                    if(idx<this.CantidadDeEnemigosTotal-1){
                        idx++;
                        this.NoHumanos.add(idx,new NoHumano(this.Tablero,Sprite.ESPECIE_SPIDER,"NH"+String.valueOf(idx+1)+"dMed"));
                    }
                break;
                case 5:
                case 6:
                    this.NoHumanos.add(idx,new NoHumano(this.Tablero,Sprite.ESPECIE_GUSANO,"NH"+String.valueOf(idx+1)+"dMin"));
                    if(idx<this.CantidadDeEnemigosTotal-1){
                        idx++;
                        this.NoHumanos.add(idx,new NoHumano(this.Tablero,Sprite.ESPECIE_SPIDER,"NH"+String.valueOf(idx+1)+"dMed"));
                    }
                    if(idx<this.CantidadDeEnemigosTotal-2){
                        idx++;
                        this.NoHumanos.add(idx,new NoHumano(this.Tablero,Sprite.ESPECIE_GARGOLA,"NH"+String.valueOf(idx+1)+"dMax"));
                    }
                break;
            }
        }
        
        int xInicial=5;
        int yInicial=5;
        for(NoHumano nh: NoHumanos){
            for(int j=yInicial;j<nh.getTablero().getAlto();j++){
                for(int i=xInicial;i<nh.getTablero().getAncho();i++){
                    if(nh.getTablero().getOcupanteCelda(i, j)==null&&
                        ((nh.getTablero().getCelda(i, j).getTipo()==Celda.TIPO_CAMINO)||
                         (nh.getTablero().getCelda(i, j).getTipo()==Celda.TIPO_SALIDA))){
                        nh.setCoordenada(i, j);
                        this.HilosEnemigos.add(new Thread(nh,"Hilo("+nh.getNombre()+")"));
                        i=nh.getTablero().getAncho()+1;
                        j=nh.getTablero().getAlto()+1;
                    }
                }
            }
        }
        this.CantidadDeEnemigosRestantes=NoHumanos.size();
    }
    
    public void CargarPersonaje(){
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Creando Personaje");}
        if(this.Humano!=null){this.Humano.DetenerHiloSprite();this.Humano=null;}
        
        if(this.NombreJugador.isEmpty()){this.NombreJugador="Alex";}
        
        this.Humano=new Humano(this.Tablero,this.NombreJugador);
        //this.Humano.setNombre(this.NombreJugador);
        
        for(int j=0;j<=this.Humano.getTablero().getAlto();j++){
            for(int i=0;i<=this.Humano.getTablero().getAncho();i++){
                if(this.Humano.getTablero().getOcupanteCelda(i, j)==null&&
                   this.Humano.getTablero().getCelda(i, j).getTipo()==Celda.TIPO_ENTRADA){
                    this.Humano.setCoordenada(i, j);
                    i=this.Humano.getTablero().getAncho()+1;
                    j=this.Humano.getTablero().getAlto()+1;
                }
            }
        }
        
        for(int j=0;j<=this.Humano.getTablero().getAlto();j++){
            for(int i=0;i<=this.Humano.getTablero().getAncho();i++){
                if(this.Humano.getTablero().getCelda(i, j).getTipo()==Celda.TIPO_SALIDA){
                    this.Humano.setDireccion(this.Humano.getX(),this.Humano.getY(),i,j);
                    i=this.Humano.getTablero().getAncho()+1;
                    j=this.Humano.getTablero().getAlto()+1;
                }
            }
        }
        this.HiloJugador = new Thread(Humano,"Hilo ("+Humano.getNombre()+")");
        //this.NombreJugador=this.Humano.getNombre();
    }
    
    public void NuevoNivel(int Nivel, int Dificultad){
        this.PausarSprites();
        this.PausarJuego();
        
        this.CargarParametros(Nivel, Dificultad);
        this.CargarLaberinto();
        this.CargarEnemigos();
        this.CargarPersonaje();
        
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Iniciando Sprites");}
        this.CorrerSprites();
        this.IniciarSprites();
        this.CorrerJuego();
        this.Reloj.Reiniciar();
        this.Reloj.Correr();
    }
   
    public void IniciarSprites(){
        try{
            if(!this.HiloJugador.isAlive()){this.HiloJugador.start();}
            for(Thread t: this.HilosEnemigos){if(!t.isAlive()){t.start();}}
        }catch(IllegalThreadStateException e){if(CONSTANTES.MENSAJE_CONSOLA){System.out.println(">>> Error: Un Sprite no se pudo iniciar. <<<");}}
    }

    public void CorrerSprites(){
        if(this.Humano!=null&&this.NoHumanos.size()>0){
            this.Humano.CorrerHiloSprite();
            for(int i=(this.NoHumanos.size()-1);i>=0;i--){this.NoHumanos.get(i).CorrerHiloSprite();}
        }
    }
    
    public void PausarSprites(){
        if(this.Humano!=null&&this.NoHumanos.size()>0){
            this.Humano.PausarHiloSprite();
            for(int i=(this.NoHumanos.size()-1);i>=0;i--){this.NoHumanos.get(i).PausarHiloSprite();}
        }
    }
    
    public void DetenerSprites(){
        if(this.NoHumanos.size()>0){for(int i=(this.NoHumanos.size()-1);i>=0;i--){this.NoHumanos.get(i).DetenerHiloSprite();}}
        if(this.Humano!=null){this.Humano.DetenerHiloSprite();}
    }
    
    public void CalcularPuntaje(int puntos){
        this.PuntuacionTotal+=(int)(this.Humano.getVitalidad()/10);
        this.PuntuacionTotal+=(int)(60/(this.Tiempo+1));
        this.PuntuacionTotal+=(int)Math.pow(2,this.Dificultad);
        this.PuntuacionTotal+=puntos;
    }
    
    public boolean JugadorGano(){
        boolean Condiciones[] = new boolean[3];
            Condiciones[0] = this.Humano.EstaVivo();
            Condiciones[1] = this.Humano.TieneLaLlave();
            Condiciones[2] = this.Humano.EstaEnUnaSalida();
            for(int i=0;i<=(Condiciones.length-1);i++){
                if(Condiciones[i]==false){
                    return false;
                }
            }
        return true;
    }
    
    public boolean JugadorNoGano(){
        boolean Condiciones[] = new boolean[3];
            Condiciones[0] = !this.Humano.EstaVivo();
            Condiciones[1] = this.JugadorGano();
            Condiciones[2] = ((Juego.TIEMPO_MAX-this.Tiempo)<=0);
            for(int i=0;i<=(Condiciones.length-1);i++){
                if(Condiciones[i]==true){
                    return true;
                }
            }
        return false;
    }
    
    public boolean CondicionesDeLlave(){return (this.Humano.EstaVivo()&&(this.CantidadDeEnemigosRestantes<=0));}
    
    public void MostrarMensajeFinal(){
        this.PausarJuego();
        this.PausarSprites();
        String Mensaje="\n"+ Humano.getNombre();
            if(this.JugadorGano()){
                Mensaje+="\nGANASTE!!!";
            }else{
                Mensaje+="\nFuiste Derrotado!!!";
            }
        this.CalcularPuntaje(this.PuntuacionActual);
        Mensaje+="\nObtuviste una puntuacion de " + this.PuntuacionTotal;
        JOptionPane.showMessageDialog(Ventana, Mensaje);
        this.CorrerJuego();
        this.CorrerSprites();
    }
            
    public boolean SeguirJugando(boolean Proximo){
        if(this.JugadorGano()&&Proximo){
            if(this.NivelActual<=(Juego.MAX_NUM_NIVELES-1)){
                if(JOptionPane.showConfirmDialog(Ventana, "¿Jugar el Nivel "+(this.NivelActual+1)+"?", "Siguiente Nivel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
                {return true;}
            }else{
                JOptionPane.showMessageDialog(Ventana, "JUEGO TERMINADO!", "FIN",JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            if(JOptionPane.showConfirmDialog(Ventana, "¿Volver a Jugar Nivel "+(this.NivelActual)+"?", "Volver a Jugar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
            {return true;}
        }
        return false;
    }
    
    public void Motor(){
        this.Tiempo=this.Reloj.getTiempoActual();
        if(!this.JugadorGano()&&!this.JugadorNoGano()){
            if(this.Humano!=null&&this.Estado!=Juego.DETENIDO){
                for(int i=(this.NoHumanos.size()-1);i>=0;i--){
                    this.NoHumanos.get(i).setRetardoIA(this.TiempoDeRetardo);
                    if(!this.NoHumanos.get(i).EstaVivo()){
                        this.PuntuacionActual+=this.NoHumanos.get(i).getPuntos();
                        this.NoHumanos.remove(i);
                    }
                }
            }
            this.CantidadDeEnemigosRestantes=this.NoHumanos.size();
            this.Humano.setLlave(this.CondicionesDeLlave());
        }else{
            this.MostrarMensajeFinal();
            if(this.JugadorGano()){
                if(this.SeguirJugando(true)){this.NuevoNivel(this.NivelActual+1, this.Dificultad);}
                else{this.DetenerJuego();}
            }else{
                if(this.SeguirJugando(false)){this.NuevoNivel(this.NivelActual, this.Dificultad);}
                else{this.DetenerJuego();}
            }
        }
    }
    
    public boolean FinDeLoop(){
        if(this.Estado==Juego.DETENIDO){this.DetenerSprites();return true;}
        if(this.Estado==Juego.PAUSADO){this.PausarSprites();}
        if(this.Estado==Juego.CORRIENDO){this.CorrerSprites();}
        return false;
    }
    
    public void setNombreJugador(String Nombre){
        this.NombreJugador=Nombre;
        this.Humano.setNombre(this.NombreJugador);
        this.HiloJugador.setName(this.Humano.getNombre());
    }
    
    @Override
    public void run() {
        long ticks=1000/GUI.CPS;
        long startTime;
        long endTime;
        
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Espere...");}
        //this.ConteoPrevio();
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Juego Iniciado");}
        Sonido.PistaDeFondo.loop();
        while (true) {//Game Loop
            startTime=System.currentTimeMillis();
                this.Motor();//IA
                this.repaint();//Render
                if(this.FinDeLoop()){break;}//Control del Loop
            endTime=System.currentTimeMillis();
            this.TiempoDeRetardo=ticks-(endTime-startTime);
            
            //Verifico el tiempo de procesamiento
            if(this.TiempoDeRetardo<=0){this.TiempoDeRetardo=10;}
   
            //Corrijo los CPS (Cuadros Por Segundo)
            try{Thread.sleep(this.TiempoDeRetardo);
            }catch(InterruptedException e){this.Ventana.dispose();}
        }
        Sonido.PistaDeFondo.stop();
        this.DetenerSprites();
        this.DetenerJuego();
        this.Ventana.dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Creando interfase");}
        JFrame Interfase=new JFrame();
                
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Cargando Juego");}
        Juego NuevoJuego=new Juego(Interfase,"Alex");
        int mNivel=Integer.parseInt(args[0]);
        int mDificultad=Integer.parseInt(args[1]);
        
        if(mNivel>=0&&mDificultad>=0){NuevoJuego.NuevoNivel(mNivel,mDificultad);}
        else{NuevoJuego.NuevoNivel(0,Juego.DIFICULTAD_MAXIMA);}
        
        Interfase.add(NuevoJuego);
        NuevoJuego.PrepararVentana(Interfase);
        NuevoJuego.MostrarVentana(Interfase);
        
        //NuevoJuego.EstadoDeJuego.NombreJugador=JOptionPane.showInputDialog(Interfase, "Ingrese su Nombre");
        //if("".equals(MiMotor.EstadoDeJuego.NombreJugador)){
        //    MiMotor.EstadoDeJuego.NombreJugador="Alex";
        //}
        //MiMotor.Humano.setNombre(MiMotor.EstadoDeJuego.NombreJugador);
        //MiMotor.HiloJugador.setName(MiMotor.Humano.getNombre());
 
        if(CONSTANTES.MENSAJE_CONSOLA){System.out.println("Arrancando el juego");}
        
        NuevoJuego.CorrerJuego();
        Thread ThreadJuego=new Thread(NuevoJuego,"Hilo De Juego");
        ThreadJuego.start();
    }
}
