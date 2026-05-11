import java.util.Scanner;
import java.util.Random;

public class Jogo {
    private Tabuleiro tabuleiroJogador;
    private Tabuleiro tabuleiroComputador;
    private Scanner leitor;
    private Random random;

    public Jogo() {
        tabuleiroJogador = new Tabuleiro();
        tabuleiroComputador = new Tabuleiro();
        leitor = new Scanner(System.in);
        random = new Random();
    }

    public void iniciar() {
        System.out.println("🚢 BEM-VINDO À BATALHA NAVAL!");
        
        // Configuração inicial (3 navios para cada)
        configurarNavios(tabuleiroJogador);
        configurarNavios(tabuleiroComputador);

        boolean jogoAtivo = true;
        while (jogoAtivo) {
            turnoDoJogador();
            if (!tabuleiroComputador.temNaviosVivos()) {
                System.out.println("🏆 PARABÉNS! Você afundou a frota inimiga!");
                break;
            }

            turnoDoComputador();
            if (!tabuleiroJogador.temNaviosVivos()) {
                System.out.println("💀 GAME OVER! O computador venceu.");
                break;
            }
        }
    }

    private void configurarNavios(Tabuleiro t) {
        t.posicionarNavioAleatorio(new Navio("Contratorpedeiro", 3));
        t.posicionarNavioAleatorio(new Navio("Submarino", 2));
        t.posicionarNavioAleatorio(new Navio("Porta-Aviões", 4));
    }

    private void turnoDoJogador() {
        System.out.println("\n--- SEU TURNO ---");
        System.out.println("Tabuleiro Inimigo:");
        tabuleiroComputador.exibir(true);

        int linha, coluna;
        do {
            System.out.print("Digite a linha (0-9): ");
            linha = leitor.nextInt();
            System.out.print("Digite a coluna (0-9): ");
            coluna = leitor.nextInt();
        } while (linha < 0 || linha > 9 || coluna < 0 || coluna > 9);

        if (tabuleiroComputador.receberTiro(linha, coluna)) {
            System.out.println("💥 FOGO! Você acertou um navio!");
        } else {
            System.out.println("💦 ÁGUA! Nada por aqui.");
        }
    }

    private void turnoDoComputador() {
        System.out.println("\n--- TURNO DO COMPUTADOR ---");
        int linha = random.nextInt(10);
        int coluna = random.nextInt(10);

        if (tabuleiroJogador.receberTiro(linha, coluna)) {
            System.out.printf("🔥 O computador acertou sua posição [%d, %d]!\n", linha, coluna);
        } else {
            System.out.printf("💨 O computador disparou em [%d, %d] e errou.\n", linha, coluna);
        }
        tabuleiroJogador.exibir(false);
    }
} 
    
