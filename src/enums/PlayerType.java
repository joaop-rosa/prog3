package enums;

public enum PlayerType {
    P1("P1"), P2("P2"), COM("COM");

    public String playerType;

    PlayerType(String player) {
        playerType = player;
    }
}
