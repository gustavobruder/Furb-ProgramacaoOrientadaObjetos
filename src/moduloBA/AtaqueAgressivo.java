package moduloBA;

import moduloBGame.Ambiente;
import moduloBGame.POOmonComportamento;

import java.util.Random;

public class AtaqueAgressivo extends AtaqueBase {
    private final int danoBaseAtaque;

    public AtaqueAgressivo(POOmonComportamento pooMonAtacante, POOmonComportamento pooMondefensor, Ambiente ambiente) {
        super(pooMonAtacante, pooMondefensor, ambiente);
        this.danoBaseAtaque = new Random().nextInt((99 - 40) + 1) + 40;
    }

    @Override
    public int getDanoAplicado() throws AtaqueInvalidoException {
        if (this.danoBaseAtaque >= pooMonAtacante.getEnergia()) {
            throw new AtaqueInvalidoException("Dano de ataque não pode ser maior ou igual a energia vital");
        }
        return getDanoConsiderandoAmbiente(this.danoBaseAtaque);
    }

    @Override
    public int getDanoConsumido() {
        return this.danoBaseAtaque;
    }
}