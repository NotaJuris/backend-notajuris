package br.com.notajuris.notajuris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotajurisApplication {

	public String MYSQLHOST = System.getenv("MYSQLHOST");
	public String MYSQLPORT = System.getenv("MYSQLPORT");
	public String MYSQLDATABASE = System.getenv("MYSQLDATABASE");
	public String MYSQLUSER = System.getenv("MYSQLUSER");
	public String MYSQLPASSWORD = System.getenv("MYSQLPASSWORD");
	public String JWTKEY = System.getenv("JWTKEY");
	public String REDISHOST = System.getenv("REDISHOST");
	public String REDISPORT = System.getenv("REDISPORT");
	public String REDISUSER = System.getenv("REDISUSER");
	public String REDISPASSWORD = System.getenv("REDISPASSWORD");
	public String SPRINGPROFILE = System.getenv("SPRINGPROFILE");

	public static void main(String[] args) {
		SpringApplication.run(NotajurisApplication.class, args);
	}

}