package dadm.scaffold.counter;


public class Communicator{
    private static int score=0;
    private static int lifes=0;
    private static int ammo=0;

    public static void addHit(){
        score++;
    }

    public static void setScore(int newScore) {
       score = newScore;
    }

    public static int getScore() {
        return score*25;
    }

    public static int getLifes() {
        return lifes;
    }
    public static void looseLife(){ lifes--;}

    public static void setLifes(int lifes) {
        Communicator.lifes = lifes;
    }

    public static int getAmmo() {
        return ammo;
    }

    public static void setAmmo(int ammo) {
        Communicator.ammo = ammo;
    }
}
