package org.example;

public class Task {
    private int id;
    private CreateFile.Status status;
    private  String description;
    private String createdAt;
    private String updateAt;
    public Task(int id, String description, CreateFile.Status status, String createdAt, String updateAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
    public  Task(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CreateFile.Status getStatus() {
        return status;
    }

    public void   setStatus(CreateFile.Status status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                '}'+'\n';
    }

    public String toJson() {
        return "{" +
                "\"id\":" + id +
                ", \"description\":\"" + description + "\"" +
                ", \"status\":\"" + status + "\"" +
                ", \"createdAt\":\"" + createdAt + "\"" +
                ", \"updateAt\":\"" + updateAt + "\"" +
                "}" ;
    }

}
