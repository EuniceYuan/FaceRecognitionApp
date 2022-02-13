package com.backend.cognitiveservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CustomVisionProject {

    public static void main(String[] args) throws JSONException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        String projectName = "imageClassifier2";
        String trainingEndpoint = "imageclassifier1.cognitiveservices.azure.com";
        String trainingApiKey = "a0e8d6bfcc3843558e14064cb4fa651a";
        String predictionResourceId = "/subscriptions/0b5b209c-a5cb-41a5-a6cc-5279e7187bdc/resourceGroups/ImageProject/providers/Microsoft.CognitiveServices/accounts/imageClassifier1-Prediction";
        String publishName = "imageClassifier2Prediction";
        String tagName1 = "eunice";
        String tagName2 = "others";

        // String projectID = createProject(restTemplate, trainingEndpoint, projectName, trainingApiKey);
        String projectID = "f422b129-09bb-4b06-9fe0-e9657ea0b4ad";
        //System.out.println("Project Created Status " + !projectID.isEmpty());

        // String tagID1 = addTags(restTemplate, trainingEndpoint, projectID, tagName1, trainingApiKey);
        // String tagID2 = addTags(restTemplate, trainingEndpoint, projectID, tagName2, trainingApiKey);
        String tagID1 = "98af3bf0-7c4d-4c68-adb6-425d4e9fdc21";
        String tagID2 = "441d8f85-4e41-4768-8a78-fbe3e7ca72ee";
        //System.out.println(tagName1 + " Tag Added Status " + !tagID1.isEmpty());
        //System.out.println(tagName2 + " Tag Added Status " + !tagID2.isEmpty());

        // String uploadStatus1 = uploadImages(restTemplate, trainingEndpoint, projectID, trainingApiKey, tagID1);
        // System.out.println("Image Upload Status Of " + tagName1 + " Tag: " + uploadStatus1);
        // String uploadStatus2 = uploadImages(restTemplate, trainingEndpoint, projectID, trainingApiKey, tagID2);
        //System.out.println("Image Upload Status Of " + tagName2 + " Tag: " + uploadStatus2);

        // String imageName = "photo_" + UUID.randomUUID() + ".png";
        // String filename = "/Users/euniceyuan/backend-service/images/" + imageName;
        // uploadImages(restTemplate, trainingEndpoint, projectID, trainingApiKey, tagID2, filename);

        // String iterationID = trainProject(restTemplate, trainingEndpoint, projectID, trainingApiKey, tagID1);
        //System.out.println("Project Training Status " + !iterationID.isEmpty());

        String iterationID = "ba651aba-a5c6-4593-97bd-632b69bc59bf";
        // publishIteration(restTemplate, trainingEndpoint, projectID, iterationID, publishName, predictionResourceId, trainingApiKey);
        //System.out.println("Project Publish Status " + publishStatus);

        String imageName = "photo_0b02b371-e41e-405c-8359-9e03a79adb7c.png";
        String filename = "/Users/euniceyuan/Desktop/fruits/" + imageName;
        // JSONArray predictions = testPredictionEndpoint(restTemplate, trainingEndpoint, projectID, iterationID, publishName, predictionResourceId, trainingApiKey, filename);
        // System.out.println(predictions);
    }

    public static String createProject(RestTemplate restTemplate, String trainingEndpoint, String projectName, String trainingApiKey) throws JSONException {
        String url = "https://{endpoint}/customvision/v3.3/Training/projects?name={name}";

        Map<String, String> params = new HashMap<>();
        params.put("endpoint", trainingEndpoint);
        params.put("name", projectName);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        // System.out.println(builder.buildAndExpand(params).toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Training-key", trainingApiKey);

        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.buildAndExpand(params).toUri(), request, String.class);
        // System.out.println(response.getBody())

        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject.getString("id");
    }

    public static String addTags(RestTemplate restTemplate, String trainingEndpoint, String projectID, String tagName, String trainingApiKey) throws JSONException {
        String url = "https://{endpoint}/customvision/v3.3/Training/projects/{projectId}/tags?name={name}";
        // String url = "https://imageclassifier1.cognitiveservices.azure.com/customvision/v3.3/Training/projects/f422b129-09bb-4b06-9fe0-e9657ea0b4ad/tags?name=eunice1";

        Map<String, String> params = new HashMap<>();
        params.put("endpoint", trainingEndpoint);
        params.put("projectId", projectID);
        params.put("name", tagName);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        // System.out.println(builder.buildAndExpand(params).toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Training-key", trainingApiKey);

        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.buildAndExpand(params).toUri(), request, String.class);
        // System.out.println(response.getBody());

        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject.getString("id");
    }

    public static void uploadImages(RestTemplate restTemplate, String trainingEndpoint, String projectID, String trainingApiKey, String tagID, String fileName) throws JSONException, IOException {
        String url = "https://{endpoint}/customvision/v3.3/training/projects/{projectId}/images?tagIds={tagIds}";

        Map<String, String> params = new HashMap<>();
        params.put("endpoint", trainingEndpoint);
        params.put("projectId", projectID);
        params.put("tagIds", tagID);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        // System.out.println(builder.buildAndExpand(params).toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("Training-key", trainingApiKey);

        Path path = Paths.get(fileName);
        byte[] imageFile = Files.readAllBytes(path);

        HttpEntity<byte[]> request = new HttpEntity<>(imageFile, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.buildAndExpand(params).toUri(), request, String.class);
        System.out.println(response.getBody());
    }

    public static String trainProject(RestTemplate restTemplate, String trainingEndpoint, String projectID, String trainingApiKey, String tagID) throws JSONException {
        String url = "https://{endpoint}/customvision/v3.3/Training/projects/{projectId}/train";

        Map<String, String>params = new HashMap<>();
        params.put("endpoint", trainingEndpoint);
        params.put("projectId", projectID);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        // System.out.println(builder.buildAndExpand(params).toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Training-key", trainingApiKey);

        HttpEntity<String> request = new HttpEntity<String>("{}", headers);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.buildAndExpand(params).toUri(), request, String.class);
        System.out.println(response.getBody());

        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject.getString("id");
    }

    public static void publishIteration(RestTemplate restTemplate, String trainingEndpoint, String projectID, String iterationID, String publishName, String predictionId, String trainingApiKey) throws JSONException {
        String url = "https://{endpoint}/customvision/v3.3/training/projects/{projectId}/iterations/{iterationId}/publish?publishName={publishName}&predictionId={predictionId}";

        Map<String, String>params = new HashMap<>();
        params.put("endpoint", trainingEndpoint);
        params.put("projectId", projectID);
        params.put("iterationId", iterationID);
        params.put("publishName", publishName);
        params.put("predictionId", predictionId);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        // System.out.println(builder.buildAndExpand(params).toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Training-key", trainingApiKey);

        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.buildAndExpand(params).toUri(), request, String.class);
        System.out.println(response.getBody());
    }

    public static JSONArray testPredictionEndpoint(RestTemplate restTemplate, String trainingEndpoint, String projectID, String iterationID, String publishName, String predictionId, String trainingApiKey, byte[] imageFile) throws JSONException, IOException {
        // String url = "https://{endpoint}/customvision/v3.3/Training/projects/{projectId}/iterations/{iterationId}/publish?publishName={publishName}&predictionId={predictionId}";
        String url = "https://{endpoint}/customvision/v3.3/training/projects/{projectId}/quicktest/image?iterationId={iterationId}";

        Map<String, String>params = new HashMap<>();
        params.put("endpoint", trainingEndpoint);
        params.put("projectId", projectID);
        params.put("iterationId", iterationID);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        // System.out.println(builder.buildAndExpand(params).toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("Training-key", trainingApiKey);

        // Path path = Paths.get(fileName);
        // byte[] imageFile = Files.readAllBytes(path);

        HttpEntity<byte[]> request = new HttpEntity<>(imageFile, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.buildAndExpand(params).toUri(), request, String.class);
        // System.out.println(response.getBody());

        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONArray predictions = jsonObject.getJSONArray("predictions");
        // String tagName = predictions.getJSONObject(0).getString("tagName");
        // String probability = predictions.getJSONObject(0).getString("probability");

        return predictions;
    }


}
