package moduloBA;

import moduloBGame.Ambiente;
import moduloBGame.POOmonComportamento;

import java.util.Random;

public class AtaqueCruel extends AtaqueBase {
    private final int danoBaseAtaque;

    public AtaqueCruel(POOmonComportamento pooMonAtacante, POOmonComportamento pooMondefensor, Ambiente ambiente) {
        super("Cruel", pooMonAtacante, pooMondefensor, ambiente);
        this.danoBaseAtaque = new Random().nextInt((200 - 100) + 1) + 100;
    }

    @Override
    public boolean consegueRealizarAtaque() {
        return (this.danoBaseAtaque * 2) < this.pooMonAtacante.getEnergia();
    }

    @Override
    public int getDanoAplicado() {
        return (int) (this.danoBaseAtaque * 1.5);
    }

    @Override
    public int getDanoAplicadoConsiderandoAmbiente() {
        return getDanoConsiderandoAmbiente(this.getDanoAplicado());
    }

    @Override
    public int getDanoConsumido() {
        return this.danoBaseAtaque;
    }
}