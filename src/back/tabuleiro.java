package back;

import cores.Cor;
import mecanicas.Carta;
import mecanicas.Tabuleiro;
import cores.StringColorida;
import java.util.*;

public class tabuleiro extends Tabuleiro {

     int pecaDeJogoDir = 0;
     int pecaDeJogoEsq = 0;
     int primeiroLance = 1;

     String vencedor = null;
     String bot = "bot";
     String jogador = "jogador";
     String vez ;

     carta[] baralho = new carta[28];
     carta[] maoJogador = new carta[7];
     carta[] maoBot = new carta[7];



     StringColorida verso = new StringColorida(" | ",Cor.PRETO, Cor.FUNDO_BRANCO);
     carta fundo = new carta(new StringColorida("   ",Cor.AZUL, Cor.FUNDO_AZUL));

     carta meio = new carta(new StringColorida(pecaDeJogoEsq+ "|" + pecaDeJogoDir, Cor.PRETO, Cor.FUNDO_BRANCO),verso,true);

     Random random = new Random();

    public tabuleiro(int numLinhas, int numColunas, Carta fundo) {
        super(numLinhas, numColunas, fundo);
    }

    public  void criarBaralho(){
        int aux = 0;

        for(int zero = 0;zero<7;zero++){
            for (int i = zero; i <= 6; i++) {
            StringColorida frente = new StringColorida(zero + "|" + i, Cor.PRETO, Cor.FUNDO_BRANCO);
            baralho[aux] = (new carta(frente, verso, true));
            aux = aux + 1;
            }
        }
//
//        while (zero <= 6) {
//            for (int i = zero; i <= 6; i++) {
//                assert false;
//                StringColorida frente = new StringColorida(zero + "|" + i, Cor.PRETO, Cor.FUNDO_BRANCO);
//                baralho[aux] = (new carta(frente, verso, true));
//                aux = aux + 1;
//            }
//            zero = zero + 1;
//        }
    }

    public  void criarMao(){
        for(int i = 0;i < 7;){
            int a  = random.nextInt(27);
            if(baralho[a]!=null) {
                maoJogador[i] = baralho[a].copia();
                baralho[a]=null;
                i++;
            }
        }
        for(int i = 0;i < 7;){
            int a  = random.nextInt(27);
            if(baralho[a]!=null) {
                maoBot[i] = new carta(baralho[a].getFrente(),verso,true);
                baralho[a]=null;
                i++;
            }
        }
    }

    public  void novoJogo(){
        criarBaralho();
        criarMao();
        primeiro();
        primeiroLance = 1;
    }

    public  void continuar(tabuleiro partida){
        save z = new save();
        z.readContent(partida);
    }

    public void primeiro(){
        //chat gpt corrigiu essa parte

        for (int i = 0; i <= 6; i++) {
            StringColorida frente = new StringColorida(i + "|" + i, Cor.PRETO, Cor.FUNDO_BRANCO);
            carta temp = new carta(frente, verso, true);

            for (int j = 0; j < 7; j++) {
                if (maoJogador[j].equals(temp)) {
                    vez = jogador;

                }
                if (maoBot[j].equals(temp)) {
                    vez = bot;

                }
            }
        }

    }

    public  void passaVez(){if (Objects.equals(vez, "jogador")){vez = "bot";}else{vez = "jogador";}}

    public  boolean verificaSeAMaoPodeJogar(carta[] x){
        for (int i = 0;i<x.length;i++) {
            if(x[i]!=null) {
                if (x[i].numDireita == pecaDeJogoDir || x[i].numDireita == pecaDeJogoEsq ||
                        x[i].numEsquerda == pecaDeJogoDir || x[i].numEsquerda == pecaDeJogoEsq) {
                    return true;
                }
            }
        }
        return false;
    }

    public  boolean acabou(){
        if(maoJogador.length == 0){
            vencedor = jogador;
            return true;
        } else if (maoBot.length == 0) {
            vencedor = bot;
            return true;
        } else if (baralho.length == 0 && !verificaSeAMaoPodeJogar(maoJogador) && !verificaSeAMaoPodeJogar(maoBot)) {
            if(maoJogador.length<maoBot.length){vencedor=jogador;return true;} else if (maoBot.length< maoJogador.length) {vencedor=bot;return true;}
        }
        return false;
    }

