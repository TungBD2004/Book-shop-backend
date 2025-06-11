package bookstore.Service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailTemplateService {
    public String renderTemplate(String templateName, Map<String, String> variables) throws IOException {
        String path = "templates/email/" + templateName;
        ClassPathResource resource = new ClassPathResource(path);

        String template = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        for (Map.Entry<String, String> entry : variables.entrySet()) {
            String placeholder = "${" + entry.getKey() + "}";
            template = template.replace(placeholder, entry.getValue());
        }

        return template;
    }
}
