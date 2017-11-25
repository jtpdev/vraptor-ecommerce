package br.com.lucy.controllers;

import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.lucy.models.Product;
import br.com.lucy.utils.ImageUtil;

public class ImagesController {
	;
	private final ImageUtil image;
	private final Result result;

	public ImagesController(ImageUtil image, Result result) {
		this.image = image;
		this.result = result;
	}

	@Post("/products/{product.id}/image")
	public void upload(Product product, final UploadedFile image) {
		this.image.save(image, product.getId());
	}
}