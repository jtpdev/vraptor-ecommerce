package br.com.lucy.vos;

public interface VO<M> {

	/**
	 * To get the model By VO
	 * 
	 * @return model
	 */
	M toModel();

	/**
	 * To construct a VO by model
	 * 
	 * @param model
	 */
	void toVO(M model);

}
