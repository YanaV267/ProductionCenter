package com.dev.productioncenter.entity;

/**
 * @author YanaV
 * The type Age group.
 * @project Production Center
 */
public class AgeGroup extends AbstractEntity implements Cloneable {
    private int minAge;
    private int maxAge;

    /**
     * Instantiates a new Age group.
     */
    public AgeGroup() {

    }

    /**
     * Instantiates a new Age group.
     *
     * @param minAge the min age
     * @param maxAge the max age
     */
    public AgeGroup(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    /**
     * Gets min age.
     *
     * @return the min age
     */
    public int getMinAge() {
        return minAge;
    }

    /**
     * Sets min age.
     *
     * @param minAge the min age
     */
    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    /**
     * Gets max age.
     *
     * @return the max age
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * Sets max age.
     *
     * @param maxAge the max age
     */
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o)) {
            return false;
        }
        AgeGroup ageGroup = (AgeGroup) o;
        return minAge == ageGroup.minAge && maxAge == ageGroup.maxAge;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + Integer.hashCode(minAge);
        result = result * 31 + Integer.hashCode(maxAge);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("minAge=").append(minAge);
        sb.append(", maxAge=").append(maxAge).append("}");
        return sb.toString();
    }

    @Override
    public AgeGroup clone() throws CloneNotSupportedException {
        return (AgeGroup) super.clone();
    }

    /**
     * The type Age group builder.
     */
    public static class AgeGroupBuilder {
        private final AgeGroup ageGroup;

        /**
         * Instantiates a new Age group builder.
         */
        public AgeGroupBuilder() {
            ageGroup = new AgeGroup();
        }

        /**
         * Sets min age.
         *
         * @param minAge the min age
         * @return the min age
         */
        public AgeGroupBuilder setMinAge(int minAge) {
            ageGroup.minAge = minAge;
            return this;
        }

        /**
         * Sets max age.
         *
         * @param maxAge the max age
         * @return the max age
         */
        public AgeGroupBuilder setMaxAge(int maxAge) {
            ageGroup.maxAge = maxAge;
            return this;
        }

        /**
         * Build age group.
         *
         * @return the age group
         */
        public AgeGroup build() {
            return ageGroup;
        }
    }
}
