package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class HelloController {

    @RequestMapping("hello")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);
        return requestURI;
    }


}
