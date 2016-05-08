/*
 * Este proyecto pertenece a Martín Alejandro Fernández.
 * Cualquier edición del siguiente archivo, sin autorización
 * no esta permitida.
 */

package lib;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 * Descripcion ...
 * @author Martín Alejandro Fernández
 * @version 1.0
 * @see <a href="http://www.MartinAlejandroFernandez.com">Página Web Proximamente...</a>
 */
public class Sonido{
    public static final int PISTA_MENU=1;
    
    public static final int FX_HUMANO_ATAQUE_ESPADA=2;
    public static final int FX_HUMANO_ATAQUE_RECIBIDO=3;
    public static final int FX_HUMANO_IA=4;
    public static final int FX_HUMANO_MUERTE=5;
    
    public static final int FX_NO_HUMANO_ATAQUE_1=6;
    public static final int FX_NO_HUMANO_ATAQUE_2=7;
    public static final int FX_NO_HUMANO_ATAQUE_3=8;
    public static final int FX_NO_HUMANO_ATAQUE_RECIBIDO_1=9;
    public static final int FX_NO_HUMANO_ATAQUE_RECIBIDO_2=10;
    public static final int FX_NO_HUMANO_ATAQUE_RECIBIDO_3=11;
    public static final int FX_NO_HUMANO_IA=12;
    public static final int FX_NO_HUMANO_MUERTE=13;
    
    //private static final String RUTA_PISTA_MENU="/snd/PistaFondo.wav";
    
    //private static final String RUTA_FX_HUMANO_ATAQUE_ESPADA="/snd/FxHumanoAtaqueEspada.wav";
    //private static final String RUTA_FX_HUMANO_ATAQUE_RECIBIDO="/snd/FxHumanoAtaqueRecibido.wav";
    //private static final String RUTA_FX_HUMANO_IA="/snd/PistaFondo.wav";
    //private static final String RUTA_FX_HUMANO_MUERTE="/snd/FxHumanoMuerte.wav";
    
//    private static final String RUTA_FX_NO_HUMANO_ATAQUE_1="/snd/PistaFondo.wav";
//    private static final String RUTA_FX_NO_HUMANO_ATAQUE_2="/snd/PistaFondo.wav";
//    private static final String RUTA_FX_NO_HUMANO_ATAQUE_3="/snd/PistaFondo.wav";
//    private static final String RUTA_FX_NO_HUMANO_ATAQUE_RECIBIDO_1="/snd/PistaFondo.wav";
//    private static final String RUTA_FX_NO_HUMANO_ATAQUE_RECIBIDO_2="/snd/PistaFondo.wav";
//    private static final String RUTA_FX_NO_HUMANO_ATAQUE_RECIBIDO_3="/snd/PistaFondo.wav";
//    private static final String RUTA_FX_NO_HUMANO_IA_1="/snd/FxNoHumanoIA1.wav";
//    private static final String RUTA_FX_NO_HUMANO_IA_2="/snd/PistaFondo.wav";
//    private static final String RUTA_FX_NO_HUMANO_IA_3="/snd/PistaFondo.wav";
//    //private static final String RUTA_FX_NO_HUMANO_MUERTE_1="/snd/FxNoHumanoMuerte1.wav";
//    private static final String RUTA_FX_NO_HUMANO_MUERTE_2="/snd/PistaFondo.wav";
//    private static final String RUTA_FX_NO_HUMANO_MUERTE_3="/snd/PistaFondo.wav";
    
        
    public static final AudioClip PistaDeFondo= Applet.newAudioClip(Sonido.class.getResource("/snd/PistaFondo.wav"));
//    private AudioClip PistaDeMenu= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_PISTA_MENU));
    public static final  AudioClip FxEspada= Applet.newAudioClip(Sonido.class.getResource("/snd/FxHumanoAtaqueEspada.wav"));
    public static final  AudioClip FxAtaqueRecibido= Applet.newAudioClip(Sonido.class.getResource("/snd/FxHumanoAtaqueRecibido.wav"));
//    private AudioClip FxIA= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_FX_HUMANO_IA));
    public static final  AudioClip FxMuerte= Applet.newAudioClip(Sonido.class.getResource("/snd/FxHumanoMuerte.wav"));
//    
//    private AudioClip FxNHAtaque1= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_FX_NO_HUMANO_ATAQUE_1));
//    private AudioClip FxNHAtaque2= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_FX_NO_HUMANO_ATAQUE_2));
//    private AudioClip FxNHAtaque3= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_FX_NO_HUMANO_ATAQUE_3));
//    private AudioClip FxNHAtaqueR1= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_FX_NO_HUMANO_ATAQUE_RECIBIDO_1));
//    private AudioClip FxNHAtaqueR2= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_FX_NO_HUMANO_ATAQUE_RECIBIDO_2));
//    private AudioClip FxNHAtaqueR3= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_FX_NO_HUMANO_ATAQUE_RECIBIDO_3));
//    private AudioClip FxNHIA1= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_FX_NO_HUMANO_IA_1));
//    private AudioClip FxNHIA2= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_FX_NO_HUMANO_IA_2));
//    private AudioClip FxNHIA3= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_FX_NO_HUMANO_IA_3));
    public static final  AudioClip FxNHMuerte1= Applet.newAudioClip(Sonido.class.getResource("/snd/FxNoHumanoMuerte1.wav"));
    
    
//    private AudioClip FxNHMuerte2= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_FX_NO_HUMANO_MUERTE_2));
//    private AudioClip FxNHMuerte3= Applet.newAudioClip(Sonido.class.getResource(Sonido.RUTA_FX_NO_HUMANO_MUERTE_1));
    
    public static final  AudioClip FxMenuSplat= Applet.newAudioClip(Sonido.class.getResource("/snd/FxMenuSplat.wav"));
    
    public static final  AudioClip FxMenuSalir= Applet.newAudioClip(Sonido.class.getResource("/snd/FxMenuSalir.wav"));
    
    public static final  AudioClip FxMenuClock= Applet.newAudioClip(Sonido.class.getResource("/snd/FxClock.wav"));
    
    
    public Sonido(){
        
        
        
    }
    
}