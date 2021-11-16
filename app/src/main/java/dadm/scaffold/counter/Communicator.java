package dadm.scaffold.counter;

public class Communicator{
    private static int score=0;

    public static void addHit(){
        score++;
    }

    public static void setScore(int newScore) {
       score = newScore;
    }

    public static int getScore() {
        return score;
    }
}
