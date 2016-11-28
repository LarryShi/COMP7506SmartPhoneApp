package mangoabliu.finalproject.Model;

/**
 * Created by herenjie on 2016/11/25.
 */

public class Planet {

    int planetID;
    String planetName;
    int planetX;
    int planetY;

    public Planet(int id, String name, int x, int y){
        planetID = id;
        planetX = x;
        planetY = y;
        planetName = name;
    }

    public int getPlanetID(){
        return planetID;
    }

    public int getPlanetX(){
        return planetX;
    }

    public int getPlanetY(){
        return planetY;
    }

    public String getPlanetName(){
        return planetName;
    }

}
