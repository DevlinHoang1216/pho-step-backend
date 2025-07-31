package fpt.duantotnghiep.sd28.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry; // Import this
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:8080") // Allow Vue dev server and backend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Thêm Resource Handler để phục vụ các file tĩnh từ thư mục uploads/images/
        // Các yêu cầu đến /images/** sẽ được ánh xạ tới thư mục file:./uploads/images/
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:./uploads/images/"); // Đảm bảo đường dẫn này chính xác
    }
}
