package org.kang.assignment.domain.settlement;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false)
    private Long newbie;

    @Column(nullable = false)
    private Long bolter;

    @Column(nullable = false)
    private Long payment;

    @Column(nullable = false)
    private Long used;

    @Column(nullable = false)
    private Long sales;

    private Settlement(LocalDateTime time, Long newbie, Long bolter, Long payment, Long used, Long sales) {
        this.time = time;
        this.newbie = newbie;
        this.bolter = bolter;
        this.payment = payment;
        this.used = used;
        this.sales = sales;
    }

    public static Settlement of(String time, String newbie, String bolter, String payment, String used, String sales) {
        return new Settlement(
                LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH")),
                Long.parseLong(newbie.replace(",", "")),
                Long.parseLong(bolter.replace(",", "")),
                Long.parseLong(payment.replace(",", "")),
                Long.parseLong(used.replace(",", "")),
                Long.parseLong(sales.replace(",", ""))
        );
    }

    public static Settlement of(String time, Long newbie, Long bolter, Long payment, Long used, Long sales) {
        return new Settlement(
                LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHH")),
                newbie,
                bolter,
                payment,
                used,
                sales
        );
    }

}
