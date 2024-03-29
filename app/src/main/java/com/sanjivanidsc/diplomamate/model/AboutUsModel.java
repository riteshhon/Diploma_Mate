package com.sanjivanidsc.diplomamate.model;

public class AboutUsModel {

    String id, imageUrl, name, describeMember, linkedInUrl, twitterUrl, githubUrl;

    public AboutUsModel() {

    }

    public AboutUsModel(String id, String imageUrl, String name, String describeMember, String linkedInUrl, String twitterUrl, String githubUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.describeMember = describeMember;
        this.linkedInUrl = linkedInUrl;
        this.twitterUrl = twitterUrl;
        this.githubUrl = githubUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribeMember() {
        return describeMember;
    }

    public void setDescribeMember(String describeMember) {
        this.describeMember = describeMember;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }
}
