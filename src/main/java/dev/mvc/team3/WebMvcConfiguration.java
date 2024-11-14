package dev.mvc.team3;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.mvc.account.Profiles;
import dev.mvc.breply.Breply;
import dev.mvc.brereply.Brereply;
import dev.mvc.board.Board;
import dev.mvc.spice.Spice;
import dev.mvc.tool.Tool;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Windows: path = "C:/kd/deploy/resort_v2sbm3c_blog/contents/storage";
        // ▶ file:///C:/kd/deploy/resort_v2sbm3c_blog/contents/storage

        // Ubuntu: path = "/home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage";
        // ▶ file:////home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage

        //
        // JSP 인식되는 경로: http://localhost:9093/profiles/storage";
        registry.addResourceHandler("/profiles/storage/**").addResourceLocations("file:///" +  Profiles.getUploadDir());
        
        //
        // JSP 인식되는 경로: http://localhost:9093/contents/storage";
        registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Breply.getUploadDir());
        
        // 게시판 사진
        // JSP 인식되는 경로: http://localhost:9093/board/storage";
        registry.addResourceHandler("/board/storage/**").addResourceLocations("file:///" +  Board.getUploadDir());

        registry.addResourceHandler("/brereply/storage/**").addResourceLocations("file:///" +  Brereply.getUploadDir());

        registry.addResourceHandler("/spice/storage/**").addResourceLocations("file:///" +  Spice.getUploadDir());
        
        // registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Contents.getUploadDir());
        // JSP 인식되는 경로: http://localhost:9091/attachfile/storage";
        // registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/attachfile/storage/");
        
        // JSP 인식되는 경로: http://localhost:9091/member/storage";
        // registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/member/storage/");
    }
    
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                   .allowedOriginPatterns("*")
                   .allowedMethods("GET", "POST", "PUT", "DELETE")
                   .allowedHeaders("*")
                   .allowCredentials(true);
    }   
    
}