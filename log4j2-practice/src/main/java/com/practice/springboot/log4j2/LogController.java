package com.practice.springboot.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Luo Bao Ding
 * @since 2018/5/16
 */
@RestController
public class LogController {


    @RequestMapping("/log")
    public String rootLog() {
        Logger logger = LogManager.getLogger("myLog");
        logger.warn("hello world");
        Logger consoleLogger = LogManager.getLogger();
        consoleLogger.warn("console hello world");
        consoleLogger.info("console info hello world");

        return "log: hello world";
    }

}
