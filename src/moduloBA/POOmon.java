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

    private void inicializarEstatisticas() {
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
        this.inicializarEstatisticas();

        String path = getLogsBatalhasPath(this.pooMonAdversario);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {

            bw.append("Log de batalha");
            bw.newLine();
            bw.append("POOmon: ").append(this.getNome()).append(" - ").append(this.getAmbienteOriginario().name());
            bw.newLine();
            bw.newLine();
            bw.append("Oponente: ").append(this.pooMonAdversario.getNome()).append(" - ").append(this.pooMonAdversario.getAmbienteOriginario().name());
            bw.newLine();

//            LocalDateTime agora = LocalDateTime.now();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
//            bw.append("Minha energia vital: ").append(String.valueOf(this.getEnergia())).append(" – ").append(agora.format(formatter));
//            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        String path = getLogsBatalhasPath(this.pooMonAdversario);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.append("Ataque efetuado: ");
            bw.append(ataque.getNomeAtaque()).append(" ");
            bw.append(Integer.toString(ataque.getDanoAplicado())).append(" ");
            bw.append("(").append(Integer.toString(ataque.getDanoAplicadoConsiderandoAmbiente())).append(")");
            bw.append(" - ");
            bw.append(ambiente.name()).append(" ");
            bw.append("(-").append(Integer.toString(ataque.getDanoConsumido())).append(")");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.carregar(ataque.getDanoConsumido() * -1);
    }

    @Override
    public void receberAtaque(int danoAtaque, Ambiente ambiente) {
        int danoAtaqueConsiderandoAmbiente = danoAtaque;
        if (this.getAmbienteOriginario() == ambiente) {
            danoAtaqueConsiderandoAmbiente = (int) (danoAtaqueConsiderandoAmbiente * 0.9);
        }

        String path = getLogsBatalhasPath(this.pooMonAdversario);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.append("Ataque recebido: ");
            bw.append(Integer.toString(danoAtaque));
            bw.append(" – ");
            bw.append(ambiente.name()).append(" ");
            bw.append("(-").append(Integer.toString(danoAtaqueConsiderandoAmbiente)).append(")");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        danoAtaqueConsiderandoAmbiente *= -1; // transformar numero em negativo
        this.carregar(danoAtaqueConsiderandoAmbiente);
    }

    @Override
    public void carregar(int energia) {
        if (energia > 0) {
            String path = getLogsBatalhasPath(this.pooMonAdversario);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
                bw.append("Energia recebida: ");
                bw.append(Integer.toString(energia));
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        String path = getEstatisticasPath();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // consumir header do arquivo

            String linha = br.readLine();
            String[] dados = linha.split(";");
            String qtdAtivacoes = dados[0];
            return Integer.parseInt(qtdAtivacoes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getVitorias() {
        String path = getEstatisticasPath();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // consumir header do arquivo

            String linha = br.readLine();
            String[] dados = linha.split(";");
            String qtdVitorias = dados[1];
            return Integer.parseInt(qtdVitorias);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getMaiorEnergiaVital() {
        String path = getEstatisticasPath();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // consumir header do arquivo

            String linha = br.readLine();
            String[] dados = linha.split(";");
            String maiorEnergiaVital = dados[2];
            return Integer.parseInt(maiorEnergiaVital);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public LocalDateTime getMomentoMaiorEnergiaVital() {
        String path = getEstatisticasPath();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // consumir header do arquivo

            String linha = br.readLine();
            String[] dados = linha.split(";");
            String momentoMaiorEnergiaVital = dados[3];
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            return LocalDateTime.parse(momentoMaiorEnergiaVital, formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getEstatisticasPath() {
        String nomeArquivo = this.getNome().toLowerCase();
        return String.format("%s\\%s.txt", this.mediador.getPastaDados(), nomeArquivo);
    }

    private String getLogsBatalhasPath(POOmonComportamento pooMonAdversario) {
        String nomeArquivo = this.getNome().toLowerCase() + "-" + pooMonAdversario.getNome().toLowerCase();
        return String.format("%s\\%s.txt", this.mediador.getPastaLogs(), nomeArquivo);
    }

    @Override
    public boolean estaVivo() {
        return this.energia > 0;
    }

    @Override
    public void vitoria() {
        String path = getLogsBatalhasPath(this.pooMonAdversario);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.append("Vitória");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void derrota() {
        String path = getLogsBatalhasPath(this.pooMonAdversario);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.append("Derrota");
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