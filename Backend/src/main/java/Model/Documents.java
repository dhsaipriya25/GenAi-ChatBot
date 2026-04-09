package com.bot.loginAndRegisterationApp.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.bot.loginAndRegisterationApp.model.UserRegistration;


@Entity
@Table(name = "documents")
public class Documents {

    @Id
    @Column(name = "id")
    private String id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "document_names", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "name")
    private List<String> name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "document_types", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "type")
    private List<String> type;

    private String prompt;

    @Column(name = "application_name")
    private String applicationName;

    @Lob
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "document_data", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "data", columnDefinition = "LONGBLOB")
    private List<byte[]> data;

    private String url;
    private String fileLink;
    private String apiKey;
    private String apiProvider;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserRegistration user;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<byte[]> getData() {
        return data;
    }

    public void setData(List<byte[]> data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiProvider() {
        return apiProvider;
    }

    public void setApiProvider(String apiProvider) {
        this.apiProvider = apiProvider;
    }

    public UserRegistration getUser() {
        return user;
    }

    public void setUser(UserRegistration user) {
        this.user = user;
    }

    public Documents(String id, List<String> name, List<String> type, List<byte[]> data, String url, String fileLink,
            String apiKey, String apiProvider, UserRegistration user) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.data = data;
        this.url = url;
        this.fileLink = fileLink;
        this.apiKey = apiKey;
        this.apiProvider = apiProvider;
        this.user = user;
    }

    public Documents() {
        super();
    }
}
