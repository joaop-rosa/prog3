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

    public void checkField(Player player) {
        this.isChecked = true;
        this.checkedPlayer = player;
    }

    public Player getCheckedPlayer() {
        return checkedPlayer;
    }

    public int getPosition() {
        return position;
    }
}
