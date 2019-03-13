package com.example.demo.statemachine2;

import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Service;

@Service
public class ProcessActionImpl {
  public void methodA(StateContext<ProcessStates, ProcessEvents> context)
      throws InterruptedException {
    System.out.println("action methodA");
  }
  public void methodB(StateContext<ProcessStates, ProcessEvents> context){
    System.out.println("action methodB");
//    int i = 1/0;
  }
  public void exception(StateContext<ProcessStates, ProcessEvents> context){
    System.out.println("exception");
  }
}
