import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class apitest {

    public Playwright  playwright;
    public Browser browser;
    public Page page;
    public BrowserContext context;
    public APIResponse response;
    APIRequestContext request;


@BeforeMethod
public void setup() {
    playwright = Playwright.create();
    browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    context=browser.newContext();
    page=context.newPage();
    request = playwright.request().newContext();
}

    @Test(priority = 0)
    public void gettest() {

        response=request.get("https://reqres.in/api/users",RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setHeader("x-api-key","pro_e18802548621c36ab0e58fe3ba7ebbe18638c179787ba21f2b19efe8df375a27")
        );


        // prints the response status code
        System.out.println("the response Status code: " + response.status());


        //prints the response status
        System.out.println("the response Status: " + response.statusText());


        // checks that response is received successfully
        assertThat(response).isOK();


        // checks the status code
        Assert.assertEquals(response.status(),200);


        // prints the text representation response body
        System.out.println("the response Body: " + response.text());


        // prints the response headers
        System.out.println("the response Headers: "+ response.headers());


        // prints the endpoint used in connection
        System.out.println("the response endpoint: "+ response.url());


        // prints the value of content-type
        System.out.println("the content-type: "+response.headers().get("content-type"));


        //checks value of the content-type
        Assert.assertTrue(response.headers().get("content-type").contains("application/json; charset=utf-8"));


        // calculates the system time after the response
        System.out.println("the response Time: "+System.currentTimeMillis());

    }


    @Test(priority = 1)
    public void posttest() {

        // Request payload (body)
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "Hatem");
        requestBody.put("job", "QA Engineer");

        //create a new resource using the given request body
         response = request.post(
                "https://reqres.in/api/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("x-api-key","pro_e18802548621c36ab0e58fe3ba7ebbe18638c179787ba21f2b19efe8df375a27")
                        .setData(requestBody)
        );

        // prints the response status code of POST request
        System.out.println("the response Status code: " + response.status());


        // prints the response status message
        System.out.println("the response Status: "+ response.statusText());


        // prints text representation of response of POST request
        System.out.println("the response Body: " + response.text());


        // prints the response headers
        System.out.println("the response Headers: "+ response.headers());


        // prints the value of content-type
        System.out.println("the content-type: "+ response.headers().get("content-type"));


         //checks the existance of content-type in the response headers and its value
        Assert.assertEquals(response.headers().get("content-type"), "application/json; charset=utf-8");


        //checks the status code of POST request
        Assert.assertEquals(response.status(), 201);


        //checks the status text of POST request
        Assert.assertEquals(response.statusText(), "Created");
    }



    @Test(priority = 2)
    public void putest() {

        Map<String, String> updatedrequestBody = new HashMap<>();
        updatedrequestBody.put("name", "eslam");
        updatedrequestBody.put("job", "teacher");
        response = request.put("https://reqres.in/api/users/1", RequestOptions.create().
                setData(updatedrequestBody).
                setHeader("Content-Type", "application/json")
                .setHeader("x-api-key","pro_e18802548621c36ab0e58fe3ba7ebbe18638c179787ba21f2b19efe8df375a27")
        );

        // prints response status code of PUT request
        System.out.println("the response Status code: " + response.status());


        // prints text representation for Response body of PUT request
        System.out.println("the response Body: " + response.text());


        // checks the status code of PUT request
        Assert.assertEquals(response.status(), 200);


        // checks the response body after Full update
        Assert.assertTrue(response.text().contains("eslam"));


        // checks the response body after Full update
        Assert.assertTrue(response.text().contains("teacher"));


        // checks the content-type in the response headers and its value
        Assert.assertTrue(response.headers().get("content-type").contains("application/json; charset=utf-8"));
    }



    @Test(priority = 3)
    public void patchtest(){

        Map<String, String> updatedrequestBody = new HashMap<>();
        updatedrequestBody.put("name", "ziad");
        updatedrequestBody.put("job", "teacher");
        response=request.put("https://reqres.in/api/users/1",RequestOptions.create().
                setData(updatedrequestBody).
                setHeader("Content-Type", "application/json")
                .setHeader("x-api-key","pro_e18802548621c36ab0e58fe3ba7ebbe18638c179787ba21f2b19efe8df375a27")
        );

        // prints response status code of PATCH request
        System.out.println("the response Status code: " + response.status());


        // checks the response status of PATCH request
        Assert.assertEquals(response.statusText(),"OK");


        // prints text representation for Response body of PATCH request
        System.out.println("the response Body: " + response.text());


        // checks the status code of PATCH request
        Assert.assertEquals(response.status(), 200);


        // checks response body after partial update
        Assert.assertTrue(response.text().contains("ziad"));


        // checks the content-type in the response headers and its value
        Assert.assertTrue(response.headers().get("content-type").contains("application/json; charset=utf-8"));
    }


    @Test(priority = 4)
    public void deletetest(){
        response=request.delete("https://reqres.in/api/users/1",RequestOptions.create().
                 setHeader("x-api-key", "pro_e18802548621c36ab0e58fe3ba7ebbe18638c179787ba21f2b19efe8df375a27")
                .setHeader("Content-Type", "application/json")
        );

        // prints the response status code after DELETE request
        System.out.println("the response Status code: " + response.status());


        // prints the response body after DELETE request
        System.out.println("the response Body: " + response.text());


        // prints the response status after DELETE request
        System.out.println("the response Status: " + response.statusText());
    }


    @Test(priority = 5)
    public void posttestexternaldata() throws IOException {

        String jsonBody = Files.readString(
                Paths.get("src/test/resources/data.json")
        );

        response=request.post("https://reqres.in/api/users/1",RequestOptions.create()
                .setData(jsonBody).
                setHeader("Content-Type", "application/json")
                .setHeader("x-api-key", "pro_e18802548621c36ab0e58fe3ba7ebbe18638c179787ba21f2b19efe8df375a27")

        );

        // prints the response status code after POST request
        System.out.println("the response Status code: " + response.status());


        // prints the response status
        System.out.println("the response Status: " + response.statusText());


        // prints the text representation for response of POST request
        System.out.println("the response Body: " + response.text());


        // checks the status code of the POST request
        Assert.assertEquals(response.status(), 201);


        // checks the status of the POST request
        Assert.assertEquals(response.statusText(), "Created");


        // checks the response after POST request using external data
        Assert.assertTrue(response.text().contains("cairo"));
    }

}
