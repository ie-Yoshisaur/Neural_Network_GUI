package jp.ac.uryukyu.ie.e205702_e205723;

import java.util.ArrayList;

/**
* Neural_Network class contains layers, propagates outputs of each layer and backpropagates gradients
* @author Koki Yanai
*/
public class Neural_Network {
    ArrayList<Layer> layers = new ArrayList<Layer>();

    /**
    * Constructor of Neural_Network class
    * @param numbers_of_neurons numbers of neurons
    */
    public Neural_Network(int[] numbers_of_neurons){
            for(int i = 0; i < numbers_of_neurons.length; i++){
                if(i == 0){
                    layers.add(new Layer(numbers_of_neurons[i], 0));
                }else{
                    layers.add(new Layer(numbers_of_neurons[i], numbers_of_neurons[i-1]));
                }
            }
    }

    /**
    * propagation method propagates the outputs of neurons in order from the embedding layer to the output layer
    * @param input_data data to use as input data
    */
    void propagation(double[] input_data){
        for(int i = 0; i < layers.size(); i++){
            if(i == 0){
                for(int j = 0; j < input_data.length; j++){
                    layers.get(i).neurons.get(j).output = input_data[j];
                }
            }else if(i != 0){
                for(int j = 0; j < layers.get(i).neurons.size(); j++){
                    layers.get(i).neurons.get(j).linear_combination(layers.get(i - 1));
                }
                if(i != layers.size() - 1){
                    layers.get(i).ReLU();
                }else if(i == layers.size() - 1){
                    layers.get(i).Softmax();
                }
            }
        }
    }

    /**
    * backpropagates method propagates the gradients of neurons in order from the output layer to the embedding layer
    * @param training_data data to use as training data
    */
    void back_propagation(double[] training_data){
        for(int i = layers.size() -1; i > 0; i--){
            if(i == layers.size() -1){
                for(int j = 0; j < layers.get(i).neurons.size(); j++){
                    layers.get(i).neurons.get(j).gradient = layers.get(i).neurons.get(j).output - training_data[j];
                }
            }else if(i != 0 && i != layers.size() -1){
                layers.get(i).derivative_of_ReLU();
            }
            layers.get(i).propagate_gradient(layers.get(i - 1));
            for(int j = 0; j < layers.get(i).neurons.size(); j++){
                layers.get(i).neurons.get(j).bias.Adam(-layers.get(i).neurons.get(j).gradient);
                for(int k = 0; k < layers.get(i).neurons.get(j).weights.size(); k++){
                    layers.get(i).neurons.get(j).weights.get(k).Adam(layers.get(i).neurons.get(j).gradient * layers.get(i - 1).neurons.get(k).output);
                }
            }
        }
    }
}