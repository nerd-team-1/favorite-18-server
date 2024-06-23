package com.nerd.favorite18.service;

import com.nerd.favorite18.core.api.ranking.service.RankService;
import com.nerd.favorite18.storage.db.core.ranking.entity.Rank;
import com.nerd.favorite18.storage.db.core.ranking.repository.RankRepository;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class) // junit4의 RunWith
@SpringBootTest
//@Transactional
public class RankServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    RankRepository rankRepository;
    @Autowired
    RankService rankService;
    @Test
    public void 랭킹조회() throws Exception {
            //given
            Rank rank = createRank();
            LocalDateTime today = LocalDateTime.now(Clock.systemDefaultZone());
            //when
            rankService.getRankAll(today, "TJ");
            //then

    }
    @Test
    private Rank createRank() {
        Rank rank = Rank.builder()
                .rankSong(Song.builder()
                        .title("아마겟돈")
                        .artist("에스파")
                        .build())
                .rankDate(LocalDateTime.now(Clock.systemDefaultZone()))
                .searchCnt(100L)
                .build();
        return rank;
    }
}
