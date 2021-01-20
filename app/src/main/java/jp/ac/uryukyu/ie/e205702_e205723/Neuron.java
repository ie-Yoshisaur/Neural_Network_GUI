package jp.ac.uryukyu.ie.e205702_e205723;

import java.util.ArrayList;

/**
* Neuron class contains weights
* @author Koki Yanai
*/
public class Neuron {
    double output = 0.0;
    ArrayList<Weight> weights = new ArrayList<Weight>();
    Weight bias;
    double gradient = 0.0;

    /**
    * Constructor of Neuron class
    * @param number_of_neurons number of neurons
    * @param number_of_weights number of weights
    */
    Neuron(int number_of_neurons, int number_of_weights){
        for(int i = 0; i < number_of_weights; i++){
            weights.add(new Weight(Math.sqrt(2/((double)number_of_neurons))));
        }
        if(number_of_weights != 0){
            bias = new Weight(0.0);
        }
    }

    /**
    * linear_combination method calculates the output of the neuron
    * @param previous_layer previous_layer
    */
    void linear_combination(Layer previous_layer){
        double sum = 0.0;
        for(int i = 0; i < weights.size(); i++){
            sum += previous_layer.neurons.get(i).output*weights.get(i).weight;
        }
        output = sum - bias.weight;
    }
}