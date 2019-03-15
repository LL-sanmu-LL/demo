package com.example.demo.statemachine2;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;

@SpringBootApplication
public class Application implements CommandLineRunner {
//  @Resource
//  private StateMachine<ProcessEvents, ProcessEvents> stateMachine;
  @Resource(name = "processFactory")
  private StateMachineFactory<ProcessStates, ProcessEvents> processFactory;
  @Resource(name = "redisStateMachinePersister")
  private StateMachinePersister<ProcessStates, ProcessEvents, String> persister;

  public static void main(String[] args) {
    System.out.println("SpringBootApplication启动");
    SpringApplication.run(Application.class, args);
    System.out.println("SpringBootApplication关闭");
  }

  @Override
  public void run(String... args) throws Exception {
    // 单实例
//    stateMachine.start();
//    stateMachine.sendEvent(ProcessEvents.event1);
//    stateMachine.sendEvent(ProcessEvents.event2);

// 工厂方式创建多实例
//    Thread.sleep(5000);
//    System.out.println("由状态机工厂创建statemachine");
//    StateMachine<ProcessStates, ProcessEvents> m1 = processFactory.getStateMachine();
//    m1.sendEvent(ProcessEvents.event1);
//    m1.sendEvent(ProcessEvents.event2);
//    System.out.println("由状态机工厂创建statemachine");
    // http://blog.sina.com.cn/s/blog_7d1968e20102wxm2.html
    StateMachine<ProcessStates, ProcessEvents> m1 = processFactory.getStateMachine();
    StateMachine<ProcessStates, ProcessEvents> m2 = processFactory.getStateMachine();
    m1.start();
    System.out.println("m1状态：" + m1.getState().getId());
    m1.sendEvent(ProcessEvents.event1);
    m1.getExtendedState().getVariables().put("name", "李琳");
    persister.persist(m1, "mysm");
    persister.restore(m2, "mysm");
    System.out.println("m2状态：" + m2.getState().getId());
    Map var = m2.getExtendedState().getVariables();
  }

}
