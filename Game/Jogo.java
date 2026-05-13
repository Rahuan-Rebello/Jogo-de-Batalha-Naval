import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Jogo implements Serializable {
    private Jogador humano;
    private Jogador bot;
    private Tabuleiro tabBot;
    private Tabuleiro tabHumano;
    
    public Jogo(String nomeJogador) {
        this.humano = new Jogador(nomeJogador);
        this.bot = new Jogador("Computador (Bot)");
        this.tabBot = new Tabuleiro();
        this.tabHumano = new Tabuleiro();
        
        this.tabBot.configurarFrota();
        this.tabHumano.configurarFrota();
    }

    public void iniciarPartida() {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        boolean jogoRolando = true;

        System.out.println("\n=== BATALHA NAVAL: " + humano.getNome() + " VS " + bot.getNome() + " ===");

        while (jogoRolando) {
            System.out.println("\n--- MAPA DO INIMIGO ---");
            tabBot.exibir(true);
            
            int linha = -1;
            int coluna = -1;

            while (true) {
                try {
                    System.out.printf("\nSua vez, Capitão %s! Digite a LINHA e COLUNA (0 a 19): ", humano.getNome());
                    
                    linha = sc.nextInt();
                    coluna = sc.nextInt();

                    if (linha >= 0 && linha < 20 && coluna >= 0 && coluna < 20) {
                        break;
                    } else {
                        System.out.println("Coordenadas fora do mapa! Tente valores entre 0 e 19.");
                    }
                } catch (Exception e) {
                    System.out.println("Entrada inválida! Por favor, digite apenas números inteiros.");
                    sc.nextLine();
                }
            }

            if (tabBot.jaFoiAtacado(linha, coluna)) {
                System.out.println("Você já atirou aí! Perdeu a vez por desatenção.");
            } else {
                boolean acertou = tabBot.receberTiro(linha, coluna, true);
                if (acertou) {
                    System.out.println("FOGO! Você acertou uma embarcação inimiga!");
                    humano.registrarAcerto();
                    System.out.println("Você abateu " + humano.getAbates() + " embarcações.");
                } else {
                    System.out.println("ÁGUA! Tiro passou longe.");
                }
            }

            if (humano.getAbates() >= 5) {
                System.out.printf("\nPARABÉNS %s! Você afundou 5 embarcações e venceu a guerra! \n", humano.getNome());
                jogoRolando = false;
                break;
            }

            System.out.println("\n[Bot processando jogada...]");
            int lBot, cBot;
            do {
                lBot = random.nextInt(20);
                cBot = random.nextInt(20);
            } while (tabHumano.jaFoiAtacado(lBot, cBot));

            boolean botAcertou = tabHumano.receberTiro(lBot, cBot, false);
            System.out.println("O Bot atirou na posição [" + lBot + ", " + cBot + "]");
            
            if (botAcertou) {
                System.out.println("Fomos atingidos!");
                bot.registrarAcerto();
                System.out.println("O bot abateu " + bot.getAbates() + " embarcações.");
            } else {
                System.out.println("O Bot errou e acertou a água.");
                System.out.println("O bot abateu " + bot.getAbates() + " embarcações.");
            }

            // Checa vitória do Bot
            if (bot.getAbates() >= 5) {
                System.out.println("\nGAME OVER! O computador destruiu sua frota.");
                jogoRolando = false;
                break;
            }

            salvarJogo();
        }
    }

    private void salvarJogo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save_vs_bot.dat"))) {
            oos.writeObject(this);
        } catch (Exception e) {
            System.out.println("Erro ao salvar checkpoint.");
        }
    }
}