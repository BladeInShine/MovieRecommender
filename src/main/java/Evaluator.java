/**
 * Created by xiaoxiaoli on 4/9/16.
 */
import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class Evaluator {

    private static int neighbourhoodSize=7;

    public static void main(String args[])
    {
        String recsFile="src/main/resources/output.csv";

            /*creating a RecommenderBuilder Objects with overriding the buildRecommender method
            this builder object is used as one of the parameters for RecommenderEvaluator - evaluate method*/

        //for Recommendation evaluations
        RecommenderBuilder userSimRecBuilder = new RecommenderBuilder() {

            public Recommender buildRecommender(DataModel model)throws TasteException
            {
                //The Similarity algorithms used in your recommender
                UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(model);

                        /*The Neighborhood algorithms used in your recommender
                         not required if you are evaluating your item based recommendations*/
                UserNeighborhood neighborhood =new NearestNUserNeighborhood(neighbourhoodSize, userSimilarity, model);

                //Recommender used in your real time implementation
                Recommender recommender =new GenericBooleanPrefUserBasedRecommender(model, neighborhood, userSimilarity);
                return recommender;
            }
        };

        try {

            //Use this only if the code is for unit tests and other examples to guarantee repeated results
            RandomUtils.useTestSeed();

            //Creating a data model to be passed on to RecommenderEvaluator - evaluate method
            FileDataModel dataModel = new FileDataModel(new File(recsFile));

                  /*Creating an RecommenderEvaluator to get the evaluation done
                  you can use AverageAbsoluteDifferenceRecommenderEvaluator() as well*/
            RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();

            //for obtaining User Similarity Evaluation Score
            double userSimEvaluationScore = evaluator.evaluate(userSimRecBuilder,null,dataModel, 0.8, 1.0);
            System.out.println("User Similarity Evaluation score : "+userSimEvaluationScore);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TasteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}