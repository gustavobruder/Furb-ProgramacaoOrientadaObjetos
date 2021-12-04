package moduloBA;

import moduloBGame.Ambiente;
import moduloBGame.POOmonComportamento;

public class AtaqueBasico extends AtaqueBase {
    private final int danoBaseAtaque;

    public AtaqueBasico(POOmonComportamento pooMonAtacante, POOmonComportamento pooMondefensor, Ambiente ambiente) {
        super("Basico", pooMonAtacante, pooMondefensor, ambiente);
        this.danoBaseAtaque = 30;
    }

    @Override
    public boolean consegueRealizarAtaque() {
        return true;
    }

    @Override
    public int getDanoAplicado() {
        return this.danoBaseAtaque;
    }

    @Override
    public int getDanoAplicadoConsiderandoAmbiente() {
        return getDanoConsiderandoAmbiente(this.danoBaseAtaque);
    }

    @Override
    public int getDanoConsumido() {
        return 0;
    }
}