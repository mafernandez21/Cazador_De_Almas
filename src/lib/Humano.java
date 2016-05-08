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
 * Descripcion ...
 * @author Martín Alejandro Fernández
 * @version 1.0
 * @see <a href="http://www.MartinAlejandroFernandez.com">Página Web Proximamente...</a>
 */
public class Humano extends Sprite implements Runnable{
    private boolean Llave;
//CONSTRUCTORES
    public Humano(Tablero t) {super(t);this.Inicializar();}
    
    public Humano(Tablero t, String n) {super(t, n);this.Inicializar();}
//METODOS PRIVADOS
    private void Inicializar(){
        this.setVitalidad(200);
        this.setPuntosDeAtaque(25);
        this.setPuntosDeDefensa(10);
        this.setLlave(false);
    }
        
    private void PintarHumano(){
        Graphics2D g=this.getTablero().getCanvas();
        //Scrolling
//        boolean EsVisible=(
//                ((this.getX()>=GUI.ScrollX)&&
//                (this.getX()<=(GUI.CELDAS_VISIBLES+GUI.ScrollX))))
//                &&
//                ((this.getY()>=GUI.ScrollY)&&
//                (this.getY()<=(GUI.CELDAS_VISIBLES+GUI.ScrollY)));
        if(this.EstaVivo()&&g!=null){
            int FrameActual=this.getAnimacionDeCola();
            //Scrolling
            if(this.getX()>=(GUI.CELDAS_VISIBLES+GUI.ScrollX-1)){this.getTablero().ScrollDerecha();}
            if(this.getX()<=GUI.ScrollX){this.getTablero().ScrollIzquierda();}
            if(this.getY()>=(GUI.CELDAS_VISIBLES+GUI.ScrollY-1)){this.getTablero().ScrollAbajo();}
            if(this.getY()<=GUI.ScrollY){this.getTablero().ScrollArriba();}

            //Dimensiones en pantalla
            int guiAncho=(int)GUI.PPC;
            int guiAlto=(int)GUI.PPC;
            int guiX1=GUI.OffSetX+((this.getX()-GUI.ScrollX)*GUI.PPC);
            int guiY1=GUI.OffSetY+((this.getY()-GUI.ScrollY)*GUI.PPC);
            int guiX2=guiX1+guiAlto;
            int guiY2=guiY1+guiAncho;
            //Dimensiones del origen de la imagen
            int imgAlto=30;
            int imgAncho=30;
            int imgX1=FrameActual*imgAncho;
            int imgY1=this.getDireccion()*imgAlto;
            int imgX2=imgX1+imgAncho;
            int imgY2=imgY1+imgAlto;

            Image img=new ImageIcon(getClass().getResource(Sprite.IMG_ESPECIE_HUMANO)).getImage();
            g.drawImage(img,guiX1, guiY1, guiX2, guiY2,imgX1, imgY1 ,imgX2,imgY2, null);

            //this.PintarEstadoSprite(g,Color.BLUE,GUI.PPC*(GUI.CELDAS_VISIBLES),100);
        }
    }
    
    private void TomarTeclado(KeyEvent e){
        if(this.EstaVivo()&&this.EstaElHiloCorriendo()){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    this.Animar(false);
                    if(this.EsPosibleElMovimiento(this.getX()-1, this.getY())){this.MoverIzquierda();}
                break;
                case KeyEvent.VK_RIGHT:
                    this.Animar(false);
                    if(this.EsPosibleElMovimiento(this.getX()+1, this.getY())){this.MoverDerecha();}
                break;
                case KeyEvent.VK_UP:
                    this.Animar(false);
                    if(this.EsPosibleElMovimiento(this.getX(), this.getY()-1)){this.MoverArriba();}
                break;
                case KeyEvent.VK_DOWN:
                    this.Animar(false);
                    if(this.EsPosibleElMovimiento(this.getX(), this.getY()+1)){this.MoverAbajo();}
                break;
                case KeyEvent.VK_SPACE:
                    this.Animar(true);
                    switch(this.getDireccion()){
                        case Sprite.DIRECCION_DERECHA:
                            this.AtacarDerecha();
                        break;
                        case Sprite.DIRECCION_IZQUIERDA:
                            this.AtacarIzquierda();
                        break;
                        case Sprite.DIRECCION_ABAJO:
                            this.AtacarAbajo();
                        break;
                        case Sprite.DIRECCION_ARRIBA:
                            this.AtacarArriba();
                        break;
                    }
                    this.Animar(false);
                break;
            }
        }else{this.Morir();}
    }
        

//METODOS PUBLICOS
    public boolean TieneLaLlave() {return Llave;}

    public void setLlave(boolean llave) {this.Llave = llave;}

    public boolean EstaEnUnaSalida(){return (this.getTablero().getMaterialCelda(this.getX(),this.getY())==Celda.TIPO_SALIDA);}    
    
    public void Animar(boolean Ataque){
        if(!Ataque){this.setFrameActual((this.getFrameActual()+1)%(Sprite.FRAME_MAX));
        }else{this.setFrameActual(Sprite.FRAME_MAX);}
        this.setAnimacionEnCola(this.getFrameActual());
    }
    
    @Override
    public boolean EsPosibleElMovimiento(int x, int y) {
        boolean Habitable=true;
        boolean Ocupada=true;
        this.setDireccion(this.getX(), this.getY(), x, y);
        if(this.getTablero().EsInterna(x, y)){
            if(this.getTablero().getMaterialCelda(x,y)==Celda.TIPO_PARED||
               this.getTablero().getMaterialCelda(x,y)==Celda.TIPO_DESCONOCIDO){Habitable=false;}
            if(this.getTablero().getOcupanteCelda(x, y)==null){Ocupada=false;}
        }
        return (Habitable&&!Ocupada);
    }
    
    @Override
    public void IA(Tablero tablero) {}

    @Override
    public void Pintar(){this.PintarHumano();}

    @Override
    public void SueltaTecla(KeyEvent e) {}//this.TomarTeclado(e);}

    @Override
    public void PresionaTecla(KeyEvent e) {this.TomarTeclado(e);}

    @Override
    public void PresionaYSueltaTecla(KeyEvent e) {}

    @Override
    public void SonidoAtaque() {
        Sonido.FxEspada.play();//this.Audio.ReproducirSonido(Sonido.FX_HUMANO_ATAQUE_ESPADA, Sprite.ESPECIE_HUMANA, false);
    }

    @Override
    public void SonidoAtaqueRecibido() {
        Sonido.FxAtaqueRecibido.play();//this.Audio.ReproducirSonido(Sonido.FX_HUMANO_ATAQUE_RECIBIDO, Sprite.ESPECIE_HUMANA, false);
    }

    @Override
    public void SonidoMuerte() {
        Sonido.FxMuerte.play();//this.Audio.ReproducirSonido(Sonido.FX_HUMANO_MUERTE, Sprite.ESPECIE_HUMANA, false);
    }

    @Override
    public void SonidoDetener() {
        
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
            try {Thread.sleep(10);
            }catch (InterruptedException ex) {}
        }
        this.DetenerHiloSprite();
    }
}
