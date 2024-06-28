package com.nerd.favorite18.job.rank;

import com.nerd.favorite18.storage.db.core.ranking.entity.Rank;
import com.nerd.favorite18.storage.db.core.ranking.repository.RankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class RankTasklet implements Tasklet {

    private final RankRepository rankRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // 미완성
        return RepeatStatus.FINISHED;
    }
}
