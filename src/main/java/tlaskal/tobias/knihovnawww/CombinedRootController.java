package tlaskal.tobias.knihovnawww;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.FileCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.MustacheException;
import com.samskivert.mustache.Template;

@RestController
public class CombinedRootController {

    @Autowired
    private Mustache.Compiler mustacheCompiler;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/component")
    String component(){
        List<String> komponenty = Arrays.asList("komponenta1", "Komponenta2","Komponenta3");
        try{
            Resource resource = resourceLoader.getResource("classpath:/templates/component.mustache");
            String templateContent = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            Template template = mustacheCompiler.compile(templateContent);

            Map<String, Object> model = new HashMap<>();
            model.put("komponenty", komponenty);
            return template.execute(model);
        } catch (MustacheException | IOException e){
            e.printStackTrace();
            return "Error rendering template";
        }
    }

    @GetMapping("/test")
    String helloWorldSecret(@RequestParam("message") String message){
        return String.format("Hello World from %s!", message);
    }
}
