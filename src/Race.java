import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Race extends JFrame {
    BufferedImage m;
    private int buff;

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



    public Race(Horse[] hourses) throws IOException, InterruptedException {
        setSize(600,485);
        setLocation(150,150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bets");
        setLayout(null);


        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.GREEN);

                int y = 0;

                for (int i = 1; i < 6; i++) {
                    g.drawLine(0, i*75, 600, i*75);
                }
                g2.setColor(Color.WHITE);
                g.drawLine(550,0,550,450);

            }
        };
        panel.setBounds(0,0,600,450);
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        add(panel);

        m = ImageIO.read(new File("white horse.png"));


        JPanel[] panels = new JPanel[6];

        for (int i = 0; i < 6; i++) {
            int finalI = i;
            panels[i] = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                        g.clearRect(0,0,panels[finalI].getWidth(),panels[finalI].getHeight());
                        g.drawImage(m,0,0,null);
                }
            };

            panels[i].setBounds(15,(i*75)+12,74,50);
            panel.add(panels[i]);
        }





        JButton button = new JButton("Watch next");
        button.setBounds(250,0,100,20);
        button.setBackground(Color.RED);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 6; i++) {
                    if(panels[i].getX() + hourses[i].getSpeed(buff) >= 550) {
                        setVisible(false);
                    }
                    panels[i].setBounds(panels[i].getX() + hourses[i].getSpeed(buff),panels[i].getY(),panels[i].getWidth(),panels[i].getHeight());

                }
                buff++;

            }

        });
        panel.add(button);








        setVisible(true);
    }
}
