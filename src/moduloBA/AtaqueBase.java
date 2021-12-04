package moduloBA;

import moduloBGame.Ambiente;
import moduloBGame.POOmonComportamento;

public abstract class AtaqueBase implements IAtaque {
    protected POOmonComportamento pooMonAtacante;
    protected POOmonComportamento pooMondefensor;
    protected Ambiente ambiente;
    private String nomeAtaque;

    protected AtaqueBase(String nomeAtaque, POOmonComportamento pooMonAtacante, POOmonComportamento pooMondefensor, Ambiente ambiente) {
        this.nomeAtaque = nomeAtaque;
        this.pooMonAtacante = pooMonAtacante;
        this.pooMondefensor = pooMondefensor;
        this.ambiente = ambiente;
    }

    protected int getDanoConsiderandoAmbiente(int danoAtaque) {
        if (estaEmSeuAmbienteOrigem(this.pooMonAtacante)) {
            danoAtaque = (int) (danoAtaque * 1.2);
        }
        return danoAtaque;
    }

    private boolean estaEmSeuAmbienteOrigem(POOmonComportamento pooMon) {
        return pooMon.getAmbienteOriginario() == this.ambiente;
    }

    @Override
    public String getNomeAtaque() {
        return this.nomeAtaque;
    }
}