package bookstore.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SslOptions;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Value("${spring.data.redis.ssl.enabled}")
    private boolean redisSslEnabled;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // Cấu hình Standalone Redis
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        redisConfig.setUsername(redisUsername);
        redisConfig.setPassword(redisPassword);
        redisConfig.setDatabase(0); // Khớp với application.yml

        // Cấu hình SSL
        LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfigBuilder =
                LettuceClientConfiguration.builder();

        if (redisSslEnabled) {
            clientConfigBuilder.useSsl()
                    .disablePeerVerification() // Tắt xác minh peer để đơn giản hóa
                    .and()
                    .clientOptions(ClientOptions.builder()
                            .sslOptions(SslOptions.builder().build())
                            .build());
        }

        // Tạo LettuceConnectionFactory
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfig, clientConfigBuilder.build());
        factory.setValidateConnection(true); // Kiểm tra kết nối khi khởi tạo
        return factory;
    }

    @Bean
    @Primary
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}