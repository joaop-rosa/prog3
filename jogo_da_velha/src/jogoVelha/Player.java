package jogoVelha;

import jogoVelha.enums.PlayerType;
import jogoVelha.enums.Symbol;

public class Player {
    private PlayerType playerType;
    private Symbol symbol;

    Player(PlayerType playerType, Symbol symbol) {
        this.playerType = playerType;
        this.symbol = symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Symbol getReverseSymbol() {
        return symbol.equals(Symbol.X) ? Symbol.O : Symbol.X;
    }

}
