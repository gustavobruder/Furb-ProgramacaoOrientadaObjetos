package moduloBA;

import moduloBGame.Ambiente;
import moduloBGame.POOmonComportamento;

public abstract class AtaqueBase implements IAtaque {
    protected POOmonComportamento pooMonAtacante;
    protected POOmonComportamento pooMondefensor;
    protected Ambiente ambiente;

    protected AtaqueBase(POOmonComportamento pooMonAtacante, POOmonComportamento pooMondefensor, Ambiente ambiente) {
        this.pooMonAtacante = pooMonAtacante;
        this.pooMondefensor = pooMondefensor;
        this.ambiente = ambiente;
    }

    protected int getDanoConsiderandoAmbiente(int danoAtaque) {
        if (estaEmSeuAmbienteOrigem(this.pooMonAtacante)) {
            danoAtaque = (int) (danoAtaque * 1.2);
        }
        if (estaEmSeuAmbienteOrigem(this.pooMondefensor)) {
            danoAtaque = (int) (danoAtaque * 0.9);
        }
        return danoAtaque;
    }

    protected boolean estaEmSeuAmbienteOrigem(POOmonComportamento pooMon) {
        return pooMon.getAmbienteOriginario() == this.ambiente;
    }
} 