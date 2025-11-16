package game.hive.State;

public class Hex {
    public enum Color {WHITE, BLACK,NONE}
    private Color color;
    private String name;
    public Hex(Hex hex){
        this.color = hex.color;
        this.name=hex.name;
    }
    //public Hex(Color color) {
      //  this.color = color;
    //}

    public Hex(Color color,String hexName){this.color = color; this.name=hexName;}

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}

