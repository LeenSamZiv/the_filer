package leensamziv.filer;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TheFilerApplication {

    public static void main(String[] args) {
        // SpringApplication.run(TheFilerApplication.class, args);
        SpringApplication springApplication = new SpringApplication(TheFilerApplication.class);
        //Powered By https://blog.csdn.net/shenshen0702/article/details/81154085
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        springApplication.run(args);
    }

}
