package pl.spring.demo.to;

public class AuthorTo implements IdAware {
	private Long id;
	private String firstName;
	private String lastName;
	
    public AuthorTo() {
    }
    
    public AuthorTo(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

	public void setId(Long id){
		this.id = id;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
    @Override
	public Long getId(){
		return this.id;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
}
