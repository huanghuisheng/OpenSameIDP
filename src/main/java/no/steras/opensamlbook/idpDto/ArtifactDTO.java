package no.steras.opensamlbook.idpDto;

public class ArtifactDTO {

    private Long userId;
    private Integer spIssuerId;
    private String  name;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSpIssuerId() {
        return spIssuerId;
    }

    public void setSpIssuerId(Integer spIssuerId) {
        this.spIssuerId = spIssuerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
