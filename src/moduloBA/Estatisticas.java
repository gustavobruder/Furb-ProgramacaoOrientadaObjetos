package moduloBA;

import java.io.*;
import java.time.LocalDateTime;

public class Estatisticas implements Serializable {
    private transient String nomeArquivo;
    private int qtdActivacoes;
    private int vitorias;
    private int maiorEnergiaVital;
    private LocalDateTime momentoMaiorEnergiaVital;

    public Estatisticas() {
        this.qtdActivacoes = 0;
        this.vitorias = 0;
        this.maiorEnergiaVital = 500;
        this.momentoMaiorEnergiaVital = LocalDateTime.now();
    }

    private void SalvarEstatisticas() {
        try
        {
            FileOutputStream fos = new FileOutputStream(this.nomeArquivo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this);

            oos.close();
            fos.close();
        }
        catch(IOException ex)
        {
            System.out.println("IOException: " + ex.getMessage());
        }
    }

    public void CarregarEstatisticas() {
        try
        {
            FileInputStream fis = new FileInputStream(this.nomeArquivo);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Estatisticas estatisticasSalvas = null;
            estatisticasSalvas = (Estatisticas) ois.readObject();

            ois.close();
            fis.close();

            this.qtdActivacoes = estatisticasSalvas.getQtdActivacoes();
            this.vitorias = estatisticasSalvas.getVitorias();
            this.maiorEnergiaVital = estatisticasSalvas.getMaiorEnergiaVital();
            this.momentoMaiorEnergiaVital = estatisticasSalvas.getMomentoMaiorEnergiaVital();
        }
        catch(IOException ex)
        {
            System.out.println("IOException: " + ex.getMessage());
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException: " + ex.getMessage());
        }
    }

    public void setNomeArquivo(String path) {
        this.nomeArquivo = path;
    }

    public int getQtdActivacoes() {
        return qtdActivacoes;
    }

    public int getVitorias() {
        return vitorias;
    }

    public int getMaiorEnergiaVital() {
        return maiorEnergiaVital;
    }

    public LocalDateTime getMomentoMaiorEnergiaVital() {
        return momentoMaiorEnergiaVital;
    }

    public void incQtdActivacoes() {
        this.qtdActivacoes++;
        this.SalvarEstatisticas();
    }

    public void incVitorias() {
        this.vitorias++;
        this.SalvarEstatisticas();
    }

    public void setMaiorEnergiaVital(int maiorEnergiaVital) {
        this.maiorEnergiaVital = maiorEnergiaVital;
        this.momentoMaiorEnergiaVital = LocalDateTime.now();
        this.SalvarEstatisticas();
    }
}