    public void comprar(carta[] mao){
        for(int i =0;i<mao.length;i++){
            if(mao[i]==null){
                int a  = random.nextInt(27);
                if(baralho[a]!=null) {
                    maoJogador[i] = baralho[a].copia();
                    baralho[a]=null;
                    break;
                }
            }
        }
        int i=0;
        while(i==0){
            int a = random.nextInt(27);
            if (baralho[a] != null) {
                mao[6] = (carta)baralho[a];
                i=1;
            }
        }
    }

    //gpt corrigiu as comparações dessa parte
    public void jogadaJogador(tabuleiro partida, int col) {
        if (primeiroLance == 1 && Objects.equals(vez, jogador)) {
            int aux = 0;
            for (int i = 6; i >= 0; i--) {
                StringColorida frente = new StringColorida(i + "|" + i, Cor.PRETO, Cor.FUNDO_BRANCO);
                carta temp = new carta(frente, verso, true);

                for (int j = 0; j < 7; j++) {
                    if (maoJogador[j] != null && maoJogador[j].equals(temp)) { // Use .equals and check for null
                        aux = i;
                    }
                }
            }
            partida.colocaCarta(4, aux+1, fundo);
            pecaDeJogoDir = maoJogador[aux+1].copia().numDireita;
            pecaDeJogoEsq = maoJogador[aux+1].copia().numEsquerda;
            maoJogador[aux+1] = null;
            atualizaMeio();
            atualizaTabuleiro();
            primeiroLance = 0;
            passaVez();
        } else if (primeiroLance != 1 && Objects.equals(vez, jogador)) {

            partida.colocaCarta(4, col, fundo);
            // partida.pegaCarta(4, col); // Uncomment if needed
            if(maoJogador[col-1]!=null) {
                if (Objects.equals(maoJogador[col - 1].numEsquerda, pecaDeJogoDir)) {
                    pecaDeJogoDir = maoJogador[col - 1].numDireita;
                } else if (Objects.equals(maoJogador[col - 1].numDireita, pecaDeJogoDir)) {
                    pecaDeJogoDir = maoJogador[col - 1].numEsquerda;
                } else if (Objects.equals(maoJogador[col - 1].numDireita, pecaDeJogoEsq)) {
                    pecaDeJogoEsq = maoJogador[col - 1].numEsquerda;
                } else if (Objects.equals(maoJogador[col - 1].numEsquerda, pecaDeJogoEsq)) {
                    pecaDeJogoEsq = maoJogador[col - 1].numDireita;
                }
            }
            maoJogador[col-1] = null;
            primeiroLance = 0;
            atualizaMeio();
            atualizaTabuleiro();
            passaVez();
        }
    }
    //gpt corrigiu as comparações dessa parte

