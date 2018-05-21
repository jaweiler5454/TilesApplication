package com.weiler.tiles;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;

public class DataBaseHelper {


    public DataBaseHelper(){

    }
    public void testMethods(){


    }
    /* EXAMPLE
    DatabaseHelper base = new DatabaseHelper();
    base.testMethods();
    */
    public ArrayList<Map<String, Object>> getUsers(){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tilessubdomain.ma1geek.org/getUsers.php");
            r.setPost(false);
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            ArrayList<Map<String, Object>> content = (ArrayList<Map<String, Object>>)result.get("root");
            return content;
        } catch(Exception err) {
            Log.e(err);
            return null;
        }
    	/* EXAMPLE
   	 	DatabaseHelper base = new DatabaseHelper();
        System.out.println(base.getUserByUsername("scottyc"));
        */
    }

    public ArrayList<Map<String, Object>> getEventById(String id)
    {
        try{
            ConnectionRequest r= new ConnectionRequest();
            r.setUrl("http://tilessubdomain.ma1geek.org/getEventById.php");
            r.setPost(false);
            r.addArgument("eventID", id);
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result= new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            ArrayList<Map<String, Object>> content = (ArrayList<Map<String, Object>>)result.get("root");
            return content;
        }
        catch(Exception e)
        {
            Log.e(e);
            return null;
        }
    }

    public Image getImage(String url, int width, int height)
    {
        Image placeholder = Image.createImage(width, height);
        EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
        Image img = getCloud().url()
                .type("fetch")
                .format("jpg")
                .transformation(new Transformation().crop("fill").width(width))
                .image(encImage, url);

        return(img);
    }
    /* EXAMPLE
      DatabaseHelper base = new DatabaseHelper();
    System.out.println(base.getImage("http://res.cloudinary.com/ask-milton/image/upload/v1494548819/vils6bur962awvvapqe4.png", 300, 300));
 */

    public Map<String, Object> uploadImage(String filePath){
        try {
            Map<String,Object> uploadResult = getCloud().uploader().upload(filePath, ObjectUtils.emptyMap());
            return uploadResult;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /* EXAMPLE
          DatabaseHelper base = new DatabaseHelper();
        System.out.println(base.uploadImage(filePath).get("url").toString());
*/

    private Cloudinary getCloud(){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "tiles",
                "api_key", "817285289173163",
                "api_secret", "H9YMuHi-ZYdtLAMfyXpfbQFZ4OI"));
        return(cloudinary);
    }

    public ArrayList getEventIDs()
    {
        ArrayList hello = new ArrayList();


        return hello;
    }

    public Map<String, Object> getUserByID(String id){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tutormilton.ma1geek.org/getUser.php");
            r.setPost(false);
            r.addArgument("id",id);
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return result;
        } catch(Exception err) {
            Log.e(err);
            return null;
        }
    	/* EXAMPLE
   	 	DatabaseHelper base = new DatabaseHelper();
        System.out.println(base.getUserByID("123456"));
        */
    }
    public ArrayList<Map<String, Object>> getPosts(String className){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tilessubdomain.ma1geek.org/getEvents.php");
            r.setPost(false);
            r.addArgument("schoolID",className);
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData())));
            ArrayList<Map<String, Object>> content = (ArrayList<Map<String, Object>>)result.get("root");
            return content;
        } catch(Exception err) {
            Log.e(err);
            return null;
        }
    	/* EXAMPLE
   	 	DatabaseHelper base = new DatabaseHelper();
        System.out.println(base.getPosts("Programming 2"));
        */
    }
    public ArrayList<Map<String, Object>> getComments(String postID){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tutormilton.ma1geek.org/getComments.php");
            r.setPost(false);
            r.addArgument("qid",postID);
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            ArrayList<Map<String, Object>> content = (ArrayList<Map<String, Object>>)result.get("root");
            return content;
        } catch(Exception err) {
            Log.e(err);
            return null;
        }
    	/* EXAMPLE
   	 	DatabaseHelper base = new DatabaseHelper();
        System.out.println(base.getComments("12345"));
        */
    }
    public boolean addUser(Map<String,String> params){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tilessubdomain.ma1geek.org/addUser.php");
            r.setPost(true);
            for(String key: params.keySet())
            {
                r.addArgument(key,params.get(key));
            }
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return Boolean.parseBoolean(result.get("worked").toString());
        } catch(Exception err) {
            Log.e(err);
            return false;
        }
    	/* EXAMPLE
    	 DatabaseHelper base = new DatabaseHelper();
         Map<String,String> params = new HashMap<String, String>();
         params.put("id", "111");
         params.put("username", "helloWorld");
         params.put("name", "Test Hello");
         params.put("class", "2018");
         params.put("email", "test_hello18@milton.edu");
         params.put("rating", "2");
         params.put("bio", "i am a test user");
         params.put("classes", "|Programming 2||English Workshop|");
         System.out.println(base.addUser(params));
         */
    }
    public boolean addComment(Map<String,String> params){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tutormilton.ma1geek.org/addComment.php");
            r.setPost(true);
            for(String key: params.keySet())
            {
                r.addArgument(key,params.get(key));
            }
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return Boolean.parseBoolean(result.get("worked").toString());
        } catch(Exception err) {
            Log.e(err);
            return false;
        }
    	/* EXAMPLE
    	 DatabaseHelper base = new DatabaseHelper();
         Map<String,String> params = new HashMap<String, String>();
         params.put("username", "scottyc");
         params.put("comment", "my test comment");
         params.put("qid", "12345");
         System.out.println(base.addComment(params));
         */
    }
    public boolean addPost(Map<String,String> params){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tilessubdomain.ma1geek.org/addEvent.php");
            r.setPost(true);

            for(String key: params.keySet())
            {
                r.addArgument(key,params.get(key));

            }
            System.out.println(params);
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return Boolean.parseBoolean(result.get("worked").toString());
        } catch(Exception err) {
            Log.e(err);
            return false;
        }
    	/* EXAMPLE

    	$sql = "INSERT INTO Events (EventID, Title, Latitude, Longitude, Location, Description, UserPosted, IfResponded, Responders, ImageURL, Date, Time, Organization, SchoolID)
VALUES ('$eventID', '$title', '$latitude', '$longitude', '$location', '$description', '$userPosted', '$ifResponded', '$responders', '$imageURL', '$date','$time','$organization', '$schoolID)";
    	 DatabaseHelper base = new DatabaseHelper();
    	 Date d = new Date();
         Map<String,String> params = new HashMap<String, String>();
         params.put("username", "scottyc");
         params.put("title", "my test post");
         params.put("body", "hey guys whats up");
         params.put("qid", d.getTime() + "");
         params.put("pic", "www.memes.com/asfdfsa");
         params.put("course", "Programming 2");
         System.out.println(base.addPost(params));
         */
    }
    public boolean deletePost(String qid){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tilessubdomain.ma1geek.org/deleteEvent.php");
            r.setPost(true);
            r.addArgument("eventID", qid);
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return Boolean.parseBoolean(result.get("worked").toString());
        } catch(Exception err) {
            Log.e(err);
            return false;
        }
    	/* EXAMPLE (might not work if post 1010 does not exist)
    	 DatabaseHelper base = new DatabaseHelper();
         System.out.println(base.deletePost("1010"));
         */
    }
    public int addLike(String qid, String username){ //returns likes if works, -10000 if doesn't. Changing likes also changes user rating
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tutormilton.ma1geek.org/addLike.php");
            r.setPost(true);
            r.addArgument("qid", qid);
            r.addArgument("username", "|" + username + "|");
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return Integer.parseInt(result.get("Likes").toString());
        } catch(Exception err) {
            Log.e(err);
            return -10000;
        }
    	/* EXAMPLE (might not work if post 1010 does not exist)
    	 DatabaseHelper base = new DatabaseHelper();
         System.out.println(base.addLike("1494292552458","scottyc"));
         */
    }
    public int removeLike(String qid, String username){ //returns likes if works, -10000 if doesn't. Changing likes also changes user rating
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tutormilton.ma1geek.org/removeLike.php");
            r.setPost(true);
            r.addArgument("qid", qid);
            r.addArgument("username", "|" + username + "|");
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return Integer.parseInt(result.get("Likes").toString());
        } catch(Exception err) {
            Log.e(err);
            return -10000;
        }
    	/* EXAMPLE (might not work if post 1010 does not exist)
    	 DatabaseHelper base = new DatabaseHelper();
         System.out.println(base.removeLike("1494292552458","scottyc"));
         */
    }

}
