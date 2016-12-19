package hello;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by traub_000 on 12/15/2016.
 */
public class Main {

    public static void main(String[] args){

        FacebookClient facebookClient= new DefaultFacebookClient(Constants.MY_ACCESS_TOKEN);

        Connection<Post> groupFeed = facebookClient.fetchConnection("761563947267935/feed", Post.class);

        int pageNum = 1;
        int postNum = 1;
        int size = groupFeed.getData().size();
        System.out.println(size);

        FileWriter fWr = null;
        try {
            fWr = new FileWriter("Foodshare Posts.csv");
            BufferedWriter wr = new BufferedWriter(fWr);

            String type;
            Calendar cal;
            int hour_24;
            int min;
            int day;
            String msg;

            for(List<Post> groupFeedPage: groupFeed) {
                for (Post post : groupFeedPage) {
                    /*
                     I'm considering filtering by type == "added_photos" in the future
                     because they are almost always free leftovers. However, it's only a
                     small percentage of the total posts.
                     */
                    type = post.getStatusType();
                    /*
                    Also considering removing posts with "?" in them. In a sample of 200 posts,
                    18 out of 20 posts with "?" in them were undesirable.
                     */
                    cal = GregorianCalendar.getInstance();
                    cal.setTime(post.getCreatedTime());
                    hour_24 = cal.get(Calendar.HOUR_OF_DAY);
                    day = cal.get(Calendar.DAY_OF_WEEK);
                    msg = post.getMessage();
                    if(msg != null) {
                        msg = msg.replaceAll("\n", " ");
                        msg = msg.replaceAll("\"", "'");
                    }
                    wr.write(type + "," + hour_24 + "," + day + "," + msg + "\n");
//                    System.out.println("PostNum/Type " + postNum + type + ": " + post.getMessage());
                    postNum++;
                }
                pageNum++;
                if (pageNum == 9) break;
            }
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
