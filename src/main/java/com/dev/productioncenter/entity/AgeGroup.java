package com.dev.productioncenter.entity;

public class AgeGroup extends AbstractEntity {
    private int minAge;
    private int maxAge;

    public AgeGroup() {

    }

    public AgeGroup(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

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

    public static class AgeGroupBuilder {
        private final AgeGroup ageGroup;

        public AgeGroupBuilder() {
            ageGroup = new AgeGroup();
        }

        public AgeGroupBuilder setMinAge(int minAge) {
            ageGroup.minAge = minAge;
            return this;
        }

        public AgeGroupBuilder setMaxAge(int maxAge) {
            ageGroup.maxAge = maxAge;
            return this;
        }

        public AgeGroup build() {
            return ageGroup;
        }
    }
}
