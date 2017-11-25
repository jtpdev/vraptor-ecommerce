package br.com.lucy.models;

public enum EStatus {
	
	PENDING("Pending"),
	PAID("Paid"),
	FINISH("Finish");
	
	private String description;

	private EStatus(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return description;
	}

}
