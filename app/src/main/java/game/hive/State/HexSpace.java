package game.hive.State;

import android.graphics.Color;

public class HexSpace {

    private Hex hex;
    public HexSpace(){

    }
    public HexSpace(HexSpace hexSpace){
        if(hexSpace.hex!=null){
            this.hex=new Hex(hexSpace.getHex());
        }
    }
    public void setHex(Hex hex) {
        this.hex = hex;
    }

    public Hex getHex() {
        return hex;
    }

    public Hex.Color getColor(){
        if(this.hex==null){
            return Hex.Color.NONE;
        }
        return this.hex.getColor();
    }



}
