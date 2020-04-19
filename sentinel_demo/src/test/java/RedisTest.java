import com.sentinel.RedisApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**



**/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisTest {
  @Autowired
  private StringRedisTemplate redisTemplate;

  //哨兵模式 测试
  @Test
  public void test() {
    redisTemplate.opsForValue().set("test", "redis");
    String test = redisTemplate.opsForValue().get("test");
    System.out.println(test);
  }

  //cluster模式
  @Test
  public void test_cluster() {
    redisTemplate.opsForValue().set("test_cluster", "cluster");
    String test = redisTemplate.opsForValue().get("test_cluster");
    System.out.println(test);
  }

  //Twemproxy 模式
  @Test
  public void test_twemproxy() {
    redisTemplate.opsForValue().set("class", "5班");
    String test = redisTemplate.opsForValue().get("class");
    String name = redisTemplate.opsForValue().get("name");
    String age = redisTemplate.opsForValue().get("age");
    System.out.println(test);
    System.out.println(name);
    System.out.println(age);

  }

  //设置大数据量
  @Test
  public void test_set_vlues() {
    Random r = new Random();
    for (int i = 0; i < 100; i++) {
      redisTemplate.opsForValue().set(r.nextInt(100000)+"test","redis");
    }
  }


}
