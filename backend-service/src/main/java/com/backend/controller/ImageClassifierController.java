package com.backend.controller;

import com.backend.cognitiveservices.CustomVisionProject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@RestController  
public class ImageClassifierController
{
    @CrossOrigin(origins = "https://frontend2022.azurewebsites.net")
    @RequestMapping(value = "/image", method = RequestMethod.POST)  
    public String saveImage(@RequestBody String imageString) throws IOException, JSONException {
        byte[] imageBytes = Base64.getDecoder().decode(imageString);
        String imageName = "photo_" + UUID.randomUUID() + ".png";
        // String filename = "/Users/euniceyuan/backend-service/images/" + imageName;
        String filename = "/images/" + imageName;
        if (new File("/images/").exists()) {
            Files.write(new File(filename).toPath(), imageBytes);
        }
        else {
            new File("/images/").mkdir();
            Files.write(new File(filename).toPath(), imageBytes);
        }

        RestTemplate restTemplate = new RestTemplate();
        String trainingEndpoint = "imageclassifier1.cognitiveservices.azure.com";
        String trainingApiKey = "a0e8d6bfcc3843558e14064cb4fa651a";
        String projectID = "f422b129-09bb-4b06-9fe0-e9657ea0b4ad";
        String tagID1 = "98af3bf0-7c4d-4c68-adb6-425d4e9fdc21";

        CustomVisionProject.uploadImages(restTemplate, trainingEndpoint, projectID, trainingApiKey, tagID1, filename);

        return "Image saved successfully";
    }

    @CrossOrigin(origins = "https://frontend2022.azurewebsites.net")
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public String validate(@RequestBody String imageString) throws IOException, JSONException {
        byte[] imageBytes = Base64.getDecoder().decode(imageString);
        // String imageName = "photo_" + UUID.randomUUID() + ".png";
        // String filename = "/Users/euniceyuan/backend-service/images/" + imageName;
        // Files.write(new File(filename).toPath(), imageBytes);

        RestTemplate restTemplate = new RestTemplate();
        String trainingEndpoint = "imageclassifier1.cognitiveservices.azure.com";
        String trainingApiKey = "a0e8d6bfcc3843558e14064cb4fa651a";
        String projectID = "f422b129-09bb-4b06-9fe0-e9657ea0b4ad";
        String publishName = "imageClassifier2Prediction";
        String iterationID = "ba651aba-a5c6-4593-97bd-632b69bc59bf";
        String predictionResourceId = "/subscriptions/0b5b209c-a5cb-41a5-a6cc-5279e7187bdc/resourceGroups/ImageProject/providers/Microsoft.CognitiveServices/accounts/imageClassifier1-Prediction";

        JSONArray predictions = CustomVisionProject.testPredictionEndpoint(restTemplate, trainingEndpoint, projectID, iterationID, publishName, predictionResourceId, trainingApiKey, imageBytes);
        String probability = predictions.getJSONObject(0).getString("probability");
        System.out.println(probability);

        return probability;
    }
}  