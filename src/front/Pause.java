package front;

import console.Console;

import java.util.Scanner;

public class Pause {
    /*   private static boolean partida;

       static void pause() {
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
                   partida.iniciarNovoJogo();

                   while(partida.fim ==0){
                       if(Objects.equals(partida.vez, "jogador")) {
                           System.out.println(partida);
                           System.out.println("JOGUE UMA PEÇA:");
                           System.out.println("1 2 3 4 5 6 7");
                           System.out.println(" OU: ");
                           System.out.println("9.compre   10.passe a vez");
                           partida.lance(partida.maoJogador, partida.baralhoPronto, partida.maoBot);
                           partida.centro = partida.atualizaCentro(partida.pecaDeJogoDir, partida.pecaDeJogoEsq);
                       }else {
                           System.out.println("VENCEDOR:" + partida.vez);
                       }
                   }
               } else if (escolha == 2) {
                   println("Continuar");
                   partida.iniciarJogo();

               } else if (escolha == 3) {
                   exibirRegras();
               } else if (escolha == 4) {
                   println("Sair");
                   break;

               } else {
                   println("ESCOLHA ENTRE 1, 2, 3, 4 OU 5. NÃO ERRE DE NOVO, SE NÃO O SEU PC VAI EXPLODIR");
               }

           } while (escolha != 5);
       }
   */


    public static void pausa() {
        Console.println("MENU DE PAUSE");
        Console.println("1-VOLTAR AO JOGO");
        Console.println("2-VOLTAR AO MENU");
        Console.println("3-Sair");
        Console.print("Escolha: ");

    }

    public static void paus1() {

        Scanner scanner = new Scanner(System.in);
        int escolha1;

        do {
            pausa();
            escolha1 = scanner.nextInt();

            switch (escolha1) {

                case 1:
                    Console.println("Voltar ao menu");

                    break;


                case 2:
                    Console.println("Voltar ao jogo");
                    break;

                case 3:
                    System.out.println("Sair do jogo");
                    exitProgram();
                    break;
                default:
                    Console.print("ESCOLHA:");
                    return;
            }
            }while (true) ;


        }

    public static void exitProgram() {
        System.exit(0);
    }
}

    /*
    public static void paus1() {


        Scanner scanner = new Scanner(System.in);
        int escolha1;


        do {
            paus();
            escolha1 = scanner.nextInt();

            switch (escolha1) {

                case 1:
                    Console.println("Voltar ao jogo");
                    if (jogoAtual != null) {
                        jogoAtual.mostrarTabuleiro();
                    } else {
                        Console.println("Nenhum jogo em andamento.");
                    }


                    break;
                case 2:
                    Console.println("voltar ao menu");
                    paus2();
                    ;

                    break;
                case 3:
                    Console.println("Fechar jogo");
                    exitProgram();
                default:
                    Console.println("ESCOLHA");
                    break;


            }
        } while (true);


    }
    /*/


