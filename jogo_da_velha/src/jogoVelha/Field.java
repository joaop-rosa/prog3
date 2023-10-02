package jogoVelha;

public class Field {
    private boolean isChecked;
    private Player checkedPlayer;
    private int position;

    Field(int position) {
        this.isChecked = false;
        this.position = position;
    }

    public boolean isChecked() {
        return isChecked;
    }

    private void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    private void setCheckedPlayer(Player player) {
        this.checkedPlayer = player;
    }

    public static void checkField(Player player, Field field) {
        field.setIsChecked(true);
        field.setCheckedPlayer(player);
    }

    public static void removeCheckField(Field field) {
        field.setIsChecked(false);
        field.setCheckedPlayer(null);
    }

    public Player getCheckedPlayer() {
        return checkedPlayer;
    }

    public int getPosition() {
        return position;
    }
}
