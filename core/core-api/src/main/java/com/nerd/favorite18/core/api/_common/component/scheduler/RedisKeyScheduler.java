package com.nerd.favorite18.core.api._common.component.scheduler;

import com.nerd.favorite18.core.api.ranking.business.RankRedisBusinessV2;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisKeyScheduler {
    private final RankRedisBusinessV2 rankRedisBusinessV2;

    @Scheduled(cron = "0 0 8 * * *") // 매일 08시에 실행됩니다.
    public void deleteSongKeys() {
        rankRedisBusinessV2.deleteKeys();
    }
}
