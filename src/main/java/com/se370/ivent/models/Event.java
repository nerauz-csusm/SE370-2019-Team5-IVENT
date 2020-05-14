package com.se370.ivent.models;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Collection;
import java.util.Map;

@Document(collection = "Event")
public class Event {
    @MongoId
    private String id;
    private String title;
    private String organizations;
    private String description;
    private float minPrice;
    private float maxPrice;
    private String beginDate;
    private String endDate;
    private String image;
    private String creatorId;
    private Collection<String> joinedUsers;

    public Collection<String> getJoinedUsers() {
        return joinedUsers;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setJoinedUsers(Collection<String> joinedUsers) {
        this.joinedUsers = joinedUsers;
    }

    public void setJoinedUsers(String joinedUser) {
        this.joinedUsers.add(joinedUser);
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganizations() {
        return organizations;
    }

    public void getOrganizations(String organizations) {
        this.organizations = organizations;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", title=" + title + ", organizations=" + organizations + "]";
    }

    private void add(String key, String value) {
        switch (key) {
            case "title":
                this.title = value;
                break;
            case "organizator":
                this.organizations = value;
                break;
            case "description":
                this.description = value;
                break;
            case "minPrice":
                this.minPrice = Float.parseFloat(value);
                break;
            case "maxPrice":
                this.maxPrice = Float.parseFloat(value);
                break;
            case "beginDate":
                this.beginDate = value;
                break;
            case "endDate":
                this.endDate = value;
                break;
            case "creatorId":
                this.creatorId = value;
                break;

        }
    }

    public void fromJSON(Map<String, String> json) {
        for (Map.Entry<String,String> elem : json.entrySet()) {
            add(elem.getKey(), elem.getValue());
        }
    }
}
