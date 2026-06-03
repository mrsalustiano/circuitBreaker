package com.mrsalustiano.publication.resilience;

public final class ResilienceMessages {

    public static final String COMMENTS_RECOVERY_FAILED =
            "Não foi possivel recuperar os comentarios";

    public static final String COMMENTS_UNAVAILABLE =
            "Nao foi possivel completar a chamada. Favor tentar mais tarde";

    public static final String COMMENTS_CIRCUIT_OPEN =
            "Circuit breaker OPEN - " + COMMENTS_UNAVAILABLE;

    private ResilienceMessages() {
    }
}
