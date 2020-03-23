public class Profile {
    enum Post {
        ADMIN, STUDENT
    }

    private Post post;
    private String name;
    private String surname;
    private String birth;

    public Profile(String surname, String name, String birth, Post post) {
        this.surname = surname;
        this.name = name;
        this.birth = birth;
        this.post = post;
    }

    public String getBirth() {
        return birth;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
