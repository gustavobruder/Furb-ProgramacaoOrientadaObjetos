package moduloBA;

public interface IAtaque {
    boolean consegueRealizarAtaque();
    int getDanoAplicado();
    int getDanoAplicadoConsiderandoAmbiente();
    int getDanoConsumido();
    String getNomeAtaque();
}
