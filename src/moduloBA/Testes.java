package moduloBA;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void deveCausarDanoComAtaqueAgressivo() {
        POOmonH agua = new POOmonH("POOmon agua");
        POOmonT terra = new POOmonT("POOmon terra");

        // 40 a 99 dano
        agua.ataqueAgressivo(terra);

        assertTrue(terra.getEnergiaVital() > 400);
        assertTrue(terra.getEnergiaVital() < 461);
        assertTrue(agua.getEnergiaVital() > 400);
        assertTrue(agua.getEnergiaVital() < 461);
        assertEquals(agua.getEnergiaVital(), terra.getEnergiaVital());
    }

    @Test
    public void deveCausarDanoComAtaqueCruel() {
        POOmonH agua = new POOmonH("POOmon agua");
        POOmonT terra = new POOmonT("POOmon terra");

        // 100 a 200 dano
        agua.ataqueCruel(terra);

        assertTrue(terra.getEnergiaVital() > 199);
        assertTrue(terra.getEnergiaVital() < 351);
        assertTrue(agua.getEnergiaVital() > 299);
        assertTrue(agua.getEnergiaVital() < 401);
    }

    @Test
    public void naoDeveCausarDanoComAtaqueAgressivoCasoConsumoDoAtaqueForMaiorOuIgualAEnergiaVital() {
        POOmonH agua = new POOmonH("POOmon agua");
        POOmonT terra = new POOmonT("POOmon terra");

        // 40 a 99 dano
        agua.setEnergiaVital(40);
        Executable ataque = () -> agua.ataqueAgressivo(terra);

        Exception exception = assertThrows(IllegalArgumentException.class, ataque);
        assertEquals(exception.getMessage(), "Dano de ataque não pode ser maior ou igual a energia vital");
        assertEquals(terra.getEnergiaVital(), 500);
        assertEquals(agua.getEnergiaVital(), 40);
    }

    @Test
    public void naoDeveCausarDanoComAtaqueCruelCasoConsumoDoAtaqueForMaiorOuIgualAoDobroDeEnergiaVital() {
        POOmonH agua = new POOmonH("POOmon agua");
        POOmonT terra = new POOmonT("POOmon terra");

        // 100 a 200 dano
        agua.setEnergiaVital(200); // dobro do consumo min (100)
        Executable ataque = () -> agua.ataqueCruel(terra);

        Exception exception = assertThrows(IllegalArgumentException.class, ataque);
        assertEquals(exception.getMessage(), "Dano de ataque não pode ser maior ou igual ao dobro da energia vital");
        assertEquals(terra.getEnergiaVital(), 500);
        assertEquals(agua.getEnergiaVital(), 200);
    }
}