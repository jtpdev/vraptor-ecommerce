package br.com.lucy.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;

import br.com.caelum.vraptor.observer.upload.UploadedFile;

@RequestScoped
public class ImageUtil {

	private File path;

	public ImageUtil(ServletContext context) {
		String imageDirectory = context.getRealPath("/WEB-INF/image");
		path = new File(imageDirectory);
		path.mkdir();
	}

	public void save(UploadedFile image, Long productId) {
		File destino = new File(path, productId + ".jpeg");
		try {
			IOUtils.copy(image.getFile(), new FileOutputStream(destino));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
