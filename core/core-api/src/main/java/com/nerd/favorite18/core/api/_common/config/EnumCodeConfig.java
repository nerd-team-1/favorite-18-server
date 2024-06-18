package com.nerd.favorite18.core.api._common.config;

import com.nerd.favorite18.core.enums.common.EnumMapper;
import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import com.nerd.favorite18.core.enums.song.MachineType;
import com.nerd.favorite18.core.enums.user.UserGender;
import com.nerd.favorite18.core.enums.user.UserRole;
import com.nerd.favorite18.core.enums.user.UserStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumCodeConfig {
    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();

        // QNA
        enumMapper.put(AnswerStatus.class);
        enumMapper.put(QnaProgressStatus.class);

        // SONG
        enumMapper.put(MachineType.class);

        // User
        enumMapper.put(UserGender.class);
        enumMapper.put(UserRole.class);
        enumMapper.put(UserStatus.class);

        return enumMapper;
    }

}
