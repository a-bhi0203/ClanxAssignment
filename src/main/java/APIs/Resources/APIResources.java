package APIs.Resources;

public enum APIResources {
    login("/auth");
    private String resource;
    APIResources(String resource){
        this.resource = resource;
    }
    public String getResource(){
        return resource;
    }
}
