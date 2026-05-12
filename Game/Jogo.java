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
            // 1. TURNO DO JOGADOR
            System.out.println("\n--- MAPA DO INIMIGO ---");
            tabBot.exibir(true);
            
            System.out.print("\nSua vez, Capitão! Digite a LINHA e COLUNA para atirar (ex: 5 10): ");
            int linha = sc.nextInt();
            int coluna = sc.nextInt();

            if (tabBot.jaFoiAtacado(linha, coluna)) {
                System.out.println("Você já atirou aí! Perdeu a vez por desatenção.");
            } else {
                boolean acertou = tabBot.receberTiro(linha, coluna, true);
                if (acertou) {
                    System.out.println("FOGO! Você acertou uma embarcação inimiga!");
                    humano.registrarAcerto();
                } else {
                    System.out.println("ÁGUA! Tiro passou longe.");
                }
            }

            // Checa vitória do Humano
            if (humano.getAbates() >= 5) {
                System.out.println("\nPARABÉNS! Você afundou 5 embarcações e venceu a guerra!");
                jogoRolando = false;
                break;
            }

            // 2. TURNO DO BOT
            System.out.println("\n[Bot processando jogada...]");
            int lBot, cBot;
            do {
                lBot = random.nextInt(20);
                cBot = random.nextInt(20);
            } while (tabHumano.jaFoiAtacado(lBot, cBot)); // Bot inteligente: não repete tiro

            boolean botAcertou = tabHumano.receberTiro(lBot, cBot, false);
            System.out.println("O Bot atirou na posição [" + lBot + ", " + cBot + "]");
            
            if (botAcertou) {
                System.out.println("Fomos atingidos!");
                bot.registrarAcerto();
            } else {
                System.out.println("O Bot errou e acertou a água.");
            }

            // Checa vitória do Bot
            if (bot.getAbates() >= 5) {
                System.out.println("\nGAME OVER! O computador destruiu sua frota.");
                jogoRolando = false;
                break;
            }

            // 3. SALVAR CHECKPOINT
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