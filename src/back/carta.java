package back;

import console.Console;
import cores.Cor;
import cores.StringColorida;
import mecanicas.Carta;

public class carta extends Carta {
    int numDireita;
    int numEsquerda;

    public carta(StringColorida frente) {
        super(frente);
    }

    public int valorD() {
        return this.numDireita;
    }

    public int valorE() {
        return this.numEsquerda;
    }

    public carta(StringColorida frente, StringColorida verso, boolean viradaParaCima) {
        super(frente, verso, viradaParaCima);

        for(int i = 0;i<=6;i++){
            for(int j =0;j<=6;j++){
                if (frente.equals(new StringColorida(i+"|"+j, Cor.PRETO, Cor.FUNDO_BRANCO))){
                    this.numDireita = j;
                    this.numEsquerda = i;
                }
            }
        }
    }

    @Override
    public carta copia() {
        return new carta(this.getFrente(), this.getVerso(), this.estaViradaParaCima());
    }
}

