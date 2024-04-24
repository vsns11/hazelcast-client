package ca.apiclient.config;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastClientConfig {

    @Autowired
    private HazelcastProperties hazelcastProperties;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(hazelcastProperties.getInstanceName());
//        clientConfig.setProperty("zone", "SHIVA");
//
//        hazelcastProperties.getAddresses().forEach(address ->
//            clientConfig.getNetworkConfig().addAddress(address));

        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}
