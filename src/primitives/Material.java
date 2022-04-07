package primitives;

/**
 * Material class represents the material from which a geometric body is made
 */
public class Material {
    /**
     * Factor of attenuation of the material
     */
    public Double3 kD = Double3.ZERO;
    /**
     * Factor of attenuation of the material
     */
    public Double3 kS = Double3.ZERO;
    /**
     * The size of shininess of the material
     */
    public int nShininess = 0;

    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
