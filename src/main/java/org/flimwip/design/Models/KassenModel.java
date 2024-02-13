package org.flimwip.design.Models;

public class KassenModel {
    private String nl;
    private String nl_name;
    private String region;
    private boolean mobil;
    private String checkout_id;
    private String version;


    public KassenModel(String nl, String nl_name, String region, boolean mobil, String checkout_id, String version) {
        this.nl = nl;
        this.nl_name = nl_name;
        this.region = region;
        this.mobil = mobil;
        this.checkout_id = checkout_id;
        this.version = version;
    }

    public String getNl() {
        return nl;
    }

    public String getNl_name() {
        return nl_name;
    }

    public String getRegion() {
        return region;
    }

    public boolean isMobil() {
        return mobil;
    }

    public String getCheckout_id() {
        return checkout_id;
    }

    public String getVersion() {
        return version;
    }
}
