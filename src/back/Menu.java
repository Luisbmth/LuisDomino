package back;

import console.Console;
import cores.Cor;
import cores.StringColorida;
import front.Pause;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import static console.Console.println;

public class Menu{

    static StringColorida x = new StringColorida("     ", Cor.FUNDO_CINZA);
    static carta fundo = new carta(x);
    static tabuleiro partida = new tabuleiro(5,9, fundo);

    public void exibirMenu() {
        int escolha = 0;
        Scanner scanner = new Scanner(System.in);

        println("====Dominó de Luis Felipe====");
        println("1 - Novo Jogo");
        println("2 - Continuar");
        println("3 - Regras");
        println("4 - Sair");
        Console.print("Escolha: ");

        do {
            try {
                escolha = scanner.nextInt();
            } catch (InputMismatchException e) {
                println("Entrada inválida. Por favor, insira um número entre 1 e 5.");
                scanner.next(); // Limpa a entrada inválida
                continue;
            }

            if (escolha == 1) {
                println("Novo Jogo");
                partida.novoJogo();

                partida.atualizaTabuleiro();

                while(!partida.acabou()){
                    System.out.println(partida);
                    lance(partida);
                }
            } else if (escolha == 2) {
                println("Continuar");
                partida.continuar(partida);

            } else if (escolha == 3) {
                exibirRegras();
            } else if (escolha == 4) {
                println("Sair");
                front.Pause.exitProgram();
                break;


            } else if (escolha == 6) {
                Pause.paus1();
            } else {
                println("ESCOLHA ENTRE 1, 2, 3, 4 OU 5. NÃO ERRE DE NOVO!");
            }

        } while (escolha != 8);
    }

    private void lance(tabuleiro partida) {

        if (Objects.equals(partida.vez, "jogador")) {
            System.out.println("JOGUE UMA PEÇA:");
            System.out.println("1 2 3 4 5 6 7");
            System.out.println(" OU: ");
            System.out.println("9.compre   10.passe a vez");
            Scanner scanner = new Scanner(System.in);

            int lance;
            lance = scanner.nextInt();

            if (lance > 0 && lance < 8) {


                partida.jogadaJogador(partida,lance);
//                partida.passaVez();
                partida.acabou();
                partida.atualizaMeio();
                System.out.println("");
                System.out.println("");
                partida.atualizaTabuleiro();
                System.out.println(partida);

            } else if (lance == 9 && partida.baralho.length !=0) {
                partida.comprar(partida.maoJogador);
            } else if (lance == 9 && partida.baralho.length ==0) {
                System.out.println("NAO HÀ NADA PARA COMPRAR");
            } else if (lance == 10) {
                partida.passaVez();
            } else {
                System.out.println("entre um numero valido");
            }
        } else if (Objects.equals(partida.vez, "bot")) {
            if (partida.verificaSeAMaoPodeJogar(partida.maoBot)) {
                partida.jogadaBot(partida);
                partida.acabou();

                partida.atualizaMeio();
                System.out.println("");
                System.out.println("");
                partida.atualizaTabuleiro();

                System.out.println(partida);

            } else if (!partida.verificaSeAMaoPodeJogar(partida.maoBot) && partida.baralho.length!=0) {
                partida.comprar(partida.maoBot);
                partida.acabou();
            } else {
                partida.passaVez();
            }
        }
    }

    private void exibirRegras() {

        println("==== Regras do Dominó ====\n\n");
        println("Objetivo do Jogo:");
        println("Para jogar dominó são necessárias 28 pedras retangulares. Cada pedra está dividida em 2 espaços" +
                "\niguales nos que aparece um número de 0 até 6. As pedras abrangem todas as combinações possíveis" +
                "\ncom estes números.\n");
        println("Preparação:");
        println("1. Mistura das Pedras: Coloque todas as pedras viradas para baixo e misture.");
        println("2. Distribuição das Pedras: Cada jogador pega 7 pedras.\n");
        println("Jogo:");
        println("Primeira Jogada: O jogador com a pedra dupla mais alta inicia.\n");
        println("Jogadas:");
        println("1. Colocação de Pedras: Coloque uma pedra que corresponda a um número de pontos de uma das extremidades" +
                "\ndas pedras já na mesa.");
        println("2. Passar a Vez: Se não puder jogar, compre pedras da reserva (se houver)  " +
                " \n ou passe a vez.\n");
        println("Fim de uma Rodada:");
        println("1. A rodada termina quando um jogador coloca sua última pedra ou ninguém pode jogar.");
        println("2. Contagem dos Pontos: O vencedor da rodada soma os pontos das pedras restantes dos oponentes.\n");
        println("Pontuação:");
        println("Vencedor da Rodada: Ganha a soma dos pontos das pedras restantes dos oponentes.\n");
        println("Vencedor do Jogo: O primeiro a alcançar o número predeterminado de pontos.\n");
        println("Empate: Rodada anulada\n");
        println("===========================");

        exibirMenu();
    }
}