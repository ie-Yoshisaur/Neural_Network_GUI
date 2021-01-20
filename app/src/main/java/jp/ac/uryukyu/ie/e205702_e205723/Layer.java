package jp.ac.uryukyu.ie.e205702_e205723;

import java.util.ArrayList;

/**
* Layer class contains neurons and activates their outputs
* @author Koki Yanai
*/
public class Layer {
    ArrayList<Neuron> neurons = new ArrayList<Neuron>();

    /**
    * Constructor of Layer class
    * @param number_of_neurons number of neurons
    * @param number_of_weights number of weights each neuron has
    */
    Layer(int number_of_neurons, int number_of_weights){
        for(int i = 0; i < number_of_neurons; i++){
            neurons.add(new Neuron(number_of_neurons, number_of_weights));
        }
    }

    /**
    * ReLU method activates the outputs of neurons
    */
    void ReLU(){
        for(int i = 0; i < neurons.size(); i++){
            if(neurons.get(i).output < 0){
                neurons.get(i).output = 0.0;
            }
        }
    }

    /**
    * ReLU method activates the outputs of neurons in the output layer
    */
    void Softmax(){
        double sum = 0.0;
        for(int i = 0; i < neurons.size(); i++){
            sum +=  Math.exp(neurons.get(i).output);
        }
        for(int i = 0; i < neurons.size(); i++){
            if(sum != 0.0){
                neurons.get(i).output = Math.exp(neurons.get(i).output)/sum;
            }
        }
    }

    /**
    * derivative_of_ReLU method fixes the gradient of each neuron according to the output
    */
    void derivative_of_ReLU(){
        for(int i = 0; i < neurons.size(); i++){
            if(neurons.get(i).output == 0){
                neurons.get(i).gradient = 0.0;
            }
        }
    }

    /**
    * propagate_gradient method propagates gradients to the previous layer
    * @param previous_layer previous_layer
    */
    void propagate_gradient(Layer previous_layer){
        for(int i = 0; i < previous_layer.neurons.size(); i++){
            double sum = 0.0;
            for(int j = 0; j < neurons.size(); j++){
                sum += neurons.get(j).gradient * neurons.get(j).weights.get(i).weight;
            }
            previous_layer.neurons.get(i).gradient = sum;

        }
    }
}