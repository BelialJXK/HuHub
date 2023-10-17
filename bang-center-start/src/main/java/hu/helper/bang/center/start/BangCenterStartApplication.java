package hu.helper.bang.center.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author : Luo Siwei
 * @Date : 2023/2/21
 * @Description : 启动类
 */

@SpringBootApplication
@ComponentScan("hu.helper.bang.center.*")
@MapperScan({"hu.helper.bang.center.**.dao"})
@EnableTransactionManagement
public class BangCenterStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(BangCenterStartApplication.class, args);
    }

}
