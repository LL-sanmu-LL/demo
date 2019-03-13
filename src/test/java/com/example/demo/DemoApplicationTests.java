package com.example.demo;

import com.example.demo.statemachine2.ProcessEvents;
import com.example.demo.statemachine2.ProcessStates;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Resource
	private StateMachine<ProcessStates, ProcessEvents> stateMachine;

	@Test
	public void contextLoads() {
		stateMachine.sendEvent(ProcessEvents.event1);
		stateMachine.sendEvent(ProcessEvents.event1);
	}

}
