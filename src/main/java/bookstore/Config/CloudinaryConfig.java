package bookstore.Config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "drwbtlcud",
                "api_key", "717531261579219",
                "api_secret", "D7suHMO78rQdDgOdWkLNzFv8xyo",
                "secure", true
        ));
    }
}
