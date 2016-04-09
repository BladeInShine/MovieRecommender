import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;
import java.util.List;

/**
 * Created by BladeInShine on 16/4/9.
 */
public class ItemBasedRecommendation {

    public static void main(String[] args){

        try{
            DataModel model = new FileDataModel (new File("src/main/resources/output.csv"));
            ItemSimilarity itemSimilarity = new EuclideanDistanceSimilarity(model);
            Recommender itemRecommender = new GenericItemBasedRecommender(model,itemSimilarity);
            List<RecommendedItem> itemRecommendations = itemRecommender.recommend(3, 2);
            for (RecommendedItem itemRecommendation : itemRecommendations) {
                System.out.println("Item: " + itemRecommendation);
            }

        }
        catch(Exception e){
            System.out.println(e);
        }

    }

}
