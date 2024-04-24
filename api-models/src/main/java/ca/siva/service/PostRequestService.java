package ca.siva.service;

import ca.siva.model.PostRequest;
import ca.siva.repository.PostRequestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class PostRequestService {

    private final PostRequestRepository postRequestRepository;
    private final ObjectMapper objectMapper;  // Jackson's ObjectMapper

    @Autowired
    public PostRequestService(final PostRequestRepository postRequestRepository, ObjectMapper objectMapper) {
        this.postRequestRepository = postRequestRepository;
        this.objectMapper = objectMapper;
        log.info("Initialized postRequestService...");
    }

    public PostRequest save(String key, String postRequestJson) throws IOException {
        PostRequest postRequest = new PostRequest();
        postRequest.setId(key);
        postRequest.setDocument(postRequestJson);
        log.info("Successfully saved record with value to database", postRequestJson);
        return postRequestRepository.save(postRequest);
    }

    public void saveAll(List<String> postRequestJsons) {
        List<PostRequest> postRequests = postRequestJsons.stream()
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, PostRequest.class);
                    } catch (IOException e) {
                        log.error("Error parsing JSON", e);
                        return null;
                    }
                })
                .collect(Collectors.toList());
        postRequestRepository.saveAll(postRequests);
    }

    public Optional<PostRequest> findById(String id) {
        return postRequestRepository.findById(id);
    }

    public List<PostRequest> findAllByIds(List<String> ids) {
        return postRequestRepository.findAllById(ids);
    }

    public List<PostRequest> findAll() {
        return postRequestRepository.findAll();
    }

    public void deleteById(String id) {
        postRequestRepository.deleteById(id);
    }

    public void deleteAllByIds(List<String> ids) {
        ids.forEach(postRequestRepository::deleteById);
    }
}
