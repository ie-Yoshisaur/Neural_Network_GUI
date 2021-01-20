package jp.ac.uryukyu.ie.e205702_e205723;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NeuralNetWorkTest {
    /**
     * ニューラルネットワークの各層のニューロンが正しく生成されているかの確認
     * 検証手順
     *  (2) intクラスを要素とする配列を生成する
     *  (1) ニューラルネットワークをNeural_Networkクラスのコンストラクタを呼び出し生成する
     *  各層のニューロンの数を確認して、それが(1)のニューロンの各要素と同じであることを期待。これを検証する。
     */
    @Test
    void generateTest() {
        int[] numbers_of_neurons = new int[]{10,20,20,3};
        Neural_Network nn = new Neural_Network(numbers_of_neurons);
        int isSameIndex = 0;
        for(int i = 0; i < numbers_of_neurons.length; i++){
            if(numbers_of_neurons[i] == nn.layers.get(i).neurons.size()){
                isSameIndex += 1;
            }
        }
        assertEquals(isSameIndex, numbers_of_neurons.length);
    }
}