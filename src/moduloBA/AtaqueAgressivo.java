package moduloBA;

import moduloBGame.Ambiente;
import moduloBGame.POOmonComportamento;

import java.util.Random;

public class AtaqueAgressivo extends AtaqueBase {
    private final int danoBaseAtaque;

    public AtaqueAgressivo(POOmonComportamento pooMonAtacante, POOmonComportamento pooMondefensor, Ambiente ambiente) {
        super("Agressivo", pooMonAtacante, pooMondefensor, ambiente);
        this.danoBaseAtaque = new Random().nextInt((99 - 40) + 1) + 40;
    }

    @Override
    public boolean consegueRealizarAtaque() {
        return this.danoBaseAtaque < this.pooMonAtacante.getEnergia();
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
        return this.danoBaseAtaque;
    }
}