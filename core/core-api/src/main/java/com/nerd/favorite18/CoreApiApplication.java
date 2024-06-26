package com.nerd.favorite18;

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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@ConfigurationPropertiesScan
@SpringBootApplication
public class CoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApiApplication.class, args);
    }

    @Bean
    @Profile("local")
    @Transactional
    public CommandLineRunner dataLoader(UserRepository userRepository, QnaRepository qnaRepository) {
        return args -> {
            // 초기 유저 데이터 추가
            User adminUser1 = User.builder()
                    .subId("google_104727441863579907650")
                    .email("epikeran@gmail.com")
                    .name("박중언")
                    .gender(UserGender.MAN)
                    .thumbnail("https://lh3.googleusercontent.com/a/ACg8ocLxQbFPRICseQzEjPz5xiYa4qyb8vzt6uYd3TmEXSsSYFmGPA=s96-c")
                    .role(UserRole.ADMIN)
                    .status(UserStatus.ACTIVE)
                    .build();

            userRepository.save(adminUser1);

            User user1 = User.builder()
                    .subId("google_101313027458036775900")
                    .email("devjake929@gmail.com")
                    .name("Jake Park")
                    .gender(UserGender.MAN)
                    .thumbnail("https://lh3.googleusercontent.com/a/ACg8ocKDjcOE64GmRwovwXDRyJYiHLdG9Lon28CdmmE49NNF19uB=s96-c")
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
