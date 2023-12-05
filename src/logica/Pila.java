package logica;

/**
 *
 * @author User
 */
public class Pila {
    private int capacidadPila;
    private int[] data;
    private RegistroPunteroI punteroInicio; //guarda referecia direccion en memoria de primera posicion.
    private RegistroPunteroF punteroFinal; //guarda referecia direccion en memoria de primera posicion.
    
    public Pila() {
        capacidadPila = 0x400; // Es 1 KiB
        this.data = new int[capacidadPila];
        this.punteroInicio.setValor((int) 0x400);
        this.punteroFinal.setValor((int) 0x400);
    }
    
    public int getValorPila(){
        return this.data[punteroInicio.getValor()];
    }
    public void siguientePosicionPila(){
        //si no esta vacio o si esta por detras del puntero final
        if(this.data[punteroInicio.getValor() - 1] != 0  || punteroInicio.getValor() <= punteroFinal.getValor()){ 
            punteroInicio.setValor( (int) (punteroInicio.getValor() - 1));
        }
    }
    public void addElementInPila(int val){
        if(punteroFinal.getValor() != 0){
            punteroFinal.setValor( (int) (punteroFinal.getValor() - 1) );
            this.data[punteroFinal.getValor()] = val;
        }else if (data[0] == 0){
            this.data[0] = val;
        }
    }
    public void removeElementInPila(){
        if(this.data[punteroFinal.getValor()] != 0 && this.data[punteroFinal.getValor() - 1 ] == 0){
            this.data[punteroFinal.getValor()]=0;
        }else if(this.data[punteroFinal.getValor()] != 0 && this.data[punteroFinal.getValor() +1 ] != 0){
            this.data[punteroFinal.getValor()]=0;
            punteroFinal.setValor((int)(punteroFinal.getValor()-1));
        }
    }

    public int getCapacidadPila() {
        return capacidadPila;
    }

    public void setCapacidadPila(int capacidadPila) {
        this.capacidadPila = capacidadPila;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public RegistroPunteroI getPunteroInicio() {
        return punteroInicio;
    }

    public void setPunteroInicio(RegistroPunteroI punteroInicio) {
        this.punteroInicio = punteroInicio;
    }

    public RegistroPunteroF getPunteroFinal() {
        return punteroFinal;
    }

    public void setPunteroFinal(RegistroPunteroF punteroFinal) {
        this.punteroFinal = punteroFinal;
    }
    
}
