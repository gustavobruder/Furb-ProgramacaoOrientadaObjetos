package moduloBA;

public interface IAtaque {
    int getDanoAplicado() throws AtaqueInvalidoException;
    int getDanoConsumido();
}
