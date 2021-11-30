package moduloBA;

import moduloBGame.Ambiente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestesPOOmons {

    /*
    Os testes foram escritos conforme o desenvolvimento das funcionalidades
    Rodar os testes da forma que estão não trará um resultado bem-sucedido
    */

    @Test
    public void deveCausarDanoComAtaqueBasico() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();

        // 30 dano
        agua.atacar(terra, Ambiente.AR);

        assertEquals(terra.getEnergia(), 470);
        assertEquals(agua.getEnergia(), 500);
    }

    @Test
    public void deveCausarDanoComAtaqueBasicoComBonusCasoForEmAmbienteDeOrigem() {
        Ambiente ambiente = Ambiente.AGUA;
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();

        // 30 fixo + 20% pelo ambiente = 36 dano
        agua.atacar(terra, ambiente);

        assertEquals(terra.getEnergia(), 464);
        assertEquals(agua.getEnergia(), 500);
    }

    @Test
    public void deveCausarDanoReduzidoComAtaqueBasicoCasoDefensorForEmAmbienteDeOrigem() {
        Ambiente ambiente = Ambiente.TERRA;
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();

        // 30 fixo - 10% pelo ambiente = 27 dano
        agua.atacar(terra, ambiente);

        assertEquals(terra.getEnergia(), 473);
        assertEquals(agua.getEnergia(), 500);
    }

    @Test
    public void deveCausarDanoComAtaqueAgressivo() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();

        // 40 a 99 dano
        agua.atacar(terra, Ambiente.AR);

        assertTrue(terra.getEnergia() > 400);
        assertTrue(terra.getEnergia() < 461);
        assertTrue(agua.getEnergia() > 400);
        assertTrue(agua.getEnergia() < 461);
        assertEquals(agua.getEnergia(), terra.getEnergia());
    }

    @Test
    public void deveCausarDanoComAtaqueCruel() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();

        // 100 a 200 dano
        agua.atacar(terra, Ambiente.AR);

        assertTrue(terra.getEnergia() > 199);
        assertTrue(terra.getEnergia() < 351);
        assertTrue(agua.getEnergia() > 299);
        assertTrue(agua.getEnergia() < 401);
    }
} 