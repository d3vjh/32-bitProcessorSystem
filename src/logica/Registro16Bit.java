package logica;

import interfaces.IRegistro;

/**
 *
 * @author User
 */
public class Registro16Bit implements IRegistro{
    private int valor;

    public Registro16Bit() {
        this.valor = 0;
    }
    
    @Override
    public void setValor(int v) {
        // Asegúrese de que la entrada sea de 16 bits
        if (v > 0b1111111111111111) {
            throw new RuntimeException();
        }
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
