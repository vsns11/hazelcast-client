package ca.apiclient.controller;

import ca.siva.model.PostRequest;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public PostController(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostMapping
    public ResponseEntity<String> addPost(@RequestBody PostRequest postRequest) {
        IMap<String, String> map = hazelcastInstance.getMap("POST_REQUEST_MAP");
        postRequest.setId(UUID.randomUUID().toString());
        map.set(postRequest.getId(), postRequest.getDocument());
        return ResponseEntity.ok("Post added successfully with ID: " + postRequest.getId());
    }

}
