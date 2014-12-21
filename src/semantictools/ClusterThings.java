package semantictools;

import java.io.File;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class ClusterThings {
	static Boolean verbose = false;
	static Integer numOfClasses = 10;
	static String nameOfPorgram = "produceClasses";
	static String vectorFileName= "";
	static String vocabFileName="";

	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption("n", true, "number of classes");
		options.addOption("i", true, "vector file (binary)");
		options.addOption("v", false, "print stuff");
		options.addOption("o", true, "file to write the classes to (not yet implemented");
		options.addOption("w", false, "vectors are word2vec vectors");
		options.addOption("g", false, "vectors are GloVe vectors");
		options.addOption("h", false, "print this help");
		options.addOption("c", true, "vocabulary file for GloVe vectors");
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse( options, args);
		HelpFormatter formatter = new HelpFormatter();
		if (cmd.hasOption("h")){
			formatter.printHelp( nameOfPorgram, options );

		}
		else { 
			if(cmd.hasOption("v")) {
				System.out.println("Verbose");
				verbose = true;
			}


			if (cmd.hasOption("n")){
				numOfClasses = Integer.parseInt(cmd.getOptionValue("n"));
				if (verbose) System.out.println("Number of classes: "+numOfClasses);
			}
			else {
				System.out.println("No number of classes given, going with default: " +numOfClasses);
			}

			if (cmd.hasOption("i")){
				vectorFileName = cmd.getOptionValue("i");
				File vectorFile = new File(vectorFileName);
				if (!vectorFile.exists()){
					formatter.printHelp( nameOfPorgram, options );
					throw new Exception("Vector file given does not exist. Check the path and try again: "+vectorFileName);
				}

			}
			else {
				formatter.printHelp( nameOfPorgram, options );
				throw new Exception("No vectors file given! Aborting");
			}

			if (cmd.hasOption("w") ){
				if  (cmd.hasOption("g")) {
					formatter.printHelp( nameOfPorgram, options );
					throw new Exception("Both -w and -g specified. Choose either w for word2vec vectors, or g for GloVe vectors");
				}
				else {
					word2vecScenario(vectorFileName,numOfClasses);
				}
			}
			else {
				if  (cmd.hasOption("g")) {
					if (cmd.hasOption("c")) {
						vocabFileName = cmd.getOptionValue("c");
						File vocabFile = new File(vocabFileName);
						if (!vocabFile.exists()){
							formatter.printHelp( nameOfPorgram, options );
							throw new Exception("Vocabulary file given does not exist. Check the path and try again: "+vocabFileName);
						}
						else {
							gloveScenario(vocabFileName, vectorFileName, numOfClasses);							
						}
					}
					else {
						formatter.printHelp( nameOfPorgram, options );
						throw new Exception("You need to specify a vocabulary file for GloVe vectors by using the -c option");
					}
				}
				else {
					formatter.printHelp( nameOfPorgram, options );
					throw new Exception("Neither -w nor -g specified. Choose either w for word2vec vectors, or g for GloVe vectors");

				}

			}
		}
	}

	/**
	 * Create clusters based on word2vec binary vectors, uses Weka's implementation of KMeans.
	 * @param vectorFileName word2vec binary vectors file, example /bibdev/travail/word2vec/trunk/minipub.vectors.bin 
	 * @param numOfClasses number of clusters (classes), k for kMeans
	 * @throws Exception does not really take care of exceptions! 
	 */
	public static void word2vecScenario(String vectorFileName, Integer numOfClasses) throws Exception{
		Word2vecHelper w2v = new Word2vecHelper(vectorFileName, true);
		SemanticTable semTable = w2v.kMeansClustering(numOfClasses);
		semTable.printSemanticClasses();
	}
	/**
	 * Create clusters based on glove binary vectors. uses Weka's implementation of KMeans. 
	 * @param vocabFileName Glove vocab file (txt), example /home/dvalsamou/workspace/glove/vocab.txt
	 * @param vectorFileName Glove binary vectors file, example /home/dvalsamou/workspace/glove/vectors.bin 
	 * @param numOfClasses integer number of clusters (classes), k for KMeans
	 * @throws Exception does not really take care of exceptions!
	 */
	public static void gloveScenario(String vocabFileName, String vectorFileName, Integer numOfClasses) throws Exception{
		GloVeHelper gh = new GloVeHelper(vocabFileName, vectorFileName, true);
		SemanticTable semTable = gh.kMeansClustering(numOfClasses);
		semTable.printSemanticClasses();


	}
}
