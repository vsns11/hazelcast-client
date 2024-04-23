package ca.siva.service;



import ca.siva.model.PostRequest;
import ca.siva.repository.PostRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class PostRequestService {

    private final PostRequestRepository postRequestRepository;

    public PostRequestService(final PostRequestRepository postRequestRepository) {
        this.postRequestRepository = postRequestRepository;
        log.info("Initialized postRequestService...");
    }

    public PostRequest save(PostRequest postRequest) {
        return postRequestRepository.save(postRequest);
    }

    public void saveAll(List<PostRequest> postRequests) {
        postRequestRepository.saveAll(postRequests);
    }

    public void deleteById(String id) {
        postRequestRepository.deleteById(id);
    }

    public void deleteAllByIds(List<String> ids) {
        ids.forEach(postRequestRepository::deleteById);
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
}
