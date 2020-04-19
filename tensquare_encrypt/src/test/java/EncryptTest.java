import com.tensquare.encrypt.EncryptApplication;
import com.tensquare.encrypt.rsa.RsaKeys;
import com.tensquare.encrypt.service.RsaService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EncryptApplication.class)
public class EncryptTest {

    @Autowired
    private RsaService rsaService;

    @Before
    public void before() throws Exception{
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void genEncryptDataByPubKey() {
        String data = "{\"title\":\"java\"}";

        try {

            String encData = rsaService.RSAEncryptDataPEM(data, RsaKeys.getServerPubKey());

            System.out.println("data: " + data);
            System.out.println("encData: " + encData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //解密数据
    @Test
    public void encryptBytesTest(){
        String data = "M/MojNsJ6gWKNeVriiWF/bZfeYC3KqveS4phMBqYe+X4/MDcjwBmq6y9nN1Fp76a22pkJQzJmTaf52kzAq2QiWAEPrUp7FDvWGjMUaOlE2nVLWb6uwcschCtf5ihNoHmcT9Z0c9d9nJU98H3uwd1xc1m0Tn0h/BYlS76nRjcotbQGBKxyZl5z/Pf1wPktIAtxzW0zgpDprGUe0UPe7YwlO0pkFhygQ0NNlEj5cZc3Gd/ofOzd/3CBLr/wPcSX/sHaAEzcxXRdbfePFcQ78oT5w4O0RNMROmskE5rIbZJLQTjoaJ+W1VzlPic4YE+s1/JCSWKORayDfRYkYkGUm4SWQ==";

        String newData = null;

        try {
            newData = rsaService.RSADecryptDataPEM(data, RsaKeys.getServerPrvKeyPkcs8());
            System.out.println("解密后的参数为：" + newData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