    public  void jogadaBot(tabuleiro partida){
        if (primeiroLance == 1 && Objects.equals(vez, bot)) {
            int aux=0;

            for (int i = 6; i >= 0; i--) {
                StringColorida frente = new StringColorida(i + "|" + i, Cor.PRETO, Cor.FUNDO_BRANCO);
                carta temp = new carta(frente, verso, true);
                for (int j = 0; j < 7; j++) {
                    if (maoBot[j].equals(temp)) {// Use .equals instead of ==
                        aux = i;
                    }
                }

            }
            partida.colocaCarta(0, aux+1, fundo);
            // partida.pegaCarta(0, j+1);  // Uncomment if needed
            pecaDeJogoDir = maoBot[aux+1].numDireita;
            pecaDeJogoEsq = maoBot[aux+1].numEsquerda;
            maoBot[aux+1] = null;  // Corrected from maoJogador to maoBot
            atualizaMeio();
            atualizaTabuleiro();
            passaVez();
        } else if (primeiroLance != 1 && Objects.equals(vez, bot)) {

            int aux = 0;
            for (int i = 0; i < maoBot.length; i++) {
                if (maoBot[i] != null &&  // Ensure that maoBot[i] is not null before accessing it
                        (maoBot[i].valorD() == pecaDeJogoDir || maoBot[i].valorD() == pecaDeJogoEsq ||
                                maoBot[i].valorE() == pecaDeJogoDir || maoBot[i].valorE() == pecaDeJogoEsq)) {
                    aux = i + 1;
                    break;  // Exit the loop once a match is found
                }
            }
            if (verificaSeAMaoPodeJogar(maoBot) && aux != 0) {
                partida.pegaCarta(0, aux);
                if(Objects.equals(maoBot[aux - 1].numEsquerda, pecaDeJogoDir)){
                    pecaDeJogoDir = maoBot[aux - 1].numDireita;
                } else if (Objects.equals(maoBot[aux - 1].numDireita, pecaDeJogoDir)) {
                    pecaDeJogoDir = maoBot[aux - 1].numEsquerda;
                } else if (Objects.equals(maoBot[aux - 1].numDireita, pecaDeJogoEsq)) {
                    pecaDeJogoEsq = maoBot[aux - 1].numEsquerda;
                } else if (Objects.equals(maoBot[aux - 1].numEsquerda, pecaDeJogoEsq)) {
                    pecaDeJogoEsq = maoBot[aux - 1].numDireita;
                }
//                pecaDeJogoDir = maoBot[aux - 1].valorD();
//                pecaDeJogoEsq = maoBot[aux - 1].valorE();
                maoBot[aux - 1] = null;  // Corrected from maoJogador to maoBot
                atualizaMeio();
                atualizaTabuleiro();
                passaVez();
            }
        }

//
//        if(primeiroLance == 1 && Objects.equals(vez, bot)){
//            System.out.println("aiai");
//            for(int i = 6;i>=0;i--){
//                StringColorida frente = new StringColorida(i + "|" + i, Cor.PRETO, Cor.FUNDO_BRANCO);
//                carta temp = new carta(frente,verso,true);
//                for (int j = 0; j < 7; j++) {
//                    if(maoBot[j]==temp){
//                        partida.colocaCarta(0,i,fundo);
////                        partida.pegaCarta(0,j+1);
//                        pecaDeJogoDir = maoBot[j].numDireita;
//                        pecaDeJogoEsq = maoBot[j].numEsquerda;
//                        maoJogador[j] = null;
//                        atualizaMeio();
//                        passaVez();
//                    }
//                }
//            }
//        } else if (Objects.equals(vez, bot)) {
//            System.out.println("aiai");
//            int aux = 0;
//            for(int i = 0;i<maoBot.length;i++){
//                if(maoBot[i].valorD() == pecaDeJogoDir ||maoBot[i].valorD() == pecaDeJogoEsq ||
//                        maoBot[i].valorE() == pecaDeJogoDir ||maoBot[i].valorE() == pecaDeJogoEsq){
//                    aux = i+1;
//                }
//            }
//            if(verificaSeAMaoPodeJogar(maoBot) && aux !=0){
//                partida.pegaCarta(0,aux);
//                pecaDeJogoDir = maoBot[aux-1].valorD();
//                pecaDeJogoEsq = maoBot[aux-1].valorE();
//                maoJogador[aux-1] = null;
//                atualizaMeio();
//                passaVez();
//            }
//        }
    }

    public void atualizaTabuleiro(){
        int tam = 9;
        if(maoJogador.length > maoBot.length){tam = maoJogador.length+2;}
        if(maoBot.length> maoJogador.length){tam = maoBot.length+2;}

//        back.tabuleiro partida = new tabuleiro(5, tam, fundo);
        //       carta aux;
        for (int i = 0; i < maoJogador.length; i++) {
//            Carta n = temp.compraQualquer(i);
//            if(n!=null) {
//                colocaCarta(4, i + 1, new carta(n.copia().getFrente(), verso, true));
//            }
            if(maoJogador[i]!=null) {
                colocaCarta(4, i + 1, new carta(maoJogador[i].getFrente(), verso, true));
            }else {
                colocaCarta(0, i + 1, new carta(new StringColorida("   ",Cor.PRETO,Cor.FUNDO_PRETO), verso, false));
            }
        }
        for (int i = 0; i < maoBot.length; i++) {
//            Carta n = temp.compraQualquer(i);
//            if(n!=null) {
//                colocaCarta(0, i + 1, new carta(n.copia().getFrente(), verso, false));
//            }
            if(maoBot[i]!=null) {
                colocaCarta(0, i + 1, new carta(maoBot[i].getFrente(), verso, false));
            }else {
                colocaCarta(0, i + 1, new carta(new StringColorida("   ",Cor.PRETO,Cor.FUNDO_PRETO), verso, false));
            }
        }


//        aux = meio;
//        PilhaDeCartas temp = new PilhaDeCartas();
//        temp.insereTopo(aux);
        atualizaMeio();
        colocaCarta(2,tam/2, meio.copia());

    }

    public void atualizaMeio(){
        meio = new carta(new StringColorida(pecaDeJogoEsq + "|" + pecaDeJogoDir, Cor.PRETO, Cor.FUNDO_BRANCO), verso,true);
    }
}