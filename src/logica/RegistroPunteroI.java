package logica;

import interfaces.IRegistro;

/**
 *
 * @author User
 */
public class RegistroPunteroI implements IRegistro{

    private int valor;

    public RegistroPunteroI() {
        this.valor = 0;
    }
    
    
    @Override
    public void setValor(int v) {
        this.valor = v;
    }

    @Override
    public int getValor() {
        return this.valor;
    }

    @Override
    public void clear() {
        this.valor = 0;
    }
    
}
