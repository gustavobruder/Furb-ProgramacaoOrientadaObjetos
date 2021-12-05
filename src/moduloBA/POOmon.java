package moduloBA;

import moduloBGame.Ambiente;
import moduloBGame.Mediador;
import moduloBGame.POOmonComportamento;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class POOmon implements POOmonComportamento {
    private String nome;
    private String historia;
    private Ambiente ambienteOriginario;
    private int energia;
    private Mediador mediador;
    private POOmonComportamento pooMonAdversario;

    public POOmon(String nome, String historia, Ambiente ambienteOriginario) {
        this.setNome(nome);
        this.setHistoria(historia);
        this.setAmbienteOriginario(ambienteOriginario);
        this.energia = 500;
    }

    private void inicializarArquivoBatalhas() {
        String path = getLogsBatalhasPath();
        File arquivoBatalhas = new File(path);
        if (arquivoBatalhas.exists()) {
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            bw.append("Log de batalha");
            bw.newLine();
            bw.append("POOmon: ").append(this.getNome()).append(" - ").append(this.getAmbienteOriginario().name());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inicializarArquivoEstatisticas() {
        String path = getEstatisticasPath();
        File arquivoEstatisticas = new File(path);
        if (arquivoEstatisticas.exists()) {
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            String linhaCabecalho = String.join(
                    ";",
                    "qtd_activacoes",
                    "qtd_vitorias",
                    "maior_energia_vital",
                    "momento_maior_energia_vital"
            );
            bw.append(linhaCabecalho);
            bw.newLine();
            String linhaDados = String.join(
                    ";",
                    "0",
                    "0",
                    Integer.toString(this.getEnergia()),
                    LocalDateTime.now().toString()
            );
            bw.append(linhaDados);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void informarOponente(POOmonComportamento pooMonOponente) {
        this.pooMonAdversario = pooMonOponente;
        this.inicializarArquivoBatalhas();
        this.inicializarArquivoEstatisticas();

        String log = new StringBuilder()
            .append("\n")
            .append("Oponente: ").append(this.pooMonAdversario.getNome())
            .append(" - ")
            .append(this.pooMonAdversario.getAmbienteOriginario().name())
            .append("\n")
            .append(this.logMinhaEnergiaVital())
            .toString();
        this.adicionarLog(log);
    }

    private String logMinhaEnergiaVital() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        return new StringBuilder()
            .append("Minha energia vital: ")
            .append(this.getEnergia())
            .append(" – ")
            .append(agora.format(formatter))
            .toString();
    }

    @Override
    public void atacar(Ambiente ambiente) {
        IAtaque ataque;
        ataque = new AtaqueCruel(this, this.pooMonAdversario, ambiente);

        if (!ataque.consegueRealizarAtaque()) {
            ataque = new AtaqueAgressivo(this, this.pooMonAdversario, ambiente);
        }
        if (!ataque.consegueRealizarAtaque()) {
            ataque = new AtaqueBasico(this, this.pooMonAdversario, ambiente);
        }

        this.pooMonAdversario.receberAtaque(ataque.getDanoAplicadoConsiderandoAmbiente(), ambiente);
        this.aplicarDanoConsumido(ataque, ambiente);
    }

    private void aplicarDanoConsumido(IAtaque ataque, Ambiente ambiente) {
        String log = new StringBuilder()
            .append("Ataque efetuado: ")
            .append(ataque.getNomeAtaque()).append(" ")
            .append(ataque.getDanoAplicado()).append(" ")
            .append("(").append(ataque.getDanoAplicadoConsiderandoAmbiente()).append(")")
            .append(" - ")
            .append(ambiente.name()).append(" ")
            .append("(-").append(ataque.getDanoConsumido()).append(")")
            .toString();
        this.adicionarLog(log);

        this.carregar(ataque.getDanoConsumido() * -1);
    }

    @Override
    public void receberAtaque(int danoAtaque, Ambiente ambiente) {
        int danoAtaqueConsiderandoAmbiente = danoAtaque;
        if (this.getAmbienteOriginario() == ambiente) {
            danoAtaqueConsiderandoAmbiente = (int) (danoAtaqueConsiderandoAmbiente * 0.9);
        }

        String log = new StringBuilder()
            .append("Ataque recebido: ")
            .append(danoAtaque)
            .append(" – ")
            .append(ambiente.name()).append(" ")
            .append("(-").append(danoAtaqueConsiderandoAmbiente).append(")")
            .toString();
        this.adicionarLog(log);

        danoAtaqueConsiderandoAmbiente *= -1; // transformar numero em negativo
        this.carregar(danoAtaqueConsiderandoAmbiente);
    }

    @Override
    public void carregar(int energia) {
        if (energia > 0) {
            this.adicionarLog("Energia recebida: " + energia);
        }
        this.energia += energia;
    }

    @Override
    public Image getImagem() {
        String estadoVital;
        if (this.getEnergia() > 350) {
            estadoVital = "saudavel";
        } else if (this.getEnergia() >= 151) {
            estadoVital = "cansado";
        } else if (this.getEnergia() >= 1) {
            estadoVital = "exausto";
        } else {
            estadoVital = "morto";
        }

        String ambientePOOmon = this.ambienteOriginario.name().toLowerCase();
        String imagemPath = String.format("imagens/%s-%s.png", ambientePOOmon, estadoVital);

        Image image = null;
        try {
            image = ImageIO.read(getClass().getResource(imagemPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public int getQtdActivacoes() {
        String qtdAtivacoes = getEstatistica(0);
        if (qtdAtivacoes == null) {
            return 0;
        }
        return Integer.parseInt(qtdAtivacoes);
    }

    @Override
    public int getVitorias() {
        String qtdVitorias = getEstatistica(1);
        if (qtdVitorias == null) {
            return 0;
        }
        return Integer.parseInt(qtdVitorias);
    }

    @Override
    public int getMaiorEnergiaVital() {
        String maiorEnergiaVital = getEstatistica(2);
        if (maiorEnergiaVital == null) {
            return 0;
        }
        return Integer.parseInt(maiorEnergiaVital);
    }

    @Override
    public LocalDateTime getMomentoMaiorEnergiaVital() {
        String momentoMaiorEnergiaVital = getEstatistica(3);
        if (momentoMaiorEnergiaVital == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(momentoMaiorEnergiaVital, formatter);
    }

    private String getEstatistica(int index) {
        String path = getEstatisticasPath();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // consumir header do arquivo

            String linha = br.readLine();
            String[] dados = linha.split(";");
            return dados[index];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getEstatisticasPath() {
        String nomeArquivo = String.format("equipe-A-%s-estatisticas", this.getNome().toLowerCase());
        return String.format("%s\\%s.txt", this.mediador.getPastaDados(), nomeArquivo);
    }

    private String getLogsBatalhasPath() {
        String nomeArquivo = String.format("equipe-A-%s-batalhas", this.getNome().toLowerCase());
        return String.format("%s\\%s.txt", this.mediador.getPastaLogs(), nomeArquivo);
    }

    @Override
    public boolean estaVivo() {
        return this.energia > 0;
    }

    @Override
    public void vitoria() {
        this.adicionarLog("Vitória");
    }

    @Override
    public void derrota() {
        this.adicionarLog("Derrota");
    }

    private void adicionarLog(String log) {
        String path = getLogsBatalhasPath();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.append(log);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** getters */
    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public String getHistoria() {
        return this.historia;
    }

    @Override
    public Ambiente getAmbienteOriginario() {
        return this.ambienteOriginario;
    }

    @Override
    public int getEnergia() {
        return this.energia;
    }

    /** setters */
    private void setNome(String nome) {
        if (nome.isEmpty() || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido!");
        }
        this.nome = nome;
    }

    private void setHistoria(String historia) {
        if (historia.isEmpty() || historia.isBlank()) {
            throw new IllegalArgumentException("História inválida!");
        }
        this.historia = historia;
    }

    private void setAmbienteOriginario(Ambiente ambienteOriginario) {
        this.ambienteOriginario = ambienteOriginario;
    }

    @Override
    public void setMediador(Mediador mediador) {
        if (mediador == null) {
            throw new IllegalArgumentException("Mediador da batalha não pode ser nulo");
        }
        this.mediador = mediador;
    }
}