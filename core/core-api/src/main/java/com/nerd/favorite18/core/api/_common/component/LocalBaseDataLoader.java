package com.nerd.favorite18.core.api._common.component;

import com.nerd.favorite18.core.api._common.config.AppPropertiesConfig;
import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import com.nerd.favorite18.core.enums.user.UserGender;
import com.nerd.favorite18.core.enums.user.UserRole;
import com.nerd.favorite18.core.enums.user.UserStatus;
import com.nerd.favorite18.storage.db.core.qna.entity.Qna;
import com.nerd.favorite18.storage.db.core.qna.repository.QnaRepository;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import com.nerd.favorite18.storage.db.core.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LocalBaseDataLoader {

    @Bean
    @Profile("local")
    @Transactional
    public CommandLineRunner dataLoader(AppPropertiesConfig appPropertiesConfig, UserRepository userRepository, QnaRepository qnaRepository) {

        return args -> {
            // 초기 유저 데이터 추가
            AppPropertiesConfig.Admin admin = appPropertiesConfig.getAdmin();
            AppPropertiesConfig.User user = appPropertiesConfig.getUser();

            User adminUser1 = User.builder()
                    .subId(admin.getSubId())
                    .email(admin.getEmail())
                    .name("관리자")
                    .gender(UserGender.MAN)
                    .thumbnail("https://lh3.googleusercontent.com/")
                    .role(UserRole.ADMIN)
                    .status(UserStatus.ACTIVE)
                    .build();

            userRepository.save(adminUser1);

            User user1 = User.builder()
                    .subId(user.getSubId())
                    .email(user.getEmail())
                    .name("사용자")
                    .gender(UserGender.MAN)
                    .thumbnail("https://lh3.googleusercontent.com/")
                    .role(UserRole.USER)
                    .status(UserStatus.ACTIVE)
                    .build();

            userRepository.save(user1);

            Qna qna1 = Qna.builder()
                    .qnaUser(user1)
                    .title("문의문의 나문의")
                    .content("호박고구마")
                    .progressStatus(QnaProgressStatus.UNPROCESSED)
                    .answerStatus(AnswerStatus.NO_REPLY)
                    .build();

            Qna qna2 = Qna.builder()
                    .qnaUser(user1)
                    .title("문의문의 나문의2")
                    .content("호박고구마!!")
                    .progressStatus(QnaProgressStatus.UNPROCESSED)
                    .answerStatus(AnswerStatus.NO_REPLY)
                    .build();

            Qna qna3 = Qna.builder()
                    .qnaUser(user1)
                    .title("문의문의 나문의3")
                    .content("호박고구마!!!")
                    .progressStatus(QnaProgressStatus.UNPROCESSED)
                    .answerStatus(AnswerStatus.NO_REPLY)
                    .build();

            qnaRepository.save(qna1);
            qnaRepository.save(qna2);
            qnaRepository.save(qna3);
        };
    }
}
