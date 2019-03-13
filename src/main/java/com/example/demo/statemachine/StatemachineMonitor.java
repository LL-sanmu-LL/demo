package com.example.demo.statemachine;

import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @Author: Shoukai Huang
 * @Date: 2018/9/28 21:05
 */
@WithStateMachine(id = "turnstileStateMachine")
public class StatemachineMonitor {

    @OnTransition
    public void anyTransition() {
        System.out.println("--- OnTransition --- init");
    }

    @OnTransition(target = "Unlocked")
    public void toState1() {
        System.out.println("--- OnTransition --- toUnocked");
    }

    @OnTransition(target = "Locked")
    public void toState2() {
        System.out.println("--- OnTransition --- toLocked");
    }

    @OnStateChanged(source = "Unlocked")
    public void fromState1() {
        System.out.println("--- OnTransition --- fromUnlocked");
    }
}