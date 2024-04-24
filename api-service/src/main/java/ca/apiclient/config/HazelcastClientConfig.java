package ca.apiclient.config;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastClientConfig {



    @Bean
    public HazelcastInstance hazelcastInstance() {

        return HazelcastClient.newHazelcastClient();
    }
}
