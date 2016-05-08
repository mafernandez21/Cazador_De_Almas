/*
 * Este proyecto pertenece a Martín Alejandro Fernández.
 * Cualquier edición del siguiente archivo, sin autorización
 * no esta permitida.
 */

package lib;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 * Nombre: NoHumanos.<BR>
 * Descripcion: Son objetos que no pueden ser controlados por el Jugador (Humano), 
 * solo pueden ser manejados por la UCP (CPU), que ejecuta la IA definida en el metodo {@see #IA}.
 * @author Martín Alejandro Fernández
 * @version 1.0
 * @see #IA(lib.Tablero).
 * @see <a href="http://www.MartinAlejandroFernandez.com">Página Web ...</a>
 */
public class NoHumano extends Sprite implements Runnable{
    public static final int AGRESIVIDAD_NULA=0;
    public static final int AGRESIVIDAD_BAJA=1;
    public static final int AGRESIVIDAD_MEDIA=2;
    public static final int AGRESIVIDAD_ALTA=3;
    private int Especie;
    private String RutaImagenEspecie;
    private int AnchoImagen;
    private int AltoImagen;
    private int Puntos;
    private int Agresividad;
    private long RetardoIA;
    
//CONSTRUCTORES
    public NoHumano(Tablero t, int especie) {super(t);this.Inicializar(especie);}
    
    public NoHumano(Tablero t, int especie, String n) {super(t, n);this.Inicializar(especie);}

//METODOS PRIVADOS
    
    private void Inicializar(int especie){
        this.setEspecie(especie);
        switch(especie){
            case Sprite.ESPECIE_GUSANO:
                this.setRutaImagenEspecie(Sprite.IMG_ESPECIE_GUSANO);
                this.setDimensionImagen(30,30);
                this.setVitalidad(50);
                this.setPuntosDeAtaque(11);
                this.setPuntosDeDefensa(1);
                this.setPuntos(10);
                this.setRetardoIA(100);
                this.setAgresividad(NoHumano.AGRESIVIDAD_BAJA);
            break;
            case Sprite.ESPECIE_SPIDER:
                this.setRutaImagenEspecie(Sprite.IMG_ESPECIE_SPIDER);
                this.setDimensionImagen(30,30);
                this.setVitalidad(100);
                this.setPuntosDeAtaque(16);
                this.setPuntosDeDefensa(6);
                this.setPuntos(20);
                this.setRetardoIA(50);
                this.setAgresividad(NoHumano.AGRESIVIDAD_MEDIA);
            break;
            case Sprite.ESPECIE_GARGOLA:
                this.setRutaImagenEspecie(Sprite.IMG_ESPECIE_GARGOLA);
                this.setDimensionImagen(80,80);
                this.setVitalidad(150);
                this.setPuntosDeAtaque(22);
                this.setPuntosDeDefensa(12);
                this.setPuntos(40);
                this.setRetardoIA(25);
                this.setAgresividad(NoHumano.AGRESIVIDAD_ALTA);
            break;
            default:
                this.setRutaImagenEspecie(Sprite.IMG_ESPECIE_GUSANO);
                this.setDimensionImagen(30,30);
                this.setVitalidad(50);
                this.setPuntosDeAtaque(11);
                this.setPuntosDeDefensa(1);
                this.setPuntos(10);
                this.setRetardoIA(100);
                this.setAgresividad(NoHumano.AGRESIVIDAD_NULA);
            break;
        }
    }

    private void setEspecie(int e) {
        if(e!=Sprite.ESPECIE_HUMANA){this.Especie = e;}
        else{this.Especie = Sprite.ESPECIE_DESCONOCIDA;}
    }
    
    private String getRutaImagenEspecie() {return RutaImagenEspecie;}

    private void setRutaImagenEspecie(String RutaImagenEspecie) {this.RutaImagenEspecie = RutaImagenEspecie;}

    private int getAnchoImagen() {return AnchoImagen;}

    private void setAnchoImagen(int AnchoImagen) {this.AnchoImagen = AnchoImagen;}

    private int getAltoImagen() {return AltoImagen;}

    private void setAltoImagen(int AltoImagen) {this.AltoImagen = AltoImagen;}
    
    private void setDimensionImagen(int Ancho, int Alto){
        this.setAltoImagen(Alto);
        this.setAnchoImagen(Ancho);
    }
    
    private void setPuntos(int v){this.Puntos=v;}
    
    private void Animar(boolean Ataque){
        if(!Ataque){this.setFrameActual((this.getFrameActual()+1)%(Sprite.FRAME_MAX));
        }else{this.setFrameActual(Sprite.FRAME_MAX);}
        this.setAnimacionEnCola(this.getFrameActual());
    }

//METODOS PUBLICOS
    public int getEspecie() {return Especie;}

    public int getPuntos(){return this.Puntos;}

    public long getRetardoIA() {return RetardoIA;}

    public void setRetardoIA(long rIA) {this.RetardoIA = rIA;}

    public int getAgresividad() {return this.Agresividad;}

    public void setAgresividad(int agresividad) {this.Agresividad = agresividad;}
    
    public int QueEspecieHayEnLaCelda(int x, int y){
        int CodOcupante=Sprite.ESPECIE_DESCONOCIDA;
        try{
            if(this.getTablero().EsInterna(x, y)&&this.getTablero().getOcupanteCelda(x, y)!=null){
                if(this.getTablero().getCelda(x, y).getOcupante() instanceof Humano){CodOcupante=Sprite.ESPECIE_HUMANA;}
                else{
                    if(this.getTablero().getCelda(x, y).getOcupante() instanceof NoHumano){
                        NoHumano Ocupante=(NoHumano)this.getTablero().getCelda(x, y).getOcupante();
                        CodOcupante=Ocupante.getEspecie();
                    }
                }
            }
        }catch(NullPointerException npe){return Sprite.ESPECIE_DESCONOCIDA;}
        return CodOcupante;
    }
    
    public void PintarNoHumano(){
        Graphics2D g=this.getTablero().getCanvas();
        //Scrolling
        boolean EsVisible=(
                ((this.getX()>=GUI.ScrollX)&&
                (this.getX()<=(GUI.CELDAS_VISIBLES+GUI.ScrollX-1))))
                &&
                ((this.getY()>=GUI.ScrollY)&&
                (this.getY()<=(GUI.CELDAS_VISIBLES+GUI.ScrollY-1)));
        if(!EsVisible){this.LimpiarColaDeAnimacion();}
        if(this.EstaVivo()&&EsVisible&&g!=null){
            int FrameActual=this.getAnimacionDeCola();
            //Dimensiones en pantalla
            int guiAncho=(int)GUI.PPC;
            int guiAlto=(int)GUI.PPC;
            int guiX1=GUI.OffSetX+((this.getX()-GUI.ScrollX)*GUI.PPC);
            int guiY1=GUI.OffSetY+((this.getY()-GUI.ScrollY)*GUI.PPC);
            int guiX2=guiX1+guiAlto;
            int guiY2=guiY1+guiAncho;
            //Dimensiones del origen de la imagen
            String imgEspecie=this.getRutaImagenEspecie();
            int imgAncho=this.getAnchoImagen();
            int imgAlto=this.getAltoImagen();
            int imgX1=FrameActual*imgAncho;
            int imgY1=this.getDireccion()*imgAlto;
            int imgX2=imgX1+imgAncho;
            int imgY2=imgY1+imgAlto;

            Image img=new ImageIcon(getClass().getResource(imgEspecie)).getImage();
            g.drawImage(img,guiX1, guiY1, guiX2, guiY2,imgX1, imgY1 ,imgX2,imgY2, null);

            //GUI.PPC*(GUI.CELDAS_VISIBLES)
            //this.PintarEstadoSprite(g,Color.GREEN,0,100);
        }
    }

    @Override
    public boolean EsPosibleElMovimiento(int x, int y) {
        boolean Habitable=true;
        boolean Ocupada=true;
        if(this.getTablero().EsInterna(x, y)){
            if(this.getTablero().getMaterialCelda(x,y)==Celda.TIPO_PARED||
               this.getTablero().getMaterialCelda(x,y)==Celda.TIPO_DESCONOCIDO){Habitable=false;}
            if(this.getTablero().getOcupanteCelda(x, y)==null){Ocupada=false;}
        }
        return (Habitable&&!Ocupada);
    }
    
    @Override
    public void Pintar() {this.PintarNoHumano();}
    
    
    @Override
    public void SonidoAtaque() {
        //this.Audio.ReproducirSonido(Sonido.FX_NO_HUMANO_ATAQUE_1, this.getEspecie(), false);
    }

    @Override
    public void SonidoAtaqueRecibido() {
        //this.Audio.ReproducirSonido(Sonido.FX_HUMANO_ATAQUE_RECIBIDO, Sprite.ESPECIE_HUMANA, false);
    }

    @Override
    public void SonidoMuerte() {
        Sonido.FxNHMuerte1.play();
    }

    @Override
    public void SonidoDetener() {
        Sonido.FxEspada.stop();//Sonido.FX_NO_HUMANO_ATAQUE_1, this.getEspecie());
        Sonido.FxAtaqueRecibido.stop();//this.Audio.DetenerSonido(Sonido.FX_HUMANO_ATAQUE_RECIBIDO, Sprite.ESPECIE_HUMANA);
        //Sonido.this.Audio.DetenerSonido(Sonido.FX_NO_HUMANO_IA, this.getEspecie());
    }
    
        
    
    
    @Override
    public void run() {
        this.CorrerHiloSprite();
        while(this.EstaVivo()&&!this.EstaElHiloDetenido()){
            if(this.EstaElHiloPausado()){
                try {Thread.sleep(10);
                }catch (InterruptedException ex) {}
            }else{
                this.IA(this.getTablero());
            }
            try {Thread.sleep(this.getRetardoIA()*20);
            }catch (InterruptedException ex) {}
        }
        this.DetenerHiloSprite();
    }
    
    /**
     * Metodo IA, que controla el comportamiento de los NoHumanos.
     * Descripción:
     * - Intenta realizar un movimiento aleatorio, si no puede, dependiendo de la agrasividad ataca o no.
     * - Si decide atacar, según su agresividad, aumenta el valor de ataque.
     * - Si puede atacar, ataca, cambia a esa direccion y luego lo disminuye su valor de ataque al valor normal.
     * - Por ultimo, si no ataca o no puede atacar, intenta moverse nuevamente.
     * - Adicionalmente controla los frame de la animación del Sprite.
     * @author Martín Alejandro Fernández
     * @version 1.0
     * @param t Objeto de tipo Tablero, que permite la interacción con los otros objetos en el juego.
     */
    @Override
    public void IA(Tablero t) {
        int x,y;
        boolean ModoAtaque=false;
        int PlusAtaque=0;
        
        this.Animar(false);
        if(!this.MovimientoAletorio()){
            for(int i=0;i<Sprite.DIRECCIONES_POSIBLES;i++){
                x=this.getX()+Sprite.DIRECCIONES[(i%Sprite.DIRECCIONES_POSIBLES)][0];
                y=this.getY()+Sprite.DIRECCIONES[(i%Sprite.DIRECCIONES_POSIBLES)][1];
                if(this.QueEspecieHayEnLaCelda(x,y)==Sprite.ESPECIE_HUMANA){
                    switch(this.getAgresividad()){
                        case 0://Sigue moviendose.
                            ModoAtaque=true;
                            PlusAtaque=0;
                        break;
                        case 1:
                            ModoAtaque=true;
                            PlusAtaque=1;
                        break;
                        case 2:
                            ModoAtaque=true;
                            PlusAtaque=5;
                        break;
                        case 3:
                            ModoAtaque=true;
                            PlusAtaque=10;
                        break;
                    }
                    
                    if(ModoAtaque){
                        this.setPuntosDeAtaque(this.getPuntosDeAtaque()+PlusAtaque);
                        if(this.Atacar(this.getTablero().getOcupanteCelda(x, y))){
                            this.setDireccion(this.getX(),this.getY(),x, y);
                            this.Animar(true);
                        }
                        else{this.Animar(false);}
                        this.setPuntosDeAtaque(this.getPuntosDeAtaque()-PlusAtaque);
                    }else{
                        this.MovimientoAletorio();
                    }
                    
                }
                
            }
        }
    }
    
    @Override
    public void SueltaTecla(KeyEvent e) {}

    @Override
    public void PresionaTecla(KeyEvent e) {}

    @Override
    public void PresionaYSueltaTecla(KeyEvent e) {}

}
