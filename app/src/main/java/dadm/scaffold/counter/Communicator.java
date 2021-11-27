package dadm.scaffold.counter;


public class Communicator{
    //Game_UI
    private static int lifes=0;
    private static int ammo=0;

    //Results
    private static int score=0;
    private static int meteors=0;
    private static int e_weak=0;
    private static int e_mid=0;
    private static int e_pro=0;

    public static void addHit(int points){
        score=score+points;
    }
    public static void setScore(int newScore) {
       score = newScore;
    }
    public static int getScore() {
        return score;
    }

    public static void addMeteors() {
        meteors++;
    }

    public static void addE_weak() {
        e_weak++;
    }

    public static void addE_mid() {
        e_mid++;
    }

    public static void addE_pro() {
        e_pro++;
    }

    public static int getE_weak() {
        return e_weak;
    }

    public static int getE_mid() {
        return e_mid;
    }

    public static int getE_pro() {
        return e_pro;
    }

    public static int getMeteors() {
        return meteors;
    }

    public static int getLifes() {
        return lifes;
    }
    public static void loseLife(){ lifes--;}

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
