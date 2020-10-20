package stepDef;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class petStoreStepDef {
	private static String newPetID;
	private static String jsonString;
	
	private String ENDPOINT_PET_STORE = "https://petstore.swagger.io/v2/pet";
	public static Logger logger = Logger.getLogger(commonStepDef.class);
	

@Given("^Rest Assured: Get all pets from store based on status \"([^\"]*)\" and verify expected results$")
public void rest_Assured_Get_all_pets_from_store_based_on_status_and_verify_expected_results(String status) {
try {
	String finalURI = ENDPOINT_PET_STORE + "/findByStatus?status=" + status;
	logger.info("Final URL : "+ finalURI);
	Response response = RestAssured.get(finalURI);
	if(response.getStatusCode()==200) {
		jsonString = response.asString();
		logger.info("Response : " + jsonString);
		List<Map<String, String>> pets = JsonPath.from(jsonString).get("id");
		System.out.println("No. of pets in store :" + pets.size() + " having status as " + status);
		Assert.assertTrue(pets.size() > 0);
	}
	else
		logger.info("Error in Get reqeest" + response.getStatusCode());
	
}catch(Exception e) {
	System.out.println("Exception occured: "+ e.getStackTrace());
}
}

@Given("^Rest Assured: Add new pet to store with name \"([^\"]*)\" and status \"([^\"]*)\" and verify new pet added$")
public void rest_Assured_Add_new_pet_to_store_with_name_and_status_and_verify_new_pet_added(String petName, String status) {
	String jsonBody = "{\r\n"
			+ "  \"id\": 0,\r\n"
			+ "  \"category\": {\r\n"
			+ "    \"id\": 0,\r\n"
			+ "    \"name\": \"string\"\r\n"
			+ "  },\r\n"
			+ "  \"name\": \"<petname>\",\r\n"
			+ "  \"photoUrls\": [\r\n"
			+ "    \"string\"\r\n"
			+ "  ],\r\n"
			+ "  \"tags\": [\r\n"
			+ "    {\r\n"
			+ "      \"id\": 0,\r\n"
			+ "      \"name\": \"string\"\r\n"
			+ "    }\r\n"
			+ "  ],\r\n"
			+ "  \"status\": \"<petstatus>\"\r\n"
			+ "}";
	String finalJsonBody = null;
	
	if(petName != null && status != null)
	finalJsonBody = jsonBody.replace("<petname>", petName).replace("<petstatus>", status);
	else
		logger.error("Pet name and Status should not be blank");
	
	logger.info("Final Json Body : \n" + finalJsonBody);
	
	try {
		Response response = RestAssured.given().
				contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(finalJsonBody)
				.when()
				.post(ENDPOINT_PET_STORE);
		System.out.println("POST Response\n" + response.asString());
		
		JsonPath jsonPathValidator = response.jsonPath();
		 
		long petsId = jsonPathValidator.getLong("id");
		String expStatus = jsonPathValidator.get("status");
		
			System.out.println("New pet Id created :" + petsId);
			System.out.println("Status : "+ expStatus);
		logger.info("Pets ID : " + petsId + " with status: "+ expStatus);	
		
		//Assign to variable to used in other steps
				newPetID = Long.toString(petsId);
			
				
		//Assert response pets ID should not be empty and status should matched
			if((petsId > 0.0) && status.equalsIgnoreCase(expStatus))
				Assert.assertTrue("New pet not created", true);
			else
				Assert.assertTrue("New pet not created", false);
		
	}catch(Exception e) {
		System.out.println("Exception occured: "+ e.getStackTrace());
	}
}

@When("^Rest Assured: Update the pet status to \"([^\"]*)\" for pet name \"([^\"]*)\" and verify the expected status$")
public void rest_Assured_Update_the_pet_status_to_for_pet_name_and_verify_the_expected_status(String status, String petName) {
	String jsonBody = "{\r\n"
			+ "  \"id\": <givenID>,\r\n"
			+ "  \"category\": {\r\n"
			+ "    \"id\": 0,\r\n"
			+ "    \"name\": \"string\"\r\n"
			+ "  },\r\n"
			+ "  \"name\": \"<petname>\",\r\n"
			+ "  \"photoUrls\": [\r\n"
			+ "    \"string\"\r\n"
			+ "  ],\r\n"
			+ "  \"tags\": [\r\n"
			+ "    {\r\n"
			+ "      \"id\": 0,\r\n"
			+ "      \"name\": \"string\"\r\n"
			+ "    }\r\n"
			+ "  ],\r\n"
			+ "  \"status\": \"<petstatus>\"\r\n"
			+ "}";
	String finalJsonBody = null;
	
	if(petName != null && status != null && newPetID!= null)
	finalJsonBody = jsonBody.replace("<givenID>", newPetID).replace("<petname>", petName).replace("<petstatus>", status);
	else
		logger.error("Pet name and Status should not be blank");
	
	logger.info("Final Json Body : \n" + finalJsonBody);
	
	try {
		Response response = RestAssured.given().
				contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(finalJsonBody)
				.when()
				.put(ENDPOINT_PET_STORE);
		System.out.println("PUT Response\n" + response.asString());
		
		JsonPath jsonPathValidator = response.jsonPath();
		 
		long petsId = jsonPathValidator.getLong("id");
		String expStatus = jsonPathValidator.get("status");
		
		logger.info("Pets ID : " + petsId + " with status: "+ expStatus);	
		
		
		//Assert response pets ID should match with given petsID and status should also matched
			if((Long.toString(petsId).equalsIgnoreCase(newPetID)) && status.equalsIgnoreCase(expStatus))
				Assert.assertTrue("New pet not updated", true);
			else
				Assert.assertTrue("New pet not updated", false);	
		
	}catch(Exception e) {
		System.out.println("Exception occured: "+ e.getStackTrace());
	}
}

@Then("^Rest Assured: Delete the pet and verify deletion$")
public void rest_Assured_Delete_the_pet_name_and_verify_deletion() {
	try {
		if(newPetID!=null) {
		Response response = RestAssured.delete(ENDPOINT_PET_STORE+"/"+newPetID);
		System.out.println("Delete Response\n" + response.asString());
		
		JsonPath jsonPathValidator = response.jsonPath();
		 
		Integer code = jsonPathValidator.getInt("code");
		String expMessage = jsonPathValidator.get("message");
		
		logger.info("Return code : " + code + " with message: "+ expMessage);	
		
		
		//Assert response pets ID should match with given message and code should be 200
			if(code.equals(200) && expMessage.equalsIgnoreCase(newPetID))
				Assert.assertTrue("New pet not deleted", true);
			else
				Assert.assertTrue("New pet not deleted", false);	
		
	}else logger.info("Pets ID is blank ::" + newPetID);
	}catch(Exception e) {
		System.out.println("Exception occured: "+ e.getStackTrace());
	}

	
}



}
