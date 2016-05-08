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
public class Cronometro extends Thread{
    private long TiempoActual;
    private boolean Detenido;
    private boolean Pausado;
    
    public void Detener(){this.Detenido=true;}
    public void Pausar(){this.Pausado=true;}
    public void Correr(){this.Pausado=false;}
    public void Iniciar(){this.start();}
    public void Reiniciar(){
        this.Detenido=false;
        this.Pausado=false;
        this.TiempoActual=0;
    }
    public long getTiempoActual(){return this.TiempoActual;}
    
    @Override
    public void run() {   
        this.setName("CronometroMAF");
        this.Detenido=false;
        this.Pausado=false;
        this.TiempoActual=0;
        
        while (!this.Detenido) {
            try{Thread.sleep(1000);
            }catch(Exception e){}
            if(!this.Pausado){TiempoActual++;}
        }
    }
    
    public static void main (String args[]){
        
        Cronometro MiCronometro=new Cronometro();
        
        MiCronometro.Iniciar();
        System.out.println("CurTime="+MiCronometro.TiempoActual);
        
        while (!MiCronometro.Detenido) {
            if(MiCronometro.TiempoActual==30){MiCronometro.Detener();}
            System.out.println("CurTime="+MiCronometro.TiempoActual);
        }
    }
}
