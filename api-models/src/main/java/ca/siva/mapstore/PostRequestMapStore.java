package ca.siva.mapstore;


import ca.siva.model.PostRequest;
import ca.siva.service.PostRequestService;
import com.hazelcast.map.MapStore;
import com.hazelcast.spring.context.SpringAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@SpringAware
@Slf4j
public class PostRequestMapStore implements MapStore<String, PostRequest> {

    private final PostRequestService postRequestService;

    public PostRequestMapStore(final PostRequestService postRequestService) {
        this.postRequestService = postRequestService;
        log.info("Initialized postRequestMapStore...");
    }

    @Override
    public void store(String key, PostRequest value) {
        postRequestService.save(value);
    }

    @Override
    public void storeAll(Map<String, PostRequest> map) {
        postRequestService.saveAll(map.values().stream().toList());
    }

    @Override
    public void delete(String key) {
        postRequestService.deleteById(key);
    }

    @Override
    public void deleteAll(Collection<String> keys) {
        postRequestService.deleteAllByIds(keys.stream().toList());
    }

    @Override
    public PostRequest load(String key) {
        log.info("Loading key: {} from the postRequest table", key);
        return postRequestService.findById(key).orElse(null);
    }

    @Override
    public Map<String, PostRequest> loadAll(Collection<String> keys) {
        Map<String, PostRequest> result = new HashMap<>();
        log.info("Loading all the value records from the postRequest table...");
        postRequestService.findAllByIds(keys.stream().toList()).forEach(postRequest -> result.put(postRequest.getId(), postRequest));
        return result;
    }

    @Override
    public Iterable<String> loadAllKeys() {
        log.info("Loading all the keys from the postRequest table");
        return postRequestService.findAll().stream().map(PostRequest::getId).toList();
    }
}
