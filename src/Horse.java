public class Horse {
    private int speed[];
    private double bet;

    public Horse(double bet) {
        speed = new int[55];
        for (int i = 0; i < 55; i++) {
            speed[i] = ((int)(10 + Math.random()*50));
        }
        this.bet = bet;
    }

    public int getLength(){
        return speed.length;
    }
    public int getSpeed(int n) {
        return speed[n];
    }

    public double getBet() {
        return bet;
    }


    public void setBet(double bet) {
        this.bet = bet;
    }

    public void refresh(){
        for (int i = 0; i < 55; i++) {
            speed[i] = ((int)(10 + Math.random()*50));
        }
        bet = (1.1+Math.random()*5);
    }



}
