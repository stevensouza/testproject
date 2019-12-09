package com.stevesouza.misc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StatusTest {
    @Test
    public void testStatus() {
        assertThat(Status.ACCEPTED.name()).isEqualTo("ACCEPTED");
        assertThat(Status.ACCEPTED.id()).isEqualTo("A");
        assertThat(Status.ACCEPTED.toString()).isEqualTo("ACCEPTED");
        assertThat(Status.toStatus("ACCEPTED")).isEqualTo(Status.ACCEPTED);
        assertThat(Status.toStatus("accepted")).isEqualTo(Status.ACCEPTED);
        assertThat(Status.toStatus("accepted").id()).isEqualTo("A");
        assertThat(Status.contains("ACCEPTED")).isTrue();
        assertThat(Status.contains("I_DO_NOT_EXIST")).isFalse();
    }
}
