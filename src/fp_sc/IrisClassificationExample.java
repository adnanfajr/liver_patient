/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp_sc;

/**
 *
 * @author Eden
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;

public class IrisClassificationExample {

	public static void main(String[] args) {
		
		/*
		 * 1. CREATING THE DATASET
		 */
		int numInput  = 10;
		int numOutput = 2;
		
		DataSet dataSet = new DataSet(numInput, numOutput);
		
		// Let's set the names of the variables and target output
		// This step is not obligatory; you're free to omit it
		dataSet.setColumnNames(new String[] { "age", "gender", "total_bilirubin", "direct_bilirubin", "alkaline_phosphotase", "alamine_aminotransferase", "astarpate_aminotransferase", "total_proteins", "albumin", "ratio_albumin" });
		dataSet.setLabel("liver_patient");
		
		// Pada contoh pertama ini, kita akan memasukkan data secara manual
		DataSetRow newRow = new DataSetRow(
			new double[] { 29,2,0.9,0.3,202,14,11,6.7,3.6,1.1 },
			new double[] { 1, 0 });
		dataSet.addRow(newRow);
		
		// Contoh memasukkan titik data kedua
		newRow = new DataSetRow(
			new double[] { 17,1,0.9,0.3,202,22,19,7.4,4.1,1.2 },
			new double[] { 0, 1 });
		dataSet.addRow(newRow);
		
		// But it's getting boring, so let's try reading the data from file
		// You could also input the data from a database
		
		// A Java 8 idiom for reading data
		// You could also use BufferedReader
		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get("patient.data"))) {
			
			Object[] lines = stream.toArray();
			for (Object line : lines) {
				if (line instanceof String) {
					
					String[] split = ((String)line).split(",");
			    	double positive = 0, negative = 0;
			    	
			    	newRow = null;
			    	
			    	if (split.length == 11) {
			    		if (split[10].contains("1")) {
			    			positive = 1;
			    		} else if (split[10].contains("2")) {
			    			negative = 1;
			    		}
			    		
			    		newRow = new DataSetRow(new double[] {	Double.parseDouble(split[0]),
			    							Double.parseDouble(split[1]),
			    							Double.parseDouble(split[2]),
                                                                                Double.parseDouble(split[3]),
                                                                                Double.parseDouble(split[4]),
                                                                                Double.parseDouble(split[5]),
                                                                                Double.parseDouble(split[6]),
                                                                                Double.parseDouble(split[7]),
                                                                                Double.parseDouble(split[8]),
                                                                                Double.parseDouble(split[9])
			    							},
			    					new double[] {	positive, negative });
			    	}
			    	
			    	if (newRow != null) {
			    		dataSet.addRow(newRow);
			    	}
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Now let's check the data set by printing it
		// Uncomment this line if you want to print the data set
		/* System.out.println(dataSet); */
			
		/*
		 * 2. CREATING THE STRUCTURE OF THE NEURAL NETWORK
		 */
		
		// Let's say we want to have a 4-2-3 multilayer neural network
		int inputCount    = 10;
		int outputCount   = 2;
		int hiddenNeurons = 2;
		
		MultiLayerPerceptron neuralNet =
				new MultiLayerPerceptron(inputCount, hiddenNeurons, outputCount);
		
		// Let's observe the initial weights of the neural network
		// Run this several times, and you will see that the neural network
		// is initialized using some random small weight values
		/*
		Double[] weights = neuralNet.getWeights();
		System.out.println(weights.length);
		for (Double weight : weights) {
			System.out.println(weight);
		}
		*/
		
		/*
		 * 3. CREATING THE LEARNING RULE AND SET SOME LEARNING PARAMETERS
		 */
		
		// Let's use plain BackPropagation in this case
		BackPropagation learningRule = neuralNet.getLearningRule();
		
		// Set some parameters
		learningRule.setLearningRate(0.05);
		learningRule.setMaxError(0.001);
		learningRule.setMaxIterations(100);
		
		/*
		 * Additionally, set a listener to print various information on the training process
		 */
		learningRule.addListener(new LearningEventListener() {
            @Override
            public void handleLearningEvent(LearningEvent event) {
                BackPropagation bp = (BackPropagation) event.getSource();
                if (event.getEventType().equals(LearningEvent.Type.LEARNING_STOPPED)) {
                    System.out.println();
                    System.out.println("Training completed in " + bp.getCurrentIteration() + " iterations");
                    System.out.println("With total error " + bp.getTotalNetworkError() + '\n');
                } else {
                    System.out.println("Iteration: " + bp.getCurrentIteration() + " | Network error: " + bp.getTotalNetworkError());
                }
            }
        });
		
		/*
		 * 4. PERFORM THE TRAINING PROCESS
		 */
		neuralNet.learn(dataSet);	// Done! You have a trained model in neuralNet
		
		// Let's observe the weights of the neural network after the training process
		/*
		Double[] weights = neuralNet.getWeights();
		System.out.println(weights.length);
		for (Double weight : weights) {
			System.out.println(weight);
		}
		*/
		
		// More specifically....
		// Disini untuk melakukan pencetakan konfigurasi bobot neural network,
		// kita memanggil fungsi printWeightConfiguration yang didefinisikan sendiri
		// Tujuan pemisahan fungsi ini adalah supaya kode lebih rapi saja
		printWeightConfiguration(neuralNet);
		
	}
	
	public static void printWeightConfiguration(MultiLayerPerceptron neuralNet) {
		
		for (int i = 0; i < neuralNet.getLayersCount(); i++) {
			System.out.println("\nLayer " + i + ": ");
			Layer l = neuralNet.getLayerAt(i);
			System.out.println(l.getNeuronsCount());
			
			for (int j = 0; j < l.getNeuronsCount(); j++) {
				
				Neuron   n = l.getNeuronAt(j);
				System.out.println("\nNeuron " + j + ": " + n);
				
				Connection[] inputs  = n.getInputConnections();
				
				for (Connection input : inputs) {
					System.out.println("\t" + input.getFromNeuron());
					System.out.println("\t" + input.getWeight());
				}
			}
		}
	}
}

