package com.dev.productioncenter.entity;

/**
 * @project Production Center
 * @author YanaV
 * The type Activity.
 */
public class Activity extends AbstractEntity {
    private String category;
    private String type;

    /**
     * Instantiates a new Activity.
     */
    public Activity() {

    }

    /**
     * Instantiates a new Activity.
     *
     * @param category the category
     * @param type     the type
     */
    public Activity(String category, String type) {
        this.category = category;
        this.type = type;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
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
        Activity activity = (Activity) o;
        if (category != null ? !category.equals(activity.category) : activity.category != null) {
            return false;
        }
        return type != null ? type.equals(activity.type) : activity.type == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + (category != null ? category.hashCode() : 0);
        result = result * 31 + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("category='").append(category);
        sb.append("', type='").append(type).append("'}");
        return sb.toString();
    }

    /**
     * The type Activity builder.
     */
    public static class ActivityBuilder {
        private final Activity activity;

        /**
         * Instantiates a new Activity builder.
         */
        public ActivityBuilder() {
            activity = new Activity();
        }

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public ActivityBuilder setId(long id) {
            activity.setId(id);
            return this;
        }

        /**
         * Sets category.
         *
         * @param category the category
         * @return the category
         */
        public ActivityBuilder setCategory(String category) {
            activity.category = category;
            return this;
        }

        /**
         * Sets type.
         *
         * @param type the type
         * @return the type
         */
        public ActivityBuilder setType(String type) {
            activity.type = type;
            return this;
        }

        /**
         * Build activity.
         *
         * @return the activity
         */
        public Activity build() {
            return activity;
        }
    }
}
