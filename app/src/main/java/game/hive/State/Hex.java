package game.hive.State;

public class Hex {
    public enum Color {WHITE, BLACK}
    private Color color;

    public Hex(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}

