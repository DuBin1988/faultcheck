package com.af.harddisk;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;

import junit.framework.TestCase;

public class TestDiskSpace extends TestCase {

	/**
	 * ºÏ≤‚¥≈≈Ãø’º‰
	 */
	public void testOne() throws Exception {
		File[] list = File.listRoots();
		for (File f : list) {
			System.out.println(f.getAbsolutePath());
			System.out.print("◊‹¥Û–°" + FormetFileSize(f.getTotalSpace()));
			System.out.println(" £”‡" + FormetFileSize(f.getFreeSpace()));
		}
	}

	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		fileSizeString = df.format((double) fileS / 1073741824) + "G";
	  	return fileSizeString;
	}

	
}
