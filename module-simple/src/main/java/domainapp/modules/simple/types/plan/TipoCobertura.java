package domainapp.modules.simple.types.plan;

public enum TipoCobertura {
    PLAN5000("60% en Farmacia", 0),
    PLAN3000("50% en Farmacia", 850),
    PLAN100("30% en Farmacia,", 1500);

    private final String farmacia;
    private final int coseguro;

    TipoCobertura(String farmacia, int coseguro) {
        this.farmacia = farmacia;
        this.coseguro = coseguro;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public int getCoseguro() {
        return coseguro;
    }
}