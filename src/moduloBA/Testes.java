/*
package moduloBA;

import moduloBGame.Ambiente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class Testes {

    // Testes antigos que podem ser utilizados de parametro

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

        // 100 a 200 dano consumo
        // 150 a 300 dano aplicado
        agua.setEnergiaVital(200); // dobro do consumo min (100)
        Executable ataque = () -> agua.ataqueCruel(terra);

        Exception exception = assertThrows(IllegalArgumentException.class, ataque);
        assertEquals(exception.getMessage(), "Dano de ataque não pode ser maior ou igual ao dobro da energia vital");
        assertEquals(terra.getEnergiaVital(), 500);
        assertEquals(agua.getEnergiaVital(), 200);
    }

    @Test
    public void deveCausarDanoComAtaqueBasicoComBonusCasoForEmAmbienteDeOrigem() {
        Ambiente ambiente = Ambiente.AGUA;
        POOmonH agua = new POOmonH("POOmon agua");
        POOmonT terra = new POOmonT("POOmon terra");
        agua.setAmbienteBatalha(ambiente);
        terra.setAmbienteBatalha(ambiente);

        // 30 fixo + 20% pelo ambiente = 36 dano
        agua.ataqueBasico(terra);

        assertEquals(terra.getEnergiaVital(), 464);
        assertEquals(agua.getEnergiaVital(), 500);
    }

    @Test
    public void deveCausarDanoComAtaqueAgressivoComBonusCasoForEmAmbienteDeOrigem() {
        Ambiente ambiente = Ambiente.AGUA;
        POOmonH agua = new POOmonH("POOmon agua");
        POOmonT terra = new POOmonT("POOmon terra");
        agua.setAmbienteBatalha(ambiente);
        terra.setAmbienteBatalha(ambiente);

        // 40 a 99 dano + 20% pelo ambiente = 48 a 118 dano
        agua.ataqueAgressivo(terra);

        assertTrue(terra.getEnergiaVital() > 381);
        assertTrue(terra.getEnergiaVital() < 453);
        assertTrue(agua.getEnergiaVital() > 400);
        assertTrue(agua.getEnergiaVital() < 461);
    }

    @Test
    public void deveCausarDanoComAtaqueCruelComBonusCasoForEmAmbienteDeOrigem() {
        Ambiente ambiente = Ambiente.AGUA;
        POOmonH agua = new POOmonH("POOmon agua");
        POOmonT terra = new POOmonT("POOmon terra");
        agua.setAmbienteBatalha(ambiente);
        terra.setAmbienteBatalha(ambiente);

        // 100 a 200 dano consumo
        // 150 a 300 dano aplicado + 20% pelo ambiente = 180 a 360 dano
        agua.ataqueCruel(terra);

        assertTrue(terra.getEnergiaVital() > 139);
        assertTrue(terra.getEnergiaVital() < 321);
        assertTrue(agua.getEnergiaVital() > 299);
        assertTrue(agua.getEnergiaVital() < 401);
    }

    @Test
    public void deveCausarDanoReduzidoComAtaqueBasicoCasoDefensorForEmAmbienteDeOrigem() {
        Ambiente ambiente = Ambiente.TERRA;
        POOmonH agua = new POOmonH("POOmon agua");
        POOmonT terra = new POOmonT("POOmon terra");
        agua.setAmbienteBatalha(ambiente);
        terra.setAmbienteBatalha(ambiente);

        // 30 fixo - 10% pelo ambiente = 27 dano
        agua.ataqueBasico(terra);

        assertEquals(terra.getEnergiaVital(), 473);
        assertEquals(agua.getEnergiaVital(), 500);
    }

    @Test
    public void deveCausarDanoReduzidoComAtaqueAgressivoCasoDefensorForEmAmbienteDeOrigem() {
        Ambiente ambiente = Ambiente.TERRA;
        POOmonH agua = new POOmonH("POOmon agua");
        POOmonT terra = new POOmonT("POOmon terra");
        agua.setAmbienteBatalha(ambiente);
        terra.setAmbienteBatalha(ambiente);

        // 40 a 99 dano - 10% pelo ambiente = 36 a 89 dano
        agua.ataqueAgressivo(terra);

        assertTrue(terra.getEnergiaVital() > 410);
        assertTrue(terra.getEnergiaVital() < 465);
        assertTrue(agua.getEnergiaVital() > 400);
        assertTrue(agua.getEnergiaVital() < 461);
    }

    @Test
    public void deveCausarDanoReduzidoComAtaqueCruelCasoDefensorForEmAmbienteDeOrigem() {
        Ambiente ambiente = Ambiente.TERRA;
        POOmonH agua = new POOmonH("POOmon agua");
        POOmonT terra = new POOmonT("POOmon terra");
        agua.setAmbienteBatalha(ambiente);
        terra.setAmbienteBatalha(ambiente);

        // 150 a 300 dano - 10% pelo ambiente = 135 a 270 dano
        agua.ataqueCruel(terra);

        assertTrue(terra.getEnergiaVital() > 229);
        assertTrue(terra.getEnergiaVital() < 366);
        assertTrue(agua.getEnergiaVital() > 299);
        assertTrue(agua.getEnergiaVital() < 401);
    }
}
*/