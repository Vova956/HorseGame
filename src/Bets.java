import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Bets extends JFrame {

    private Horse[] horses;
    private double[] bets;
    private double money;
    private Race race;
    private double VictoryMoney;
    private int maxSpeed;
    private int maxSpeedIndex;
    private int horseWon;
    private boolean IsRefreshed;


    public double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public void print(double[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "   ");
        }
        System.out.println();
    }

    public Bets(){
     setSize(600,300);
     setLocation(150,150);
     setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
     setTitle("Bets");
     setLayout(null);

    JPanel panel = new JPanel();
    panel.setBounds(0,0,600,300);
    panel.setLayout(null);
    panel.setBackground(Color.gray);
    add(panel);

    horses = new Horse[6];
        for (int i = 0; i < 6; i++) {
            horses[i] = new Horse((1.1+Math.random()*5));
        }

    horseWon = 0;

    JTextField[][] fields = new JTextField[2][3];
    String[][] names = new String[2][3];
    money = 500;
    IsRefreshed = true;
    bets = new double[6];
        int buff = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                names[i][j] = "Hourse№" + Integer.toString(buff+1) + "(x" + round(horses[buff].getBet(),2) + ")";
                buff++;
            }
        }


    int x = 80;
    int y = 40;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j] = new JTextField();
                fields[i][j].setBounds(x,y,100,20);
                panel.add(fields[i][j]);
                x += 150;
            }
            x = 80;
            y += 100;
        }

        JLabel[][] labels = new JLabel[2][3];
        x = 80;
        y = 20;


        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                labels[i][j] = new JLabel(names[i][j]);
                labels[i][j].setBounds(x,y,100,20);
                labels[i][j].setBackground(Color.green);
                panel.add(labels[i][j]);
                x += 150;

            }
            x = 80;
            y += 100;
        }

        JLabel m = new JLabel("Money:  " + money + "$");
        m.setBounds(0,0,100,20);
        panel.add(m);


        JButton button = new JButton("Continue");
        button.setBounds(175,200,100,50);
        panel.add(button);

        JButton refresh = new JButton("Refresh");
        refresh.setBounds(325,200,100,50);
        panel.add(refresh);

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 6; i++) {
                    horses[i].refresh();
                }

                m.setText("Money:  " + round(money,2) + "$");
                int n = 0;
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 3; j++) {
                        names[i][j] = "Hourse№" + Integer.toString(n+1) + "(x" + round(horses[n].getBet(),2) + ")";
                        labels[i][j].setText(names[i][j]);
                        n++;
                    }
                }
                IsRefreshed = true;
            }
        });


        button.addActionListener(new ActionListener() {
            int z = 0;
            int buff = 0;
            public void actionPerformed(ActionEvent e) {
                if (IsRefreshed) {
                    m.setText("Money:  " + money + "$");
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 3; j++) {
                            try {
                                if (Double.parseDouble(fields[i][j].getText()) < 0)
                                    bets[buff] = 0;
                                else
                                    bets[buff] = Double.parseDouble(fields[i][j].getText());
                            } catch (Exception m) {
                                bets[buff] = 0;
                            }
                            buff++;
                        }
                    }
                    buff = 0;
                    print(bets);

                    z = 0;
                    for (int i = 0; i < 6; i++) {
                        z += bets[i];
                    }


                    if (z <= money) {
                        VictoryMoney = 0;
                        maxSpeed = 0;
                        maxSpeedIndex = 100;


                        for (int i = 0; i < 6; i++) {
                            for (int j = 0; j < horses[i].getLength(); j++) {
                                if (maxSpeed >= 550) {
                                    if (maxSpeedIndex > j) {
                                        maxSpeedIndex = j;
                                        horseWon = i;
                                        break;
                                    } else
                                        break;
                                } else {
                                    maxSpeed += horses[i].getSpeed(j);
                                }
                            }
                            maxSpeed = 0;

                        }

                        VictoryMoney = round(horses[horseWon].getBet() * bets[horseWon], 2);
                        System.out.println("Won Horse№:  " + (horseWon + 1) + " ");
                        for (int i = 0; i < 6; i++) {
                            if (i != horseWon)
                                VictoryMoney -= bets[i];
                        }
                        try {
                            race = new Race(horses);
                        } catch (IOException | InterruptedException ioException) {
                            ioException.printStackTrace();
                        }

                        if (money + VictoryMoney <= 1)
                            setVisible(false);
                        else {
                            money += VictoryMoney;
                            m.setText("Money:  " + round(money, 2) + "$");
                        }


                    } else
                        System.out.println("Can`t");

                    IsRefreshed = false;
                }
                else
                    System.out.print("Refresh!");
            }
        });



        setVisible(true);
    }
}
