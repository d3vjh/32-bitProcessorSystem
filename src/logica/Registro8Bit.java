package logica;

import interfaces.IRegistro;

public class Registro8Bit implements IRegistro {

    private int valor;

    public Registro8Bit() {
        this.valor = 0;
    }

    @Override
    public void setValor(int v) {
        // Asegúrese de que la entrada sea de 8 bits
        if (v > 0b11111111) {
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
