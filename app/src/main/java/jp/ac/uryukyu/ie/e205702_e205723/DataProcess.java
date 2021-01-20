package jp.ac.uryukyu.ie.e205702_e205723;

import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
* DataProcess class deals with data-generating/processing
* @author Yoshiaki Sano
*/
public class DataProcess{

    /**
    * Constructor of DataProcess class
    *
    */
    public DataProcess(){}

    /**
    * get_iris_data method extracts and processes data from the file 'iris.csv'
    * @return three dimensional double array which contains input data and supervising data
    */
    public double[][][] get_iris_data(){
        ArrayList<ArrayList<Double>> input_data = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> training_data = new ArrayList<ArrayList<Double>>();
        ArrayList<String> rows = new ArrayList<String>();
        String filePath = "src/main/java/jp/ac/uryukyu/ie/e205702_e205723/data/iris.csv";
        String row;
        while(rows.size()==0){try{
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
                try {
                    while ((row = csvReader.readLine()) != null) {rows.add(row);}
                    csvReader.close();
                } catch (IOException e) {System.err.println(e);}}catch(FileNotFoundException e){filePath = "app/src/main/java/jp/ac/uryukyu/ie/e205702_e205723/data/iris.csv";}
        }
        for(int rowNum = 0; rowNum < rows.size(); rowNum++){
            String[] data = rows.get(rowNum).split(",");
            ArrayList<Double> input = new ArrayList<>();
            ArrayList<Double> output = new ArrayList<>();
            if(rowNum != 0){
                for(int j = 0; j < 16; j++){input.add(0.0);}
                for(int i = 0; i < data.length; i++){
                    if(i != data.length -1){
                        if((i+1)%4 == 1){if(Double.parseDouble(data[i]) >= 4 && Double.parseDouble(data[i]) < 5){input.set(0, 1.0);}else if(Double.parseDouble(data[i]) >= 5 && Double.parseDouble(data[i]) < 6){input.set(1, 1.0);}else if(Double.parseDouble(data[i]) >= 6 && Double.parseDouble(data[i]) < 7){input.set(2, 1.0);}else if(Double.parseDouble(data[i]) >= 7 && Double.parseDouble(data[i]) <= 8){input.set(3, 1.0);}
                        }else if((i+1)%4 == 2){if(Double.parseDouble(data[i]) >= 2 && Double.parseDouble(data[i]) < 3){input.set(4, 1.0);}else if(Double.parseDouble(data[i]) >= 3 && Double.parseDouble(data[i]) < 4){input.set(5, 1.0);}else if(Double.parseDouble(data[i]) >= 4 && Double.parseDouble(data[i]) <= 5){input.set(6, 1.0);}
                        }else if((i+1)%4 == 3){if(Double.parseDouble(data[i]) >= 1 && Double.parseDouble(data[i]) < 2){input.set(7, 1.0);}else if(Double.parseDouble(data[i]) >= 2 && Double.parseDouble(data[i]) < 3){input.set(8, 1.0);}else if(Double.parseDouble(data[i]) >= 3 && Double.parseDouble(data[i]) < 4){input.set(9, 1.0);}else if(Double.parseDouble(data[i]) >= 4 && Double.parseDouble(data[i]) < 5){input.set(10, 1.0);}else if(Double.parseDouble(data[i]) >= 5 && Double.parseDouble(data[i]) < 6){input.set(11, 1.0);}else if(Double.parseDouble(data[i]) >= 6 && Double.parseDouble(data[i]) <= 7){input.set(12, 1.0);}
                        }else if((i+1)%4 == 0){if(Double.parseDouble(data[i]) >= 0 && Double.parseDouble(data[i]) < 1){input.set(13, 1.0);}else if(Double.parseDouble(data[i]) >= 1 && Double.parseDouble(data[i]) < 2){input.set(14, 1.0);}else if(Double.parseDouble(data[i]) >= 2 && Double.parseDouble(data[i]) <= 3){input.set(15, 1.0);}
                        }
                    }else{
                        for(int j = 0; j < 3; j++){output.add(0.0);}
                        if(data[i].equals("Setosa")){output.set(0, 1.0);}
                        else if(data[i].equals("Versicolor")){output.set(1, 1.0);}
                        else if(data[i].equals("Virginica")){output.set(2, 1.0);}
                    }
                }
                input_data.add(input);
                training_data.add(output);
            }
        }
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for(int i = 0; i < input_data.size(); i++){numbers.add(i);}
        Collections.shuffle(numbers);
        double[][] shuffled_input_data = new double[input_data.size()][input_data.get(0).size()];
        double[][] shuffled_training_data = new double[training_data.size()][training_data.get(0).size()];
        for(int i = 0; i < input_data.size(); i++){
            for(int j = 0; j < input_data.get(0).size(); j++){shuffled_input_data[i][j] = input_data.get(numbers.get(i)).get(j);}
            for(int j = 0; j < training_data.get(0).size(); j++){shuffled_training_data[i][j] = training_data.get(numbers.get(i)).get(j);}
        }
        return new double[][][] {shuffled_input_data, shuffled_training_data};
    }

