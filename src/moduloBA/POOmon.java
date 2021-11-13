package moduloBA;

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

    public int getEnergiaVital() {
        return energiaVital;
    }

    public void setEnergiaVital(int energiaVital) {
        this.energiaVital = energiaVital;
    }
}