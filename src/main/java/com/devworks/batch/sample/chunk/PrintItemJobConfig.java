package com.devworks.batch.sample.chunk;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 홀수만 출력하는 Job
 */
@Configuration
@RequiredArgsConstructor
public class PrintItemJobConfig {

    private final JobRepository jobRepository; // 배치 작업의 메타데이터(ex. 작업 실행 상태, 각 Step의 실행 결과 등)을 저장 및 추적
    private final PlatformTransactionManager transactionManager; // 트랜잭션을 관리, 롤백 및 커밋 처리

    @Bean
    public Job printItemJob(Step printItemStep) {
        return new JobBuilder("printItemJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(printItemStep)
            .build();
    }

    @Bean
    public Step printItemStep() {
        return new StepBuilder("printItemStep", jobRepository)
            .<Long, Long>chunk(10, transactionManager)
            .reader(itemReader())
            .processor(itemProcessor())
            .writer(itemWriter())
            .build();
    }

    private ItemReader<Long> itemReader() {
        return new ListItemReader<>(getItems());
    }

    private ItemProcessor<Long, Long> itemProcessor() {
        return item -> {
            if (item % 2 == 1) {
                return null;
            }
            return item;
        };
    }

    private ItemWriter<Long> itemWriter() {
        return items -> {
            items.forEach(item -> System.out.println("Writer item : " + item));
        };
    }

    private List<Long> getItems() {
        List<Long> list = new ArrayList<>();
        for (long i = 1; i <= 1100; i++) {
            list.add(i);
        }
        return list;
    }
}
