package ca.siva.mapstore;


import ca.siva.model.PostRequest;
import ca.siva.service.PostRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.MapLoaderLifecycleSupport;
import com.hazelcast.map.MapStore;
import com.hazelcast.spring.context.SpringAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@Slf4j
@SpringAware
public class PostRequestMapStore implements MapStore<String, String>, MapLoaderLifecycleSupport {

    @Autowired
    private PostRequestService postRequestService;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void store(String key, String value) {

        try {
            log.info("Saving record with key:{} to database", key);
            postRequestService.save(key, value);
        } catch (IOException e) {
        }
    }

    @Override
    public void storeAll(Map<String, String> map) {
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
    public String load(String key) {
        log.info("Loading key: {} from the postRequest table", key);
        return postRequestService.findById(key).map(x -> {
            try {
                return objectMapper.writeValueAsString(x);
            } catch (JsonProcessingException e) {
                return null;
            }
        }).orElse(null);
    }

    @Override
    public Map<String, String> loadAll(Collection<String> keys) {
        Map<String, String> result = new HashMap<>();
        log.info("Loading all the value records from the postRequest table...");
        postRequestService.findAllByIds(keys.stream().toList()).forEach(postRequest -> {
            try {
                result.put(postRequest.getId(), objectMapper.writeValueAsString(postRequest));
            } catch (JsonProcessingException e) {

            }
        });
        return result;
    }

    @Override
    public Iterable<String> loadAllKeys() {
        log.info("Loading all the keys from the postRequest table");
        return postRequestService.findAll().stream().map(PostRequest::getId).toList();
    }

    @Override
    public void init(HazelcastInstance hazelcastInstance, Properties properties, String mapName) {
        log.info("Init method execution for PostRequest Mapstore.");
        hazelcastInstance.getConfig().getManagedContext().initialize(this);
    }

    @Override
    public void destroy() {
        log.info("Destroyed PostRequest Mapstore.");
    }
}
