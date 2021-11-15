package moduloBA;

import java.util.Random;

public class POOmon {
    private String nome;
    private int energiaVital;
    private Ambiente ambienteOrigem;
    private Ambiente ambienteBatalha;

    public POOmon(String nome, Ambiente ambienteOrigem) {
        this.nome = nome;
        this.ambienteOrigem = ambienteOrigem;
        this.energiaVital = 500;
    }

    public void ataqueBasico(POOmon pooMonDefensor) {
        int danoAtaque = 30;

        if (this.estaEmSeuAmbienteOrigem()) {
            danoAtaque = (int) (danoAtaque * 1.2);
        }

        if (pooMonDefensor.estaEmSeuAmbienteOrigem()) {
            danoAtaque = (int) (danoAtaque * 0.9);
        }

        pooMonDefensor.setEnergiaVital(pooMonDefensor.getEnergiaVital() - danoAtaque);
    }

    public void ataqueAgressivo(POOmon pooMonDefensor) {
        int danoAtaque = new Random().nextInt((99 - 40) + 1) + 40;
        int danoAtaqueBonificado = danoAtaque;

        if (danoAtaque >= this.getEnergiaVital()) {
            throw new IllegalArgumentException("Dano de ataque não pode ser maior ou igual a energia vital");
        }

        if (this.estaEmSeuAmbienteOrigem()) {
            danoAtaqueBonificado = (int) (danoAtaqueBonificado * 1.2);
        }

        if (pooMonDefensor.estaEmSeuAmbienteOrigem()) {
            danoAtaqueBonificado = (int) (danoAtaqueBonificado * 0.9);
        }

        pooMonDefensor.setEnergiaVital(pooMonDefensor.getEnergiaVital() - danoAtaqueBonificado);
        this.setEnergiaVital(this.getEnergiaVital() - danoAtaque);
    }

    public void ataqueCruel(POOmon pooMonDefensor) {
        int danoAtaque = new Random().nextInt((200 - 100) + 1) + 100;
        int danoAtaqueBonificado = (int) (danoAtaque * 1.5);

        if ((danoAtaque * 2) >= this.getEnergiaVital()) {
            throw new IllegalArgumentException("Dano de ataque não pode ser maior ou igual ao dobro da energia vital");
        }

        if (this.estaEmSeuAmbienteOrigem()) {
            danoAtaqueBonificado = (int) (danoAtaqueBonificado * 1.2);
        }

        if (pooMonDefensor.estaEmSeuAmbienteOrigem()) {
            danoAtaqueBonificado = (int) (danoAtaqueBonificado * 0.9);
        }

        pooMonDefensor.setEnergiaVital(pooMonDefensor.getEnergiaVital() - danoAtaqueBonificado);
        this.setEnergiaVital(this.getEnergiaVital() - danoAtaque);
    }

    public int getEnergiaVital() {
        return energiaVital;
    }

    public void setEnergiaVital(int energiaVital) {
        this.energiaVital = energiaVital;
    }

    public void setAmbienteBatalha(Ambiente ambienteBatalha) {
        this.ambienteBatalha = ambienteBatalha;
    }

    public boolean estaEmSeuAmbienteOrigem() {
        return this.ambienteOrigem == this.ambienteBatalha;
    }
}