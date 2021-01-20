package jp.ac.uryukyu.ie.e205702_e205723;

/**
* Weight class
* @author Koki Yanai
*/
public class Weight {
    double weight;
    double mean_of_gradient = 0.0;
    double variance_of_gradient = 0.0;
    int time = 0;

    /**
    * Constructor of Weight class
    * @param standard_deviation standard deviation of random values generated with Gaussian distribution
    */
    Weight(double standard_deviation){
        weight = this.box_muller_transform(standard_deviation);
    }

    /**
    * box_muller_transform method generates a ramdom value with Gaussian distribution
    * @param standard_deviation standard deviation of random values generated with Gaussian distribution
    */
    double box_muller_transform(double standard_deviation){
        return Math.sqrt(-2* Math.log(Math.random()))*Math.cos(2*Math.PI*Math.random())*standard_deviation;
    }

    /**
    * Adam method updates the weight with the gradient
    * @param gradient gradient of weight
    */
    void Adam(double gradient){
        double learning_rate = 0.001;
        double beta1 = 0.9;
        double beta2 = 0.999;
        double epsilon = Math.pow(10, -8);
        if(gradient != 0){
            time += 1;
            mean_of_gradient = beta1*mean_of_gradient + (1.0 - beta1)*gradient;
            variance_of_gradient = beta2*variance_of_gradient + (1.0 - beta2)*Math.pow(gradient, 2.0);
            double hat_m = mean_of_gradient/(1.0 - Math.pow(beta1, (double)time));
            double hat_v = variance_of_gradient/(1.0 - Math.pow(beta2, (double)time));
            weight = weight - learning_rate*hat_m/(Math.sqrt(hat_v) + epsilon);
        }
    }
}