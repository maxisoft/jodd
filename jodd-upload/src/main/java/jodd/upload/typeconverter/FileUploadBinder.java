// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.upload.typeconverter;

import jodd.typeconverter.TypeConverterManager;
import jodd.typeconverter.TypeConverterManagerBean;
import jodd.typeconverter.impl.FileConverter;
import jodd.upload.FileUpload;

import java.io.File;

/**
 * Binder with the <code>jodd-bean</code> module.
 */
public class FileUploadBinder {

	/**
	 * Registers type converters.
	 */
	public static void registerTypeConverter() {
		TypeConverterManagerBean typeConverterManagerBean = TypeConverterManager.getDefaultTypeConverterManager();

		typeConverterManagerBean.register(FileUpload.class, new FileUploadConverter());

		FileConverter fileConverter = (FileConverter) typeConverterManagerBean.lookup(File.class);

		fileConverter.registerAddonConverter(new FileUploadToFileTypeConverter());
	}
}