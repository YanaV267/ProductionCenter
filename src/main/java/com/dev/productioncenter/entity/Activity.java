package com.dev.productioncenter.entity;

public class Activity extends AbstractEntity {
    private String category;
    private String type;

    public Activity() {

    }

    public Activity(String category, String type) {
        this.category = category;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

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

    public static class ActivityBuilder {
        private final Activity activity;

        public ActivityBuilder() {
            activity = new Activity();
        }

        public ActivityBuilder setId(long id) {
            activity.setId(id);
            return this;
        }

        public ActivityBuilder setCategory(String category) {
            activity.category = category;
            return this;
        }

        public ActivityBuilder setType(String type) {
            activity.type = type;
            return this;
        }

        public Activity build() {
            return activity;
        }
    }
}
