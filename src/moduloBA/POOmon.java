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
    private Estatisticas estatisticas;

    public POOmon(String nome, String historia, Ambiente ambienteOriginario) {
        this.setNome(nome);
        this.setHistoria(historia);
        this.setAmbienteOriginario(ambienteOriginario);
        this.energia = 500;
        this.estatisticas = new Estatisticas();
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
        this.estatisticas.setNomeArquivo(path);

        File arquivoEstatisticas = new File(path);
        if (arquivoEstatisticas.exists()) {
            this.estatisticas.CarregarEstatisticas();
        }
    }

    public void informarOponente(POOmonComportamento pooMonOponente) {
        this.pooMonAdversario = pooMonOponente;
        this.inicializarArquivoBatalhas();
        this.inicializarArquivoEstatisticas();
        this.estatisticas.incQtdActivacoes();

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
        this.energia += energia;
        if (energia > 0) {
            String log = new StringBuilder()
                    .append("Energia recebida: ")
                    .append(energia)
                    .append("\n")
                    .append(this.logMinhaEnergiaVital())
                    .toString();
            this.adicionarLog(log);
        }

        if (this.energia > this.estatisticas.getMaiorEnergiaVital()) {
            this.estatisticas.setMaiorEnergiaVital(this.energia);
        }
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
        return this.estatisticas.getQtdActivacoes();
    }

    @Override
    public int getVitorias() {
        return this.estatisticas.getVitorias();
    }

    @Override
    public int getMaiorEnergiaVital() {
        return this.estatisticas.getMaiorEnergiaVital();
    }

    @Override
    public LocalDateTime getMomentoMaiorEnergiaVital() {
        return this.estatisticas.getMomentoMaiorEnergiaVital();
    }

    private String getEstatisticasPath() {
        String nomeArquivo = String.format("equipe-A-%s-estatisticas", this.getNome().toLowerCase());
        return String.format("%s\\%s.ser", this.mediador.getPastaDados(), nomeArquivo);
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
        this.estatisticas.incVitorias();
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