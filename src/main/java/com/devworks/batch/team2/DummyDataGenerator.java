package com.devworks.batch.team2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DummyDataGenerator implements ApplicationRunner {

    private final UserRepository userRepository;
    private final SalesLogRepository salesLogRepository;

    private static final int USER_COUNT = 100_000;
    private static final Random random = new Random();
    private static final LocalDate START_DATE = LocalDate.of(2025, 7, 1);
    private static final LocalDate END_DATE = LocalDate.of(2025, 7, 31);

    @Override
    public void run(ApplicationArguments args) {
        List<User> users = new ArrayList<>();
        List<SalesLog> salesLogs = new ArrayList<>();

        for (int i = 1; i <= USER_COUNT; i++) {
            User user = new User(Rank.NORMAL);
            users.add(user);
        }

        userRepository.saveAll(users);

        // 저장된 유저 기준으로 거래 데이터 생성
        for (User user : users) {
            int transactionCount = 1 + random.nextInt(5); // 1~5건
            for (int j = 0; j < transactionCount; j++) {
                LocalDate date = getRandomDate();
                int amount = 1_000 + random.nextInt(1_000_000);

                // buyer/seller 동일한 사용자
                SalesLog log = new SalesLog(user, user, date, amount);
                salesLogs.add(log);
            }
        }

        salesLogRepository.saveAll(salesLogs);
    }

    private LocalDate getRandomDate() {
        int days = (int) ChronoUnit.DAYS.between(START_DATE, END_DATE);
        return START_DATE.plusDays(random.nextInt(days + 1));
    }
}
