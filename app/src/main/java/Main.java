import jp.ac.uryukyu.ie.e205702_e205723.*;

public class Main {
    public static void main(String[] args){
        /**
         * 使い方:
         * 中間層のニューロンの数を入力する
         * 'stop'と入力するとGUIが起動する
         * GUIを閉じるとプログラムが終了する
         *
         * GUIの見方:
         * 一番右の層は教師データによってどの位置のニューロンが出力されるべきかを表している。
         * 右から2番目の層は出力層
         * 一番左の層は入力層
         * ニューロンの色が変化する、色が濃いとニューロンの出力が比較的大きく、薄いと比較的小さい
         */
        DataProcess DP = new DataProcess();
        double[][][] dataset = DP.get_iris_data();
        Neural_Network nn = new Neural_Network(DP.get_neurons_nums(dataset));

        double[][] input_data = new double[dataset[0].length][dataset[0][0].length];
        double[][] training_data = new double[dataset[1].length][dataset[1][0].length];

        for(int i = 0; i < dataset[0].length; i++){
            input_data[i] = dataset[0][i];
            training_data[i] = dataset[1][i];
        }
        DrawNeuralNetwork drawNN = new DrawNeuralNetwork(1200, 800, nn, input_data, training_data);
        drawNN.animate();
    }
}