    /**
    * max_and_minimum method returns the maximum/minimum output and weight from each of a neural network's layers
    * @param nn neural network to extracts outputs and weights from
    * @return two dimensional Double ArrayList which contains the maximum/minimum output and weight in each of neural network's layers
    */
    public ArrayList<ArrayList<Double>> max_and_minimum(Neural_Network nn){
        ArrayList<Double> max_outputs = new ArrayList<Double>();
        ArrayList<Double> minimum_outputs = new ArrayList<Double>();
        ArrayList<Double> max_weights = new ArrayList<Double>();
        ArrayList<Double> minimum_weights = new ArrayList<Double>();

        for(int i = 1; i < nn.layers.size(); i++){
            double max_o = 0.0;
            double minimum_o = 0.0;
            double max_w = 0.0;
            double minimum_w = 0.0;
            for(int j = 0; j < nn.layers.get(i).neurons.size(); j++){
                for(int k = 0; k < nn.layers.get(i).neurons.get(j).weights.size(); k++){
                    if(k == 0){
                        max_w = Math.abs(nn.layers.get(i).neurons.get(j).weights.get(k).weight);
                        minimum_w = Math.abs(nn.layers.get(i).neurons.get(j).weights.get(k).weight);
                    }else if(k != 0){
                        if(max_w < Math.abs(nn.layers.get(i).neurons.get(j).weights.get(k).weight)){
                            max_w = Math.abs(nn.layers.get(i).neurons.get(j).weights.get(k).weight);
                        }else if(minimum_w > Math.abs(nn.layers.get(i).neurons.get(j).weights.get(k).weight)){
                            minimum_w = Math.abs(nn.layers.get(i).neurons.get(j).weights.get(k).weight);
                        }
                    }
                }
                if(j == 0){
                    max_o = nn.layers.get(i).neurons.get(j).output;
                    minimum_o = nn.layers.get(i).neurons.get(j).output;
                }else if(j != 0){
                    if(max_o < nn.layers.get(i).neurons.get(j).output){
                        max_o = nn.layers.get(i).neurons.get(j).output;
                    }else if(minimum_o > nn.layers.get(i).neurons.get(j).output){
                        minimum_o = nn.layers.get(i).neurons.get(j).output;
                    }
                }
            }
            max_outputs.add(max_o);
            minimum_outputs.add(minimum_o);
            max_weights.add(max_w);
            minimum_weights.add(minimum_w);
        }
        ArrayList<ArrayList<Double>> max_and_minimu = new ArrayList<ArrayList<Double>>();
        max_and_minimu.add(max_outputs);
        max_and_minimu.add(minimum_outputs);
        max_and_minimu.add(max_weights);
        max_and_minimu.add(minimum_weights);

        return max_and_minimu;
    }

    /**
    * get_neurons_nums method decides the number of neurons in each layer in a neural network from user's input
    * @param dataset
    * @return array which contains the numbers of neurons in layers in order from the embedding layer to the output layer
    */
    public int[] get_neurons_nums(double[][][] dataset){
        ArrayList<Integer> layers_neurons = new ArrayList<Integer>();
        int index = 1;
        String str = "";
        Scanner scan = new Scanner(System.in);
        layers_neurons.add(dataset[0][0].length);
        while(!str.equals("stop") || index == 1){
        if(index == 1){
            System.out.println("第" + index + "中間層のニューロンの数を定めてください。");
        }else{
            System.out.println("第" + index + "中間層のニューロンの数を定めてください。(終了する: 'stop'と入力)");
        }
        str = scan.next();
        try{
            if(!str.equals("stop") && Integer.parseInt(str) > 0){
                layers_neurons.add(Integer.parseInt(str));
                index += 1;
            }
        }catch(NumberFormatException e){
            System.err.println(e);
        }
        }
        scan.close();
        layers_neurons.add(dataset[1][0].length);
        int[] neurons_nums = new int[layers_neurons.size()];
        for(int i = 0; i < neurons_nums.length; i++){
        neurons_nums[i] = layers_neurons.get(i);
        }
        return neurons_nums;
    }
}