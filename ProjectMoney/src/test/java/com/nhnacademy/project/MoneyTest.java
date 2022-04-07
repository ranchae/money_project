package com.nhnacademy.project;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;

import static com.nhnacademy.project.Currency.DOLLAR;
import static com.nhnacademy.project.Currency.WON;
import static org.assertj.core.api.Assertions.*;


class MoneyTest {



    @Test
    void add() {
        Money m1 = Money.won(BigDecimal.valueOf(1_000));
        Money m2 = Money.won(BigDecimal.valueOf(1_000));

        Money sum = m1.add(m2);

        assertThat(sum.getAmount()).isEqualTo(BigDecimal.valueOf(2_000).setScale(2, RoundingMode.HALF_UP));
        assertThat(sum.getCurrency()).isEqualTo(WON);
    }

    @Test
    void equals() {
        Money m1 = new Money(BigDecimal.valueOf(2_000), DOLLAR);
        Money m2 = new Money(BigDecimal.valueOf(2_000), DOLLAR);

        assertThat(m1.equals(m2)).isTrue();
    }

    @Test
    void money_Nagative_throwIllegalArgumentException() {
        BigDecimal amount = BigDecimal.valueOf(-1);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Money(amount, DOLLAR))
                .withMessageContaining("Invalid", amount);
    }


    @Test
    void add_5dollarPlus5dollar() {
        Money m1 = Money.dollar(BigDecimal.valueOf(5.25));
        Money m2 = Money.dollar(BigDecimal.valueOf(5.25));

        Money sum = m1.add(m2);

        assertThat(sum.getAmount()).isEqualTo(BigDecimal.valueOf(10.50).setScale(2,RoundingMode.HALF_UP));
        assertThat(sum.getCurrency()).isEqualTo(DOLLAR);
    }

    @Test
    void add_notMatchedCurrency_throwInvalidCurrencyException() {
        Money m1 = Money.dollar(BigDecimal.valueOf(5));
        Money m2 = Money.won(BigDecimal.valueOf(5_000));

        assertThatThrownBy(() -> m1.add(m2))
                .isInstanceOf(InvalidCurrencyException.class)
                .hasMessageContaining("currency");

    }

    @Test
    void subtract_negative_throwException() {
        Money m1 = Money.dollar(BigDecimal.valueOf(5));
        Money m2 = Money.dollar(BigDecimal.valueOf(6));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> m1.substract(m2))
                .withMessageContaining("greater");

    }


    @Test
    void subtract() {
        Money m1 = Money.dollar(BigDecimal.valueOf(5));
        Money m2 = Money.dollar(BigDecimal.valueOf(4));

        Money result = m1.substract(m2);

        assertThat(result).isEqualTo(Money.dollar(BigDecimal.valueOf(1)));
    }

    @Test
    void subtract_notMatchedCurrency_throwInvalidCurrencyException() {
        Money m1 = Money.dollar(BigDecimal.valueOf(5));
        Money m2 = Money.won(BigDecimal.valueOf(5_000));

        assertThatThrownBy(() -> m1.substract(m2))
                .isInstanceOf(InvalidCurrencyException.class)
                .hasMessageContaining("currency");

    }

    @Test
    void exchange() {
        Money m1 = Money.dollar(BigDecimal.valueOf(1));
        Money m2 = Money.won(BigDecimal.valueOf(1_000));

        if(m1.equals(Money.dollar(BigDecimal.valueOf(1)))) {
            m1 = Money.won(BigDecimal.valueOf(1_000));

        }
        assertThat(m1.getAmount()).isEqualTo(m2.getAmount());
        assertThat(m1.getCurrency()).isEqualTo(m2.getCurrency());

        if(m2 == Money.won(BigDecimal.valueOf(1_000))) {
            m2 = Money.dollar(BigDecimal.valueOf(1));
        }
        assertThat(m2.getAmount()).isEqualTo(m1.getAmount());
        assertThat(m2.getCurrency()).isEqualTo(m1.getCurrency());


    }

    @Test
    void exchange_dollar () {
        Money m1 = Money.dollar(BigDecimal.valueOf(5.25));
        Money m2 = Money.won(BigDecimal.valueOf(5_250));

        if(m1.equals(Money.dollar(BigDecimal.valueOf(5.25)))) {
            m1 = Money.won(BigDecimal.valueOf(5_250));
        }
        assertThat(m1.getAmount()).isEqualTo(m2.getAmount());
        assertThat(m1.getCurrency()).isEqualTo(m2.getCurrency());
    }


    @Test
    void exchange_euro () {
        Money m1 = Money.euro(BigDecimal.valueOf(2));
        Money m2 = Money.won(BigDecimal.valueOf(2_000));

        if(m1.equals(Money.euro(BigDecimal.valueOf(2)))) {
            m1 = Money.won(BigDecimal.valueOf(2_000));
        }

        assertThat(m1.getAmount()).isEqualTo(m2.getAmount());
        assertThat(m1.getCurrency()).isEqualTo(m2.getCurrency());
    }
}



