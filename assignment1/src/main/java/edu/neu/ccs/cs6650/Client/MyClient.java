package edu.neu.ccs.cs6650.Client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class MyClient {
  private WebTarget webTarget;

  // for testing
//  private String url;

  public MyClient(WebTarget webTarget) { //  String url
        this.webTarget = webTarget;

    // for testing
//    this.url = url;
//    Client client = ClientBuilder.newClient();
//    this.webTarget = client.target(url).path("/webapi/myresource");
  }

  public Response postText(Object requestEntity) throws ClientErrorException {
    return webTarget.request(MediaType.TEXT_PLAIN)
        .post(Entity.entity(requestEntity, MediaType.TEXT_PLAIN));
  }

  public String getStatus() throws ClientErrorException {
    WebTarget resource = webTarget;
    return resource.request(MediaType.TEXT_PLAIN).get(String.class);
  }

  // To test get and post from client to server for both localhost and AWS server.
//    public static void main(String[] argv) {
//        MyClient client = new MyClient("http://54.237.220.154:8080/assignment1");
//
////        MyClient mc = new MyClient("http://localhost:8080");
//
////        Response response = client.webTarget.request(MediaType.TEXT_PLAIN).get();
////        String result = response.readEntity(String.class);
////        System.out.println(response.getStatus());
////        System.out.println(result);
//
//
//        String entity = "hello world";
//        Response response = client.webTarget.request(MediaType.TEXT_PLAIN).post(
//            Entity.entity(entity, MediaType.TEXT_PLAIN)
//        );
//
//        String result = response.readEntity(String.class);
//        System.out.println(response.getStatus());
//        System.out.println(result);
//    }
}