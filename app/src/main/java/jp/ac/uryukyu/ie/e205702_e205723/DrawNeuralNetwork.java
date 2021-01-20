package jp.ac.uryukyu.ie.e205702_e205723;

import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;

/**
* DrawNeuralNetwork class depicts the proccess done by a neural network and the neural network itself
* @author Yoshiaki Sano
*/
public class DrawNeuralNetwork extends JPanel{
    private static final long serialVersionUID = 1L;
    int width;
    int height;
    int epoch = 1;
    int success = 0;
    int limit = 100;
    double accuracy;
    Neural_Network nn;
    double[][] input_data;
    double[][] training_data;

    /**
    * Constructor of DrawNeuralNetwork class
    * @param width width of a window where a neural network is displayed
    * @param height height of a window where a neural network is displayed
    * @param nn neural network to be displayed
    * @param input_data data to use as input data
    * @param training_data data to use as training data
    */
    public DrawNeuralNetwork(int width, int height, Neural_Network nn, double[][] input_data, double[][] training_data){
        this.width = width;
        this.height = height;
        this.nn = nn;
        this.input_data = input_data;
        this.training_data = training_data;

        setPreferredSize(new Dimension(width, height));
        Thread th = new AnimeThread();
        th.start();
    }

    /**
    * paintComponent method used to set values for depiction
    * @param g　Graphics instance in which values will be set for depiction
    */
    public void paintComponent(Graphics g) {
        g.setColor(new Color(49, 49, 92));
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);
        g.drawString("精度:" + (int)(((double)success)*100/((double)epoch)) + "% (" + limit + "回中)", width*4/5, height*14/15);
        Random random = new Random();
        int random_number = random.nextInt(input_data.length);
        nn.propagation(input_data[random_number]);
        nn.back_propagation(training_data[random_number]);
        ArrayList<ArrayList<Double>> max_and_minimu = (new DataProcess()).max_and_minimum(nn);
        ArrayList<Double> max_outputs = max_and_minimu.get(0);
        ArrayList<Double> minimum_outputs = max_and_minimu.get(1);
        ArrayList<Double> max_weights = max_and_minimu.get(2);
        ArrayList<Double> minimum_weights = max_and_minimu.get(3);
        int guess_index = 0;
        double effect = 1.0;
        if(epoch > limit){
            epoch = 0;
            success = 0;
        }
        epoch += 1;

        for(int i = 0; i < nn.layers.size(); i++){for(int j = 0; j < nn.layers.get(i).neurons.size(); j++){for(int k = 0; k < nn.layers.get(i).neurons.get(j).weights.size(); k++){
                    if(i != 0){
                        effect = (Math.abs(nn.layers.get(i).neurons.get(j).weights.get(k).weight) - minimum_weights.get(i-1))/(max_weights.get(i-1) - minimum_weights.get(i-1));
                        if(effect < 0){effect = 0.0;}else if(effect > 1){effect = 1.0;}
                        effect = effect/2.0;
                        g.setColor(new Color((int)(210.0 * effect + 49.0 * (1.0 - effect)), (int)(182.0 * effect + 49.0 * (1.0 - effect)), (int)(46.0 * effect + 92.0 * (1.0 - effect))));
                        g.drawLine(width*i/(nn.layers.size()+2), height*(k+1)/(nn.layers.get(i).neurons.get(j).weights.size()+1), width*(i+1)/((nn.layers.size()+2)), height*(j+1)/(nn.layers.get(i).neurons.size()+1));
                }}
                if(i != 0){effect = (nn.layers.get(i).neurons.get(j).output - minimum_outputs.get(i-1))/(max_outputs.get(i-1) - minimum_outputs.get(i-1));}else if(i == 0){effect = 1.0;}
                if(i == nn.layers.size() - 1){
                    if(j == 0){guess_index = 0;}else if(j != 0){if(nn.layers.get(i).neurons.get(j).output > nn.layers.get(i).neurons.get(guess_index).output){guess_index = j;}}
                }
                g.setColor(new Color((int)(147.0 * effect + 49.0 * (1.0 - effect)), (int)(0.0 * effect + 49.0 * (1.0 - effect)), (int)(86.0 * effect + 92.0 * (1.0 - effect))));
                g.fillOval(width*(i+1)/(nn.layers.size()+2) - height/((nn.layers.get(i).neurons.size()+1)*3), height*(j+1)/(nn.layers.get(i).neurons.size()+1) - height/((nn.layers.get(i).neurons.size()+1)*3), height*2/((nn.layers.get(i).neurons.size()+1)*3), height*2/((nn.layers.get(i).neurons.size()+1)*3));
        }}
        for(int i = 0; i < training_data[random_number].length; i++){
            if(training_data[random_number][i] == 1.0){g.setColor(new Color(147, 0, 86));if(i == guess_index){ success += 1;}
            }else{ g.setColor(new Color(49, 49, 92));}
            g.fillOval(width*(nn.layers.size()+1)/(nn.layers.size()+2) - height/((training_data[random_number].length+1)*5), height*(i+1)/(training_data[random_number].length+1) - height/((training_data[random_number].length+1)*5), height*2/((training_data[random_number].length+1)*5), height*2/((training_data[random_number].length+1)*5));
        }
    }

    /**
    * AnimeThread class repaints depictions
    */
    class AnimeThread extends Thread {
        public void run() {
            while(true) {
                repaint();
                try {
                    Thread.sleep((int)((750*((double)success/(double)epoch))));
                } catch(Exception e) {
                }
    }}}

    /**
    * animate method animates the dipiction
    */
    public void animate() {
        JFrame f = new JFrame();
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(new DrawNeuralNetwork(width, height, nn, input_data, training_data));
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setVisible(true);
    }
}