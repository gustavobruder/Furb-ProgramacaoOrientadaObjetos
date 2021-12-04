package utils;

import moduloBGame.Mediador;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
* Classe criada para realizar testes unitarios e integrados
* */
public class MediadorCombate implements Mediador {
    private Path pastaLogs;
    private Path pastaEstatisticas;

    public MediadorCombate() {
        String pathBase = "C:\\poo\\noturno\\equipe-A";
        File diretorioBase = new File(pathBase);
        if (!diretorioBase.exists()) {
            System.out.println("Criando diretorio base...");
            diretorioBase.mkdirs();
        }

        this.setPastaLogs(diretorioBase + "/batalhas");
        File diretorioBatalhas = new File(this.pastaLogs.toString());
        if (!diretorioBatalhas.exists()) {
            System.out.println("Criando diretorio de batalhas...");
            diretorioBatalhas.mkdir();
        }
        this.setPastaEstatisticas(diretorioBase + "/estatisticas");
        File diretorioEstatisticas = new File(this.pastaEstatisticas.toString());
        if (!diretorioEstatisticas.exists()) {
            System.out.println("Criando diretorio de estatisticas...");
            diretorioEstatisticas.mkdir();
        }
    }

    @Override
    public Path getPastaLogs() {
        return this.pastaLogs;
    }

    @Override
    public Path getPastaDados() {
        return this.pastaEstatisticas;
    }

    private void setPastaLogs(String pastaLogs) {
        if (pastaLogs == null || pastaLogs.isEmpty() || pastaLogs.isBlank()) {
            throw new IllegalArgumentException("Diretório de logs de batalha inválido");
        }
        this.pastaLogs = Paths.get(pastaLogs);
    }

    private void setPastaEstatisticas(String pastaEstatisticas) {
        if (pastaEstatisticas == null || pastaEstatisticas.isEmpty() || pastaEstatisticas.isBlank()) {
            throw new IllegalArgumentException("Diretório de estatísticas inválido");
        }
        this.pastaEstatisticas = Paths.get(pastaEstatisticas);
    }
}