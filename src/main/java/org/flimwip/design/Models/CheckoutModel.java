package org.flimwip.design.Models;

public record CheckoutModel(String branch,
                            String branch_name,
                            String region,
                            boolean mobil,
                            String checkout_id,
                            String version) {
}
