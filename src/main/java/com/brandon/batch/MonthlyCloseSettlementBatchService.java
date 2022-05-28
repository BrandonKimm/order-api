package com.brandon.batch;

import com.brandon.domain.Settlement;
import com.brandon.repository.SettlementCustomRepository;
import com.brandon.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MonthlyCloseSettlementBatchService {

    private final SettlementRepository settlementRepository;
    private final SettlementCustomRepository settlementCustomRepository;

    //매달 1일 03시에 실행
    //@Scheduled(cron = "0 0 0 3 * *")
    public void updateFirstProductPage() {

        LocalDateTime start = LocalDate.now().minusDays(1).withDayOfMonth(1).atTime(0,0,0,0);
        LocalDateTime end = LocalDate.now().minusDays(1).atTime(23,59,59,1000000000-1);

        settlementCustomRepository.settlement(start,end)
                .stream()
                .map(dto -> new Settlement(dto.getMemberId(),
                        dto.getMemberName(),
                        dto.getOrderDate(),
                        dto.getVendorId(),
                        dto.getVendorName(),
                        dto.getCategoryId(),
                        dto.getCategoryName(),
                        dto.getProductOrderQuantity(),
                        dto.getProductOrderPrice()))
                .forEach(settlement -> settlementRepository.save(settlement));

    }

}
