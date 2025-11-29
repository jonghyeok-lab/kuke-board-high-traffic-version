package kuke.board.article.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageCalculatorTest {

    @Test
    void calculatePageLimit() {
        calculatePageLimit(1L, 30L, 10L, 301L);
        calculatePageLimit(10L, 30L, 10L, 301L);
        calculatePageLimit(11L, 30L, 10L, 601L);
        calculatePageLimit(17L, 30L, 10L, 601L);
        calculatePageLimit(20L, 30L, 10L, 601L);
    }

    private void calculatePageLimit(Long page, Long limit, Long movablePageSize, Long expected) {
        Long result = PageCalculator.calculatePageLimit(page, limit, movablePageSize);
        assertThat(result).isEqualTo(expected);
    }
}