package com.gateway.queue;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class QueueConfig {
    @Bean
    public TaskExecutor taskExec() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public CommandLineRunner schedulingRunner(TaskExecutor executor, Queue queue) {
        return (f)-> executor.execute(queue);
    }

    @Bean
    @Scope("singleton")
    public Queue queue() {
        return new Queue();
    }
}
