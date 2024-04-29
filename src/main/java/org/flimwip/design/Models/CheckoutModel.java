package org.flimwip.design.Models;

public record CheckoutModel(
        String branch,
        String branch_name,
        String region,
        String checkout_id,

        String betriebsstelle,

        String hostname,

        String ip,

        String modell,

        String os
) {
}
