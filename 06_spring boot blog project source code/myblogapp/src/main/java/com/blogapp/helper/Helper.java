package com.blogapp.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.jsoup.Jsoup;
import org.springframework.web.multipart.MultipartFile;

public class Helper {

	public static String getUrlFromTitle(String postTitle) {
		String title = postTitle.trim().toLowerCase();

		String url = title.replaceAll("\\s", "-");
		url = url.replaceAll("[^A-Za-z0-9]", "-");
		return url;
	}

	// to Save File
	public static boolean saveFile(String uploadDir, String fileName, MultipartFile multipartFile) {

		Path uplaodPath = Paths.get(uploadDir);

		try (InputStream inpitstream = multipartFile.getInputStream()) {

			if (!Files.exists(uplaodPath)) {
				Files.createDirectories(uplaodPath);
			}

			// represents the full path with root direct + sub directory + image name
			Path fullPath = uplaodPath.resolve(fileName);// postfiles/1/sanket.png

			Files.copy(inpitstream, fullPath, StandardCopyOption.REPLACE_EXISTING);
			return true;

		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}

	}
	
	
	public static String htmlSanitize(String shortDesc)
	{
		return Jsoup.parse(shortDesc).text();
		
	}
	
	
	

}
