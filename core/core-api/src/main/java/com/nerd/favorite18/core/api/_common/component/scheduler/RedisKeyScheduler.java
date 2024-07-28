package com.nerd.favorite18.core.api._common.component.scheduler;

import com.nerd.favorite18.core.api.ranking.service.RankRedisServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisKeyScheduler {
    private final RankRedisServiceV2 rankRedisServiceV2;

    @Scheduled(cron = "0 0 8 * * *") // 매일 08시에 실행됩니다.
    public void deleteSongKeys() {
        rankRedisServiceV2.deleteSong();
        rankRedisServiceV2.deleteSongSearchCount();
    }
}
