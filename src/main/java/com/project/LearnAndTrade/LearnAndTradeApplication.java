package com.project.LearnAndTrade;

import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Service.GetUserData;
import com.project.LearnAndTrade.Service.SearchComplementaryUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class LearnAndTradeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnAndTradeApplication.class, args);
	}

}
