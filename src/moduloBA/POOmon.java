package moduloBA;

import java.util.Random;

public class POOmon {
    private String nome;
    private int energiaVital;

    public POOmon(String nome) {
        this.nome = nome;
        this.energiaVital = 500;
    }

    public void ataqueBasico(POOmon pooMonDefensor) {
        int danoAtaque = 30;
        pooMonDefensor.setEnergiaVital(pooMonDefensor.getEnergiaVital() - danoAtaque);
    }

    public void ataqueAgressivo(POOmon pooMonDefensor) {
        int danoAtaque = new Random().nextInt((99 - 40) + 1) + 40;

        if (danoAtaque >= this.getEnergiaVital()) {
            throw new IllegalArgumentException("Dano de ataque não pode ser maior ou igual a energia vital");
        }

        pooMonDefensor.setEnergiaVital(pooMonDefensor.getEnergiaVital() - danoAtaque);
        this.setEnergiaVital(this.getEnergiaVital() - danoAtaque);
    }

    public void ataqueCruel(POOmon pooMonDefensor) {
        int danoAtaque = new Random().nextInt((200 - 100) + 1) + 100;
        int danoAtaqueBonificado = (int) (danoAtaque * 1.5);

        if ((danoAtaque * 2) >= this.getEnergiaVital()) {
            throw new IllegalArgumentException("Dano de ataque não pode ser maior ou igual ao dobro da energia vital");
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
}