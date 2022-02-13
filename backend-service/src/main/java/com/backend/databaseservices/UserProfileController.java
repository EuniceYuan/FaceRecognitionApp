package com.backend.databaseservices;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@CrossOrigin(origins = "https://frontend2022.azurewebsites.net")
public class UserProfileController {

    private final UserProfileRepository userProfileRepository;

    public UserProfileController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @RequestMapping(value = "/createProfile", method = RequestMethod.POST)
    public UserProfile createUserProfile(@RequestBody UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @RequestMapping(value = "/getAllProfile", method = RequestMethod.GET)
    public Iterable<UserProfile> getUserProfile() {
        return userProfileRepository.findAll();
    }

    @RequestMapping(value = "/getByName/{fullname}", method = RequestMethod.GET)
    public Iterable<UserProfile> getByName(@PathVariable("fullname") String fullname) {
        System.out.println("fullname: " + fullname);
        return userProfileRepository.findByFullName(fullname);
    }

    @RequestMapping(value = "/getByEmail/{email}", method = RequestMethod.GET)
    public Iterable<UserProfile> getByEmail(@PathVariable("email") String email) {
        return userProfileRepository.findByEmail(email);
    }

    @RequestMapping(value = "/getProfile/{fullname}/{email}", method = RequestMethod.GET)
    public Iterable<UserProfile> getProfile(@PathVariable("email") String email, @PathVariable("fullname") String fullname) {
        return userProfileRepository.findByEmailOrFullname(email, fullname);
    }
}
