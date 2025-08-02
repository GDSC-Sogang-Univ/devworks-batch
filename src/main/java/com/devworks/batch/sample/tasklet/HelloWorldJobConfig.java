package com.devworks.batch.sample.tasklet;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Hello World 출력하는 Job
 */
@Configuration
@RequiredArgsConstructor
public class HelloWorldJobConfig {

    private final JobRepository jobRepository; // 배치 작업의 메타데이터(ex. 작업 실행 상태, 각 Step의 실행 결과 등)을 저장 및 추적
    private final PlatformTransactionManager transactionManager; // 트랜잭션을 관리, 롤백 및 커밋 처리

    @Bean
    public Job helloWorldJob(Step helloWorldStep) {
        return new JobBuilder("helloWorldJob", jobRepository)
            .incrementer(new RunIdIncrementer()) // 주석 처리하고 실행해보면 ?
            .start(helloWorldStep)
            .build();
    }

    @Bean
    public Step helloWorldStep(Tasklet helloWorldTasklet) {

        return new StepBuilder("helloWorldStep", jobRepository)
            .tasklet(helloWorldTasklet, transactionManager)
            .build();

    }

    @Bean
    public Tasklet helloWorldTasklet() {
        return ((contribution, chunkContext) -> {
            System.out.println("Hello World");
            return RepeatStatus.FINISHED;
        });
    }
}