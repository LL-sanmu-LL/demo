package com.example.demo.statemachine2;

import java.util.EnumSet;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.data.redis.RedisStateMachinePersister;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;

@Configuration
//@EnableStateMachine(name = "stateMachine") // 单例状态机
@EnableStateMachineFactory(name = "processFactory") // 状态机工厂，用于创建多实例
public class Config extends EnumStateMachineConfigurerAdapter<ProcessStates, ProcessEvents> {

  @Resource
  private ProcessActionImpl action;


  @Override
  public void configure(StateMachineConfigurationConfigurer<ProcessStates, ProcessEvents> config)
      throws Exception {
    config
        .withConfiguration()
        .listener(new ProcessListener())
        .autoStartup(true);
  }

  @Override
  public void configure(StateMachineStateConfigurer<ProcessStates, ProcessEvents> states)
      throws Exception {
    states
        .withStates()
        .initial(ProcessStates.init)
        .end(ProcessStates.end)
        .states(EnumSet.allOf(ProcessStates.class));
  }

  @Override
  public void configure(
      StateMachineTransitionConfigurer<ProcessStates, ProcessEvents> transitions)
      throws Exception {
    transitions
        .withExternal()
        .source(ProcessStates.init).target(ProcessStates.state1)
        .event(ProcessEvents.event1)
        .action(context -> {
              try {
                action.methodA(context);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            },
            context -> action.exception(context))
        .and()
        .withExternal()
        .source(ProcessStates.state1).target(ProcessStates.end)
        .event(ProcessEvents.event2)
        .action(context -> action.methodB(context),
            context -> action.exception(context));

  }

  @Bean
  public StateMachinePersist<ProcessStates, ProcessEvents, String> processPersist(
      RedisConnectionFactory connectionFactory) {
    RedisStateMachineContextRepository<ProcessStates, ProcessEvents> repository =
        new RedisStateMachineContextRepository<>(connectionFactory);
    return new RepositoryStateMachinePersist<>(repository);
  }

  @Bean("redisStateMachinePersister")
  public RedisStateMachinePersister<ProcessStates, ProcessEvents> redisProcesseRsister(
      StateMachinePersist<ProcessStates, ProcessEvents, String> processPersist) {
    return new RedisStateMachinePersister<>(processPersist);
  }
}
