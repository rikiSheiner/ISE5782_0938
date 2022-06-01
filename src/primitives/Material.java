package primitives;

/**
 * Material class represents the material from which a geometric body is made
 * @author Rivka Sheiner
 */
public class Material {
    /**
     * Diffusional factor of attenuation of the material
     */
    public Double3 kD = Double3.ZERO;
    /**
     * Specular factor of attenuation of the material
     */
    public Double3 kS = Double3.ZERO;
    /**
     * Transparent factor of attenuation of the material
     */
    public Double3 kT = Double3.ZERO;
    /**
     * Reflected factor of attenuation of the material
     */
    public Double3 kR = Double3.ZERO;
    /**
     * The size of shininess of the material
     */
    public int nShininess = 0;

    /**
     * This method is used for updating the diffusional factor of attenuation
     * @param kD - the updated point which represents the diffusional factor of attenuation
     * @return Material
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * This method is used for updating the specular factor of attenuation
     * @param kS - the updated point which represents the specular factor of attenuation
     * @return Material
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * This method is used for updating the diffusional factor of attenuation
     * @param kD - the updated diffusional factor of attenuation
     * @return Material
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * This method is used for updating the specular factor of attenuation
     * @param kS - the updated specular factor of attenuation
     * @return Material
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * This method is used for updating the transparent factor of attenuation
     * @param kT - the updated transparent factor of attenuation
     * @return Material
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * This method is used for updating the reflected factor of attenuation
     * @param kR - the updated reflected factor of attenuation
     * @return Material
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * This method is used for updating the shininess of the material
     * @param nShininess - the updated size of shininess of the material
     * @return Material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

}
