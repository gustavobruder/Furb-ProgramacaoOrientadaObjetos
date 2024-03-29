package utils;

import moduloBA.*;
import moduloBGame.Ambiente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestesAtaques {

    @Test
    public void deveMontarAtaqueBasico() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        IAtaque ataque = new AtaqueBasico(agua, terra, Ambiente.AR);

        int danoAplicado = ataque.getDanoAplicado();
        int danoConsumido = ataque.getDanoConsumido();
        String nomeAtaque = ataque.getNomeAtaque();

        assertEquals(nomeAtaque, "Basico");
        assertEquals(danoAplicado, 30);
        assertEquals(danoConsumido, 0);
    }

    @Test
    public void deveMontarAtaqueAgressivo() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        IAtaque ataque = new AtaqueAgressivo(agua, terra, Ambiente.AR);

        int danoAplicado = ataque.getDanoAplicado();
        int danoConsumido = ataque.getDanoConsumido();
        String nomeAtaque = ataque.getNomeAtaque();

        assertEquals(nomeAtaque, "Agressivo");
        assertTrue(danoAplicado >= 40);
        assertTrue(danoAplicado <= 99);
        assertTrue(danoConsumido >= 40);
        assertTrue(danoConsumido <= 99);
        assertEquals(danoAplicado, danoConsumido);
    }

    @Test
    public void deveMontarComAtaqueCruel() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        IAtaque ataque = new AtaqueCruel(agua, terra, Ambiente.AR);

        int danoAplicado = ataque.getDanoAplicado();
        int danoConsumido = ataque.getDanoConsumido();
        String nomeAtaque = ataque.getNomeAtaque();
        int danoConsumidoAproximado = (int) (danoConsumido * 1.5);

        assertEquals(nomeAtaque, "Cruel");
        assertTrue(danoAplicado >= 150);
        assertTrue(danoAplicado <= 300);
        assertTrue(danoConsumido >= 100);
        assertTrue(danoConsumido <= 200);
        assertEquals(danoAplicado, danoConsumidoAproximado);
    }

    @Test
    public void naoDeveConseguirRealizarAtaqueAgressivoCasoConsumoForMaiorOuIgualAEnergiaVital() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        IAtaque ataque = new AtaqueAgressivo(agua, terra, Ambiente.AR);

        // 20 de energia
        agua.carregar(-480);
        boolean consegueAtacar = ataque.consegueRealizarAtaque();

        assertFalse(consegueAtacar);
    }

    @Test
    public void naoDeveConseguirRealizarAtaqueCruelCasoConsumoForMaiorOuIgualAoDobroDeEnergiaVital() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        IAtaque ataque = new AtaqueCruel(agua, terra, Ambiente.AR);

        // 200 de energia
        agua.carregar(-300);
        boolean consegueAtacar = ataque.consegueRealizarAtaque();

        assertFalse(consegueAtacar);
    }

    @Test
    public void deveMontarAtaqueBasicoComBonusCasoForEmAmbienteDeOrigem() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        IAtaque ataque = new AtaqueBasico(agua, terra, Ambiente.AGUA);

        // 30 fixo + 20% pelo ambiente = 36 dano
        int danoAplicado = ataque.getDanoAplicadoConsiderandoAmbiente();
        int danoConsumido = ataque.getDanoConsumido();

        assertEquals(danoAplicado, 36);
        assertEquals(danoConsumido, 0);
    }

    @Test
    public void deveMontarAtaqueAgressivoComBonusCasoForEmAmbienteDeOrigem() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        IAtaque ataque = new AtaqueAgressivo(agua, terra, Ambiente.AGUA);

        // 40 a 99 dano + 20% pelo ambiente = 48 a 118 dano
        int danoAplicado = ataque.getDanoAplicadoConsiderandoAmbiente();
        int danoConsumido = ataque.getDanoConsumido();

        assertTrue(danoAplicado >= 48);
        assertTrue(danoAplicado <= 118);
        assertTrue(danoConsumido >= 40);
        assertTrue(danoConsumido <= 99);
    }

    @Test
    public void deveMontarAtaqueCruelComBonusCasoForEmAmbienteDeOrigem() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        IAtaque ataque = new AtaqueCruel(agua, terra, Ambiente.AGUA);

        // 100 a 200 dano consumo
        // 150 a 300 dano aplicado + 20% pelo ambiente = 180 a 360 dano
        int danoAplicado = ataque.getDanoAplicadoConsiderandoAmbiente();
        int danoConsumido = ataque.getDanoConsumido();

        assertTrue(danoAplicado >= 180);
        assertTrue(danoAplicado <= 360);
        assertTrue(danoConsumido >= 100);
        assertTrue(danoConsumido <= 200);
    }

    /*
    @Test
    public void deveMontarAtaqueBasicoComDanoReduzidoCasoDefensorForEmAmbienteDeOrigem() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        IAtaque ataque = new AtaqueBasico(agua, terra, Ambiente.TERRA);

        // 30 fixo - 10% pelo ambiente = 27 dano
        int danoAplicado = ataque.getDanoAplicado();
        int danoConsumido = ataque.getDanoConsumido();

        assertEquals(danoAplicado, 27);
        assertEquals(danoConsumido, 0);
    }

    @Test
    public void deveMontarAtaqueAgressivoComDanoReduzidoCasoDefensorForEmAmbienteDeOrigem() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        IAtaque ataque = new AtaqueAgressivo(agua, terra, Ambiente.TERRA);

        // 40 a 99 dano - 10% pelo ambiente = 36 a 89 dano
        int danoAplicado = ataque.getDanoAplicado();
        int danoConsumido = ataque.getDanoConsumido();

        assertTrue(danoAplicado >= 36);
        assertTrue(danoAplicado <= 89);
        assertTrue(danoConsumido >= 40);
        assertTrue(danoConsumido <= 99);
    }

    @Test
    public void deveMontarAtaqueCruelComDanoReduzidoCasoDefensorForEmAmbienteDeOrigem() {
        POOmonH agua = new POOmonH();
        POOmonT terra = new POOmonT();
        IAtaque ataque = new AtaqueCruel(agua, terra, Ambiente.TERRA);

        // 150 a 300 dano - 10% pelo ambiente = 135 a 270 dano
        int danoAplicado = ataque.getDanoAplicado();
        int danoConsumido = ataque.getDanoConsumido();

        assertTrue(danoAplicado >= 135);
        assertTrue(danoAplicado <= 270);
        assertTrue(danoConsumido >= 100);
        assertTrue(danoConsumido <= 200);
    }
    */
} 