package dadm.scaffold.counter;

import android.view.View;

public class Communicator{
    private static int score=0;
    private static int lifes=0;
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
}
