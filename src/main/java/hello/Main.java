package hello;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;

/**
 * Created by traub_000 on 12/15/2016.
 */
public class Main {

    public static void main(String[] args){

        FacebookClient facebookClient= new DefaultFacebookClient(Constants.MY_ACCESS_TOKEN);

        Connection<Post> groupFeed = facebookClient.fetchConnection("ritfoodshare/feed", Post.class);
//        Connection<Group> groupFeed = facebookClient.fetchConnection("886700444689855/groups", Group.class);

        for(int i = 0; i < 10; i++) {
            System.out.println("Message " + i + ": " + groupFeed.getData().get(i).getMessage());
        }
    }
}
