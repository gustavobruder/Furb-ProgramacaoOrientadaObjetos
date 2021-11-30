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
    public int getDanoAplicado() throws AtaqueInvalidoException {
        if ((this.danoBaseAtaque * 2) >= this.pooMonAtacante.getEnergia()) {
            throw new AtaqueInvalidoException("Dano de ataque não pode ser maior ou igual ao dobro da energia vital");
        }
        int danoAtaque = (int) (this.danoBaseAtaque * 1.5);
        return getDanoConsiderandoAmbiente(danoAtaque);
    }

    @Override
    public int getDanoConsumido() {
        return this.danoBaseAtaque;
    }
}