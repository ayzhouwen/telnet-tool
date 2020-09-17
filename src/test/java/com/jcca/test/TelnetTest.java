package com.jcca.test;



import com.jcca.common.util.TelnetUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试资产
 * @author lyp
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TelnetTest {

	
	
	@Test
	public void test() {

		TelnetUtil telnet = new TelnetUtil("192.168.59.1", "cisco", "Pwd@1234","cisco","23","Serial0/1/0:0");
		//  System.setOut(new PrintStream("D:/telnet.txt"));
		telnet.connect();
		String data=telnet.getInterfaceData();
		telnet.disconnect();
	}
	
	
}
