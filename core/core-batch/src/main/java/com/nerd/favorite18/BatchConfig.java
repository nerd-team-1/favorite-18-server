package com.nerd.favorite18;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class BatchConfig {

    @Bean
    public Job rankJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws DuplicateJobException {
        return new JobBuilder("rankJob", jobRepository)
                .start(rankStep(jobRepository, transactionManager))
                .build();
    }

    public Step rankStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("rankStep",jobRepository)
                .tasklet(rankTasklet(),transactionManager)
                .build();
    }

    public Tasklet rankTasklet(){
        return ((contribution, chunkContext) -> {
            System.out.println("***** hello batch! *****");
            // 원하는 비지니스 로직 작성
            return RepeatStatus.FINISHED;
        });
    }
}