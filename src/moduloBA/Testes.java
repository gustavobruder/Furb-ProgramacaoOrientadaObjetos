package moduloBA;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Testes {

    @Test
    public void deveCausarDanoComAtaqueBasico() {
        POOmonH agua = new POOmonH("POOmon agua");
        POOmonT terra = new POOmonT("POOmon terra");

        // 30 dano
        agua.ataqueBasico(terra);

        assertEquals(terra.getEnergiaVital(), 470);
        assertEquals(agua.getEnergiaVital(), 500);
    }
}