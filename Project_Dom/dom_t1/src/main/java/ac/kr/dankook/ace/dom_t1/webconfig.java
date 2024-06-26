package ac.kr.dankook.ace.dom_t1;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webconfig implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resitry){
        resitry.addResourceHandler("/upload/**")
            .addResourceLocations("file:///C:/Dom/image/");
    }
}
