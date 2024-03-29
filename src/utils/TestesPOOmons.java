package utils;

import moduloBA.POOmonH;
import moduloBA.POOmonT;
import moduloBGame.Ambiente;
import moduloBGame.Mediador;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestesPOOmons {

    private final Mediador mediador = new MediadorCombate();

    @Test
    public void deveCausarDanoComAtaqueBasico() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        agua.setMediador(this.mediador);
        terra.setMediador(this.mediador);
        agua.informarOponente(terra);
        terra.informarOponente(agua);
        agua.carregar(-480);

        // 30 dano
        agua.atacar(Ambiente.AR);

        assertEquals(terra.getEnergia(), 470);
        assertEquals(agua.getEnergia(), 20);
    }

    @Test
    public void deveCausarDanoComAtaqueAgressivo() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        agua.setMediador(this.mediador);
        terra.setMediador(this.mediador);
        agua.informarOponente(terra);
        terra.informarOponente(agua);
        agua.carregar(-400);

        // 40 a 99 dano
        agua.atacar(Ambiente.AR);

        assertTrue(terra.getEnergia() > 400);
        assertTrue(terra.getEnergia() < 461);
    }

    @Test
    public void deveCausarDanoComAtaqueCruel() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        agua.setMediador(this.mediador);
        terra.setMediador(this.mediador);
        agua.informarOponente(terra);
        terra.informarOponente(agua);

        // 100 a 200 dano
        agua.atacar(Ambiente.AR);

        assertTrue(terra.getEnergia() > 199);
        assertTrue(terra.getEnergia() < 351);
        assertTrue(agua.getEnergia() > 299);
        assertTrue(agua.getEnergia() < 401);
    }

    @Test
    public void deveCarregarImagensDeAcordoComEstadoVital() {
        POOmonH agua = new POOmonH();

        Image imgSaudavel = agua.getImagem();
        assertNotNull(imgSaudavel);

        agua.carregar(-150);
        Image imgCansado = agua.getImagem();
        assertNotNull(imgCansado);

        agua.carregar(-200);
        Image imgExausto = agua.getImagem();
        assertNotNull(imgExausto);

        agua.carregar(-150);
        Image imgMorto = agua.getImagem();
        assertNotNull(imgMorto);
    }
} 